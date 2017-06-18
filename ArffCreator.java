import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ArffCreator {

	public static void createArff(int[][] instance, ArrayList<String> vocab,
			int[] classFeature, String string) {

		Writer writer = null;

		try {
			
				writer = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(string+"1.arff"), "utf-8"));
				Iterator<String> itr = vocab.iterator();

				writer.write("@relation perceptrons");
				writer.write(System.getProperty("line.separator"));
				while (itr.hasNext()) {
					writer.write("@attribute  " + itr.next() + "  numeric");
					writer.write(System.getProperty("line.separator"));
				}
				writer.write("@attribute  Class numeric  ");
				writer.write(System.getProperty("line.separator"));
				writer.write("@data");
				writer.write(System.getProperty("line.separator"));
				for (int i = 0; i < instance.length; i++) {
					for (int j = 0; j < instance[i].length; j++) {
						writer.write(instance[i][j] + " , ");
					}
					if (classFeature[i] == -1)
						writer.write("0");
					else if (classFeature[i] == 1)
						writer.write("1");
					writer.write(System.getProperty("line.separator"));
					writer.write(System.getProperty("line.separator"));

				
				

			}
		} catch (IOException ex) {

		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
			}
		}

	}

}
