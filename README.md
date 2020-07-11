# Coffee Machine | Virender Bhargav

## 1. Problem Statment
Write the working code to create a working coffee machine. Here are the desired features
* It will be serving some beverages.
* Each beverage will be made using some ingredients.
* Assume time to prepare a beverage is the same for all cases.
* The quantity of ingredients used for each beverage can vary. Also, the same ingredient (ex:
water) can be used for multiple beverages.
* There would be N ( N is an integer ) outlet from which beverages can be served. as being available.
* Maximum N beverages can be served in parallel.
* Any beverage can be served only if all the ingredients are available in terms of quantity.
* There would be an indicator that would show which all ingredients are running low. We need
some methods to refill them.
* Please provide functional integration test cases for maximum coverage.
- - - -

## 2. Solution Approach
* CoffeeMachine: Singleton Class to ensure only one object of CoffeeMachine in the system. Takes <OutletId> <DrinkId> as input to
 make a drink.
* CoffeeMachineOutlet: Outlets attached to a coffee machine
* Ingredient : an enum to represent all ingredients  having name and threshold. Threshold is used to give warning if
 stock for particular ingredient falls below threshold value.
* IngredientInventory : a ConcurrentHashMap to store the quantity of all Ingredients present in coffee machine
* DrinkOption : Enum to to represent all drink options available in the coffee machine
* DrinkRecipes : Map to store quantity of all required Ingredients to make a drink
* RecipeRepository : a HashMap of all DrinkOptions and their corresponding DrinkRecipes

* CoffeeMakerJob - Runnable job to brew drink corresponding to given <DrinkId> on given  <OutletId>. Its makeDrink()
 function deducts quantity of all Ingredients of ordered drink from IngredientInventory of coffee machine
* CoffeeDrinkerJob - Runnable job to to consume prepared drink on given <OutletId>

* InsufficientIngredientException : RuntimeException raised when existing quantity of any ingredient of ordered drink is less than required quantity
 
- - - -

## 3. Install and Run 
#### 4.1 Install and build

`mvn -U clean install`

#### 4.1 Running the application:

`mvn exec:java`

## 5. Test Cases

#### 5.1 To run all test cases

`mvn test`