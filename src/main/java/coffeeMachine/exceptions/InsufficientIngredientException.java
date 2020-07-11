package coffeeMachine.exceptions;

import coffeeMachine.ingredients.Ingredient;
import lombok.Getter;
import lombok.Setter;

/**
 * RuntimeException raised when existing quantity of any ingredient of ordered drink is less than required quantity
 * @author Virender Bhargav
 */
@Setter
@Getter
public class InsufficientIngredientException extends RuntimeException {

    private Ingredient ingredient;

    public InsufficientIngredientException(final String message) {
        super(message);
    }

    public InsufficientIngredientException(final String message, Ingredient ingredient) {
        super(message);
        this.ingredient = ingredient;
    }
}
