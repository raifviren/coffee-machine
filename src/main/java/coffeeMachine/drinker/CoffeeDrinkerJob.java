package coffeeMachine.drinker;

import coffeeMachine.beverage.Beverage;
import coffeeMachine.machine.CoffeeMachine;
import coffeeMachine.machine.CoffeeMachineOutlet;
import coffeeMachine.machine.OutletState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Runnable job to to consume prepared drink on given <OutletId>
 *
 * @author Virender Bhargav
 */
@Getter
@Setter
@AllArgsConstructor
public class CoffeeDrinkerJob implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoffeeDrinkerJob.class);

    private CoffeeMachineOutlet outlet;


    @Override
    public void run() {
        drinkCoffee();
    }

    void drinkCoffee() {
        synchronized (CoffeeMachine.locks[this.outlet.getId() - 1]) {
            if (this.outlet.getOutletState() != OutletState.BREWING_COMPLETED) {
//                System.out.println(" Waiting for coffee machine to prepare coffee on outlet: "+this.outlet.getId());
                try {
                    CoffeeMachine.locks[this.outlet.getId() - 1].wait();
                }
                catch (InterruptedException e) {
                    LOGGER.error(e.getMessage());
                }
            }
            if (Objects.nonNull(outlet.getCurrentBeverage())) {
                Beverage currentBeverage = outlet.getCurrentBeverage();
                currentBeverage.drink();
                CoffeeMachine.incrementOrderId();
                System.out.println("Order Completed for "+currentBeverage.getName()+". OrderId: " + CoffeeMachine.getOrderId() + "\n");
            }
//            else {
//                CoffeeMachine.incrementOrderId();
//                System.out.println("OrderId: " + CoffeeMachine.getOrderId() + " could not be completed due to "
//                                   + "insufficient Inventory\n");
//
//            }
            this.outlet.setOutletState(OutletState.READY_TO_TAKE_ORDER);
            CoffeeMachine.locks[this.outlet.getId() - 1].notifyAll();

        }
    }
}
