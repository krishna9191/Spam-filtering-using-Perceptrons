import java.io.IOException;
import java.util.*;

public class Perceptrons {

	public static void main(String[] args) throws IOException {

		String removeStopWords = "no";
		Scanner input = new Scanner(System.in);
		System.out
				.println("Enter the path of folder  which contains  test and training data sets Example: E:\\ ");
		String path = input.next();
		

		System.out
				.println("press NO if you dont want to remove stop words and YES if you want to remove it ");
		removeStopWords = input.next();
		System.out.println("enter the learning rate");
		double rate=input.nextDouble();
		System.out.println("enter the Max no. of iterations");
		int iterations=input.nextInt();
		input.close();

		String[] stopWords = DataSet.getStopWords(path + "\\stop.txt");
		ArrayList<String> dataSet = new ArrayList<String>();
		String[] Folder = new String[2];
		Folder[0] = path + "\\train\\spam";
		DataSet d = new DataSet();

		dataSet = d.getDataSet(Folder[0]);
		String[][][] mail = new String[2][][];
		mail[0] = new String[dataSet.size()][];
		Iterator<String> itr = dataSet.iterator();
		int i = 0;
		if (removeStopWords.equalsIgnoreCase("yes")) {
			while (itr.hasNext())
				mail[0][i++] = d.getContent(Folder[0] + "\\" + itr.next(),
						stopWords);

		} else {

			while (itr.hasNext())
				mail[0][i++] = d.getContent(Folder[0] + "\\" + itr.next());

		}

		ArrayList<String> dataSet2 = new ArrayList<String>();
		DataSet d2 = new DataSet();
		Folder[1] = path + "\\train\\ham";
		dataSet2 = d2.getDataSet(Folder[1]);
		mail[1] = new String[dataSet2.size()][];
		itr = dataSet2.iterator();
		i = 0;

		if (removeStopWords.equalsIgnoreCase("yes")) {

			while (itr.hasNext())
				mail[1][i++] = d.getContent(Folder[1] + "\\" + itr.next(),
						stopWords);

		}

		else {

			while (itr.hasNext())
				mail[1][i++] = d.getContent(Folder[1] + "\\" + itr.next());
		}

		// /////////////////////////////////////////////////
		ArrayList<String> vocab = new ArrayList<String>();
		vocab = getVocab(mail);

		ArrayList<String> testDataSet = new ArrayList<String>();
		String[] testMailFolder = new String[2];
		testMailFolder[0] = path + "\\test\\spam";
		DataSet dTest = new DataSet();
		testDataSet = dTest.getDataSet(testMailFolder[0]);
		String[][][] testMail = new String[2][][];
		testMail[0] = new String[testDataSet.size()][];
		itr = testDataSet.iterator();
		i = 0;
		if (removeStopWords.equalsIgnoreCase("yes")) {
			while (itr.hasNext())
				testMail[0][i++] = dTest.getContent(testMailFolder[0] + "\\"
						+ itr.next(), stopWords);
		}

		else {
			while (itr.hasNext())
				testMail[0][i++] = dTest.getContent(testMailFolder[0] + "\\"
						+ itr.next());
		}

		// //////////////////////////////////////////////

		ArrayList<String> testDataSet2 = new ArrayList<String>();
		testMailFolder[1] = path + "\\test\\Ham";
		DataSet dTest2 = new DataSet();
		testDataSet2 = dTest2.getDataSet(testMailFolder[1]);
		testMail[1] = new String[testDataSet2.size()][];
		itr = testDataSet2.iterator();
		i = 0;
		if (removeStopWords.equalsIgnoreCase("yes")) {
			while (itr.hasNext())
				testMail[1][i++] = dTest2.getContent(testMailFolder[1] + "\\"
						+ itr.next(), stopWords);
		}

		else {
			while (itr.hasNext())
				testMail[1][i++] = dTest2.getContent(testMailFolder[1] + "\\"
						+ itr.next());
		}

		// ///////////////////////////////////////////////////////////////////

		int[][] instance = new int[mail[0].length + mail[1].length][vocab
				.size() + 1];
		int[] classFeature = new int[mail[0].length + mail[1].length];

		for (int a = 0; a < 2; a++) {
			for (i = 0; i < mail[a].length; i++) {
				String[] data = mail[a][i];
				classFeature[a * mail[0].length + i] = -1+a*2;
				itr = vocab.iterator();
				int m = 0;
				instance[(a * (mail[0].length)) + i][m++] = 1;
				for (; m <= vocab.size(); m++) {
					String word = itr.next();
					instance[(a * (mail[0].length)) + i][m] = (WordCount(data,
							word));

				}

			}

		}

		int[][] testInstance = new int[testMail[0].length + testMail[1].length][vocab
				.size() + 1];
		int[] testClassFeature = new int[testMail[0].length
				+ testMail[1].length];

		for (int a = 0; a < 2; a++) {
			for (i = 0; i < testMail[a].length; i++) {
				String[] data = testMail[a][i];
				testClassFeature[a * testMail[0].length + i] = a*2-1;
				itr = vocab.iterator();
				int m = 0;
				testInstance[(a * (testMail[0].length)) + i][m++] = 1;
				for (; m <= vocab.size(); m++) {
					String word = itr.next();
					testInstance[(a * (testMail[0].length)) + i][m] = (WordCount(
							data, word));

				}

			}

		}
		
		double[] weights = train(instance, classFeature,rate,iterations);


		test(weights,testInstance,testClassFeature);



	}

