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

	public int NimAIPlayer(int stoneUpperBound, int currentStoneAmount, int initStoneAmount) {

    // return a winning guaranteed stone amount

    // init return stone amount
    int returnAmount = 0;


    // get winNumbersList
    int[] winNumber = winNumbers(initStoneAmount, stoneUpperBound);

    // then identify the number to pick from the winNumber list above
    for (int i = 0; i < winNumber.length - 2; i++){
      // if match winning condition
      if (winNumber[i] > 0){
        if ((initStoneAmount - currentStoneAmount) < winNumber[i] && (initStoneAmount - currentStoneAmount) > winNumber[i+1]){
          returnAmount = winNumber[i] - (initStoneAmount - currentStoneAmount);
          // exit loop
          i = winNumber.length - 2;
        }
        // if not match winning condition, return valid random number
        else if ((initStoneAmount - currentStoneAmount) == winNumber[i]) {
          returnAmount = ThreadLocalRandom.current().nextInt(1, stoneUpperBound + 1);
          // exit loop
          i = winNumber.length - 2;
        }           
      }
    }

    return returnAmount;

	}

  // make a list of number to reach to guarantee wins
  private static int[] winNumbers(int initStoneAmount, int stoneUpperBound){
    int[] winNumber = new int[20];
    int remainStoneAmount = initStoneAmount-1;
    int count = 0;
    while (remainStoneAmount >= 0){
      winNumber[count] = remainStoneAmount;
      remainStoneAmount = remainStoneAmount - (stoneUpperBound + 1);
      count++;
    }
    return winNumber;
  }

    // remove stone method
    public int removeStone(String currentPlayer, int stoneUpperBound, int currentStoneAmount, int initStoneAmount){
        Nimsys nameList  = new Nimsys();
        String playerName = nameList.playerList(currentPlayer).split(",")[1];
        System.out.printf("%s's turn - remove how many?%n", playerName);
        System.out.println();
        return NimAIPlayer(stoneUpperBound, currentStoneAmount, initStoneAmount);
    }

	public String advancedMove(boolean[] available, String lastMove) {
		// the implementation of the victory
		// guaranteed strategy designed by you
		String move = "";
		
		return move;
	}
}