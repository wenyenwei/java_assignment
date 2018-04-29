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

public class Nimsys {

	//public variables
	private static int currentStoneAmount, removeStoneAmount, stoneUpperBound;
	private static String currentPlayer, player1, player2;
	private static Scanner keyboard = new Scanner(System.in);
	private static String[] playersList = new String[20];
	private static int playerAmount = 0;
	enum Actions {addplayer, removeplayer, editplayer, resetstats, displayplayer, rankings, startgame, exit};
	private static int indexOfUsername;
	private static String variables;
	private static String username;


	public static void main (String[] args) {

		System.out.println("Welcome to Nim");
		prompt();

	}

	// command prompt
	private static void prompt() {

		NimPlayer prompt = new NimPlayer();
		String action = prompt.promptInput();

		checkAction(action);

	}

	// proceed input action
	private static void checkAction(String action) {
		action = action.toLowerCase();
		Actions function = null;
		function = Actions.valueOf(action.split(" ")[0]);
		// set vars back to null
		variables = null; 
		username = null;

		// get username index
		if (action.split(" ").length > 1){
			variables = action.split(" ")[1]; 
			username = variables.split(",")[0];
			indexOfUsername = getUserIndex(username);		
		}

		switch(function){

			// addplayer 
			case addplayer:
				addPlayer();
				break;

			// removeplayer function
			case removeplayer:
				removePlayer();
				break;

			// editplayer function
			case editplayer:
				editPlayer();
				break;

			// resetstats function
			case resetstats:
				resetStats();
				break;

			// displayplayer function
			case displayplayer:
				displayPlayer();
				break;

			// rankings function
			case rankings:
				rankings();
				break;

			// startgame function
			case startgame:
				startGame();
				break;

			// exit function
			case exit:
				System.out.println();
				System.exit(0);
				break;
		}
	}

	// get user index
	private static int getUserIndex(String usernameInput){
		int index = -1;
		for (int i = 0; i < playersList.length; i++){
			if(playersList[i] != null){
				if (playersList[i].equalsIgnoreCase(usernameInput)){
					index = i;
				}	
			}
		}
		return index;
	}

	// if user exist
	private static boolean userExist(String usernameInput){
		boolean userExist = false;
		if (getUserIndex(usernameInput) == -1){
			userExist = false;
		}else if (getUserIndex(usernameInput) >= 0){
			userExist =  true;
		}
		return userExist;
	}

	// add player function
	private static void addPlayer(){
		// player not exist
		if (indexOfUsername == -1){
			playersList[playerAmount] = username;
			playersList[playerAmount + 1] = variables + ",0 games,0 wins";
			playerAmount = playerAmount + 2;
			prompt();
		}
		// player exist
		else if (indexOfUsername >= 0){
			System.out.println("The player already exists.");
			prompt();
		}
	}

	// remove player function
	private static void removePlayer(){
		// remove all players
		if (variables == null){
			NimPlayer removePrompt = new NimPlayer();
			String reply = removePrompt.removePromptInput();
			if (reply.equalsIgnoreCase("y")){
				for (int i = 0; i < playersList.length; i++){
					playersList[i] = null;
				}
			}
		}
		// player not exist
		else if (indexOfUsername == -1){
			System.out.println("The player does not exist.");
		}
		// player exist
		else if (indexOfUsername >= 0){
			playersList[indexOfUsername] = null;
			playersList[indexOfUsername + 1] = null;
			playerAmount = playerAmount - 2;
		}
		prompt();
	}

	// edit player function
	private static void editPlayer(){
		// player not exist
		if (indexOfUsername == -1){
			System.out.println("The player does not exist.");
		}
		// player exist
		else if (indexOfUsername >= 0){
			playersList[indexOfUsername] = username;
			playersList[indexOfUsername + 1] = variables;
		}
		prompt();
	}