	private static void test(double[] weights, int[][] testInstance, int[] testClassFeature) {
		 double accuracyLR = 0;
		 for (int i = 0; i < testInstance.length; i++)
		 {
			 if (testClassFeature[i] == getClass(testInstance[i], weights))
				 accuracyLR++; 
		}
		 accuracyLR = accuracyLR * 100.0 / testInstance.length; 
		 System.out .println( "accuracy =      "+ accuracyLR);		
	}

	private static int WordCount(String[] mail, String word) {
		int count = 0, i;
		for (i = 0; i < mail.length; i++) {

			if (word.equalsIgnoreCase(mail[i])) {
				count++;
			}

		}
		return count;
	}

	private static ArrayList<String> getVocab(String[][][] mail) {

		ArrayList<String> vocab = new ArrayList<String>();
		int i = 0, n = 0;
		String[] data;
		for (n = 0; n < 2; n++) {
			for (i = 0; i < mail[n].length; i++) {
				data = new String[mail[n][i].length];
				data = mail[n][i];
				for (int j = 0; j < data.length && data[j] != null; j++) {
					if (!vocab.contains(data[j])) {
						vocab.add(data[j]);
					}
				}

			}
		}
		System.out.println("vocabulary size= " + vocab.size());
		return vocab;

	}

	private static int sigmoid(double z) {
		if ((1 / (1 + Math.exp(-z))) > 0)
			return 1;
		else
			return -1;
	}
	public static double[] train(int[][] instances, int[] classFeature,double rate,int ITERATIONS){
		double[] weights = new double[instances[0].length ];
		int n;
		for (n = 0; n < ITERATIONS; n++) {
			for(int i=0;i<instances.length;i++)
			{
				double predicted=getClass(instances[i],weights);
				double diff=classFeature[i]-predicted;
				if(diff!=0)
					for (int jWeight = 0; jWeight < weights.length; jWeight++)

					{				
						double delta = rate * (diff) * instances[i][jWeight];
						weights[jWeight] = weights[jWeight] + delta;			
					}
			}
/*			System.out.print(n + "th iteration of perceptron    " );
			//if(n%10==0)
			{
				test(weights,testInstance,testClassFeature);
			}
			System.out.println();
		*/
		}
		return weights;
	}

	static int getClass(int[] x, double[] weights) {
		double logit =0.0;
		int i;
		for (i = 0; i < weights.length; i++) {
			logit += weights[i] * x[i];
		}
		if(logit>0)
			return 1;
		else 
			return -1;
}



}
