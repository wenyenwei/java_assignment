/*
   The University of Melbourne
   School of Computing and Information Systems
   COMP90041 Programming and Software Development
   Lecturer: Prof. Rui Zhang
   Semester 1, 2018
   Copyright The University of Melbourne 2018
   Project A
   Wenyen Wei, username: wenyenw, studentID: 949624
*/
import java.util.Scanner;
public class NimGame {

	//public variables
	private static int currentStoneAmount, removeStoneAmount, stoneUpperBound, initStoneAmount, winnerCount;
	private static String currentPlayer, player1, player2;
	private static Scanner keyboard = new Scanner(System.in);
	private static String[] winner = new String[20];

	public static void game(String initStone, String maxStone, String playerOne, String playerTwo) {

		NimPlayer player = new NimPlayer();

		player1 = playerOne;
		player2 = playerTwo;

		initStoneAmount = Integer.parseInt(initStone);
		// init vars
		for (int i = 0; i < winner.length; i++){
			winner[i] = null;
		}
		winnerCount = 0;

		initializeGameSetting(initStoneAmount, Integer.parseInt(maxStone));
	}

    // add result to record
    public static String[] addRecord() {
    	return winner;
    }

	// initialize stone number
	private static void initializeGameSetting(int initStone, int maxStone) {

		// set upperbound
		stoneUpperBound = maxStone;

		// set initial number
		currentStoneAmount = initStone;

		// start with first player 
		currentPlayer = player1;

		// start game
		showCurrentStoneAmount(currentStoneAmount);
		switchPlayer();

	}

	// show stone number in asterisks
	private static void printStars(int numStars){
		for(int i = 0; i < numStars; i++)
			System.out.printf(" *");
		System.out.println();
	}

	// current stone amount indicator
	private static void showCurrentStoneAmount(int currentStoneAmount){
		System.out.printf("%d stones left:", currentStoneAmount);
		printStars(currentStoneAmount);
	}

	// current player indicator
	private static void currentPlayerSwitcher(String nowPlayer){
		if (nowPlayer == player1){
			currentPlayer = player2;
		}
		else if (nowPlayer == player2){
			currentPlayer = player1;
		}
	}

	// reduce stones according to user input
	private static void stoneReducer(int removeStoneAmount){
		currentStoneAmount = currentStoneAmount - removeStoneAmount;
	}

	// check win
	private static void checkWin() {
		currentPlayerSwitcher(currentPlayer);
		// End game
		if (currentStoneAmount <= 0) {
			System.out.println("Game Over");
			System.out.printf("%s wins!%n", currentPlayer);
			winner[winnerCount] = currentPlayer;
			winnerCount += 1;
			playAgain();
		}
		// Game continue
		else {
			showCurrentStoneAmount(currentStoneAmount);
			switchPlayer();
		}
	}


	// switch player action
    private static void switchPlayer() {

	    NimPlayer checkRemoveAmount = new NimPlayer();
	    int removeStoneAmount = checkRemoveAmount.removeStone(currentPlayer);

	    // if entry over upperbound
        if (removeStoneAmount > stoneUpperBound) {
            System.out.printf("Amount upper bound is %d, please try again.%n", stoneUpperBound);
            switchPlayer();
        }
        // if entry less than 1 
        else if (removeStoneAmount < 1) {
            System.out.printf("Amount has to be greater than zero, please try again.%n");
            switchPlayer();        	
        } 
        else {
            stoneReducer(removeStoneAmount);
            checkWin();
        }
    }

	// ask if play again
	private static void playAgain(){

		NimPlayer playerReply = new NimPlayer();
		String reply = playerReply.reply();

		if (reply.equalsIgnoreCase("Y")) {
			initializeGameSetting(initStoneAmount, stoneUpperBound);
		}

	}
}