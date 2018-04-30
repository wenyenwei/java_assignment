/*
   The University of Melbourne
   School of Computing and Information Systems
   COMP90041 Programming and Software Development
   Lecturer: Prof. Rui Zhang
   Semester 1, 2018
   Copyright The University of Melbourne 2018
   Project B
   Wenyen Wei, username: wenyenw, studentID: 949624
*/
public class NimGame {

	//public variables
	private static int currentStoneAmount, removeStoneAmount, stoneUpperBound, initStoneAmount, winnerCount;
	private static String currentPlayer, player1, player2, player1Name, player2Name;
	private static String[] winner = new String[40];

	public static void game(String initStone, String maxStone, String playerOne, String playerTwo) {

		NimPlayer player = new NimPlayer();

		player1 = playerOne;
		player2 = playerTwo;

		Nimsys nameList = new Nimsys();
		player1Name = nameList.playerList(player1).split(",")[1]+" "+nameList.playerList(player1).split(",")[2];
		player2Name = nameList.playerList(player2).split(",")[1]+" "+nameList.playerList(player2).split(",")[2];

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

		// print game setting
		System.out.println();
		System.out.printf("Initial stone count: %d%n", initStone);
		System.out.printf("Maximum stone removal: %d%n", maxStone);
		System.out.printf("Player 1: %s%n", player1Name);
		System.out.printf("Player 2: %s%n", player2Name);
		System.out.println();


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

	// get current player name
	private static String currentPlayerName(String currentPlayer){
		String currentPlayerName = null;
		// player1
		if (currentPlayer == player1){
			currentPlayerName =  player1Name;
		}
		// player2
		else if (currentPlayer == player2){
			currentPlayerName = player2Name;
		}
		return currentPlayerName;
	}

	// check win
	private static void checkWin() {
		currentPlayerSwitcher(currentPlayer);
		// End game
		if (currentStoneAmount <= 0) {
			System.out.println("Game Over");
			System.out.printf("%s wins!%n", currentPlayerName(currentPlayer));
			winner[winnerCount] = currentPlayer;
			winnerCount += 1;
			// playAgain();
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
	// private static void playAgain(){

	// 	NimPlayer playerReply = new NimPlayer();
	// 	String reply = playerReply.reply();

	// 	if (reply.equalsIgnoreCase("Y")) {
	// 		initializeGameSetting(initStoneAmount, stoneUpperBound);
	// 	}

	// }
}