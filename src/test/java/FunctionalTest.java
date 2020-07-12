import coffeeMachine.beverage.DrinkOption;
import coffeeMachine.drinker.CoffeeDrinker;
import coffeeMachine.drinker.CoffeeDrinkerJob;
import coffeeMachine.machine.CoffeeMachine;
import coffeeMachine.machine.CoffeeMachineOutlet;
import coffeeMachine.machine.CoffeeMaker;
import coffeeMachine.machine.CoffeeMakerJob;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Virender Bhargav
 * Functional test for Coffee Machine Implementation
 */
public class FunctionalTest {
    CoffeeMachine coffeeMachine;

    @BeforeClass
    public static void setup() {

    }

    @Before
    public void setUp() throws Exception {
        this.coffeeMachine = CoffeeMachine.getInstance();

    }

    @After
    public void finalize() throws IOException {
        this.coffeeMachine.reset();
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
    public void testOutput1() {
        System.out.println("\n### Test Case 1 Started ###");
        CountDownLatch makerLatch = new CountDownLatch(4);
        CountDownLatch drinkerLatch = new CountDownLatch(4);

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

        try {
            makerLatch.await();
            drinkerLatch.await();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\n### Test Case 1 Completed ###");
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
    public void testOutput2() {
        System.out.println("\n### Test Case 2 Started ###");
        CountDownLatch makerLatch = new CountDownLatch(4);
        CountDownLatch drinkerLatch = new CountDownLatch(4);

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

        try {
            makerLatch.await();
            drinkerLatch.await();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\n### Test Case 2 Completed ###");
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
    public void testOutput3() {
        System.out.println("\n### Test Case 3 Started ###");
        CountDownLatch makerLatch = new CountDownLatch(4);
        CountDownLatch drinkerLatch = new CountDownLatch(4);

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

        try {
            makerLatch.await();
            drinkerLatch.await();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\n### Test Case 3 Completed ###");
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
            try {
                super.run();
            }
            catch (Exception e) {
                e.printStackTrace();
            }finally {
                latch.countDown();
            }
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
            try {
                super.run();
            }
            catch (Exception e) {
                e.printStackTrace();
            }finally {
                latch.countDown();
            }
        }
    }
}
