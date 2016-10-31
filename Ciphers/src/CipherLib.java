
public interface CipherLib {
	public String vigenereCipherEncrypt(String cipher, String password, String alphabet);
	public String vigenereCipherDecrypt(String cipher, String password, String alphabet);
	
	public String rotationCipherEncrypt(String cipher, String password, String alphabet);
	public String rotationCipherDecrypt(String cipher, String password, String alphabet);

	// returns the decoded text
	public String rotationCipherCrack(String cipher, String alphabet);

	// returns the password
	public String vigenereCipherCrackThreeLetter(String cipher, String alphabet);
	
	// returns the password
	public String vigenereCipherCrack(String cipher, String alphabet);
}