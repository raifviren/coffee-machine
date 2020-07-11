package coffeeMachine.machine;

import coffeeMachine.beverage.Beverage;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Outlets attached to a coffee machine
 * @author Virender Bhargav
 */
@Getter
@Setter
public class CoffeeMachineOutlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoffeeMachineOutlet.class);
    private int id;
    private CoffeeMachine coffeeMachine;
    private OutletState outletState;
    private Beverage currentBeverage;

    public CoffeeMachineOutlet(int id, CoffeeMachine coffeeMachine) {
        this.id = id;
        this.coffeeMachine = coffeeMachine;
        this.outletState = OutletState.READY_TO_TAKE_ORDER;
    }

}
