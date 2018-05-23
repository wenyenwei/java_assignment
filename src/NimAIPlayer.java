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
import java.util.concurrent.ThreadLocalRandom;

public class NimAIPlayer extends NimPlayer{
	// you may further extend a class or implement an interface
	// to accomplish the tasks.	

	public int NimAIPlayer(int stoneUpperBound) {
		//return a valid stone amount randomly generated
		int randomNum = ThreadLocalRandom.current().nextInt(1, stoneUpperBound + 1);
		return randomNum;
	}

    // remove stone method
    public int removeStone(String currentPlayer, int stoneUpperBound){
        Nimsys nameList  = new Nimsys();
        String playerName = nameList.playerList(currentPlayer).split(",")[1];
        System.out.printf("%s's turn - remove how many?%n", playerName);
        System.out.println();
        return NimAIPlayer(stoneUpperBound);
    }

	public String advancedMove(boolean[] available, String lastMove) {
		// the implementation of the victory
		// guaranteed strategy designed by you
		String move = "";
		
		return move;
	}
}