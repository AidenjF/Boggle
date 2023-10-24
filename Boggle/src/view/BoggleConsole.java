/**
 * The main function that creates the boggle object, runs the entire game and then prints out
 * what is needed like score, correct guesses, incorrect guesses, and the possible words not
 * found
 * @author Aiden Foster
*/
package view;

import java.io.*;
import java.util.*;
import model.Boggle;

public class BoggleConsole {

	 //matrix that was used for debugging purposes
	 private static char[][] longWords = {
				  { 'Q', 'E', 'E', 'B' },
				  { 'I', 'T', 'T', 'N' },
				  { 'N', 'D', 'E', 'D' },  
				  { 'A', 'S', 'T', 'N' } };
	
	public static void main(String[] args) throws FileNotFoundException {
		Boggle game1 = new Boggle();
		System.out.println("  BOGGLE  ");
		game1.printBoard();
		
		// Take in the user inputs
		System.out.println("\nEnter as many words as you can find!");
		System.out.println("Enter 'ZZ' to end the game");
		ArrayList<String> userGuesses = new ArrayList<>();
		Scanner scannerObj = new Scanner(System.in);
		boolean flag = true;
		while(flag) {
		    String input = scannerObj.nextLine();
		    if(!input.equals("ZZ")) {
		    	String[] inputList = input.split(" ");
		    	for(String word: inputList) {
		    		userGuesses.add(word);
		    	}
		    }
		    else {
		    	flag = false;
		    }
	    }
		System.out.println("\nGame Over\n");
		// filter out the user inputs to only include lower case words
		HashSet<String> userGuessesCleaned = game1.cleanUpInput(userGuesses);
		
		// get a list of the correct and incorrect words
		ArrayList<String> correctWords = game1.getCorrectWords(userGuessesCleaned);
		ArrayList<String> incorrectWords = game1.getIncorrectWords(userGuessesCleaned);
		int score = game1.getScore(correctWords);
		// print out the users score
		System.out.println("Your score: " + score);
		
		// print out the correct words
		System.out.println("\nWords you found:");
		System.out.println("================");
		printCorrectWords(correctWords);
		System.out.println();

		// print out the incorrect words
		System.out.println("\nIncorrect words:");
		System.out.println("================");
		printIncorrectWords(incorrectWords);
		System.out.println();

		// find the possible words not guessed and print them out 
		int numWordsNotFound = game1.getNumWordsNotFound(correctWords);
		ArrayList<String> wordsNotFound = game1.getWordsNotFound(correctWords);
		System.out.println("\nYou could have found these " + numWordsNotFound + " more words:");
		System.out.println("=====================================================================");
		printWordsNotFound(wordsNotFound);
		System.out.println();
	}
	
	/**
	 * This method prints out all of the correctly guessed words
	 */
	public static void printCorrectWords(ArrayList<String> correctWords) {
		for(int i = 0; i < correctWords.size(); i++) {
			System.out.print(correctWords.get(i) + " ");
			if(i % 10 == 0 && i !=0) {
				System.out.println();
			}
		}
	}
	
	/**
	 * This method prints out all of the incorrectly guessed words
	 */
	public static void printIncorrectWords(ArrayList<String> incorrectWords) {
		for(int i = 0; i < incorrectWords.size(); i++) {
			System.out.print(incorrectWords.get(i) + " ");
			if(i % 10 == 0 && i !=0) {
				System.out.println();
			}
		}
	}
	
	public static void printWordsNotFound(ArrayList<String> wordsNotFound) {
		for(int i = 0; i < wordsNotFound.size(); i++) {
			System.out.print(wordsNotFound.get(i) + " ");
			if(i % 10 == 0 && i !=0) {
				System.out.println();
			}
		}
	}
}
