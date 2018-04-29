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
public class NimPlayer {
    private static Scanner keyboard = new Scanner(System.in);

    // initialize player
    // public String playerName(String playerNumber)
    // {
    //     System.out.println("Please enter Player "+playerNumber+"'s name:");
    //     return keyboard.nextLine();
    // }

    // remove stone method
    public int removeStone(String currentPlayer){
        System.out.printf("%s's turn - remove how many?%n", currentPlayer);
        return keyboard.nextInt();
    }

    // // initialize stone upper bound
    // public int stoneUpperBound(){
    //     System.out.println("Please enter upper bound of stone removal:");
    //     return keyboard.nextInt();
    // }

    // // initialize stone amount
    // public int currentStoneAmount(){
    //     System.out.println("Please enter initial number of stones:");
    //     return keyboard.nextInt();
    // }

    // remove player prompt 
    public String removePromptInput(){
        System.out.println("Are you sure you want to remove all players? (y/n)");
        return keyboard.nextLine();        
    }

    // reset stat prompt 
    public String resetStatInput(){
        System.out.println("Are you sure you want to reset all player statistics?");
        return keyboard.nextLine();        
    }

    // prompt input
    public String promptInput(){
        System.out.println(" ");
        System.out.print("$");
        return keyboard.nextLine();        
    }

    // play again
    public String reply(){
        System.out.println("Do you want to play again (Y/N):");
        return keyboard.next();        
    }
}
