

public class CryptoManager {
	
	private static final char LOWER_BOUND = ' ';
	private static final char UPPER_BOUND = '_';
	private static final int RANGE = UPPER_BOUND - LOWER_BOUND + 1;

	/**
	 * This method determines if a string is within the allowable bounds of ASCII codes 
	 * according to the LOWER_BOUND and UPPER_BOUND characters
	 * @param plainText a string to be encrypted, if it is within the allowable bounds
	 * @return true if all characters are within the allowable bounds, false if any character is outside
	 */
	public static boolean stringInBounds (String plainText) {
		
		boolean isWithinBounds = true; //create bool that will keep track if string is within bounds
		char currentChar; //create char variable that will go through the char in the string
		for(int i = 0; i < plainText.length(); i++) { //iterate through the string
			currentChar = plainText.charAt(i); //take character at index i 
			if(currentChar < LOWER_BOUND || currentChar > UPPER_BOUND) //if character is lower or greater
				isWithinBounds = false; //we change our bool to false
		}
		return isWithinBounds;
	}

	/**
	 * Encrypts a string according to the Caesar Cipher.  The integer key specifies an offset
	 * and each character in plainText is replaced by the character \"offset\" away from it 
	 * @param plainText an uppercase string to be encrypted.
	 * @param key an integer that specifies the offset of each character
	 * @return the encrypted string
	 */
	public static String encryptCaesar(String plainText, int key) {
		char currentChar;
		char newChar; 
		String encryptedText = ""; 
		int asciiValue;
		while(key > UPPER_BOUND) { //before we start encrypting we want to have our key within our bounds
			key -= RANGE; //if it is over, we continuously subtract by the range until it is within bounds
		}
		for(int i = 0; i < plainText.length(); i++) { //iterate through string
			currentChar = plainText.charAt(i); //take character at index i
			asciiValue = currentChar; //take the ascii dec value of the current character
			asciiValue += key; //we add that dec value with the key 
			if(asciiValue > UPPER_BOUND) { 
				asciiValue -= UPPER_BOUND;
				asciiValue += LOWER_BOUND; 
			} //this if statement is for the "wrap around" in our bounds if the new ascii value goes outside our bound
			newChar = (char) asciiValue; //we now convert the new ascii dec value to a char again
			encryptedText += newChar; //we add this character to our string which will be the encrypted text
		}
		return encryptedText;	
	}
	
	/**
	 * Encrypts a string according the Bellaso Cipher.  Each character in plainText is offset 
	 * according to the ASCII value of the corresponding character in bellasoStr, which is repeated
	 * to correspond to the length of plainText
	 * @param plainText an uppercase string to be encrypted.
	 * @param bellasoStr an uppercase string that specifies the offsets, character by character.
	 * @return the encrypted string
	 */
	public static String encryptBellaso(String plainText, String bellasoStr) {
		int j = 0; //need separate iterator to go through the key since key may be smaller than original text
		char ogChar; //char at original text
		char keyChar; //char at key text 
		char newChar; //the new char after doing encryption
		int asciiValue1; 
		int asciiValue2; //make two ascii value variables to hold each the key and the original text
		int newAscii; //this will hold the new acquired ascii value after performing the encryption
		String encryptedText = "";
		for(int i = 0; i < plainText.length(); i ++) {
			ogChar = plainText.charAt(i);
			asciiValue1 = ogChar; //we get our ascii dec value of the plaintext
			
			keyChar = bellasoStr.charAt(j);
			asciiValue2 = keyChar; //we get our ascii dec value of the key 
			
			newAscii = asciiValue1 + asciiValue2; 
			do {
				newAscii -= RANGE;
			} while(newAscii > UPPER_BOUND); //we add the 2 dec values then subtract 64; if greater than 95 
								      //we subtract another 64. Use do while for post condition check
			newChar = (char) newAscii; //cast the new dec value to its corresponding char
			encryptedText += newChar; //add the new char to our string to create encrypted string
			
			if(j == bellasoStr.length() - 1)
				j = 0;
			else //we need this if else statement to check if our key iterator is going to go out of bounds
				j++;  //we set it back to 0 if it is; otherwise we add 1 to go to the next index
		}
		return encryptedText; 
		
	}
	
	/**
	 * Decrypts a string according to the Caesar Cipher.  The integer key specifies an offset
	 * and each character in encryptedText is replaced by the character \"offset\" characters before it.
	 * This is the inverse of the encryptCaesar method.
	 * @param encryptedText an encrypted string to be decrypted.
	 * @param key an integer that specifies the offset of each character
	 * @return the plain text string
	 */
	public static String decryptCaesar(String encryptedText, int key) {
		char currentChar; 
		char newChar; 
		String decryptedText = "";
		int asciiValue;
		while(key > UPPER_BOUND) { //before we start decrypting we want to have our key within our bounds
			key -= RANGE; //if it is over, we continuously subtract by the range until it is within bounds
		}
		for(int i = 0; i < encryptedText.length(); i++) { //iterate through string
			currentChar = encryptedText.charAt(i); //take char at index i 
			asciiValue = currentChar; //get the ascii dec value of the char
			asciiValue -= key; //we subtract that value by the key to get our orignial char
			if(asciiValue < LOWER_BOUND) { 
				asciiValue = LOWER_BOUND - asciiValue;
				asciiValue = UPPER_BOUND - asciiValue; 
			} //we use this if statement to wrap our ascii value around to the upper bound
			//if the ascii value goes under our lower bound
			newChar = (char) asciiValue; 
			decryptedText += newChar; 
		}
		return decryptedText;
	}
	
	/**
	 * Decrypts a string according the Bellaso Cipher.  Each character in encryptedText is replaced by
	 * the character corresponding to the character in bellasoStr, which is repeated
	 * to correspond to the length of plainText.  This is the inverse of the encryptBellaso method.
	 * @param encryptedText an uppercase string to be encrypted.
	 * @param bellasoStr an uppercase string that specifies the offsets, character by character.
	 * @return the decrypted string
	 */
	public static String decryptBellaso(String encryptedText, String bellasoStr) {
		char currentChar;
		char keyChar;
		char ogChar;
		int totalAscii;
		int keyAsciiValue;
		int ogAsciiValue; 
		String decryptedStr = "";
		int j = 0;
		for(int i = 0; i < encryptedText.length(); i ++) {
			currentChar = encryptedText.charAt(i); //take char at index i
			totalAscii = currentChar; //get the ascii dec value of char at index i
			totalAscii += RANGE; 
			
			keyChar = bellasoStr.charAt(j); //we take the char at corresponding index j 
			keyAsciiValue = keyChar; //get the ascii dec value of the key corresponding index j 
			
			if(totalAscii - keyAsciiValue < LOWER_BOUND)
				totalAscii += RANGE; //if total ascii value - the dec value of the key char
									//was under the range, we add by the range again bc it means we subtracted 
									//by the range twice
			
			ogAsciiValue = totalAscii - keyAsciiValue; //to get original ascii value we subtract total by key value
			ogChar = (char) ogAsciiValue; //this gives us our original dec value 
			decryptedStr += ogChar; //add to our decrypted string
			
			if(j == bellasoStr.length() - 1)
				j = 0;
			else //we need this if else statement to check if our key iterator is going to go out of bounds
				j++;  //we set it back to 0 if it is; otherwise we add 1 to go to the next index
		}
		return decryptedStr;
	}
}
