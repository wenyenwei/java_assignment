/*
   The University of Melbourne
   School of Computing and Information Systems
   COMP90041 Programming and Software Development
   Lecturer: Prof. Rui Zhang
   Semester 1, 2018
   Copyright The University of Melbourne 2018
   Project C
   Wenyen Wei, username: wenyenw, studentID: 949624
*/
public class NimGame {

	//public variables
	private static int currentStoneAmount, removeStoneAmount, stoneUpperBound, initStoneAmount, winnerCount;
	private static String currentPlayer, player1, player2, player1Name, player2Name;
	private static String[] winner = new String[40];
	private static boolean player1AI, player2AI;

	// new game start
	public static void game(String initStone, String maxStone, String playerOne, String playerTwo) {

		// players setting
		NimPlayer player = new NimPlayer();

		player1 = playerOne;
		player2 = playerTwo;

		Nimsys nameList = new Nimsys();
		player1Name = nameList.playerList(player1).split(",")[1]+" "+nameList.playerList(player1).split(",")[2];
		player2Name = nameList.playerList(player2).split(",")[1]+" "+nameList.playerList(player2).split(",")[2];
		

		// indicate if players are ai players
		if (player1.length() > 3){
			if (player1.substring(0,3).equalsIgnoreCase("ai_")){
				player1AI = true;
			}else{
				player1AI = false;
			}			
		}
		if (player2.length() > 3){
			if (player2.substring(0,3).equalsIgnoreCase("ai_")){
				player2AI = true;
			}else{
				player2AI = false;
			}		
		}

		// init vars
		initStoneAmount = Integer.parseInt(initStone);
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

	// move validity exception http://xanxusvervr.blogspot.com.au/2017/12/java.html
	private static class moveException extends Exception{
	    public moveException(String msg){
	        super(msg);
	    }
	}
	
	// move validity
	private static void checkStoneNumValidity(int removeStoneAmount) throws moveException{
	    if (removeStoneAmount > stoneUpperBound || removeStoneAmount < 1){
	    	String msg = "Invalid move. You must remove between 1 and "+stoneUpperBound+" stones";
	        throw new moveException(msg);
	    }
	}

	// check if player is ai
	private static boolean checkAI(String currentPlayer) {
		boolean indicator = false;
		if (currentPlayer == player1){
			if (player1AI){
				indicator = true;
			}
		}else if (currentPlayer == player2) {
			if (player2AI){
				indicator = true;
			}
		}
		return indicator;
	}

	// check move validity	
	private static void checkStoneAmount(int removeStoneAmount){
	    try {
	    	checkStoneNumValidity(removeStoneAmount);
	    } catch(moveException e) {
	    	System.out.println(e.getMessage());
            switchPlayer();
	    }		
	}

	// switch player action
    private static void switchPlayer() {
    	int removeStoneAmount;
    	// if currentPlayer is AIPlayer, call NimAIPlayer
    	if (checkAI(currentPlayer)){
    		// call NimAIPlayer to return a remove stone amount
    		NimAIPlayer aiPlayer = new NimAIPlayer();
    		removeStoneAmount = aiPlayer.removeStone(currentPlayer, stoneUpperBound, currentStoneAmount, initStoneAmount);
    	}
    	// if currentlyPlayer is human player, call NimPlayer
    	else{
    		NimPlayer humanPlayer = new NimPlayer();
	    	removeStoneAmount = humanPlayer.removeStone(currentPlayer);	
    	}
    	checkStoneAmount(removeStoneAmount);
	    stoneReducer(removeStoneAmount);
        checkWin();
    }
}