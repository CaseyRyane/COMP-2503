import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This program reads input from a file, populates an ArrayList with food items,
 * sorts the arrayList and prints out the contents to a file in a structured
 * manner.
 * 
 * @author Casey Ryane
 *
 */
public class A1 {

	private static Scanner sc = new Scanner(System.in);
	private static ArrayList<Food> foodList;
	private static A1 a1;

	/**
	 * No argument constructor for the A1 class that initializes the main ArrayList
	 * used to store the ingredients from the basic ingredient list and any
	 * ingredients from the "recipe book"
	 */
	public A1() {
		foodList = new ArrayList<>();
	}

	/**
	 * Main method is run and instantiates a new a1 object.
	 * 
	 */
	public static void main(String[] args) {
		a1 = new A1();
		a1.run();
	}

	/**
	 * Load basic food items into an ArrayList. 
	 * Read in input from standard input and add to ArrayList. 
	 * Print detailed formatted output.
	 * 
	 */
	public static void run() {
		loadBasicFoodList();
		getInput();
		printOutput();
	}

	/**
	 * New Input is read from the file, all Title, *'s and directions are discarded.
	 * ALl new ingredients are read in an added to the FoodList or frequency is incremented 
	 * if the ingredient previously existed.
	 */
	private static void getInput() {
		do {
			String nextLine = sc.nextLine();
			if (nextLine.equals("---")) {
				boolean recipieEnd = false;
				while (!recipieEnd) {
					String foodItem = sc.nextLine();
					if (foodItem.equals("---")) {
						recipieEnd = true;
					} else {
						String ingredient = getIngredientName(foodItem);
						addIngredient(ingredient);
					}
				}
			}
		} while (sc.hasNext());
	}

	/**
	 * Takes a string containing an ingredient and identifies if the ingredient
	 * exists in foodList, If already existing the ingredient is incremented.
	 * If the ingredient does not exists the string is processed and the new
	 * ingredient is identified.
	 * 
	 * @param foodItem
	 *            a string that contains a food item.
	 * @return The name of the food item to be incremented or added to the foodList
	 */
	private static String getIngredientName(String foodItem) {
		boolean foodAlreadyExists = false;
		String ingredient = "";
		for (int i = 0; i < foodList.size(); i++) {
			Food food = foodList.get(i);
			if (foodItem.contains(food.getFoodName().toLowerCase())) {
				foodAlreadyExists = true;
				ingredient = food.getFoodName();
				i = foodList.size() + 1;
			}
		}
		if (foodAlreadyExists) {
			return ingredient;
		} else {
			ingredient = trimIngredient(foodItem);	
		}
		return ingredient;
	}
	
	/**
	 * Trims White spaces from the beginning and end of the given string
	 * Takes the last word from the string to be sued as the ingredient name
	 * Removes any single trailing 's'
	 * Removes any trailing 'es'
	 * 
	 * @param foodItem a string containing new food items.
	 * @return A String containing a single ingredient name.
	 */
	private static String trimIngredient(String foodItem){
		foodItem = foodItem.trim();
		String ingredient = foodItem.substring(foodItem.lastIndexOf(" ") + 1);
		
		if (ingredient.charAt(ingredient.length() - 1) == 's' && ingredient.charAt(ingredient.length() -2) != 's') {
			ingredient = ingredient.substring(0, ingredient.length() - 1);
			if (ingredient.charAt(ingredient.length() - 1) == 'e') {
				ingredient = ingredient.substring(0, ingredient.length() - 1);
			}
		}
		return ingredient; 
	}

	/**
	 * Takes the given ingredient name and checks if it exists in the foodList. 
	 * If the ingredient exists the timesUsed is incremented by one.
	 * If the ingredient does not exists a new ingredient is added with frequency 1. 
	 * 
	 * @param ingredient
	 *            the name of the ingredient to be added to the foodList
	 */
	private static void addIngredient(String ingredient) {
		boolean newIngredient = true;
		for (int i = 0; i < foodList.size(); i++) {
			Food listItem = foodList.get(i);
			String foodName = listItem.getFoodName();

			if (ingredient.contains(foodName.toLowerCase())) {
				listItem.incrementTimesUsed();
				i = foodList.size() + 1;
				newIngredient = false;
			}
		}
		if (newIngredient) {
			addNewIngredient(ingredient);
		}
	}

