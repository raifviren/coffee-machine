package coffeeMachine.machine;

import coffeeMachine.beverage.Beverage;
import coffeeMachine.beverage.DrinkOption;
import coffeeMachine.beverage.DrinkRecipe;
import coffeeMachine.exceptions.InsufficientIngredientException;
import coffeeMachine.ingredients.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Runnable job to brew drink corresponding given <DrinkId> on given  <OutletId>
 *
 * @author Virender Bhargav
 */
@Getter
@Setter
@AllArgsConstructor
public class CoffeeMakerJob implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoffeeMakerJob.class);

    private CoffeeMachineOutlet outlet;
    private DrinkOption drinkOption;

    @Override
    public void run() {
        try {
            makeCoffee(this.drinkOption);
            TimeUnit.SECONDS.sleep(10);
        }
        catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }
    }


    /**
     * deducts quantity for all {@link Ingredient} of {@link DrinkOption} from
     * {@link coffeeMachine.storage.IngredientInventory}
     * of coffee machine
     *
     * @param drinkOption
     * @return
     * @throws InsufficientIngredientException
     * @throws InterruptedException
     */
    public Beverage makeDrink(DrinkOption drinkOption) throws InsufficientIngredientException, InterruptedException {
        Beverage result = new Beverage(drinkOption.getDisplayName());
//        System.out.println("Inventory check for drink: " + drinkOption.name()+ " successful..brewing time: "+
//        DRINK_PREPARATION_TIME+" secs");
        TimeUnit.SECONDS.sleep(Constants.DRINK_PREPARATION_TIME);
        DrinkRecipe recipe = this.outlet.getCoffeeMachine().getRecipeRepository().getDrinkRecipe(drinkOption);
        LOGGER.debug("Deducting from inventory for recipe:{}", recipe);
//        System.out.println("Checking inventory for drink: " + drinkOption.name());
        for (Map.Entry<Ingredient, Integer> requireIngredient : recipe.getIngredients().entrySet()) {
            LOGGER.debug("Ingredient:{}  deducting quantity :{}", requireIngredient.getKey(),
                         requireIngredient.getValue());
            this.outlet.getCoffeeMachine().getIngredientInventory().deduct(requireIngredient.getKey(),
                                                                           requireIngredient.getValue());
            result.getIngredients().add(requireIngredient.getKey());
        }
        this.outlet.getCoffeeMachine().getIngredientInventory().checkStock();
        return result;
    }

    /**
     * synchronised function to make a drink
     *
     * @param choice
     * @throws InterruptedException
     */
    private void makeCoffee(DrinkOption choice) throws InterruptedException {
        synchronized (CoffeeMachine.locks[this.outlet.getId() - 1]) {
            Beverage beverage = null;
            if (this.outlet.getOutletState() != OutletState.READY_TO_TAKE_ORDER) {
                CoffeeMachine.locks[this.outlet.getId() - 1].wait();
            }
            try {
                System.out.println("Order for " + choice.name() + " in process. Please wait while we prepare your " + "order." + " ETA: " + Constants.DRINK_PREPARATION_TIME + " secs");
                this.outlet.setOutletState(OutletState.BREWING_DRINK);

                LOGGER.debug("making drink: {} outletId:{} orderId:{}", choice.name(), this.outlet.getId(),
                             CoffeeMachine.getOrderId());
                beverage = this.makeDrink(choice);
                this.outlet.setOutletState(OutletState.BREWING_COMPLETED);

                this.outlet.setCurrentBeverage(beverage);
                System.out.println(choice.getDisplayName() + " is prepared");
                LOGGER.debug("drink ready: {} outletId:{} orderId:{} notifying all", choice.name(),
                             this.outlet.getId(), CoffeeMachine.getOrderId());
//                CoffeeMachine.locks[this.outlet.getId() - 1].notifyAll();
            }
            catch (InsufficientIngredientException e) {
                Ingredient ingredient = e.getIngredient();
                System.out.println(choice.getDisplayName() + " cannot be prepared because " + ingredient.getDisplayName() + " is " + "not sufficient");
            }finally {
                this.outlet.setOutletState(OutletState.BREWING_COMPLETED);
                CoffeeMachine.locks[this.outlet.getId() - 1].notifyAll();
            }
        }
    }
}
