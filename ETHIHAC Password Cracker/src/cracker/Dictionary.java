package cracker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;


public class Dictionary {
	private final File folder = new File("dictionary");
	
	private HashSet<String> entries = new HashSet<>();
	
	Dictionary() {
		loadFiles();
	}
	
	
	private void loadFiles() {
		String[] dictionaries = folder.list();
		for (int i = 0; i < dictionaries.length; i++) {
			String dictionary = folder.toString() + "/" + dictionaries[i];
			readDictionary(new File(dictionary));
		}
	}
	
	
	private void readDictionary(File dictionary) {
		try (BufferedReader reader = new BufferedReader(new FileReader(dictionary))) {
			String line;
			while ((line = reader.readLine()) != null) {
				entries.add(line);
			}
		
		} catch (FileNotFoundException ex) {
		} catch (IOException ex) {} 
	}
	
	
	HashSet<String> getEntries() {
		return entries;
	}
}