	// reset stats function
	private static void resetStats(){
		// remove all players
		if (variables == null){
			NimPlayer resetStatsPrompt = new NimPlayer();
			String reply = resetStatsPrompt.resetStatInput();
			if (reply.equalsIgnoreCase("y")){
				for (int i = 0; i < playersList.length; i+=2){
					updateStat(i);
				}
			}
		}
		// player not exist
		else if (indexOfUsername == -1){
			System.out.println("The player does not exist.");
		}
		// player exist
		else if (indexOfUsername >= 0){
			updateStat(indexOfUsername);
		}
		prompt();
	}

	// update player stat 
	private static void updateStat(int index){
		playersList[index + 1] = playersList[index].split(",")[0] + playersList[index].split(",")[1] + playersList[index].split(",")[2] + " 0 games, 0 wins";
	}	

	// display player function
	private static void displayPlayer(){
		// display all players
		if (variables == null){	
			for (int i = 1; i < playersList.length; i+=2){
				if (playersList[i] != null){
					System.out.println(playersList[i]);
				}
			}
		}
		// player not exist
		else if (indexOfUsername == -1){
			System.out.println("The player does not exist.");
		}
		// player exist
		else if (indexOfUsername >= 0){
			System.out.println(playersList[indexOfUsername]);
		}
		prompt();
	}

	// rankings function
	private static void rankings(){
		//apply sorting
		if(variables == null) {
			for (int i = 1; i < playersList.length; i += 2){
				if (playersList[i] != null){
					String[] variable = playersList[i].split(",");
					int gameAmount = Integer.parseInt(variable[3].split(" ")[0]);
					int percentage = Integer.parseInt(variable[4].split(" ")[0]) / Integer.parseInt(variable[3].split(" ")[0]) * 100;
					String result = "%d%% | %d games | %s %s%n";
					System.out.printf(result, percentage, gameAmount, variable[1], variable[2]);	
				}
			}
		}
		prompt();
	}

	// start game function
	private static void startGame(){
		NimGame newGame = new NimGame();
		String[] gameVariables = variables.split(",");
		// user exist
		if (userExist(gameVariables[2]) && userExist(gameVariables[3])){
			newGame.game(gameVariables[0], gameVariables[1], gameVariables[2], gameVariables[3]);
		// user not exist
		}else{
			System.out.println("One of the players does not exist.");	
		}
		// add game record
		String[] winner = newGame.addRecord();
		for (int i = 0; i < winner.length; i++){
			if (winner[i] != null){
			addPlayerRecord(gameVariables[2], winner[i]);
			addPlayerRecord(gameVariables[3], winner[i]);	
			}		
		}	
		prompt();
	}

	// add player record function
	private static void addPlayerRecord(String usernameInput, String winner){
		
		// winner
		if (usernameInput.equalsIgnoreCase(winner)){
	
			int index1 = getUserIndex(usernameInput);
			String[] playerVariables = playersList[index1 + 1].split(",");
			playerVariables[3] = Integer.toString(Integer.parseInt(playerVariables[3].split(" ")[0]) + 1) + " games";
			playerVariables[4] = Integer.toString(Integer.parseInt(playerVariables[4].split(" ")[0]) + 1) + " wins";
			String updatedPlayer = "";

			for (int i = 0; i < playerVariables.length - 1; i++){
				updatedPlayer = updatedPlayer + playerVariables[i] + ",";
			}
			playersList[index1 + 1] = updatedPlayer + playerVariables[4];			

		}
		// loser
		else{

			int index2 = getUserIndex(usernameInput);
			String[] playerVariables = playersList[index2 + 1].split(",");
			playerVariables[3] = Integer.toString(Integer.parseInt(playerVariables[3].split(" ")[0]) + 1) + " games";
			String updatedPlayer = "";

			for (int i = 0; i < playerVariables.length - 1; i++){
				updatedPlayer = updatedPlayer + playerVariables[i] + ",";;
			}
			playersList[index2 + 1] = updatedPlayer + playerVariables[4];	
		
		}

	}

}
