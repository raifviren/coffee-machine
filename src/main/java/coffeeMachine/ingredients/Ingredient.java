package coffeeMachine.ingredients;

/**
 * enum to represent all ingredients
 * Threshold is used to give warning if stock for particular ingredient falls below threshold value.
 * @author Virender Bhargav
 */
public enum Ingredient {

    HOT_WATER("hot_water", 200),
    HOT_MILK("hot_milk", 200),
    GINGER_SYRUP("ginger_syrup",40),
    SUGAR_SYRUP("sugar_syrup",60),
    GREEN_MIXTURE("green_mixture", 40),
    TEA_LEAVED_SYRUP("tea_leaves_syrup",40);

    private final String displayName;
    private  int threshold;

    Ingredient(String displayName, int threshold) {
        this.displayName = displayName;
        this.threshold = threshold;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getThreshold(){ return threshold;}

    public static Ingredient fromString(String text) {
        for (Ingredient ingredient : Ingredient.values()) {
            if (ingredient.displayName.equalsIgnoreCase(text)) {
                return ingredient;
            }
        }
        return null;
    }
}
