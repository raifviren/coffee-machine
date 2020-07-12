import coffeeMachine.beverage.DrinkOption;
import coffeeMachine.drinker.CoffeeDrinker;
import coffeeMachine.drinker.CoffeeDrinkerJob;
import coffeeMachine.machine.CoffeeMachine;
import coffeeMachine.machine.CoffeeMachineOutlet;
import coffeeMachine.machine.CoffeeMaker;
import coffeeMachine.machine.CoffeeMakerJob;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Virender Bhargav
 * Functional test for Coffee Machine Implementation
 */
public class FunctionalTest {
    CoffeeMachine coffeeMachine;
    Integer outletId;
    Integer drinkId;


    @Before
    public void setUp() throws Exception {
        this.coffeeMachine = CoffeeMachine.getInstance();
        this.outletId = 1;
        this.drinkId = 1;

    }

    private CoffeeMachineOutlet getOutletById(int outletId) {
        return this.coffeeMachine.getOutletById(outletId);
    }

    private DrinkOption getDrinkById(int drinkId) {
        return this.coffeeMachine.getDrinkById(drinkId);
    }

    /**
     * Order No.    OutletId  DrinkId        Drink
     * 1             1       1           hot_tea
     * 2             2       2           hot_coffee
     * 3             3       4           green_tea
     * 4             1       3           black_tea
     *
     * @throws InterruptedException
     */
    @Test
    public void testOutput1() throws InterruptedException {
        CountDownLatch makerLatch = new CountDownLatch(3);
        CountDownLatch drinkerLatch = new CountDownLatch(3);

        //Placing first order
        CoffeeMakerJobTester coffeeMakerJobTester = new CoffeeMakerJobTester(getOutletById(1), getDrinkById(1),
                                                                             makerLatch);
        CoffeeDrinkerJobTester coffeeDrinkerJobTester = new CoffeeDrinkerJobTester(getOutletById(1), drinkerLatch);
        new CoffeeMaker().makeCoffee(coffeeMakerJobTester);
        new CoffeeDrinker().drinkCoffee(coffeeDrinkerJobTester);

        //Placing second order
        coffeeMakerJobTester = new CoffeeMakerJobTester(getOutletById(2), getDrinkById(2), makerLatch);
        coffeeDrinkerJobTester = new CoffeeDrinkerJobTester(getOutletById(2), drinkerLatch);
        new CoffeeMaker().makeCoffee(coffeeMakerJobTester);
        new CoffeeDrinker().drinkCoffee(coffeeDrinkerJobTester);

        //Placing third order
        coffeeMakerJobTester = new CoffeeMakerJobTester(getOutletById(3), getDrinkById(4), makerLatch);
        coffeeDrinkerJobTester = new CoffeeDrinkerJobTester(getOutletById(3), drinkerLatch);
        new CoffeeMaker().makeCoffee(coffeeMakerJobTester);
        new CoffeeDrinker().drinkCoffee(coffeeDrinkerJobTester);

        //Placing fourth order
        coffeeMakerJobTester = new CoffeeMakerJobTester(getOutletById(1), getDrinkById(3), makerLatch);
        coffeeDrinkerJobTester = new CoffeeDrinkerJobTester(getOutletById(1), drinkerLatch);
        new CoffeeMaker().makeCoffee(coffeeMakerJobTester);
        new CoffeeDrinker().drinkCoffee(coffeeDrinkerJobTester);

        makerLatch.await(10, TimeUnit.SECONDS);
        drinkerLatch.await(10, TimeUnit.SECONDS);
    }

