package coffeeMachine.storage;

import coffeeMachine.beverage.DrinkOption;
import coffeeMachine.beverage.DrinkRecipe;

import java.util.HashMap;
import java.util.Map;

/**
 * a HashMap of all DrinkOptions and their corresponding DrinkRecipes
 *
 * @author Virender Bhargav
 */
public class RecipeRepository {
    private final static Map<DrinkOption, DrinkRecipe> drinkMap = new HashMap<DrinkOption, DrinkRecipe>();

    public DrinkRecipe getDrinkRecipe(DrinkOption drinkOption) {
        DrinkRecipe drinkRecipe = drinkMap.get(drinkOption);
        return drinkRecipe;
    }

    public void clear() {
        drinkMap.clear();
    }

    public void put(DrinkOption drinkOption, DrinkRecipe drinkRecipe) {
        drinkMap.put(drinkOption, drinkRecipe);
    }
}
