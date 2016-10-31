public class BagSimpleExample {
	/* This class demonstrates how to use the Bag data structure. */

	public static void main(String[] args) {
		Bag bag = new Bag();

		bag.add("a");			// add 3 "a"'s to the bag
		bag.add("a");
		bag.add("a");
		
		bag.add("e");			// add 4 "e"'s to the bag
		bag.add("e");
		bag.add("e");
		bag.add("e");
		
		bag.add("x");			// add 5 other letters
		bag.add("x");
		bag.add("z");
		bag.add("w");
		bag.add("p");
		
		// Then you can ask it about frequencies:
		System.out.println("Total items: " + bag.getTotalWords());
		System.out.println("Total unique items: " + bag.getNumUniqueWords());
		System.out.println("'a' occured: " + bag.getNumOccurances("a") + " times ");
		System.out.println("Most frequently occuring is: " + bag.getMostFrequent());
		System.out.println("Top 2 most frequent items: " + bag.getNMostFrequentStrings(2));
	}
}
