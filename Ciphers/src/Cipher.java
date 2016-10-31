
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public abstract class Cipher{
	public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789,.() '\"![]/%-_;?-=:"
			+ '\n' + '\r';
	public static Dictionary dictionary = Dictionary.buildDictionary("Ciphers/words.txt");

	public static void main(String[] args) {
		System.out.println(rotationCipherEncrypt("fancy", 1, ALPHABET));
		System.out.println(rotationCipherDecrypt("gbodz", 1, ALPHABET));

		System.out.println(vigenereCipherEncrypt("I want to go get help", "key", ALPHABET));
		System.out.println(vigenereCipherDecrypt("S[UkrR;xM;kM;kCD[FopN", "key", ALPHABET));

		System.out.println(rotationCipherCrack("gbodz", ALPHABET));

		System.out.println(vigenereCipherCrackThreeLetter("S[UkrR;xM;kM;kCD[FopN;vCkpJI[zkhJI[zogyEwC;vGqlR;rMG[G;eK;hWsrE;sD;xFsvQD[yxhhs[UswF;xFswhGsSvhhxsR;fC;lyztCxmLq[zogyEwC;mhkqhDCNsrE;wM;qSmlhGsPnwhBmErxhxsU;mR;mQ;kCDxGxkhkrLyCGxk"));
		
		System.out.println(vigenereCipherCrack("S[UkrR;xM;kM;kCD[FopN;vCkpJI[zkhJI[zogyEwC;vGqlR;rMG[G;eK;hWsrE;sD;xFsvQD[yxhhs[UswF;xFswhGsSvhhxsR;fC;lyztCxmLq[zogyEwC;mhkqhDCNsrE;wM;qSmlhGsPnwhBmErxhxsU;mR;mQ;kCDxGxkhkrLyCGxk", ALPHABET));
	}
	
	public static String vigenereCipherCrack(String encrypted, String alphabet) {
		for (int passwordLength = 1; passwordLength < 12; passwordLength++) {
			String d = vigenereCipherCrackNLetter(encrypted, passwordLength);
			if (isEnglish(d)) return d;
		}
		return encrypted;
	}
	
	public static String vigenereCipherCrack(String encrypted) {
		return vigenereCipherCrack(encrypted, ALPHABET);
	}
	
	public static String vigenereCipherCrackThreeLetter(String cipher) {
		return vigenereCipherCrackThreeLetter(cipher, ALPHABET);
	}

	public static String vigCipherCrackWithDecryptAndEncrypt(String encrypted, String decrypted, String alphabet) { // S[UkrR;xM;kM;kCD[FopN
		int[] indexENCRYPT = new int[encrypted.length()];
		int[] indexDECRYPT = new int[decrypted.length()];
		String code = "";
		for (int i = 0; i < encrypted.length(); i++) {
			indexENCRYPT[i] = alphabet.indexOf(encrypted.substring(i, i + 1));
			indexDECRYPT[i] = alphabet.indexOf(decrypted.substring(i, i + 1));
			indexENCRYPT[i] = indexENCRYPT[i] - indexDECRYPT[i];
		}
		for (int x = 0; x < encrypted.length(); x++) {
			String letter = alphabet.substring(indexENCRYPT[x], indexENCRYPT[x] + 1);
			code += letter;
		}

		for (int j = 1; j < code.length(); j++) {
			if (code.substring(0, j).equals(code.substring(j, j * 2))) {
				return code.substring(0, j);
			}
		}
		return code;
	}

	public static String vigenereCipherCrackwithDecryptAndEncrypt(String encrypted, String decrypted, String alphabet) {
		return vigCipherCrackWithDecryptAndEncrypt(encrypted, decrypted, alphabet);
	}

	public static boolean isEnglish(String PlainText) {
		double count = 0;
		String[] words = getWords(PlainText);

		for (int i = 0; i < words.length; i++) {
			if (dictionary.isWord(words[i]))
				count++;
		}
		if (count / words.length >= 0.4) {
			return true;
		}
		return false;
	}

	public static String[] getWords(String plainText) {
		String word = "";
		ArrayList<String> words = new ArrayList<String>();
		plainText += " ";

		for (int i = 0; i < plainText.length(); i++) {
			word = "";
			while (!plainText.substring(i, i + 1).equals(" ")) {
				word += plainText.substring(i, i + 1);
				i++;
			}
			if (word.length() >= 1 && word != " ")
				words.add(word);
		}
		String[] wordArray = new String[words.size()];
		for (int j = 0; j < wordArray.length; j++) {
			wordArray[j] = words.get(j);
		}
		return wordArray;
	}

	private static String rotcipherCrack(String encrypted, String alphabet) {
		double counter = 0.0, english = 0.0;
		String words = "";

		for (int i = 0; i < alphabet.length(); i++) {
			counter = 0;
			english = 0;
			words = rotationCipherDecrypt(encrypted, i, ALPHABET);
			String[] arrayOfWords = getWords(words);
			for (int j = 0; j < arrayOfWords.length; j++) {
				if (dictionary.isWord(arrayOfWords[j]))
					counter++;
			}
			english = counter / arrayOfWords.length;
			if (english > 0.5)
				return words;
		}
		return null;
	}

	public static String getLettersOut(String encrypted, int index, int length) {
		String every3rdLetterString = "";
		for (int i = index; i < encrypted.length(); i = i + length) {
			every3rdLetterString = every3rdLetterString + encrypted.substring(i, i + 1);
		}
		return every3rdLetterString;
	}

	public static String findNCodeLetter(String everyNLetterString, int shiftAmount) {	
			String decoded = rotationCipherDecrypt(everyNLetterString, shiftAmount, ALPHABET);
			Bag bag = new Bag();
			for (int x = 0; x < decoded.length(); x++) {
				bag.add(decoded.substring(x, x + 1));
			}
			if (bag.getMostFrequent().equals(" ")) {
				return ALPHABET.substring(shiftAmount, shiftAmount + 1);
			}
		return "e"; 
	}
	
	public static String findCodeLetter(String everyNLetterString) {
		for (int i = 0; i < ALPHABET.length(); i++) {
			String decoded = rotationCipherDecrypt(everyNLetterString, i, ALPHABET);
			Bag bag = new Bag();
			for (int x = 0; x < decoded.length(); x++) {
				bag.add(decoded.substring(x, x + 1));
			}
			if (bag.getMostFrequent().equals(" ")) {
				return ALPHABET.substring(i, i + 1);
			}
		}
		return "e"; 
	}

	public static String vigenereCipherCrackThreeLetter(String encrypted, String alphabet) {
		String code = "";
		for (int passwordKey = 0; passwordKey < 3; passwordKey++) {
			String every3rdLetter = getLettersOut(encrypted, passwordKey, 3);
			code += findCodeLetter(every3rdLetter);
		}
		return (vigenereCipherDecrypt(encrypted, code, ALPHABET));
	}
	
	public static String vigenereCipherCrackNLetter(String encrypted, int passwordLength) {
		String code = "";
			for (int passwordKey = 0; passwordKey < passwordLength; passwordKey++) {
				String everyNLetter = getNLettersOut(encrypted, passwordKey, passwordLength); //replace 3 with j
				code += findCodeLetter(everyNLetter);
			}
			return vigenereCipherDecrypt(encrypted, code, ALPHABET);	
	}
	
	public static String getNLettersOut(String encrypted, int index, int length) {
		String everyNLetterString = "";
		for (int i = index; i < encrypted.length(); i = i + length) {
			everyNLetterString = everyNLetterString + encrypted.substring(i, i + 1);
		}
		return everyNLetterString;
	}

	public static String getGroup(String in, int start, int skip) {
		String group = "";
		for (int i = start; i < in.length(); i += skip) {
			group += in.charAt(i);
		}

		return group;
	}

	public static String[] splitIntoGroups(String in, int numGroups) {
		String[] groups = new String[numGroups];
		for (int start = 0; start < numGroups; start++) {
			groups[start] = getGroup(in, start, numGroups);
		}
		
		return groups;
	}

	public static String getFileAsString(String filename) {
		Scanner s;
		StringBuilder sb = new StringBuilder(500000);
		try {
			s = new Scanner(new FileReader(filename));
			s.useDelimiter("");

			while (s.hasNext()) {
				String n = s.next();
				sb.append(n);
			}
		} catch (Exception e) {

		}
		return sb.toString();
	}

	public static void writeStringToFile(String filename, String text) {
		try {
			PrintWriter out = new PrintWriter(filename);
			for (int i = 0; i < text.length(); i++) {
				char n = text.charAt(i);
				out.print(n);
			}
			out.close();
		} catch (Exception e) {
		}
	}

	public static String vigenereCipherDecrypt(String cipher, String code, String alphabet) {
		return vigenereCipher(cipher, code, -1);
	}

	public static String vigenereCipherEncrypt(String cipher, String code, String alphabet) {
		return vigenereCipher(cipher, code, 1);
	}

	// direction must be +1 or -1 depending on direction to shift.
	private static String vigenereCipher(String plain, String code, int direction) {
		String cipher = "";
		int originalIndex, shiftAmount, newIndex;
		int length = code.length();

		if (!isValidCodeWord(code))
			return "INVALID CODEWORD!";
		plain = stripInvalid(plain);

		for (int i = 0; i < plain.length(); i++) {
			char c = plain.charAt(i);
			originalIndex = ALPHABET.indexOf(c);

			if (originalIndex >= 0) {
				shiftAmount = ALPHABET.indexOf(code.charAt(i % length));

				newIndex = originalIndex + direction * shiftAmount;

				while (newIndex < 0)
					newIndex += ALPHABET.length();
				newIndex %= ALPHABET.length();

				cipher += ALPHABET.charAt(newIndex);
			} else {
				// System.out.println("Skipping letter: \"" + c + "\"");
			}
		}
		return cipher;
	}

	// Strips out all characters not in our alphabet
	private static String stripInvalid(String plain) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < plain.length(); i++) {
			if (ALPHABET.indexOf(plain.charAt(i)) >= 0)
				b.append(plain.charAt(i));
			else
				System.out.println("Stripping letter: \"" + plain.charAt(i) + "\"");
		}
		return b.toString();
	}

	private static boolean isValidCodeWord(String code) {
		for (int i = 0; i < code.length(); i++) {
			if (!ALPHABET.contains(code.substring(i, i + 1)))
				return false;
		}

		return true;
	}

	private static String rotationCipher(String plainText, int shiftValue) {
		String cipherText = "";
		for (int i = 0; i < plainText.length(); i++) {
			char c = plainText.charAt(i);
			int shiftedIndex = ALPHABET.indexOf(c) + shiftValue;
			while (shiftedIndex < 0)
				shiftedIndex += ALPHABET.length();
			shiftedIndex %= ALPHABET.length();
			cipherText += ALPHABET.charAt(shiftedIndex);
		}
		return cipherText;
	}

	public static String rotationCipherEncrypt(String plain, int movement, String alphabet) {
		return rotationCipher(plain, movement);
	}

	public static String rotationCipherDecrypt(String plain, int movement, String alphabet) {
		return rotationCipher(plain, -movement);
	}

	public static String rotationCipherCrack(String encrypted, String alphabet) {
		return rotcipherCrack(encrypted, ALPHABET);
	}

}


