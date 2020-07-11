import coffeeMachine.machine.CoffeeMachine;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author Virender Bhargav
 */
public class CoffeeMachineApplication {
    public static void main(String[] args) throws IOException {
        File file = new File(CoffeeMachineApplication.class.getClassLoader().getResource("data.json").getFile());
        System.out.println(file);
        String jsonStr = FileUtils.readFileToString(file, "UTF-8");
        CoffeeMachine coffeeMachine = new CoffeeMachine(jsonStr);
        System.out.println(coffeeMachine);
        coffeeMachine.runMachine();
    }
}