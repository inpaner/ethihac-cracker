package cracker;

import crypt.Sha512Crypt;

public class Cracker {
	Dictionary dictionary;
	UserManager userManager;
	
	
	public Cracker() {
		dictionary = new Dictionary();
		userManager = new UserManager();
	}
	
	
	public void begin() {
		for (User user : userManager.getUsers()) {
			setPassword(user);
			System.out.println(user);
		}
	}
	
	
	private void setPassword(User user) {
		for (String entry : dictionary.getEntries()) {
			String attemptedHash = Sha512Crypt.Sha512_crypt(entry, user.getEncryptedSalt(), 0);
			if (user.getEncryptedHash().equals(attemptedHash)) {
				user.setPassword(entry);
				return;
			}
		}
	}
}
