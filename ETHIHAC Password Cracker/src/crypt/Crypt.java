package crypt;

public enum Crypt {
	MD5, SHA256, SHA512;

	public static Crypt extractCryptTypeFromHash(String hash) {

		char hashChar = 'a';

		if (hash.length() > 1)
			hashChar = hash.charAt(1);

		switch (hashChar) {
		case '1':
			return MD5;
		case '5':
			return SHA256;
		case '6':
			return SHA512;
		default:
			return SHA512;
		}

	}
}
