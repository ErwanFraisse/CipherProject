public class BagExample {
	/* This class demonstrates how to use the Bag data structure. */

	public static void main(String[] args) {
		Bag bag = new Bag();

		String text = "This is my favorite english text.  It must be somewhat long in order to get reasonable frequencies.  Sitar's favorite pie is Macaroon.  Also maybe Pican.  Pican is the name of a very fancy, excellent, restaurant in Oakland where I had a delicious Chicken.";
		
		String[] letters = text.split("");
		
		for (String letter : letters) {
			bag.add(letter);
		}
		
		// Then you can ask it about frequencies:
		System.out.println("Total items: " + bag.getTotalWords());
		System.out.println("Total unique items: " + bag.getNumUniqueWords());
		System.out.println("'a' occured: " + bag.getNumOccurances("a") + " times ");
		System.out.println("Top 2 most frequent items: " + bag.getNMostFrequentStrings(5));
		double freq = (double)bag.getNumOccurances("a") / bag.getTotalWords();
		System.out.println("Frequency of 'a' in data set is: " + freq);

	}
}
