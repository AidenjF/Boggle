/**
 * Model the tray of dice in the game Boggle. A DiceTray can
 * be constructed with a 4x4 array of characters for testing.
 * @author Aiden Foster
*/
package model;

import java.util.*;

public class DiceTray {

	private char[][] theBoard;
	private Stack<ArrayList<Integer>> stack;
	private char[][] die = { {'L', 'R', 'Y', 'T', 'T', 'E'}, {'A', 'N', 'A', 'E', 'E', 'G'}, 
							 {'A', 'F', 'P', 'K', 'F', 'S'}, {'Y', 'L', 'D', 'E', 'V', 'R'},
			                 {'V', 'T', 'H', 'R', 'W', 'E'}, {'I', 'D', 'S', 'Y', 'T', 'T'}, 
			                 {'X', 'L', 'D', 'E', 'R', 'I'}, {'Z', 'N', 'R', 'N', 'H', 'L'},
			                 {'E', 'G', 'H', 'W', 'N', 'E'}, {'O', 'A', 'T', 'T', 'O', 'W'}, 
			                 {'H', 'C', 'P', 'O', 'A', 'S'}, {'N', 'M', 'I', 'H', 'U', 'Q'},
			                 {'S', 'E', 'O', 'T', 'I', 'S'}, {'M', 'T', 'O', 'I', 'C', 'U'}, 
			                 {'E', 'N', 'S', 'I', 'E', 'U'}, {'O', 'B', 'B', 'A', 'O', 'J'}
						   };
	
	/**
	 * Construct a DiceTray object using a hard-coded 2D array of chars.
	 * One is provided in the given unit test. You can create others.
	 */
	public DiceTray(char[][] newBoard) {
		theBoard = newBoard;
	}
	
	/**
	 * Constructs a DiceTray object using a randomized 2D array of chars.
	 */
	public DiceTray() { 
		theBoard = new char[4][4];
		ArrayList<Integer> dieOrder = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15));
		Collections.shuffle(dieOrder);
		int dieNum = 0;
		for(int row = 0; row < 4; row++) {
			for(int col = 0; col < 4; col++) {
				int dieSide = (int) (Math.random() * 6);
				theBoard[row][col] = die[dieOrder.get(dieNum)][dieSide];
				dieNum++;
			}
		}
		System.out.println();
	} 

	/**
	 * Return true if attempt can be found on the DiceTray following the rules of Boggle, like a die can only be used once.
	 * @param attempt: A word that may be in the DiceTray by connecting consecutive letters
	 * @return True if search is found in the DiceTray or false if not. You need not check the dictionary now.
	 */
	public boolean found(String attempt) { 
		if(attempt.length() < 3) {
			return false;
		}
		boolean found = false;
		for(int row = 0; row < 4; row++) {
			for(int column = 0; column < 4; column++) {
				stack = new Stack<>();
				if(search(attempt, row, column, 0)) {
					found = true;
				}
			}
		}
		return found;
	}
	
	/**
	 * This is a recursive function that takes in a coordinate and checks to see if the word is possible
	 * starting from the coordinate passed in
	 * @param word: this is the word we are looking for
	 * @param row: the row coordinate of what we are looking at
	 * @param column: the column coordinate of what we are looking at
	 * @param charStep: this is an integer that represents the character we are looking for from our word
	 * @return: this will return true or false depending on if it was found or not
	 */
	public boolean search(String word, int row, int column, int charStep) {
		boolean found = false;
		int specialCase = 0;
		// check to see if the first character is what we are looking for, and then after recursion call we look for
		// the next character in the word we are looking for
		if(Character.toUpperCase(theBoard[row][column]) == Character.toUpperCase(word.charAt(charStep)) && !stack.contains(Arrays.asList(row, column))) {
			// we add the coordinate to the stack to ensure we don't use it again
			stack.push(new ArrayList<>(Arrays.asList(row, column)));
			// if the charStep is the same length as the word then we know that we have found the entire word
			if(charStep == word.length() - 1) {
				found = true;
			}
			// special case for 'Qu'
			if(Character.toUpperCase(word.charAt(charStep)) == 'Q' && Character.toUpperCase(word.charAt(charStep + 1)) == 'U') {
				specialCase++;
			}
			//check up
			if(!found && row > 0) {
				found = search(word, row - 1, column, charStep + 1 + specialCase);
			}
			//check down
			if(!found && row < 3) {
				found = search(word, row + 1, column, charStep + 1 + specialCase);
			}
			//check left
			if(!found && column > 0) {
				found = search(word, row, column - 1, charStep + 1 + specialCase);
			}
			//check right
			if(!found && column < 3) {
				found = search(word, row, column + 1, charStep + 1 + specialCase);
			}
			//check up left
			if(!found && row > 0 && column > 0) {
				found = search(word, row - 1, column - 1, charStep + 1 + specialCase);
			}
			//check up right
			if(!found && row > 0 && column < 3) {
				found = search(word, row - 1, column + 1, charStep + 1 + specialCase);
			}
			//check down left
			if(!found && row < 3 && column > 0) {
				found = search(word, row + 1, column - 1, charStep + 1 + specialCase);
			}
			//check down right
			if(!found && row < 3 && column < 3) {
				found = search(word, row + 1, column + 1, charStep + 1 + specialCase);
			}
			if(found) {
				return true;
			}
			else {
				stack.pop();
				return false;
			}
		}
		// if the character is not the expected character we are looking for then we will return false, to either
		// continue the flower search or to return false to start flower search somewhere else in the matrix
		return found;
	}
	
	public void printBoard() {
		for(int row = 0; row < 4; row++) {
			for(int col = 0; col < 4; col++) {
				if(theBoard[row][col] != 'Q') {
					System.out.print(theBoard[row][col] + "  ");
				}
				else {
					System.out.print("Qu" + " ");
				}
			}
			System.out.println();
		}
	}
	
	public char[][] getTray() {
		return theBoard;
	}
}