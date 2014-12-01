package cracker;

public class User {
	private int id;
	private String username;
	private String encryptedHash;
	private String encryptedSalt;
	private String password = "";
	private boolean passwordFound = false;
	
	
	User(int id, String username, String encryptedHash) {
		this.id = id;
		this.username = username;
		this.encryptedHash = encryptedHash;
		
		String[] splitHash = encryptedHash.split("\\$");
		encryptedSalt = "$6$" + splitHash[2]; 
	}

	
	int getId() {
		return id;
	}


	String getUsername() {
		return username;
	}


	String getEncryptedHash() {
		return encryptedHash;
	}


	String getPassword() {
		return password;
	}

	
	void setPassword(String password) {
		this.password = password;
		passwordFound = true;
	}

	
	String getEncryptedSalt() {
		return encryptedSalt;
	}


	boolean isPasswordFound() {
		return passwordFound;
	}

	
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder(username);
		string.append(": ");
		
		if (passwordFound) {
			string.append(password);
		} else {
			string.append("[not found]");
		}
		return string.toString();
	}
}
