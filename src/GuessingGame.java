import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

public class GuessingGame {
	
	/**
	 * Create a random code value for the guessing game.
	 * @param range Maximum allowed value
	 * @return a number between 1 and range, inclusive
	 */
	public static int randomCodeValue(int range) {
		return (int)(Math.random()*range) + 1;
	}
	
	public static int[] randomCode(int length, int range) {
		int[] code = new int[length];
		for (int i = 0; i < length; i++) {
			code[i] = randomCodeValue(range);
		}
		return code;
	}
	
	/**
	 * Check to see that we've won the game (codes are equal).
	 * Assume both code array have the same length.
	 * @param userCode
	 * @param secretCode
	 * @return
	 */
	public static boolean haveWon(int[] userCode, int[] secretCode) {
		if (userCode.length != secretCode.length) {
			// We can never win if the guess is not the same lenth as the code!
			return false;
		}
		for (int i = 0; i < userCode.length; i++) {
			if (userCode[i] != secretCode[i]) {
				return false;
			}
		}
		return true; // The two codes must be equal if we got here.
	}
	
	/**
	 * Count matches between the two codes. Assume both have the same length.
	 * @param userCode
	 * @param secretCode
	 * @return
	 */
	public static int countMatches(int[] userCode, int[] secretCode) {
		int counter = 0;
		for (int i = 0; i <= userCode.length; i++) {
			if (userCode[i] == secretCode[i]) {
				counter = counter + 1;
			}
		}
		return counter;
	}
	
	/**
	 * Count the right code values that are in the wrong locations. Only works
	 * for codes of length 4.
	 * @param userCode
	 * @param secretCode
	 * @return
	 */
	public static int countRightValuesInWrongPlaces(int[] userCode, int[] secretCode) {
		boolean[] selectedSecretCodeElements = {false, false, false, false};
		// First, we must set aside the perfectly matching elements
		for (int i = 0; i < secretCode.length; i++) {
			if (userCode[i] == secretCode[i]) {
				selectedSecretCodeElements[i] = true;
			}
		}
		// Next, we must match up the user code elements against the secret code elements
		int count = 0;
		for (int i = 0; i < userCode.length; i++) {
			if (userCode[i] == secretCode[i]) {
				// userCode[i] is already matched, so ignore it
				continue;
			}
			for (int j = 0; j < secretCode.length; j++) {
				if (userCode[i] == secretCode[j] && !selectedSecretCodeElements[j]) {
					// We have a match!
					count++;
					// Make sure we don't match against the same secret code
					// element twice.
					selectedSecretCodeElements[j] = true;
					// Make sure this code element doesn't match twice against two
					// secret code elements.
					break;
				}
			}
		}
		return count;
	}
	
	
	public static void convertEnteredCharactersToCode(int[] enteredCharacters, int[] code) {
		// Convert first character
		for (int i = 0; i < enteredCharacters.length; i++) {			
			code[i] = enteredCharacters[i] - '0';
		}
	}
	
	public static void main(String[] args) throws IOException {
		// Represent individual code values as ints
		// 1-6 are the valid values
		// 0 is an invalid value (uninitialized)
		
		/* Test code for countRightValuesInWrongPlaces
		for (int i = 0; i < 50; i++) {
			int[] code1 = randomCode(4, 6), code2 = randomCode(4,6);
			System.out.println(Arrays.toString(code1) + " " +Arrays.toString(code2)+": "+countRightValuesInWrongPlaces(code1, code2));
		}
		*/
		
		// Generate random code
		
		int[] secretCode = {1,2,3,4};//randomCode(4, 6);
		int[] userCode = new int[4]; // initialized with 0
		while (!haveWon(userCode, secretCode)) {
			System.out.println("Please enter your guess using digits 1-6");
			int[] typedDigits = {0,0,0,0};
			for (int i = 0; i < typedDigits.length; i++) {
				typedDigits[i] = System.in.read();
			}
			while (System.in.read() != '\n'); // Read until newline (enter key)
			convertEnteredCharactersToCode(typedDigits, userCode);
			System.out.println("Number correct: "+countMatches(userCode, secretCode));
			System.out.println("Number correct: "+countRightValuesInWrongPlaces(userCode, secretCode));
		}
		System.out.println("You won!");
	}
}
