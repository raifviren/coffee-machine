package coffeeMachine.storage;

import coffeeMachine.exceptions.InsufficientIngredientException;
import coffeeMachine.ingredients.Ingredient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * a ConcurrentHashMap to store the quantity of all Ingredients present in coffee machine
 *
 * @author Virender Bhargav
 */
public class IngredientInventory {
    private static final Logger LOGGER = LoggerFactory.getLogger(IngredientInventory.class);

    private Map<Ingredient, Integer> inventory = new ConcurrentHashMap<Ingredient, Integer>();

    public int getQuantity(Ingredient ingredient) {
        Integer value = inventory.get(ingredient);
        return value == null ? 0 : value;
    }

    public synchronized void add(Ingredient ingredient, int quantity) {
        int count = inventory.get(ingredient);
        inventory.put(ingredient, count + quantity);
    }

    public synchronized void deduct(Ingredient ingredient, int quantity) {
        if (hasSufficientQuantity(ingredient, quantity)) {
            int count = inventory.get(ingredient);
            inventory.put(ingredient, count - quantity);
        }
        else
            throw new InsufficientIngredientException("Insufficient quantity for ingredient: " + ingredient.getDisplayName() + " Required: " + quantity + " Stock: " + getQuantity(ingredient), ingredient);
    }

    public boolean hasSufficientQuantity(Ingredient ingredient, int requiredQuantity) {
        return getQuantity(ingredient) >= requiredQuantity;
    }

    public boolean hasIngredient(Ingredient ingredient) {
        return getQuantity(ingredient) > 0;
    }

    public void clear() {
        inventory.clear();
    }

    public Set<Map.Entry<Ingredient, Integer>> entrySet() {
        return inventory.entrySet();
    }

    public synchronized void put(Ingredient ingredient, int quantity) {
        inventory.put(ingredient, quantity);
    }

    public void checkStock() {
        for (Map.Entry<Ingredient, Integer> ingredientMap : inventory.entrySet()) {
            Ingredient ingredient = ingredientMap.getKey();
            Integer ingredientStock = ingredientMap.getValue();
            if (ingredientStock < ingredient.getThreshold()) {
                LOGGER.debug("{} stock:{} threshold: {}", ingredient.getDisplayName(), ingredientStock,
                             ingredient.getThreshold());
                System.out.println("Running low on stock of ingredient: " + ingredient.getDisplayName() + " current " + "stock: " + ingredientStock);
            }
        }
    }

    public void printStock() {
        for (Map.Entry<Ingredient, Integer> ingredientMap : inventory.entrySet()) {
            Ingredient ingredient = ingredientMap.getKey();
            Integer ingredientStock = ingredientMap.getValue();
            System.out.println("Ingredient: " + ingredient.getDisplayName() + " current stock: " + ingredientStock);
        }
    }
}


