import java.io.*;
import java.util.*;

/**
 * The processor class handles reading in the name of a file to be parsed and
 * the length of the kmers to be parsed. The kmers are placed in to a hashMap
 * along with an int value marking each occurrence of the kmer in the given
 * file.
 * 
 * The user can prompt for a search for any give kmer and a response will be
 * given showing how many occurrences the kmer has in the file and at what
 * positions.
 * 
 * @author Casey Ryane
 *
 */
public class Processor {

	private Scanner keyboard = new Scanner(System.in);
	private static Processor processor;
	private int sequenceLength;
	private HashMap<Kmer, ArrayList<Integer>> map;

	/**
	 * The main provides the main access point to the program, it initializes the
	 * Processor and calls the run function.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		processor = new Processor();
		processor.run();
	}

	/**
	 * The run method takes in the name of the file to be parsed along with the
	 * length of sequences to be parsed The user is then prompted for a sequence
	 * which is searched for 1 millions times before reporting the number of time it
	 * occurs and the positions of each occurrence.
	 * 
	 */
	private void run() {
		String fileName = getFileName();
		try {
			sequenceLength = getSequenceLength();
			map = new HashMap<>((int) (1.5 * (Math.pow(4, sequenceLength))));
			loadFile(fileName, sequenceLength);
		} catch (InputMismatchException e) {
			System.out.println("Invalid input for \"k\" program terminated");
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found, program terminated.");
		}
		sequenceSearch();
	}

	/*
	 * THis method loads the file given by the user into a hashMap by utilizing a
	 * stringBuilder to read in sequences of a proper length and then create Kmer
	 * objects which are loaded into the HashMap
	 * 
	 */
	private void loadFile(String fileName, int k) throws FileNotFoundException {
		File fastaFile = new File(fileName);
		FileReader fr = new FileReader(fastaFile);
		BufferedReader reader = new BufferedReader(fr);
		int positionCounter = 0;
		try {
			String comment = reader.readLine();
			System.out.println("Loading genome from file with description: " + comment);
			StringBuilder kmerString = new StringBuilder();
			String nextLine = "";
			Kmer newKmer = new Kmer();
			while (nextLine != null) {
				nextLine = reader.readLine();
				for (int i = 0; i <= nextLine.length() - 1; i++) {
					kmerString.append(nextLine.charAt(i));

					if (kmerString.length() == sequenceLength) {
						newKmer = new Kmer(kmerString.toString());
					} else if (kmerString.length() > sequenceLength) {
						kmerString.deleteCharAt(0);
						newKmer = new Kmer(kmerString.toString());
					}
					if (kmerString.length() == sequenceLength) {
						addKmer(newKmer, positionCounter);
						positionCounter++;
					}
				}
			}
		} catch (IOException e) {
		} catch (NullPointerException e) {
		}
		printMap(positionCounter);
	}

	/**
	 * This method takes in a kmer and the position of the sequence that kmer was
	 * found and either adds it to the HashMap or updates the positionList of a kmer
	 * already in the HashMap if it already exists.
	 * 
	 * @param newKmer
	 *            to be entered / updated in the HashMap
	 * @param positionCounter
	 *            position in sequence kmer was found
	 */
	private void addKmer(Kmer newKmer, int positionCounter) {
		ArrayList<Integer> positionList = new ArrayList<Integer>();
		if (map.containsKey(newKmer)) {

			positionList = map.get(newKmer);
			positionList.add(positionCounter);
			map.put(newKmer, positionList);
		} else {
			positionList.add(positionCounter);
			map.put(newKmer, positionList);
		}
	}

	/**
	 * This method prints the total number of sequences and the final size of the
	 * HashMap after being filled.
	 * 
	 * @param positionCounter
	 *            int representing total number of sequences
	 */
	private void printMap(int positionCounter) {
		System.out.println("Sequences of size: " + sequenceLength + " count: " + positionCounter);
		System.out.println("Map size: " + map.size());
	}

	/**
	 * Retrives the file name from the user and returns a string value
	 * 
	 * @return file name provided by user
	 */
	private String getFileName() {
		System.out.println("Enter file name: ");
		String fileName = keyboard.nextLine();
		return fileName;
	}

	/**
	 * Prompts the user for and takes in the length of sequences to be stored
	 * 
	 * @return int of sequence length
	 * @throws InputMismatchException
	 */
	private int getSequenceLength() throws InputMismatchException {
		System.out.println("Enter the length (k) of the sequence to store ");
		int K = keyboard.nextInt();
		keyboard.nextLine();
		return K;
	}

	/**
	 * gets sequence from user a searches the hashMap 1 millions times before
	 * outputting the number of occurrences of the sequence and the position of each
	 * occurrence.
	 */
	private void sequenceSearch() {
		long startTime = 0;
		String position = "";
		Boolean userWantsToContinue = true;
		int numLocations = 0;
		while (userWantsToContinue) {
			String sequence = promptForSequence();
			userWantsToContinue = validateSequence(sequence);

			Kmer kmer = new Kmer(sequence);

			startTime = System.currentTimeMillis();
			boolean present = checkForKmer(kmer);
			long stopTime = System.currentTimeMillis();
			
			if (present) {
				position = map.get(kmer).toString();
				numLocations = map.get(kmer).size();
				long elapsedTime = stopTime - startTime;
				printLocations(elapsedTime, numLocations, position, sequence);
			} else {
				System.out.println("Sequence not found");
			}
		}
		System.out.println("Program Terminated");
	}
	
	/**
	 * This method searches the hashMap for a given kmer 1 millions times
	 * and returns a boolean value stating if the kmer was found.
	 * 
	 * @param kmer to be searched for 
	 * @return boolean value
	 */
	private boolean checkForKmer(Kmer kmer){
		boolean present = false;
		try{	
		for (int i = 0; i < 1000000; i++) {
			if (map.containsKey(kmer)) {
				present = true;
			}
		}
		}catch(NullPointerException e){
		}
		return present;
	}

	/**
	 * This methods takes in the time taken to search for a given sequence, the
	 * number of locations of that sequence, the given sequence and the positions of
	 * that sequence and prints them out in a legible formatted fashion.
	 * 
	 * @param elapsedTime
	 *            total time to find kmer 1 millions times
	 * @param numLocations
	 *            number of times kmer appears
	 * @param position
	 *            positions where kmer appears
	 * @param sequence
	 *            given sequence
	 */
	private void printLocations(long elapsedTime, int numLocations, String position, String sequence) {
		System.out.println("Sequence: " + sequence);
		System.out.println(position);
		System.out.println("There are " + numLocations + " locations wth the sequence: " + sequence);
		System.out.println("Search time: " + elapsedTime + "ms");
	}

	/**
	 * Prompts user for a given sequence to be searched for
	 * 
	 * @return String representing sequence
	 */
	private String promptForSequence() {
		System.out.println("Enter new sequence to find postions. Enter \"q\" to quit...");
		String input = keyboard.nextLine();
		return input;
	}

	/**
	 * Validates that the user has inputed a valid sequence and does not wish to
	 * quit the program
	 * 
	 * @param sequence
	 *            String entered by user
	 * @return boolean value if sequence is valid
	 */
	private boolean validateSequence(String sequence) {
		boolean result = true;
		if (sequence.equalsIgnoreCase("q")) {
			result = false;
		} else if (sequence.length() != sequenceLength) {
			System.out.println("Given sequence does not match length of sequence stored. Program terminated");
			result = false;
		}
		return result;
	}

}
