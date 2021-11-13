/*Project 1 ConnectFour EECS 1510
 * Author: Johney Domas
 *  
 * Goal is to create a program that simulates playing connect four.
 *  
 * 
 */

import java.util.Scanner;

public class ConnectFour
{
	public static Scanner input = new Scanner(System.in); // need a scanner
	static final int ROWS = 6, COLS = 7; // rows and columns of array
	static char[][] board = new char[ROWS][COLS]; // 6-x-7 array to simulate the board

	public static void main(String[] args)
	{

		int playagain = 0; // declaring play again to be used for loop

		do  //need a loop, using a do while
		{
			boolean over = false; // over used for ending loop, is determined by isWinner method
			int count = 0; // count is also used for ending loop counts how many turns

			displayBoard(board); // displays board

			int i = 1;
			switch (i)
			{
			case 1:
				while (over != true || count == 42)
				{

					dropDisk('Y', board); // drops yellow disk
					count++;
					displayBoard(board); // displays board
					over = isWinner(board);
					if (over == true || count == 42)
						break; // checks winner

					dropDisk('R', board); // drops red disk
					count++;
					displayBoard(board); // displays board
					over = isWinner(board);
					if (over == true || count == 42)
						break; // checks winner

				}
			}
			//need to check who won
			if (over == true && isOddEven(count) == 1)
				System.out.println("Y has WON the game!"); //if count is odd, Y wins
			else if (over == true && isOddEven(count) == 2)
				System.out.println("R has WON the game!"); //if count is even, R wins
			else if (count == 42)
				System.out.println("I declare a draw"); //if count is 42, draw

			//Prompting to play again 
			System.out.println("DO YOU WANT TO PLAY A NEW GAME? (type 1 for yes)");
			playagain = input.nextInt();
			//reset board if user decides to play again
			if (playagain == 1)
				board = new char[6][7];
		} while (playagain == 1); //continues to loop as long as playagain is 1

	}

	private static void displayBoard(char[][] board)
	{
		// This method displays the current board, so the user can see what's
		// currently where, and they can plan their next move accordingly
		
		
		for (int i = board.length - 1; i >= 0; i--)  //for every row starting on the left
		{
			System.out.print("| ");  //print side of board
			for (int j = 0; j < board[i].length; j++) //for every column starting on the right
			{
				System.out.print(board[i][j] + " | "); //print value of spot with other side of board
			}
			System.out.println(); // newline
		}
	}

	public static boolean isWinner(char[][] board)
	{
		/* This method checks for a winner of the connect four game. 
		 * It uses static variable board, and checks the values of the array.
		 * It does this via many different logic checks.
		 */
		for (int r = 0; r < ROWS; r++) // for EVERY row
			for (int c = 0; c <= 3; c++) // for columns 0-3
				if (board[r][c] != '\u0000' && // if it's not empty, AND
						board[r][c] == board[r][c + 1] && // it matches neighbor 1 AND
						board[r][c] == board[r][c + 2] && // it matches TWO over AND
						board[r][c] == board[r][c + 3])
					return true; // THREE over, then it's a WINNER!

		for (int r = 0; r <= 2; r++) // for rows less than or equal to 2
			for (int c = 0; c < COLS; c++) // for all columns
				if (board[r][c] != '\u0000' && // if it's not empty, AND
						board[r][c] == board[r + 1][c] && // it matches above 1 AND
						board[r][c] == board[r + 2][c] && // it matches above 2 AND
						board[r][c] == board[r + 3][c])
					return true; // it match above 3, then it's a WINNER!

		for (int r = 0; r < 3; r++) // for rows less than 3
			for (int c = 0; c < 4; c++) // for columns less than 4
				if (board[r][c] != '\u0000' && // if it's not empty, AND
						board[r][c] == board[r + 1][c + 1] && // it matches above and over 1 AND
						board[r][c] == board[r + 2][c + 2] && // it matches above and over 2 AND
						board[r][c] == board[r + 3][c + 3]) // it matches above and over 3
					return true; // then it's a WINNER!

		for (int r = 3; r < ROWS; r++) // for EVERY row
			for (int c = 0; c < 4; c++) // for columns less than 4
				if (board[r][c] != '\u0000' && // if it's not empty, AND
						board[r][c] == board[r - 1][c + 1] && // it matches down and over 1 AND
						board[r][c] == board[r - 2][c + 2] && // it matches down and over 2 AND
						board[r][c] == board[r - 3][c + 3]) // it match down and over 3
					return true; // then it's a WINNER!

		for (int r = 3; r < 5; r++) // for rows than 5
			for (int c = 3; c < 6; c++) // for columns less than 6
				if (board[r][c] != '\u0000' && // if it's not empty, AND
						board[r][c] == board[r - 1][c - 1] && // it matches down and over 1 AND
						board[r][c] == board[r - 2][c - 2] && // it matches down and over 2 AND
						board[r][c] == board[r - 3][c - 3]) // it match down and over 3
					return true; // then it's a WINNER!

		return false; //otherwise return false, there is no winner

	}

	public static void dropDisk(char player, char[][] board)
	{
		/* This checks if the value entered within the range of the board index,
		 * and makes sure there isn't anything already placed there.
		 * 
		 */
		boolean done = false;
		do 
		{
			
			int column = input.nextInt(); //user enters column
			if (placeADisk(board, column, player)) //if method runs fine, then done=true
			{
				done = true;
			} else //else have them re enter a number
				System.out.println("YOU ENTERED AN INVALID INTEGER OR THE COLUMN IS FULL.");
		} while (done == false); //continues to loop until a valid number is entered.
	}

	
	static boolean placeADisk(char[][] board, int column, char player)
	{
		/* This method is used to edit the static array called board, 
		 * and place a disk at the location specified in the call.
		 * 
		 */
		
		if (column < 0 || column > 6) //checks for proper index
			return false;
		else 
		{
			for (int i = 0; i < board.length; i++) //run through every row
			{
				if (board[i][column] == '\u0000') //if the location is empty, disk gets dropped
				{
					board[i][column] = player;
					return true; // location was available so disc was dropped
				}
			}
			return false; // column is full
		}
	}

	public static int isOddEven(int a)
	{
		/*Checks if int a is even or odd, I used this to determine who won
		 * since yellow always starts.
		 */
		if (a % 2 == 0) // if the remainder is 0 then it is even
			return a = 2;
		else // if not then a is odd
			return a = 1;
	}

}
