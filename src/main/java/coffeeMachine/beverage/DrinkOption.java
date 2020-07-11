package coffeeMachine.beverage;

/**
 * Enum to to represent all drink options available in the coffee machine
 * @author Virender Bhargav
 */
public enum DrinkOption {
    HOT_TEA("hot_tea"),
    HOT_COFFEE("hot_coffee"),
    BLACK_TEA("black_tea"),
    GREEN_TEA("green_tea"),
    CAPPUCCINO("cappuccino"),
    FLAT_WHITE("flat_white"),
    AMERICANO("americano"),
    ESPRESSO("espresso"),
    LATTE("latte"),
    COLD_COFFEE("cold_coffee");

    private final String displayName;

    DrinkOption(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static DrinkOption fromString(String text) {
        for (DrinkOption drinkOption : DrinkOption.values()) {
            if (drinkOption.displayName.equalsIgnoreCase(text)) {
                return drinkOption;
            }
        }
        return null;
    }
}
