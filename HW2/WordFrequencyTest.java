/*
 * I worked with D. Jaeger on this code.
 */
import java.io.*;
import java.text.*;

public class WordFrequencyTest{
	private static int letterCount 		= 0;
	private static int totalWordCount 	= 0;
	//private static int tokenCount 		= 0;
	//private static int k		 		= 0;
	//private static int x				= 0;
	private static int[] freq = new int[8];


	private static int[] readFile(String filename) throws IOException{

		try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
			String line;

			while ((line = reader.readLine()) != null) {
				line = line.replaceAll("[^A-Za-z0-9-_]+", " ");
				String text = line;
				text += new String(" ");
				
				for(char ch:text.toCharArray()){
					if(Character.isLetterOrDigit(ch)){
						letterCount++;
					}
					else if(Character.compare(ch, '-') == 0){
					}
					else if(Character.compare(ch, '_') == 0){
					}
					else if(Character.isWhitespace(ch)){
						if (letterCount == 0){

						}
						else if (letterCount >= 8){
							freq[7]++;
							letterCount = 0;
							totalWordCount++;
						}
						else{
							freq[letterCount-1]++;
							letterCount = 0;
							totalWordCount++;
						}
					}
				}
			}
			reader.close();	
			return freq;
		}						
	}

	public static int getWordCount(){
		return totalWordCount;
	}
		

	public static void main(String[] args) throws IOException{

		PrintStream fileOut = new PrintStream("Q1 Unthreaded Output.txt");
		System.setOut(fileOut);

		long start =  System.currentTimeMillis();
		
		System.out.println("Unthreaded Version");

		//int[] frequency = readFile("test.txt");
		//int[] frequency = readFile("test2.txt");
		int[] frequency = readFile("enwik9");
 
		double percentage = 0;
		int wordCount = getWordCount();

		System.out.println("Total Number Of Words: " + wordCount);

		for (int i=0; i<frequency.length; i++){
			percentage = ((double)frequency[i]) / wordCount;
			percentage = percentage * 100;

			if(i==7){
				System.out.printf("8 or more letter words: %d words, %.2f%%\n", frequency[i], percentage);
			}
			else{
				System.out.printf("%d letter words: %d words, %.2f%%\n", i+1, frequency[i], percentage);
			}
		}
		
		long end = System.currentTimeMillis();

		NumberFormat formatter = new DecimalFormat("#0.000");
		System.out.println("Runtime: " + formatter.format((end - start) / 1000d) + " seconds");
	}
}
