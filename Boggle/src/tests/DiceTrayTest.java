package tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import model.DiceTray;

/**
* Grader tests for BoggleTray. Just tests word searches. We
* are not seeking words in a dictionary, just strings for now.
* @author mercer, michaels, Aiden Foster
* @version 1.2
*/

public class DiceTrayTest {

  private char[][] longWords = {
  { 'A', 'B', 'S', 'E' },
  { 'I', 'M', 'T', 'N' },
  { 'N', 'D', 'E', 'D' },  
  { 'S', 'S', 'E', 'N' } };
  
  private char[][] longWordsTwo = {
  { 'Q', 'U', 'E', 'E' },
  { 'E', 'I', 'T', 'N' },
  { 'Q', 'D', 'A', 'D' },  
  { 'E', 'T', 'E', 'L' } };
  
  private char[][] longWordsThree = {
  { 'Z', 'Q', 'E', 'E' },
  { 'E', 'I', 'T', 'N' },
  { 'Q', 'D', 'A', 'D' },  
  { 'E', 'T', 'E', 'L' } };

  private char[][] longWordsFour = {
  { 'M', 'L', 'K', 'J' },
  { 'O', 'N', 'M', 'N' },
  { 'U', 'I', 'L', 'V' },  
  { 'Q', 'Z', 'X', 'Y' } };
  
  private DiceTray tray = new DiceTray(longWords);
  private DiceTray trayTwo = new DiceTray(longWordsTwo);
  private DiceTray trayThree = new DiceTray(longWordsThree);
  private DiceTray trayFour = new DiceTray(longWordsFour);
  //private DiceTray trayFive = new DiceTray();

  @Test
  public void testForSmallStringsNotRealWords() {
    // We are not looking for words in a dictionary now, just strings.
    // searchBoard must return false for strings < length() of 3
    // asserts can take a string argument that prints when the assert fails.
	DiceTray trayFive = new DiceTray();
	trayFive.printBoard();
	assertFalse(tray.found(""));
    assertFalse(tray.found("TS"));  // Not a word, but the sequence exists
    assertTrue(tray.found("TMI"));
    assertTrue(tray.found("aBs"));  // Case insensitive
    assertTrue(tray.found("AbS"));
    assertFalse(tray.found("aba"));
    assertFalse(tray.found("abmia"));
    assertFalse(trayFive.found("H"));
  }

  @Test
  public void testFound2() {
    assertTrue(tray.found( "sent"));
    assertTrue(tray.found( "SENT"));
    assertTrue(tray.found( "minded"));
    assertTrue(tray.found( "teen"));
    assertTrue(tray.found( "dibtd"));
  }

  @Test
  public void testForLongerStrings() {
    assertTrue(tray.found( "NTMINDED")); // Not a word, but the sequence exists
    assertTrue(tray.found( "ABSENTMINDEDNESS"));
  }
  
//  { 'Q', 'U', 'E', 'E' }
//  { 'E', 'I', 'T', 'N' }
//  { 'Q', 'D', 'A', 'D' }
//  { 'E', 'T', 'E', 'L' }
  @Test
  public void testForSecondSet() {
	  assertTrue(trayTwo.found("que"));
	  assertTrue(trayTwo.found("queque"));
	  assertTrue(trayTwo.found("laedntee"));
	  assertTrue(trayTwo.found("Quue"));
	  assertFalse(trayTwo.found("laedl"));
	  assertTrue(trayTwo.found("laed"));
	  assertTrue(trayTwo.found("quialdetdte")); // Not a word, but an absurd sequence to find
	  assertFalse(trayTwo.found("quialdetdtendl"));
  }
  
//  { 'Z', 'Q', 'E', 'E' }
//  { 'E', 'I', 'T', 'N' }
//  { 'Q', 'D', 'A', 'D' }
//  { 'E', 'T', 'E', 'L' }
  @Test
  public void testForThirdSet() {
	  assertTrue(trayThree.found("zquee"));
	  assertTrue(trayThree.found("zqueendleteque"));
	  assertFalse(trayThree.found("zqueendletequez"));
	  assertTrue(trayThree.found("dneequz"));
	  assertTrue(trayThree.found("zequedteendl"));
	  assertTrue(trayThree.found("laizeque"));
	  assertTrue(trayThree.found("leadnetequi"));
	  assertFalse(trayThree.found("leadnetequit"));
  }
  
//  { 'M', 'L', 'K', 'J' },
//  { 'O', 'N', 'M', 'N' },
//  { 'U', 'I', 'L', 'V' },  
//  { 'Q', 'Z', 'X', 'Y' }
  @Test
  public void testForFourthSet() {
	  assertTrue(trayFour.found("MLK"));
	  assertFalse(trayFour.found("MOM"));
	  assertTrue(trayFour.found("NMN"));
	  assertFalse(trayFour.found("NMNMN"));
	  assertTrue(trayFour.found("MLKMNOUILZXYVNJ"));

  }
}