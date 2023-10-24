/**
 * Holds the methods that will be needed to run the BoggleConsole.java program
 * @author Aiden Foster
*/
package model;

import java.io.*;
import java.util.*;
public class Boggle {
	
	private DiceTray tray;
	private ArrayList<String> dictionary;
	private ArrayList<String> possibleWords;
	private ArrayList<String> wordsNotFound;

	/**
	 * This is the constructor that initializes all of the data sets that we need, and then runs 
	 * the game as well
	 * @throws FileNotFoundException: This exception is used for opening our file
	 */
	public Boggle() throws FileNotFoundException {
		tray = new DiceTray();
		dictionary = new ArrayList<>();
		possibleWords = new ArrayList<>();
		wordsNotFound = new ArrayList<>();
		storeDictionaryWords();
		findAllPossibleWords();	
	}
	
	/**
	 * Constructor that was used for debugging. This allowed us to use specific matrixes
	 * that we could test on and compare the inputs and outputs
	 * @param matrix: this was the specific character matrix we would want to use for the board
	 */
	public Boggle(char[][] matrix) throws FileNotFoundException {
		tray = new DiceTray(matrix);
		dictionary = new ArrayList<>();
		possibleWords = new ArrayList<>();
		wordsNotFound = new ArrayList<>();
		storeDictionaryWords();
		findAllPossibleWords();	
	}
	
	/**
	 * This method is used to go through the text file and store all of the words in a dictionary
	 * @throws FileNotFoundException: This exception is used for opening our file
	 */
	public void storeDictionaryWords() throws FileNotFoundException {
		File file = new File("BoggleWords.txt");
		Scanner line = new Scanner(file);
		while(line.hasNextLine()) {
			dictionary.add(line.nextLine().toLowerCase());
		}
	}
	
	/**
	 * This method goes through the dictionary and finds all the words that are possible on the tray
	 */
	public void findAllPossibleWords() {
		for(String word: dictionary) {
			if(tray.found(word)) {
				possibleWords.add(word);
			}
		}
	}
	
	/**
	 * Filters the input to not include duplicates and makes all the words lower case to compare easier
	 * @param userGuesses: list of all the inputs the user had entered
	 * @return: returns a cleaned up hash set of the user guesses
	 */
	public HashSet<String> cleanUpInput(ArrayList<String> userGuesses) {
		HashSet<String> cleanedUpList = new HashSet<>();
		for(String word: userGuesses) {
			cleanedUpList.add(word.toLowerCase());
		}
		return cleanedUpList;
	}
	
	/**
	 * This method goes through all of the users guesses and determines if they are good or not. If they
	 * are good it assigns a point value to them
	 */
	public ArrayList<String> getCorrectWords(HashSet<String> userGuesses) {
		ArrayList<String> correctWords = new ArrayList<>();
		for(String word: userGuesses) {
			if(possibleWords.contains(word)) {
				correctWords.add(word);
			}
		}
		return correctWords;
	}
	
	/**
	 * This method goes through all of the users guesses and determines if they are good or not. If they
	 * are good it assigns a point value to them
	 */
	public ArrayList<String> getIncorrectWords(HashSet<String> userGuesses) {
		ArrayList<String> incorrectWords = new ArrayList<>();
		for(String word: userGuesses) {
			if(!possibleWords.contains(word)) {
				incorrectWords.add(word);
			}
		}
		return incorrectWords;
	}
	
	/**
	 * The method returns all of the words that were possible but were not found within the tray
	 * @param userGuesses: the guesses the user has entered
	 * @return: this returns an array list of all the possible words the user did not find
	 */
	public ArrayList<String> getWordsNotFound(ArrayList<String> userGuesses) {
		ArrayList<String> wordsNotFound = new ArrayList<>();
		for(String word: possibleWords) {
			if(!userGuesses.contains(word)) {
				wordsNotFound.add(word);
			}
		}
		return wordsNotFound;
	}
	
	/**
	 * This method prints out all of the words that weren't guessed but were still possible
	 */
	public int getNumWordsNotFound(ArrayList<String> correctWords) {
		for(String word: possibleWords) {
			if(!correctWords.contains(word)) {
				wordsNotFound.add(word);
			}
		}
		return wordsNotFound.size();
	}
	
	/**
	 * This method prints out the board for the user to see
	 */
	public void printBoard() {
		tray.printBoard();
	}
	
	/**
	 * This method returns the score the user achieved
	 * @param correctWords: a list of all the correct words the user guessed
	 * @return: this returns an integer of the score the user got
	 */
	public int getScore(ArrayList<String> correctWords) {
		int score = 0;
		for(String word: correctWords) {
			if(word.length() == 3 || word.length() == 4) {
				score += 1;
			}
			else if(word.length() == 5) {
				score += 2;
			}
			else if(word.length() == 6) {
				score += 3;
			}
			else if(word.length() == 7) {
				score += 5;
			}
			else if(word.length() >= 8) {
				score += 11;
			}
		}
		return score;
	}
	
	public char[][] getTray() {
		return tray.getTray();
	}
	
}


