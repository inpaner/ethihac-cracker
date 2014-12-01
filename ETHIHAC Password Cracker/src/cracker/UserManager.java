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


public class UserManager {
	private final File folder = new File("data");
	/**
	 * Key: username <br>
	 * Value: [<i>null</i>, hash algorithm, salt, hash]
	 * <br><br>
	 * Invalid users would have a value array with length 1.
	 */
	private HashMap<String, String[]> shadowCache = new HashMap<>();
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
				String[] splitHash = splitLine[1].split("\\$");
				shadowCache.put(splitLine[0], splitHash);
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
	
	
	private void getPassword(String username) {
		String[] shadowLine = shadowCache.get(username);
		String salt = shadowLine[2];
		byte[] actualHash = shadowLine[3].getBytes();
		
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-512");
			
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		
		System.out.println(username + " : ");
		System.out.println(shadowLine[3]);
		for (String entry : dictionary.getEntries()) {
			String saltedEntry = entry + salt;
			digest.update(saltedEntry.getBytes());
			byte[] hashToTest = digest.digest();
			if (Arrays.equals(actualHash, hashToTest)) {
				System.out.print(entry);
			}
		}
		
		System.out.println();
	}
}
	
