import java.util.*;

/**
 * The Food class contains a single food item name and the number of times the
 * item has been used across multiple recipes.
 *
 * @author Casey Ryane
 *
 */
public class Food implements Comparable<Food> {

	private String foodName;
	private int timesUsed;

	/**
	 * A default constructor that requires no parameters and sets food name blank
	 * and times used to 0
	 */
	public Food() {
		foodName = "";
		timesUsed = 0;

	}

	/**
	 * Constructor that requires the name of a food item and sets the uses to 0
	 * 
	 * @param foodName
	 *            A string that give the food Item a name.
	 */
	public Food(String foodName) {
		this.foodName = foodName;
		timesUsed = 0;
	}

	/**
	 * Constructor that takes in both the name of a food item and the number of
	 * times it has been used
	 * 
	 * @param foodName
	 *            A string that gives the food item a name
	 * @param timesUsed
	 *            An int that tracks the number of times the item is used in
	 *            recipies.
	 */
	public Food(String foodName, int timesUsed) {
		this.foodName = foodName;
		this.timesUsed = timesUsed;
	}

	/**
	 * Getter that returns the name of a food item
	 * 
	 * @return The name of the food item as a String
	 */
	public String getFoodName() {
		return foodName;
	}

	/**
	 * A getter for the number of times the food item has been used.
	 * 
	 * @return an int representing the number of times the food item has been used.
	 */
	public int getTimesUsed() {
		return timesUsed;
	}

	/**
	 * This method increases the timesUsed count by 1 when called, used to keep
	 * track of the number of times an ingredient is used in a recipe.
	 */
	public void incrementTimesUsed() {
		timesUsed += 1;
	}

	/**
	 * An equals method to determine if a food item is the same as another
	 * 
	 * @param a
	 *            A food object that is taken in to be compared to this food object
	 * @return Boolean answer
	 */
	public boolean equals(Food a) {
		if (a == this) {
			return true;
		}
		if (a == null) {
			return false;
		}
		Food f = (Food) a;
		return this.getFoodName().equals(f.getFoodName());
	}

	/**
	 * A compareTo method used to compare and sort the Food items in an ArrayList by
	 * alphabetical order.
	 * 
	 * @param a
	 *            A food object that is taken in to be compared to this food object
	 *            in terms of alphabetical order
	 * @return int answer if the name is before, after or the same value.
	 */
	public int compareTo(Food a) {
		return this.getFoodName().compareTo(a.getFoodName());
	}

	/**
	 * A comparator method that compares food objects by the length of the name of
	 * the food item
	 */
	public static Comparator<Food> CompIngLength = new Comparator<Food>() {
		public int compare(Food f1, Food f2) {
			if (f1.getFoodName().length() > f2.getFoodName().length()) {
				return -1;
			} else if (f1.getFoodName().length() < f2.getFoodName().length()) {
				return 1;
			} else {
				return 0;
			}
		}
	};

	/**
	 * A comparator that compares food objects by the frequency in which they are
	 * used (the timesUsed)
	 */
	public static Comparator<Food> CompFrequency = new Comparator<Food>() {

		public int compare(Food f1, Food f2) {
			if (f1.getTimesUsed() > f2.getTimesUsed()) {
				return -1;
			} else if (f1.getTimesUsed() < f2.getTimesUsed()) {
				return 1;
			} else {
				return 0;
			}
		}
	};

}
