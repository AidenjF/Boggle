/**
 * Holds the methods that will be needed to run the BoggleGUI.java program
 * @author Aiden Foster
*/

package view;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.Boggle;

import java.io.*;
import java.util.*;

public class BoggleGUI extends Application {

	private Button startButton = new Button("Start Game");
	private Button endButton = new Button("End Game");
	private TextArea inputArea = new TextArea("");
	private TextArea outputArea = new TextArea("");
	private TextArea trayArea = new TextArea();
	private BorderPane buttonPane = new BorderPane();
	private GridPane gridPane;
	private Label inputLabel = new Label("Enter attempts below:");
	private Label resultsLabel = new Label("Results:");
	private Boggle game;
	
	// matrix of characters that was used for debugging
	private static char[][] longWords = {
			  { 'T', 'N', 'Q', 'W' },
			  { 'A', 'C', 'O', 'T' },
			  { 'O', 'K', 'V', 'E' },  
			  { 'S', 'R', 'U', 'E' } };
	
	/**
	 * The main function that launches the gui program
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * This is where we set up the stage and set it to show so the user can play the game
	 */
	public void start(Stage stage) {
		stage.setTitle("Boggle");
		layoutGUI();
		registerHandlers();
				
		Scene scene = new Scene(gridPane, 850,300);
		stage.setScene(scene);
		
		stage.show();
	}
	
	/**
	 * This method creates the layout for the boggle GUI version. It creates the buttons
	 * textAreas, and sets up the layout for everything.
	 */
	private void layoutGUI() {
	    gridPane = new GridPane();
		gridPane.setGridLinesVisible(false);
		
		buttonPane.setLeft(startButton);
		buttonPane.setRight(endButton);
		endButton.setDisable(true);
		
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		
		gridPane.add(buttonPane, 0, 0);
		
		GridPane.setHalignment(inputLabel, HPos.CENTER);
		GridPane.setHalignment(resultsLabel, HPos.CENTER);
		
		inputArea.setMaxSize(180, 180);
		inputArea.setWrapText(true);
		Font font = Font.font("ChalkBoard", FontWeight.NORMAL, 14);
		inputArea.setFont(font);
		
		outputArea.setMaxSize(420, 180);
		outputArea.setWrapText(true);
		outputArea.setEditable(false);
		
		trayArea.setMaxSize(180, 180);
		trayArea.setWrapText(true);
		trayArea.setEditable(false);
		
		gridPane.setAlignment(Pos.CENTER);
		
		gridPane.add(trayArea, 0, 1);
		gridPane.add(inputArea, 1, 1);
		gridPane.add(outputArea, 2, 1);
		
		gridPane.add(inputLabel, 1, 0);
		gridPane.add(resultsLabel, 2, 0);
	}
	
	/**
	 * This method handles creating the action events so that we know when the buttons are clicked
	 */
	private void registerHandlers() {
		startButton.setOnAction(new isStartButton());
		endButton.setOnAction(new isEndButton());		  
	}
	
	/**
	 * This is the event handler for the start button, it lets us know when the button is pressed
	 * so that we know when to start the game.
	 */
	private class isStartButton implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent arg0) {
			try {
				game = new Boggle();
			} 
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			endButton.setDisable(false);
			startButton.setDisable(true);
			inputArea.setEditable(true);
			printTray();
			inputArea.setText("");
			outputArea.setText("");
		} 
	}
	
	/**
	 * This is the event handler for the end button, it lets us know when the button is pressed
	 * so that we know when to end the game.
	 */
	private class isEndButton implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent arg0) {
			startButton.setDisable(false);
			endButton.setDisable(true);
			inputArea.setEditable(false);
			getAnswers();
		} 
	}
	
	/**
	 * This method prints out the boggle tray for the current game within the tray area
	 */
	private void printTray() {
		StringBuilder trayString = new StringBuilder();
		char[][] tray = game.getTray();
		for(char[] row: tray) {
			for(char charac: row) {
				trayString.append(charac + " ");
			}
			trayString.append("\n");
		}
		trayArea.setText(trayString.toString());
		Font font = Font.font("Courier New", FontWeight.BOLD, 20);
		trayArea.setFont(font);
	}
	
	/**
	 * This method retrieves all of the correct, incorrect, and words not found from your input
	 */
	private void getAnswers() {
		String answers = inputArea.getText();
		String[] answerList = answers.split(" ");
		ArrayList<String> cutWords = new ArrayList<>();
		for(String word: answerList) {
			word = word.replaceFirst("\n", "");
			cutWords.add(word);
		}
		HashSet<String> cleanedWords = game.cleanUpInput(cutWords);
		ArrayList<String> correctWords = game.getCorrectWords(cleanedWords);
		ArrayList<String> incorrectWords = game.getIncorrectWords(cleanedWords);
		ArrayList<String> wordsNotFound = game.getWordsNotFound(correctWords);
		int score = game.getScore(correctWords);
		int numWordsNotFound = game.getNumWordsNotFound(correctWords);
		Collections.sort(correctWords);
		Collections.sort(incorrectWords);
		Collections.sort(wordsNotFound);
		appendToResults(correctWords, incorrectWords, wordsNotFound, score, numWordsNotFound);
	} 
	
	/**
	 * This method takes these sets of data and creates the results output for us to append
	 * @param correctWords: the correct words the user found
	 * @param incorrectWords: the incorrect words the user found
	 * @param wordsNotFound: the words the user did not find
	 * @param score: the score the user achieved
	 * @param numWordsNotFound: the number of words the user did not find
	 */
	private void appendToResults(ArrayList<String> correctWords, ArrayList<String> incorrectWords, ArrayList<String> wordsNotFound, int score, int numWordsNotFound) {
		StringBuilder results = new StringBuilder();
		results.append("Your score: " + score + "\n");
		
		results.append("\nWords you found:\n");
		results.append(getString(correctWords));
		
		results.append("\n\nIncorrect words:\n");
		results.append(getString(incorrectWords));
		
		results.append("\n\nYou could have found these " + numWordsNotFound + " more words:\n");
		results.append(getString(wordsNotFound));
		outputArea.setText(results.toString());
	}
	
	/**
	 * This method takes in a list and builds a string out of it with a specific format
	 * @param list: the list we are making a word
	 * @return: we are returning the string we created
	 */
	private String getString(ArrayList<String> list) {
		StringBuilder string = new StringBuilder();
		for(String word: list) {
			string.append(word + " ");
		}
		return string.toString();
	}
}
