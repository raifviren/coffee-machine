package coffeeMachine.machine;

import coffeeMachine.beverage.DrinkOption;
import coffeeMachine.beverage.DrinkRecipe;
import coffeeMachine.data.CoffeeMachineDto;
import coffeeMachine.data.MachineDto;
import coffeeMachine.drinker.CoffeeDrinker;
import coffeeMachine.drinker.CoffeeDrinkerJob;
import coffeeMachine.ingredients.Ingredient;
import coffeeMachine.storage.IngredientInventory;
import coffeeMachine.storage.RecipeRepository;
import coffeeMachine.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Singleton Class to simulate a CoffeeMachine
 * locks : collection of locks, one for every {@link CoffeeMachineOutlet}
 * orderNo : unique identifier for each order
 * outlets : list of all {@link CoffeeMachineOutlet} which coffee machine is made of
 * drinkOptions: list of all {@link DrinkOption} available in coffee machine
 * ingredientInventory: a {@link java.util.concurrent.ConcurrentHashMap} to store quantity of all {@link Ingredient}
 * currently present in the coffee machine
 * recipeRepository : a {@link HashMap} of all {@link DrinkOption} and their corresponding {@link DrinkRecipe}
 *
 * @author Virender Bhargav
 */
@Getter
@Setter
public final class CoffeeMachine {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoffeeMachine.class);

    private static CoffeeMachine INSTANCE;
    public static Object[] locks;
    private static AtomicInteger orderNo = new AtomicInteger();

    private List<CoffeeMachineOutlet> outlets = new ArrayList<CoffeeMachineOutlet>();
    private List<DrinkOption> drinkOptions = new ArrayList<DrinkOption>();
    private IngredientInventory ingredientInventory = new IngredientInventory();
    private RecipeRepository recipeRepository = new RecipeRepository();

    /**
     * makes class singleton in nature
     * will return CoffeeMachine.INSTANCE is it already exits else creates one
     * @return
     * @throws IOException
     */
    public static CoffeeMachine getInstance() throws IOException {
        if(INSTANCE == null) {
            File file = new File(CoffeeMachine.class.getClassLoader().getResource("data.json").getFile());
            String jsonStr = FileUtils.readFileToString(file, "UTF-8");
            INSTANCE = new CoffeeMachine(jsonStr);
        }
        return INSTANCE;
    }


    /**
     * private constructor, {@link CoffeeMachine} can only be instantiated from CoffeeMachine.getInstance
     * @param json
     * @throws IOException
     */
    private CoffeeMachine(String json) throws IOException {
        LOGGER.debug(json);
        orderNo.set(0);
        MachineDto coffeeMachine = JsonUtils.deserialize(json, MachineDto.class);
        CoffeeMachineDto machine = coffeeMachine.getMachine();
        //creating outlets
        int outletCount = machine.getOutlets().getCount_n();
        for (int i = 1; i <= outletCount; i++) {
            this.outlets.add(new CoffeeMachineOutlet(i, this));
        }
        //initialising locks array
        CoffeeMachine.locks = new Object[outletCount];
        for (int i = 0; i < outletCount; i++) {
            CoffeeMachine.locks[i] = new Object();
        }

        //filling up inventory
        for (Map.Entry<String, Integer> ingredientMap : machine.getTotal_items_quantity().entrySet()) {
            LOGGER.debug("Ingredient: {}  Quantity: {} ", ingredientMap.getKey(), ingredientMap.getValue());
            this.ingredientInventory.put(Ingredient.fromString(ingredientMap.getKey()), ingredientMap.getValue());
        }
        //adding recipes and drinkOptions
        final Map<String, Map<String, Integer>> beverages = machine.getBeverages();
        for (Map.Entry<String, Map<String, Integer>> beverageMap : beverages.entrySet()) {
            DrinkOption drinkOption = DrinkOption.fromString(beverageMap.getKey());
            this.drinkOptions.add(drinkOption);
            Map<String, Integer> beverageIngredientMap = beverageMap.getValue();
            Map<Ingredient,Integer> beverageIngredients = new HashMap<>();
            for (Map.Entry<String, Integer> ingredientMap : beverageIngredientMap.entrySet()) {
                LOGGER.debug("Beverage :{} Ingredient: {} Quantity:{}", beverageMap.getKey(), ingredientMap.getKey(),
                            ingredientMap.getValue());
                beverageIngredients.put(Ingredient.fromString(ingredientMap.getKey()), ingredientMap.getValue());
            }
            DrinkRecipe drinkRecipe = new DrinkRecipe(beverageIngredients);

            this.recipeRepository.put(drinkOption, drinkRecipe);
        }
    }

    public CoffeeMachineOutlet getOutletById(int outletId) {
        return this.outlets.get(outletId - 1);
    }

    public DrinkOption getDrinkById(int drinkId) {
        return this.drinkOptions.get(drinkId - 1);
    }

    /**
     * synchronized function to increment orderId
     */
    public static synchronized void incrementOrderId() {
        CoffeeMachine.orderNo.incrementAndGet();;
    }

    public static Integer getOrderId() {
        return CoffeeMachine.orderNo.get();
    }

    /**
     * function to take input from user in form of : <<OutletId> <DrinkId>> <DrinkId>
     * for every order starts
     * 1)A new thread of {@link CoffeeMakerJob} to brew drink corresponding given <DrinkId> on
     * given  <OutletId>
     * 2)A new thread of {@link CoffeeDrinkerJob} to consume prepared drink on given <OutletId>
     *
     */
    public void runMachine() {
        Scanner input = new Scanner(System.in);
        System.out.println("****SELECTION MENU****");
        System.out.print("Available Outlet Ids:");
        for (CoffeeMachineOutlet outlet : this.outlets) {
            System.out.print(outlet.getId() + " ");
        }
        System.out.println();
        int i = 1;
        System.out.println("Id\tDrink Name");
        for (DrinkOption drinkOption : this.drinkOptions) {
            System.out.println(i + ".\t" + drinkOption.name());
            i++;
        }

        while (true) {
            System.out.println("Please enter a selection or EXIT to quit: ");
            System.out.println("<OutletId> <DrinkId>");
            if (input.hasNextInt()) {
                int outletId = input.nextInt();
                int drinkId = input.nextInt();
                if (drinkId >0 && outletId  >0 && this.drinkOptions.size() >= drinkId  && this.outlets.size() >= outletId) {
                    //CoffeeMaker Thread to brew drink
                    Runnable coffeeMakerJob = new CoffeeMakerJob(this.getOutletById(outletId),
                                                              this.getDrinkById(drinkId));
                    new CoffeeMaker().makeCoffee(coffeeMakerJob);

                    //CoffeeDrinker Thread to consume drink
                    Runnable coffeeDrinkerJob = new CoffeeDrinkerJob(this.getOutletById(outletId));
                    new CoffeeDrinker().drinkCoffee(coffeeDrinkerJob);
                }
                else System.out.println("Enter a valid OutletId/DrinkId");
            }
            else {
                String command = input.next();
                if (command.equalsIgnoreCase("EXIT")) {
                    System.out.println("Goodbye ! Have a nice day");
                    System.exit(0);
                }
            }
        }
    }
}