	/**
	 * A new ingredient is added to the foodList with a count of 1.
	 * The foodList is sorted by the length of ingredient names.
	 * 
	 * @param ingredient
	 *            the name of the ingredient to be added.
	 */
	private static void addNewIngredient(String ingredient) {
		Food newFood = new Food(ingredient, 1);
		foodList.add(newFood);
		Collections.sort(foodList, Food.CompIngLength);
	}

	/**
	 * Ensure the proper output is printed to a file. 
	 * The total number of ingredients is printed. 
	 * Up to the top 10 most common ingredients are printed in order from most frequent to least used. 
	 * The complete list of foods used in alphabetical order are printed.
	 */
	private static void printOutput() {
		int numIngredients = getTotalNumIngredients();
		System.out.println("The recipe book has " + numIngredients + " different ingredients");
		System.out.println("---------------------------------------");
		if (numIngredients >= 10) {
			numIngredients = 10;
		}
		System.out.println("Top " + numIngredients + " ingredients, from most common to least common");
		String mostCommon = mostCommonIngredients();
		System.out.print(mostCommon);
		System.out.println("---------------------------------------");
		System.out.println("The complete list of foods in the recipe book, in alphabetical order");
		String allFood = allFoodUsed();
		System.out.print(allFood);
	}

	/**
	 * Sorts through the foodList and takes up to the top 10 most commonly used
	 * ingredients and creates a formated string displaying each ingredient and the
	 * number of times its been used.
	 * 
	 * @return a formatted string containing up to 10 of the most common ingredients
	 *         used
	 */
	private static String mostCommonIngredients() {
		Collections.sort(foodList);
		Collections.sort(foodList, Food.CompFrequency);
		int numIngredients = getTotalNumIngredients();
		if (numIngredients >= 10) {
			numIngredients = 10;
		}
		String mostCommon = "";
		for (int i = 0; i < numIngredients; i++) {
			Food food = foodList.get(i);
			mostCommon += String.format("(%s,%d)%n", food.getFoodName(), food.getTimesUsed());
		}
		return mostCommon;
	}

	/**
	 * Sorts the entire foodList into alphabetical order "A-Z" and creates a
	 * formated string showing each ingredient with a frequency > 0 
	 * and the number of times it has been used.
	 * 
	 * @return a formated string containing all ingredients with frequency > 0.
	 */
	private static String allFoodUsed() {
		Collections.sort(foodList);
		String allFood = "";
		for (Food food : foodList) {
			if (food.getTimesUsed() > 0) {
				allFood += String.format("(%s,%d)%n", food.getFoodName(), food.getTimesUsed());
			}
		}
		return allFood;
	}

	/**
	 * Retrieves the total number of ingredients in the foodList that have been used
	 * at least once in a recipe (frequency > 0 ). 
	 * 
	 * @return an int that represents the number of ingredients used.
	 */
	private static int getTotalNumIngredients() {
		int numIngredients = 0;
		for (Food food : foodList) {
			if (food.getTimesUsed() > 0) {
				numIngredients++;
			}
		}
		return numIngredients;
	}

	/**
	 * This method loads all basic ingredients into the FoodList with a times used
	 * count at 0. 
	 * Sorts the list by the length of ingredient names. 
	 */
	private static void loadBasicFoodList() {
		foodList.add(new Food("baking powder"));
		foodList.add(new Food("baking soda"));
		foodList.add(new Food("cheese"));
		foodList.add(new Food("broth"));
		foodList.add(new Food("tomato paste"));
		foodList.add(new Food("tomato sauce"));
		foodList.add(new Food("tomato"));
		foodList.add(new Food("flour"));
		foodList.add(new Food("egg"));
		foodList.add(new Food("garlic"));
		foodList.add(new Food("rice"));
		foodList.add(new Food("onion"));
		foodList.add(new Food("salt"));
		foodList.add(new Food("pepper"));
		foodList.add(new Food("vinegar"));
		foodList.add(new Food("carrot"));
		foodList.add(new Food("sweet potato"));
		foodList.add(new Food("potato"));
		foodList.add(new Food("cream"));
		foodList.add(new Food("milk"));
		foodList.add(new Food("bean"));
		foodList.add(new Food("green bean"));
		foodList.add(new Food("beef"));
		foodList.add(new Food("chicken"));
		foodList.add(new Food("cumin"));
		foodList.add(new Food("basil"));
		foodList.add(new Food("oregano"));
		foodList.add(new Food("oil"));
		foodList.add(new Food("fish"));
		
		Collections.sort(foodList, Food.CompIngLength);

	}

}
