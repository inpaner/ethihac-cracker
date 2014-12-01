package cracker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;

import crypt.Sha512Crypt;


public class UserManager {
	private final File folder = new File("data");
	/**
	 * Key: username <br>
	 * Value: $ hash_algorithm $ salt $ hash
	 */
	private HashMap<String, String> shadowCache = new HashMap<>();
	private Dictionary dictionary;
	
	public UserManager() {
		dictionary = new Dictionary();
		readShadowToCache();
		generateUsers();
	}
	
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		UserManager um = new UserManager();
		long end = System.currentTimeMillis();
		
		
	}
	
	
	private void readShadowToCache() {
		File shadow = new File(folder.toString() + "/" + "shadow");
		try (BufferedReader reader = new BufferedReader(new FileReader(shadow))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] splitLine = line.split(":");
				shadowCache.put(splitLine[0], splitLine[1]);
			}
		
		} catch (FileNotFoundException ex) {
		} catch (IOException ex) {} 
	}
	
	
	private void generateUsers() {
		File passwd = new File(folder.toString() + "/" + "passwd");
		try (BufferedReader reader = new BufferedReader(new FileReader(passwd))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] splitLine = line.split(":");
				int userid = Integer.valueOf(splitLine[2]);
				if (userid >= 1000 && userid != 65534) { // 65534 is 'nobody'.
					getPassword(splitLine[0]);
				}
			}
		
		} catch (FileNotFoundException ex) {
		} catch (IOException ex) {}
	}
	
	
	// TODO clean up printouts
	private void getPassword(String username) {
		String encryptedHash = shadowCache.get(username);
		String[] splitHash = encryptedHash.split("\\$");
		String encryptedSalt = "$6$" + splitHash[2]; 
		
		System.out.println(username);
		long start = System.currentTimeMillis();
		for (String entry : dictionary.getEntries()) {
			String attemptedHash = Sha512Crypt.Sha512_crypt(entry, encryptedSalt, 0);
			if (encryptedHash.equals(attemptedHash)) {
				System.out.println(entry);
				System.out.println(System.currentTimeMillis() - start + " ms");
				break;
			}
		}
		
		System.out.println();
	}
}
