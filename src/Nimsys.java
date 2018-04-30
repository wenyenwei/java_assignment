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
import java.util.Arrays;

public class Nimsys {

	//public variables
	private static int currentStoneAmount, removeStoneAmount, stoneUpperBound;
	private static String currentPlayer, player1, player2;
	private static String[] playersList = new String[40];
	private static int playerAmount = 0;
	enum Actions {
		addplayer, removeplayer, editplayer, resetstats, displayplayer, rankings, startgame, exit
	};
	private static int indexOfUsername;
	private static String variables;
	private static String username;


	public static void main (String[] args) {

		System.out.println("Welcome to Nim");
		prompt();

	}

	// return player data according to username
	public String playerList(String username){
		int index = getUserIndex(username);
		return playersList[index+1];
	}	

	// command prompt
	private static void prompt() {
		NimPlayer prompt = new NimPlayer();
		String action = prompt.promptInput();

		checkAction(action);

	}

	// proceed input action
	private static void checkAction(String action) {
		if (action.length() > 0){
			Actions function = null;
			String[] action_split = action.split(" ");
			function = Actions.valueOf(action_split[0].toLowerCase());
			// set vars back to null
			variables = null; 
			username = null;

			// get username index
			if (action_split.length > 1){
				// swap lname, fname for addplayer and editplayer
				if (action_split[0].equalsIgnoreCase("addplayer") || action_split[0].equalsIgnoreCase("editplayer")){
					variables = action_split[1].split(",")[0]+"," + action_split[1].split(",")[2] +","+ action_split[1].split(",")[1]; 
				}else{
					variables = action.split(" ")[1];
				}
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
					prompt();
					break;

				// exit function
				case exit:
					System.out.println();
					System.exit(0);
					break;
			}			
		}else{
			prompt();
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
			String gameStat = ","+playersList[indexOfUsername+1].split(",")[3]+","+playersList[indexOfUsername+1].split(",")[4];
			playersList[indexOfUsername + 1] = variables + gameStat;
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
			// getting sub list
			String[] varsList = new String[20];
			varsList = sort("alphabet", playersList);
			for (int i = 0; i < varsList.length; i++){
				if (varsList[i] != null){
					System.out.println(varsList[i]);
				}
			}
		}
		// player not exist
		else if (indexOfUsername == -1){
			System.out.println("The player does not exist.");
		}
		// player exist
		else if (indexOfUsername >= 0){
			System.out.println(playersList[indexOfUsername + 1]);
		}
		prompt();
	}

	// rankings function
	private static void rankings(){
		//apply sorting
		String[] varList = new String[20]; 

		// list in desc order
		if(variables == null || variables.equalsIgnoreCase("desc")) {
			varList = sort("desc", playersList);
		}
		// list in asc order 
		else if(variables.equalsIgnoreCase("asc")) {
			varList = sort("asc", playersList);
		}

		for (int i = 0; i < 10; i++){
			if (varList[i] != null){
				String[] variable = varList[i].split(",");
				int gameAmount = Integer.parseInt(variable[3].split(" ")[0]);
				int percentage;
				if (gameAmount == 0){
					percentage = 0;
				}else{
					percentage = 100 * Integer.parseInt(variable[4].split(" ")[0]) / gameAmount;
				}
				String result = "%-4d%%| %s games | %s %s%n";
				System.out.printf(result, percentage, statFormatter(gameAmount), variable[1], variable[2]);	
			}
		}
		prompt();
	}

	// stat formatter
	private static String statFormatter(int gameAmount){
		String gameAmountString;
		if (gameAmount < 10){
			gameAmountString = "0"+Integer.toString(gameAmount);
		}else{
			gameAmountString = Integer.toString(gameAmount);
		}
		return gameAmountString;
	}

	// sorting ranking function
	private static String[] sort(String order, String[] args){
		// getting sub list
		String[] varsList = new String[20];
		int count = 0;
		for (int i = 1; i < args.length - 1; i += 2){
			varsList[count] = args[i];
			count ++;
		}

		// if alphabetically
		if (order.equalsIgnoreCase("alphabet")){
		    for(int i = 0; i < varsList.length - 1; i++){
		        for(int j = i+1; j < varsList.length; j++){
		        	if (varsList[i] != null && varsList[j] != null){
			            if(varsList[i].split(",")[0].compareTo(varsList[j].split(",")[0]) >=0){
			                String temp = varsList[i];
			                varsList[i] = varsList[j];
			                varsList[j] = temp;
			            }
		        	}
		        }
		    }
	    }		

		//if asc
		if (order.equalsIgnoreCase("asc")){
			// selection sort
			for(int i = 0; i < varsList.length - 1; i++){
		        for(int j = i + 1;j < varsList.length; j++){
		        	if (varsList[i] != null && varsList[j] != null){

		        		// count percentage and avoid divide 0
		        		int perc_i, perc_j;
			        	if (Integer.parseInt(varsList[i].split(",")[3].split(" ")[0]) != 0){
			        		perc_i = Integer.parseInt(varsList[i].split(",")[4].split(" ")[0]) / Integer.parseInt(varsList[i].split(",")[3].split(" ")[0]);
			        	}else{
			        		perc_i = 0;
			        	}

			        	if (Integer.parseInt(varsList[j].split(",")[3].split(" ")[0]) != 0){
			        		perc_j = Integer.parseInt(varsList[j].split(",")[4].split(" ")[0]) / Integer.parseInt(varsList[j].split(",")[3].split(" ")[0]);
			        	}else{
			        		perc_j = 0;
			        	}

			        	// selection sort
			            if(perc_i > perc_j){
			                String temp = varsList[i];
			                varsList[i] = varsList[j];
			                varsList[j] = temp;
			            }	        		
		        	}
		        }
		    }			
		}
		// if desc
		else if (order.equalsIgnoreCase("desc")){
			// selection sort
			for(int i = varsList.length - 1; i >= 0; i--){
		        for(int j = i - 1;j >= 0; j--){
		        	if (varsList[i] != null && varsList[j] != null){

		        		// count percentage and avoid divide 0
		        		int perc_i, perc_j;
			        	if (Integer.parseInt(varsList[i].split(",")[3].split(" ")[0]) != 0){
			        		perc_i = Integer.parseInt(varsList[i].split(",")[4].split(" ")[0]) / Integer.parseInt(varsList[i].split(",")[3].split(" ")[0]);
			        	}else{
			        		perc_i = 0;
			        	}

			        	if (Integer.parseInt(varsList[j].split(",")[3].split(" ")[0]) != 0){
			        		perc_j = Integer.parseInt(varsList[j].split(",")[4].split(" ")[0]) / Integer.parseInt(varsList[j].split(",")[3].split(" ")[0]);
			        	}else{
			        		perc_j = 0;
			        	}

			        	// selection sort
			            if(perc_i > perc_j){
			                String temp = varsList[i];
			                varsList[i] = varsList[j];
			                varsList[j] = temp;
			            }	        		
		        	}
		        }
		    }			
		}
	    return varsList;
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
