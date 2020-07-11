package coffeeMachine.drinker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Strategy to start {@link CoffeeDrinkerJob}
 *
 * @author Virender Bhargav
 */
public class CoffeeDrinker {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoffeeDrinker.class);

    public void drinkCoffee(Runnable job) {
        new Thread(job).start();
        LOGGER.debug("Coffee Drinker job initiated");
    }
}
