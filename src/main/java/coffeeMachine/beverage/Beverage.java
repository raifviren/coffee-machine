package coffeeMachine.beverage;

import coffeeMachine.ingredients.Ingredient;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * class to represent a beverage present in the system
 *
 * @author Virender Bhargav
 */
@Getter
public class Beverage {
    private String name;
    private List<Ingredient> ingredients = new ArrayList<Ingredient>();

    public Beverage(String name) {
        this.name = name;
    }


    public void drink() {
        System.out.println("\n" + this.name + " consumed!");
    }
}
