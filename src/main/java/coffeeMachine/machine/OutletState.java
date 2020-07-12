package coffeeMachine.machine;

/**
 * Enum to define states of a {@link CoffeeMachineOutlet}
 * @author Virender Bhargav
 */
public enum OutletState {
    READY_TO_TAKE_ORDER,
    BREWING_DRINK,
    BREWING_COMPLETED,
    OUT_OF_SERVICE;
}