//private static String vigenereCipherCrackWithFrequency(String encrypted, String alphabet) {
//String decryptedCode = "";
//
//for (int currentPasswordLength = 1; currentPasswordLength < encrypted.length(); currentPasswordLength++) {
//	String password = "";
//	String[] groups = splitIntoGroups(encrypted, currentPasswordLength);
//	
//	for (String group : groups) {
//		double leastDifferenceInBetweenFrequencies = Integer.MAX_VALUE;
//		double totalFrequencyForShiftAmount = 0.0;
//		double totalEnglishFrequencyForShiftAmount = 0.0;
//		double compareNtoActualFrequency = 0.0;
//		int bestShiftAmount = 0;
//		
//		for (int shiftAmount = 0; shiftAmount < ALPHABET.length(); shiftAmount++) {
//			String decoded = findNCodeLetter(group, shiftAmount); //get frequency of all letters for one shiftamount
//			Bag bag = new Bag();
//			bag.add(decoded);
//			
//			for (int letter = 0; letter < decoded.length(); letter++) {
//				int numberOfNLetter = bag.getNumOccurances(alphabet.substring(letter, letter + 1));
//				totalFrequencyForShiftAmount += numberOfNLetter/ (double) bag.getTotalWords();
//				totalEnglishFrequencyForShiftAmount += numberOfNLetter; //or whatever it is
//			}
//			
//			compareNtoActualFrequency = Math.abs(totalFrequencyForShiftAmount - totalEnglishFrequencyForShiftAmount);
//			if (compareNtoActualFrequency < leastDifferenceInBetweenFrequencies) {
//				leastDifferenceInBetweenFrequencies = compareNtoActualFrequency;
//				bestShiftAmount = shiftAmount;
//			}
//		}
//		password += findLetterCorrespondingTo(bestShiftAmount);
//	}
//	String possibleDecryptedCode = vigenereCipherDecrypt(encrypted, password);
//	if (isEnglish(possibleDecryptedCode)) return possibleDecryptedCode;		
//}
//return decryptedCode;
//}