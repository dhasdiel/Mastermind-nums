import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
// -------------------------------------------------------------
// Mastermind Game.
// -------------------------------------------------------------
//
// General: A game that the user need to guess witch numbers
// the CP choose.
//
// Input: The user typing numbers for guessing what the CP choose.
//
// Process: The program activates a methods to perform the
// operation according to the play.
//
// Output: Print the results of the actoin of the user.
//
// -------------------------------------------------------------
// Programmer : Daniel Hasdiel
// -------------------------------------------------------------
public class Main {

  // Final definition
  public static final int PLAYING = 0, PLAYER_WON = 1, CP_WON = 2;
  public static final int ROWS = 8, COLS = 4; // number of rows and columns
  public static final int MIN = 1, MAX = COLS;

  // -----------------------
  // Global variable definition
  public static int[][] board = new int[ROWS][COLS]; // game board in 2D array of the player
  public static int[] answer = new int[COLS]; // game board in 2D array of the Cp
  public static int currentState; // = PLAYING or PLAYER_WON or CP_WON
  public static int currentRow = 1, currentCol = 0;

  // -----------------------
  // Scanner definition
  public static Scanner in = new Scanner(System.in);

  // -----------------------
  public static void main(String[] args) {
    // Code section
    initGame();
    cpMove();
    do {
      currentCol = 0;
      playerMove(board[currentRow - 1]);
      printPlayerBoard();
      playerHasWon(board[currentRow - 1]);
      counterPlaces(board[currentRow - 1]);
      if (currentRow == ROWS) {
        currentState = CP_WON;
      }
      currentRow++;
    } while (currentState == PLAYING);
    if (currentState == PLAYER_WON) {
      printCpBoard();
      System.out.println("You are won!\nYou did it in " + (currentRow - 1) + " rounds.");
    } else {
      printCpBoard();
      System.out.println("You have lose :( ");
    }
  }
  // -------------------------------------------------------------
  // Methods
  // -------------------------------------------------------------
  // Init Game
  // -----------------------
  public static void initGame() {
    for (int row = 0; row < ROWS; ++row) {
      for (int col = 0; col < COLS; ++col) {
        board[row][col] = 0; // all cells empty
      }
    }
    currentState = PLAYING;
  }
  // -------------------------------------------------------------
  // Cp Move
  // -----------------------
  public static void cpMove() {
    int[] checkNum = new int[COLS];
    int randomNum;
    System.out.println("The CP choosing numbers");
    for (int index = 0; index < COLS; index++) {
      do {
        randomNum = ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
      } while (checkNum[randomNum - 1] != 0);
      checkNum[randomNum - 1]++;
      answer[index] = randomNum;
    }
  }
  // -------------------------------------------------------------
  // Player Move
  // -----------------------
  public static void playerMove(int[] board) {
    int[] checkNum = new int[COLS];
    boolean validInput = false; // for input validation
    int num;
    for (int i = 0; i < COLS; i++) {
      System.out.println("Enter a number between (" + MIN + "-" + MAX + ") in place: #" + (i + 1));
      do {
        num = in.nextInt();
        if (num >= MIN && num <= MAX && checkNum[num - 1] < 1) {
          checkNum[num - 1]++;
          board[i] = num;
          validInput = true; // input okay, exit loop
        } else {
          System.out.println("Worng input!");
          i--;
        }
      } while (!validInput);
    }
  }
  // -------------------------------------------------------------
  // Player Has Won
  // -----------------------
  public static void playerHasWon(int[] board) {
    for (int index = 0; index < COLS; index++) {
      if (board[index] != answer[index]) {
        return;
      }
    }
    currentState = PLAYER_WON;
  }
  // -------------------------------------------------------------
  // Counter Places
  // -----------------------
  public static void counterPlaces(int[] board) {
    int countPlace = 0;
    for (int index = 0; index < COLS; index++) {
      if (board[index] == answer[index]) {
        countPlace++;
      }
    }
    System.out.println("Round #" + currentRow + "\nYou was right in " + countPlace + " places.");
  }
  // -------------------------------------------------------------
  // Print Player Board
  // -----------------------
  public static void printPlayerBoard() {
    for (int row = 0; row < currentRow; row++) {
      for (int col = 0; col < COLS; col++) {
        System.out.print(board[row][col]); // print each of the cells
        if (col != COLS - 1) {
          System.out.print("|"); // print vertical partition
        }
      }
      System.out.println();
      if (row != ROWS - 1) {
        System.out.println("-------"); // print horizontal partition
      }
    }
    System.out.println();
  }
  // -------------------------------------------------------------
  // Print Cp Board
  // -----------------------
  public static void printCpBoard() {
    System.out.println("The numbers that the CP choose are: ");
    for (int index = 0; index < COLS; index++) {
      System.out.print(answer[index]); // print each of the cells
      if (index != COLS - 1) {
        System.out.print("|"); // print vertical partition
      }
    }
    System.out.println();
    System.out.println("-------");
  }
}
