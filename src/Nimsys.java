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
import java.util.Arrays;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Nimsys {

	//public variables
	private static int currentStoneAmount, removeStoneAmount, stoneUpperBound;
	private static String currentPlayer, player1, player2;
	private static String[] playersList = new String[40];
	private static int playerAmount = 0;
	enum Actions {
		addplayer, addaiplayer, removeplayer, editplayer, resetstats, displayplayer, rankings, startgame, exit
	};
	private static int indexOfUsername;
	private static String variables;
	private static String username;
	private static boolean isAIPlayer = false;

	// system start
	public static void main (String[] args) {
		// read file
		Scanner inputStream = null;
		try{
			inputStream = new Scanner(new FileInputStream("players.txt"));
		}
		catch(FileNotFoundException e){
			System.out.println(e.getMessage());
			exitGame();
		}
		String playerListString = inputStream.nextLine();
		// store file to player list
		processingData(playerListString);

		// start Nimsys
		System.out.println("Welcome to Nim");
		prompt();
	}

	// return player data according to username
	public String playerList(String username){
		int index = getUserIndex(username);
		return playersList[index+1];
	}	

	// processing data from file
	private static void processingData(String list) {
		playersList = list.substring(1,list.length()-1).split(", ");
		int countPlayerAmount = 0;
		for (int i = 0; i < 40; i++){
			if (playersList[i] != null && !playersList[i].equalsIgnoreCase("null")){
				countPlayerAmount++;
			}
		}

		playerAmount = countPlayerAmount;
	}

	// command prompt
	private static void prompt() {
		NimPlayer prompt = new NimPlayer();
		String action = prompt.promptInput();

		// check action
		checkAction(action);
	}

	// action validity exception http://xanxusvervr.blogspot.com.au/2017/12/java.html
	private static class ActionException extends Exception{
	    public ActionException(String msg){
	        super(msg);
	    }
	}
	
	// action validity
	private static void checkActionValidity(String action) throws ActionException{
	    if (!action.equalsIgnoreCase("addPlayer") && !action.equalsIgnoreCase("editplayer") && !action.equalsIgnoreCase("removeplayer") && !action.equalsIgnoreCase("displayplayer") && !action.equalsIgnoreCase("resetstats") && !action.equalsIgnoreCase("rankings") && !action.equalsIgnoreCase("startgame") && !action.equalsIgnoreCase("exit")){
	        String msg = "‘"+action+"’ is not a valid command.";
	        throw new ActionException(msg);
	    }
	}

	// action validity exception http://xanxusvervr.blogspot.com.au/2017/12/java.html
	private static class NumOfArgsException extends Exception{
		public NumOfArgsException(String msg){
	        super(msg);
	    }
	}
	
	// num of args validity
	private static void checkNumberOfArgumentsValidity(String[] action, int correctNum) throws NumOfArgsException{
	    if (action.length != correctNum){
	    	String msg = "Incorrect number of arguments supplied to command.";
	        throw new NumOfArgsException(msg);
	    }
	}

	// num of args validity: 0 args 
	private static void checkNumberOfArgumentsValidityZero(String action) throws NumOfArgsException{
	    if (!action.equalsIgnoreCase("removeplayer") && !action.equalsIgnoreCase("resetstats") && !action.equalsIgnoreCase("displayplayer") && !action.equalsIgnoreCase("rankings") && !action.equalsIgnoreCase("exit")){
	    	String msg = "Incorrect number of arguments supplied to command.";
	        throw new NumOfArgsException(msg);
	    }
	}


	// return number of required args according to action
	private static int validNumberOfArgs(String action){
		int numOfArgs = 0;
		switch (action){
			case "addplayer":
				numOfArgs = 3;
				break;
			case "addaiplayer":
				numOfArgs = 3;
				break;
			case "removeplayer":
				numOfArgs = 1;
				break;
			case "editplayer":
				numOfArgs = 3;
				break;
			case "resetstats":
				numOfArgs = 1;
				break;
			case "displayplayer":
				numOfArgs = 1;
				break;
			case "startgame":
				numOfArgs = 4;
				break;
		}
		return numOfArgs;
	}

	// proceed input action
	private static void checkAction(String action) {
		// get action and check action validity
		String[] action_split = action.split(" ");
		if (action.length() > 0){
			// check action validity
			try{
		        checkActionValidity(action_split[0].toLowerCase());
		    }
		    catch(ActionException e){
		        System.out.println(e.getMessage());
		        prompt();
		    }
			Actions function = null;
			function = Actions.valueOf(action_split[0].toLowerCase());

			// set vars back to null
			variables = null; 
			username = null;

			// if has args, check number of args validity and get username index
			if (action_split.length > 1){
				// check num of args validity
				try{
			        checkNumberOfArgumentsValidity(action_split[1].split(","), validNumberOfArgs(action_split[0].toLowerCase()));
			    }
			    catch(NumOfArgsException e){
			        System.out.println(e.getMessage());
			        prompt();
			    }				
				// swap lname, fname for addplayer and editplayer
				if (action_split[0].equalsIgnoreCase("addplayer") || action_split[0].equalsIgnoreCase("editplayer")){
					variables = action_split[1].split(",")[0]+"," + action_split[1].split(",")[2] +","+ action_split[1].split(",")[1]; 
				}else{
					variables = action.split(" ")[1];
				}
				// get username and index
				username = variables.split(",")[0];
				indexOfUsername = getUserIndex(username);		
			}
			// if no args check number of args validity
			else if (action_split.length == 1){
				try{
					checkNumberOfArgumentsValidityZero(action_split[0].toLowerCase());
				}catch(NumOfArgsException e){
			        System.out.println(e.getMessage());
			        prompt();					
				}
			}

			switch(function){

				// addplayer 
				case addplayer:
					addPlayer();
					break;

				// addaiplayer
				case addaiplayer:
					addAiPlayer();
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
					exitGame();
					break;
			}
		
		// Scanner no waiting problem, prevent blank input				
		}else{
			prompt();
		}
	}

	// exit function
	private static void exitGame(){
		PrintWriter outputStreamName = null;
		try{
			outputStreamName = new PrintWriter(new FileOutputStream("players.txt", false));
		}
		catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
		outputStreamName.println(Arrays.toString(playersList));
		outputStreamName.close();
		System.out.println();
		System.exit(0);
	}


	// get user index
	private static int getUserIndex(String usernameInput){
		int index = -1;
		for (int i = 0; i < playersList.length; i++){
			if(playersList[i] != null){
				if (playersList[i].equalsIgnoreCase(usernameInput)){
					index = i;
				}
				// check if is ai player
				else if (playersList[i].equalsIgnoreCase("ai_"+usernameInput)){
					index = i;
					isAIPlayer = true;
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

	// is AI player indicator
	private static boolean isAIPlayer(){
		return isAIPlayer;
	}

	// add ai player function
	private static void addAiPlayer(){
		// player not exist
		if (indexOfUsername == -1){
			// mark in username as ai
			username = "ai_" + username;
			// save to playerList
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
			// sort player ajphabetically
			varsList = sort("alphabet", playersList);
			for (int i = 0; i < varsList.length; i++){
				if (varsList[i] != null && !varsList[i].equalsIgnoreCase("null")){
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
		if(variables == null || variables.equalsIgnoreCase("desc") || variables.equalsIgnoreCase("asc")) {
			varList = sort("desc", playersList);
		}

		// processing user game data
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
		// if number less than 10 then add 0 in front
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
			// check if player2 is ai player
			if (isAIPlayer()){
				gameVariables[3] = "ai_" + gameVariables[3];
			}
			// switch to player1 and check ai			
			userExist(gameVariables[2]);
			if (isAIPlayer()){
				gameVariables[2] = "ai_" + gameVariables[2];
			}

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
		// get index and get stats
		int index = getUserIndex(usernameInput);
		String[] playerVariables = playersList[index + 1].split(",");
		playerVariables[3] = Integer.toString(Integer.parseInt(playerVariables[3].split(" ")[0]) + 1) + " games";
		// if winner then update wins
		if (usernameInput.equalsIgnoreCase(winner)){
			playerVariables[4] = Integer.toString(Integer.parseInt(playerVariables[4].split(" ")[0]) + 1) + " wins";
		}
		String updatedPlayer = "";
		// re-assemble stat data
		for (int i = 0; i < playerVariables.length - 1; i++){
			updatedPlayer = updatedPlayer + playerVariables[i] + ",";
		}
		playersList[index + 1] = updatedPlayer + playerVariables[4];
	}

}
