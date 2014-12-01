package cracker;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Dictionary {
	private final File folder = new File("dictionary");
	
	private HashSet<String> entries = new HashSet<>();
	private ArrayList<String> list = new ArrayList<>(); 
	
	Dictionary() {
		loadFiles();
	}
	
	public static void main(String[] args) {
		Dictionary d = new Dictionary();
		
		for (String entry : d.entries) {
			int count = 0;
			for (String arrayEntry : d.list) {
				if (entry.equals(arrayEntry))
					count++;
			}
			if (count > 1)
				System.out.println(entry);
		}
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
				list.add(line);
			}
		
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} 
	}
	
	
	HashSet<String> getEntries() {
		return entries;
	}
	
}
