package coffeeMachine.beverage;

import coffeeMachine.ingredients.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * Map to store quantity of all required {@link Ingredient} to make a drink
 * @author Virender Bhargav
 */
@Getter
@AllArgsConstructor
@ToString
public class DrinkRecipe {
    private Map<Ingredient, Integer> ingredients = new HashMap<Ingredient, Integer>();

}