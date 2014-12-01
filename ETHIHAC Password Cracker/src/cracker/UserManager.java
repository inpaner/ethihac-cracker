package cracker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class UserManager {
	private final File folder = new File("data");
	/**
	 * Key: username <br>
	 * Value: $ hash_algorithm $ salt $ hash
	 */
	private HashMap<String, String> shadowCache = new HashMap<>();
	private List<User> users = new ArrayList<>();
	
	
	UserManager() {
		readShadowToCache();
		generateUsers();
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
					String username = splitLine[0];
					User user = new User(userid, username, shadowCache.get(username));
					users.add(user);
				}
			}
		
		} catch (FileNotFoundException ex) {
		} catch (IOException ex) {}
	}
	
	
	List<User> getUsers() {
		return users;
	}
}