    /**
     * Order No.    OutletId  DrinkId        Drink
     * 1             1       1           hot_tea
     * 2             2       3           black_tea
     * 3             3       4           green_tea
     * 4             1       2           hot_coffee
     *
     * @throws InterruptedException
     */
    @Test
    public void testOutput2() throws InterruptedException {
        CountDownLatch makerLatch = new CountDownLatch(3);
        CountDownLatch drinkerLatch = new CountDownLatch(3);

        //Placing first order
        CoffeeMakerJobTester coffeeMakerJobTester = new CoffeeMakerJobTester(getOutletById(1), getDrinkById(1),
                                                                             makerLatch);
        CoffeeDrinkerJobTester coffeeDrinkerJobTester = new CoffeeDrinkerJobTester(getOutletById(1), drinkerLatch);
        new CoffeeMaker().makeCoffee(coffeeMakerJobTester);
        new CoffeeDrinker().drinkCoffee(coffeeDrinkerJobTester);

        //Placing second order
        coffeeMakerJobTester = new CoffeeMakerJobTester(getOutletById(2), getDrinkById(3), makerLatch);
        coffeeDrinkerJobTester = new CoffeeDrinkerJobTester(getOutletById(2), drinkerLatch);
        new CoffeeMaker().makeCoffee(coffeeMakerJobTester);
        new CoffeeDrinker().drinkCoffee(coffeeDrinkerJobTester);

        //Placing third order
        coffeeMakerJobTester = new CoffeeMakerJobTester(getOutletById(3), getDrinkById(4), makerLatch);
        coffeeDrinkerJobTester = new CoffeeDrinkerJobTester(getOutletById(3), drinkerLatch);
        new CoffeeMaker().makeCoffee(coffeeMakerJobTester);
        new CoffeeDrinker().drinkCoffee(coffeeDrinkerJobTester);

        //Placing fourth order
        coffeeMakerJobTester = new CoffeeMakerJobTester(getOutletById(1), getDrinkById(2), makerLatch);
        coffeeDrinkerJobTester = new CoffeeDrinkerJobTester(getOutletById(1), drinkerLatch);
        new CoffeeMaker().makeCoffee(coffeeMakerJobTester);
        new CoffeeDrinker().drinkCoffee(coffeeDrinkerJobTester);

        makerLatch.await(10, TimeUnit.SECONDS);
        drinkerLatch.await(10, TimeUnit.SECONDS);
    }

    /**
     * Order No.    OutletId  DrinkId        Drink
     * 1             1       2           hot_coffee
     * 2             2       3           black_tea
     * 3             3       4           green_tea
     * 4             1       1           hot_tea
     *
     * @throws InterruptedException
     */
    @Test
    public void testOutput3() throws InterruptedException {
        CountDownLatch makerLatch = new CountDownLatch(3);
        CountDownLatch drinkerLatch = new CountDownLatch(3);

        //Placing first order
        CoffeeMakerJobTester coffeeMakerJobTester = new CoffeeMakerJobTester(getOutletById(1), getDrinkById(2),
                                                                             makerLatch);
        CoffeeDrinkerJobTester coffeeDrinkerJobTester = new CoffeeDrinkerJobTester(getOutletById(1), drinkerLatch);
        new CoffeeMaker().makeCoffee(coffeeMakerJobTester);
        new CoffeeDrinker().drinkCoffee(coffeeDrinkerJobTester);


        //Placing second order
        coffeeMakerJobTester = new CoffeeMakerJobTester(getOutletById(2), getDrinkById(3), makerLatch);
        coffeeDrinkerJobTester = new CoffeeDrinkerJobTester(getOutletById(2), drinkerLatch);
        new CoffeeMaker().makeCoffee(coffeeMakerJobTester);
        new CoffeeDrinker().drinkCoffee(coffeeDrinkerJobTester);

        //Placing third order
        coffeeMakerJobTester = new CoffeeMakerJobTester(getOutletById(3), getDrinkById(4), makerLatch);
        coffeeDrinkerJobTester = new CoffeeDrinkerJobTester(getOutletById(3), drinkerLatch);
        new CoffeeMaker().makeCoffee(coffeeMakerJobTester);
        new CoffeeDrinker().drinkCoffee(coffeeDrinkerJobTester);

        //Placing fourth order
        coffeeMakerJobTester = new CoffeeMakerJobTester(getOutletById(1), getDrinkById(1), makerLatch);
        coffeeDrinkerJobTester = new CoffeeDrinkerJobTester(getOutletById(1), drinkerLatch);
        new CoffeeMaker().makeCoffee(coffeeMakerJobTester);
        new CoffeeDrinker().drinkCoffee(coffeeDrinkerJobTester);

        makerLatch.await(10, TimeUnit.SECONDS);
        drinkerLatch.await(10, TimeUnit.SECONDS);
    }

    private class CoffeeMakerJobTester extends CoffeeMakerJob {

        private final CountDownLatch latch;

        public CoffeeMakerJobTester(CoffeeMachineOutlet coffeeMachineOutlet, DrinkOption drinkOption,
                                    CountDownLatch latch) {
            super(coffeeMachineOutlet, drinkOption);
            this.latch = latch;
        }

        @Override
        public void run() {
            super.run();
            latch.countDown();
        }
    }

    private class CoffeeDrinkerJobTester extends CoffeeDrinkerJob {

        private final CountDownLatch latch;

        public CoffeeDrinkerJobTester(CoffeeMachineOutlet coffeeMachineOutlet, CountDownLatch latch) {
            super(coffeeMachineOutlet);
            this.latch = latch;
        }

        @Override
        public void run() {
            super.run();
            latch.countDown();
        }
    }
}
