//Made by Julian Lopez

import java.util.*;

public class java_sudoku
{
	private static int [][] answerBoard = new int [9][9];
	private static String [][] questionBoard = new String [9][9];
	private static int emptySpaces;

  public static void main(String[] args)
  {
		boolean wantsToPlay = true;
		Scanner input = new Scanner(System.in);
		while(wantsToPlay == true)
		{
			boolean validEmptySpaces = false;
			while(validEmptySpaces == false)
			{
				System.out.println("How many empty spaces do you want on the board?");
				System.out.print("Please enter a value between 1-80: ");
				emptySpaces = input.nextInt();
				if(emptySpaces > 0 && emptySpaces < 81)
				{
					validEmptySpaces = true;
				}
				else
				{
					System.out.print("	Please enter a value between 1-80");
				}
			}			

			System.out.println("Here's a board!");    
			board_methods.generateAnswerBoard(answerBoard);
			board_methods.generateStringCopyBoard(answerBoard, questionBoard);	//copies contents of answerBoard to questionBoard
			board_methods.generateQuestionBoard(questionBoard, emptySpaces);
			boolean hasWon = false;
			while(hasWon == false)
			{
				board_methods.boardPrint(questionBoard);
				sudoku_methods.changeValuePos(questionBoard);
				if(sudoku_methods.checkHasWon(questionBoard) == true)
				{
					hasWon = true;
					System.out.println("\nYou got Sudoku!\nHere's the CPU's solution:");
					board_methods.boardPrint(answerBoard);
				}
			}

			boolean validYN = false;
			String yesNo = "";
			while(validYN == false)
			{
				System.out.print("Do you want to play again: ");
				yesNo = input.next();
				if(yesNo.equals("yes") || yesNo.equals("Yes"))
				{
					validYN = true;
					wantsToPlay = true;
				}
				else if(yesNo.equals("no") || yesNo.equals("No"))
				{
					validYN = true;
					wantsToPlay = false;
				}
				else
				{
					System.out.println("	Please enter in either \"yes\" or \"no\"");
				}
			}
		}
  }
}

class board_methods
{
  public static void boardPrint(int [][] board)
  {
    System.out.println("\n  012 345 678");
    for(int yPos = 0; yPos < board.length; yPos++) //increments i in the y-direction
    {
			System.out.print(yPos);
			for(int xPos = 0; xPos < board[0].length; xPos++)  //increments j in the x-direction
      {
        if(xPos % 3 == 0)
        {
          System.out.print("|");
        }
        System.out.print(board[yPos][xPos]);
      }
      System.out.print("|\n");
      if(yPos % 3 == 2 && yPos != board.length-1)
      {
        System.out.print("-------------\n");
      }
		}
		System.out.println();
	}
	public static void boardPrint(String [][] board)
  {
    System.out.println("\n  012 345 678");
    for(int yPos = 0; yPos < board.length; yPos++) //increments i in the y-direction
    {
			System.out.print(yPos);
			for(int xPos = 0; xPos < board[0].length; xPos++)  //increments j in the x-direction
      {
        if(xPos % 3 == 0)
        {
          System.out.print("|");
        }
        System.out.print(board[yPos][xPos]);
      }
      System.out.print("|\n");
      if(yPos % 3 == 2 && yPos != board.length-1)
      {
        System.out.print("-------------\n");
      }
		}
		System.out.println();
  }
  public static int [][] boardZero(int [][] board)
  {
    for(int i = 0; i < board.length; i++) //increments i in the y-direction
    {
      for(int j = 0; j < board[0].length; j++)  //increments j in the x-direction
      {
        board[j][i] = 0;
      }
    }
    return board;
  }
  public static int [][] generateAnswerBoard(int [][] board)
  {
		int bombsDetonated = 0;
		boolean goodBoard = false;
		while (goodBoard == false)
		{
			boolean bombDetonated = false;
			boardZero(board);
			for(int nineNum = 1; nineNum <= 9; nineNum++)
    	{
    		for(int i = 0; i < 9; i+=3) // increments in the y direction
   			{
     			for(int j = 0; j < 9; j+=3) // increments in the x direction
     			{
       	 		boolean validPos = false;
				 		int numStuck = 0;
				 		while( validPos == false && bombDetonated == false)
				 		{
          		int xPos = (int) (Math.random() * 3 );
          		int yPos = (int) (Math.random() * 3 );
          		if(board[yPos+i][xPos+j] == 0 && checkLinePos(nineNum, board, xPos + j, yPos +i))
          		{
            		board[yPos+i][xPos+j] = nineNum;
            		validPos = true;
          		}
          		numStuck++;
          		if(numStuck >= 999)
          		{
								bombDetonated = true;
								bombsDetonated++;
          		}
        		}   
					}
    		}
			}
			if(bombDetonated == false)
			{
				goodBoard = true;
			}
		}
		System.out.println("Boards Generated: " + bombsDetonated);
		return board;
	}
	public static String [][] generateQuestionBoard(String [][] board, int numToClear)
  {
		for(int i = 0; i < numToClear; i++)
		{
			int xPos = (int) (Math.random() * 9 );
			int yPos = (int) (Math.random() * 9 );
			board[yPos][xPos] = " ";
		}
		return board;
  }
  public static boolean checkLinePos(int nineNum, int [][] board, int xPos, int yPos)
  {
    for(int i = 0; i < board.length; i++)
    {
      if(board[i][xPos] == nineNum && i != yPos)
      {
        return false;
			}
			if(board[yPos][i] == nineNum && i != xPos)
      {
        return false;
      }
    }
    return true;
	}
	public static boolean checkLinePos(int nineNum, String [][] board, int xPos, int yPos)
  {
    for(int i = 0; i < board.length; i++)
    {
      if(board[i][xPos].equals(Integer.toString(nineNum)) && i != yPos)
      {
        return false;
			}
			if(board[yPos][i].equals(Integer.toString(nineNum)) && i != xPos)
      {
        return false;
      }
    }
    return true;
	}
	public static boolean checkSquarePos(int nineNum, int [][] board, int xPos, int yPos)
  {
		for( int i = xPos - (xPos % 3); i < xPos - (xPos % 3) + 3; i++)
		{
			for( int j = yPos - (yPos % 3); j < yPos - (yPos % 3) + 3; j++)
			{
				if(board[j][i] == nineNum && i != xPos && j != yPos)
				{
					return false;
				}
			}
		}
		return true;
	}
	public static boolean checkSquarePos(int nineNum, String [][] board, int xPos, int yPos)
  {
		for( int i = xPos - (xPos % 3); i < xPos - (xPos % 3) + 3; i++)
		{
			for( int j = yPos - (yPos % 3); j < yPos - (yPos % 3) + 3; j++)
			{
				if(board[j][i].equals(Integer.toString(nineNum)) && i != xPos && j != yPos)
				{
					return false;
				}
			}
		}
		return true;
	}
	public static String [][] generateStringCopyBoard(int [][] boardOne, String [][] boardTwo)
	{
		for(int i = 0; i < boardOne.length; i++)
		{
			for(int j = 0; j < boardOne.length; j++)
			{
				boardTwo[j][i] = String.valueOf(boardOne[j][i]);
			}
		}
		return boardTwo;
	}
}

