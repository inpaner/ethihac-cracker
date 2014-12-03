package cracker;

import crypt.Crypt;
import crypt.MD5Crypt;
import crypt.Sha256Crypt;
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

		Crypt cryptType = user.getCryptType();

		for (String entry : dictionary.getEntries()) {

			String attemptedHash = "";

			if (cryptType.equals(Crypt.MD5))
				attemptedHash = MD5Crypt.crypt(entry, user.getEncryptedSalt());
			else if (cryptType.equals(Crypt.SHA256))
				attemptedHash = Sha256Crypt.Sha256_crypt(entry, user.getEncryptedSalt(), 0);
			else if (cryptType.equals(Crypt.SHA512))
				attemptedHash = Sha512Crypt.Sha512_crypt(entry, user.getEncryptedSalt(), 0);

			if (user.getEncryptedHash().equals(attemptedHash)) {
				user.setPassword(entry);
				return;
			}
		}
	}
}
