/**
 * Contains all of the methods necessary to test the Boggle.java file
 * @author Aiden Foster
*/
package tests;

import model.Boggle;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.io.*;
import java.util.*;

class BoggleTest {

	 private static char[][] longWords = {
				  { 'Q', 'E', 'E', 'B' },
				  { 'I', 'T', 'T', 'N' },
				  { 'N', 'D', 'E', 'D' },  
				  { 'A', 'S', 'T', 'N' } };
	 
	 private ArrayList<String> userGuesses = new ArrayList<>(Arrays.asList("Queen", "QuieT", "ant", 
				"ant", "ant", "ANT", "NotHere", "123", "detente", "edit", "end", "quietest",
				"tee", "teen", "ten", "tend", "tended", "intend", "intended", "intendeds", 
				"intent", "intents", "tenet", "tenets", "tent", "tents", "test", "tie", "tin"));
	
	@Test
	void test() throws FileNotFoundException{
		Boggle game1 = new Boggle();
		Boggle game2 = new Boggle(longWords);
		
	}
	
	@Test
	void testCorrectGuesses() throws FileNotFoundException {
		// creates a predetermined boggle tray and input user guesses then check to make sure all of 
		// the possible words guessed are found and accounted for
		Boggle game = new Boggle(longWords);
		HashSet<String> cleanedUpInput = game.cleanUpInput(userGuesses);
		ArrayList<String> correctGuesses = new ArrayList<>(Arrays.asList("tent", "quietest", "queen", 
				"tents", "tend", "tie", "tenets", "intendeds", "tee", "intended", "tin", "end",
				"quiet", "ten", "tenet", "intents", "test", "edit", "ant", "teen", 
				"intent", "tended", "detente", "intend"));
		
		ArrayList<String> gameGuesses = game.getCorrectWords(cleanedUpInput);
		assertEquals(gameGuesses, correctGuesses);
	}
	
	@Test
	void testIncorrectGuesses() throws FileNotFoundException {
		// creates a predetermined boggle tray and input user guesses then check to make sure all of 
		// the incorrect guessed are found and accounted for
		Boggle game = new Boggle(longWords);
		HashSet<String> cleanedUpInput = game.cleanUpInput(userGuesses);
		ArrayList<String> incorrectGuesses = new ArrayList<>(Arrays.asList("nothere", "123"));
		
		ArrayList<String> gameGuesses = game.getIncorrectWords(cleanedUpInput);
		assertEquals(gameGuesses, incorrectGuesses);
	}
	
	@Test
	void testWordsNotFound() throws FileNotFoundException {
		// creates a predetermined boggle tray and input user guesses then check to make sure all of 
		// the possible words that were not guessed are accounted for
		Boggle game = new Boggle(longWords);
		HashSet<String> cleanedUpInput = game.cleanUpInput(userGuesses);
		ArrayList<String> wordsNotThere = new ArrayList<>(Arrays.asList("ads", "and", 
				 "ani", "ante", "anted", "antes", "anti", "antique", "bee", "beet", "bend",
				 "bent", "bet", "betide", "betided", "betides", "betted", "den", "dent", "dents", "die",
				 "diet", "dieted", "din", "dins", "dint", "eds", "ended", "ides", "ids", "indent",
				 "indents", "ins", "inset", "nee", "nest", "net", "nets", "nett", "netted", "nit",
				 "nite", "nites", "queened", "quid", "quids", "quieted", "quint", "quintet", "quintets", "quit",
				 "quite", "quitted", "sad", "sand", "sanded", "sedan", "send", "sent", "set", "settee",
				 "snide", "snit", "stet", "tented", "tide", "tided", "tides", "tins"));
		
		ArrayList<String> gameGuesses = game.getWordsNotFound(game.getCorrectWords(cleanedUpInput));
		int numWordsNotFound = game.getNumWordsNotFound(game.getCorrectWords(cleanedUpInput));
		assertEquals(gameGuesses, wordsNotThere);
		assertEquals(numWordsNotFound, 69);
	}
	
	@Test
	void testScore() throws FileNotFoundException {
		// creates a predetermined boggle tray and input user guesses then check to make sure all of 
		// the possible words guessed are found and counted towards the correct score
		Boggle game = new Boggle(longWords);
		HashSet<String> cleanedUpInput = game.cleanUpInput(userGuesses);
		ArrayList<String> correctWordList = game.getCorrectWords(cleanedUpInput);
		int score = game.getScore(correctWordList);
		assertEquals(score, 74);
	}
	
	@Test
	void testRandomBoard() throws FileNotFoundException {
		// prints out 5 different boards to ensure they are different and unique
		Boggle game;
		for(int i = 0; i < 5; i++) {
			game = new Boggle();
			game.printBoard();
		}
	}
	

}
