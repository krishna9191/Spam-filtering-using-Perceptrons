import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataSet {

	static String[] getStopWords(String string) throws IOException {

		ArrayList<String> data1 = new ArrayList<String>();
		Scanner sc2 = null;
		try {
			sc2 = new Scanner(new File(string));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (sc2.hasNextLine()) {
			Scanner s2 = new Scanner(sc2.nextLine());

			while (s2.hasNext()) {
				String s = s2.next();

				data1.add(s);

			}
			s2.close();
		}

		String[] data = new String[data1.size()];
		Iterator<String> itr = data1.iterator();
		int i = 0;
		while (itr.hasNext()) {
			data[i++] = (String) itr.next();

		}

		sc2.close();
		return data;

	}

	ArrayList<String> getDataSet(String folder) {
		ArrayList<String> results = new ArrayList<String>();
		File[] files = new File(folder).listFiles();
		// If this pathname does not denote a directory, then listFiles()
		// returns null.

		for (File file : files) {
			if (file.isFile()) {
				results.add(file.getName());
			}
		}
		return results;
	}

	String[] getContent(String string) throws IOException {

		ArrayList<String> data1 = new ArrayList<String>();
		Scanner sc2 = null;
		try {
			sc2 = new Scanner(new File(string));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (sc2.hasNextLine()) {
			Scanner s2 = new Scanner(sc2.nextLine());

			while (s2.hasNext()) {
				String s = s2.next();
				if (IsString(s.charAt(0))) {
					data1.add(s);
				}
			}
			s2.close();
		}

		String[] data = new String[data1.size()];
		Iterator<String> itr = data1.iterator();
		int i = 0;
		while (itr.hasNext()) {
			data[i++] = (String) itr.next();

		}

		sc2.close();
		return data;

	}

	String[] getContent(String string, String[] stop) throws IOException {

		ArrayList<String> data1 = new ArrayList<String>();
		Scanner sc2 = null;
		try {
			sc2 = new Scanner(new File(string));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (sc2.hasNextLine()) {
			Scanner s2 = new Scanner(sc2.nextLine());

			while (s2.hasNext()) {
				String s = s2.next();
				if (IsString(s.charAt(0))) {
					if (s != null)
						data1.add(s);
				}
			}
			s2.close();
		}

		String[] data = new String[data1.size()];
		Iterator<String> itr = data1.iterator();
		int i = 0;
		while (itr.hasNext()) {
			String word = (String) itr.next();
			int j = 0;
			for (int q = 0; q < stop.length; q++) {
				if (word.equalsIgnoreCase(stop[q]))
					j = 1;

			}

			if (j == 1 || word == null) {
			} else
				data[i++] = word;

		}

		sc2.close();
		return data;

	}

	private boolean IsString(char s) {
		if (s >= 65 && s <= 90)
			return true;
		if (s >= 97 && s <= 122)
			return true;
		return false;
	}

}
