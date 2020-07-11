import coffeeMachine.machine.CoffeeMachine;

import java.io.IOException;

/**
 * @author Virender Bhargav
 */
public class CoffeeMachineApplication {
    public static void main(String[] args) throws IOException {
        CoffeeMachine coffeeMachine = CoffeeMachine.getInstance();
        coffeeMachine.runMachine();
    }
}