class sudoku_methods
{
	public static String [][] changeValuePos(String [][] board)
	{
		Scanner input = new Scanner(System.in);
		boolean xPosValid = false;
		boolean yPosValid = false;
		boolean squareValueValid = false;
		int xPos = 0;
		int yPos = 0;
		int squareValue = 0;

		while( xPosValid == false)
		{
			System.out.print("What is the X-Value [0-8] of the square you want to change: ");
			xPos = input.nextInt();
			if( xPos >= 0 && xPos <= 8 )
			{
				xPosValid = true;
			}
			else
			{
				System.out.println("	Please enter in a X-Value between 0 and 8");
			}
		}
		while(yPosValid == false)
		{
			System.out.print("What is the Y-Value [0-8] of the square you want to change: ");
			yPos = input.nextInt();
			if( yPos >= 0 && yPos <= 8 )
			{
				yPosValid = true;
			}
			else
			{
				System.out.println("	Please enter in a Y-Value between 0 and 8");
			}
		}

		System.out.print("What is the value you want to change the square to: ");
		board[yPos][xPos] = input.next();
		return board;
	}
	public static boolean checkSquareSum(String [][] board, int xPos, int yPos)
  {
		int sumOfNumsInSquare = 0;
		for( int i = xPos - (xPos % 3); i < xPos - (xPos % 3) + 3; i++)
		{
			for( int j = yPos - (yPos % 3); j < yPos - (yPos % 3) + 3; j++)
			{
				if(board[j][i].equals(" ") != true)
				{
					sumOfNumsInSquare = sumOfNumsInSquare + Integer.parseInt(board[j][i]);
				}
			}
		}
		if(sumOfNumsInSquare == 45)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public static boolean checkColSum(String [][] board, int xPos)
  {
		int sumOfNumsInCol = 0;
		for( int i = 0; i < board.length; i++)
		{
			if(board[i][xPos].equals(" ") != true)
			{
				sumOfNumsInCol += Integer.parseInt(board[i][xPos]);
			}			
		}
		if(sumOfNumsInCol == 45)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public static List<Integer> findPossibleList(String [][] board, int yPos, int xPos)
	{
		List<Integer> possibleList = new ArrayList<Integer>();
		possibleList.add(0);

		return possibleList;
	}
	public static boolean checkRowSum(String [][] board, int yPos)
  {
		int sumOfNumsInRow = 0;
		for( int i = 0; i < board.length; i++)
		{
			if(board[yPos][i].equals(" ") != true)
			{
				sumOfNumsInRow += Integer.parseInt(board[yPos][i]);
			}			
		}
		if(sumOfNumsInRow == 45)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public static boolean checkHasWon(String [][] board)
	{
		for(int yPos = 0; yPos < board.length; yPos++)	//increments in y
		{
			for(int xPos = 0; xPos < board[0].length; xPos++)	//increments in x
			{
				if(board[yPos][xPos] == Integer.toString(0) || checkSquareSum(board, xPos, yPos) == false || checkColSum(board, xPos) == false || checkRowSum(board, yPos) == false)
				{
					System.out.println("Test Failure at " + xPos + ", " + yPos + ": value is " + board[yPos][xPos]);
					System.out.println("	checkSquareSum: " + checkSquareSum(board, xPos, yPos));
					return false;
				}
			}
		}
		return true;
	}
}