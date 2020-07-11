package coffeeMachine.machine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Strategy to start {@link CoffeeMakerJob}
 * @author Virender Bhargav
 */
public class CoffeeMaker {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoffeeMaker.class);

    public void makeCoffee(Runnable job){
        new Thread(job).start();
        LOGGER.debug("Coffee Maker job initiated");
    }
}
