/*
 * I worked with D. Jaeger on this code.
 */
import java.io.*;
import java.text.*;

public class WordFrequencyTest{
	private static int charCount 		= 0;			//var to hold the chars in a word
	private static int wordCount 		= 0;			//var to hold the total words
	private static int[] freqCount 		= new int[8];	//array to hold the frequency count


	/*
	 * Method to read the input file's words, test that the words are made up of (a-z,A-Z,0-9), and determine the word-length frequency
	 */
	private static int[] readFile(String filename) throws IOException{
		try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
			String line;

			while ((line = reader.readLine()) != null) {
				line = line.replaceAll("[^A-Za-z0-9-_]+", " ");
				String text = line;
				text += new String(" ");
				
				//start the counts
				for(char ch:text.toCharArray()){
					if(Character.isLetterOrDigit(ch)){
						charCount++;
					}
					else if(Character.compare(ch, '-') == 0){
					}
					else if(Character.compare(ch, '_') == 0){
					}
					else if(Character.isWhitespace(ch)){
						if (charCount == 0){

						}
						else if (charCount >= 8){
							freqCount[7]++;
							charCount = 0;
							wordCount++;
						}
						else{
							freqCount[charCount-1]++;
							charCount = 0;
							wordCount++;
						}
					}
				}
			}
			reader.close();	
			return freqCount;
		}						
	}

	public static int getWordCount(){
		return wordCount;
	}
		

	public static void main(String[] args) throws IOException{

		//Set up the output file
		PrintStream fileOut = new PrintStream("Version-1 Output.txt");
		System.setOut(fileOut);

		//Keep track of the time to run in ms
		long start =  System.currentTimeMillis();
		
		System.out.println("Version 1 Output");

		//int[] frequency = readFile("test.txt");
		//int[] frequency = readFile("test2.txt");
		int[] frequency = readFile("enwik9");
 
		double percentage = 0;
		int wordCount = getWordCount();

		//Set up the output
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

		NumberFormat timeFormat = new DecimalFormat("#0.000");
		System.out.println("Runtime: " + timeFormat.format((end - start) / 1000d) + " seconds");
	}
}
