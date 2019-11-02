import java.io.*;
import java.util.concurrent.*;
import java.util.StringTokenizer;
import java.time.*;
import java.text.*;
import java.lang.Comparable;


public class MultiThread{

	private static BlockingQueue<String> stringQueue = new LinkedBlockingQueue<>();
	private static Thread[] consumers;
	private static boolean finishedReading = false;
	private static int tokenCount, wordCount, letterCount;
	private static int[] freq = new int[8];


	public static void main(String[] args) throws InterruptedException, IOException{
		if (args.length != 1) {
			System.out.println("1 argument needed: input file name");
			System.exit(1);
		}

		String inputFile = args[0];

		PrintStream fileOut = new PrintStream("Q1 Threaded Output.txt");
		System.setOut(fileOut);

		long start =  System.currentTimeMillis();

		System.out.println("Threaded Version, Running Program With 4 Consumer Threads");

		consumers = new Thread[4];

		Thread producer = new Thread(new Producer(inputFile));
		producer.start();

		for (int i=0; i<4; i++){
			consumers[i] = new Thread(new Consumer());
			consumers[i].start();
		}

		producer.join();
		for (int i=0; i<4; i++){
			consumers[i].join();
		}

		double percentage = 0;
		System.out.println("Total Number Of Words: " + wordCount);

		for (int i=0; i<freq.length; i++){
			percentage = ((double)freq[i]) / wordCount;
			percentage = percentage * 100;

			if(i==7){
				System.out.printf("8 or more letter words: %d words, %.2f%%\n", freq[i], percentage);
			}
			else{
				System.out.printf("%d letter words: %d words, %.2f%%\n", i+1, freq[i], percentage);
			}
		}

		long end = System.currentTimeMillis();

		NumberFormat formatter = new DecimalFormat("#0.000");
		System.out.println("Runtime: " + formatter.format((end - start) / 1000d) + " seconds");
	}


	public static class Producer extends Thread{
		private String inputFile;

		public Producer(String inputFile){
			this.inputFile = inputFile;
		}

		@Override
		public void run(){
			File input = new File(inputFile);
			int count = 0; 
			try (BufferedReader reader = new BufferedReader(new FileReader(input));){
				String line;
				while ((line = reader.readLine()) != null){
					stringQueue.put(line);
				}
			}
			catch (Exception e){
			}
			finishedReading = true;
		}
	}


	public static class Consumer extends Thread {
		
		@Override
		public void run(){

			while (!finishedReading || !stringQueue.isEmpty()){
				String line = stringQueue.poll();
				if (line == null) continue;

				line = line.replaceAll("[^A-Za-z0-9-_]+", " ");
				String text = line;
				text += new String(" ");
				
				arrayUpdate(text);	
			}
		}
	}


	public static synchronized void arrayUpdate(String text){
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
					wordCount++;	
				}
				else{
					freq[letterCount-1]++;
					letterCount = 0;
					wordCount++;
				}
			}
		}
	}
}

