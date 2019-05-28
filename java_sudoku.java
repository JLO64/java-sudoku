//Made by Julian Lopez

import java.util.*;

public class java_sudoku
{
	private static int [][] answerBoard = new int [9][9];
	private static int [][] questionBoard = new int [9][9];
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
			board_methods.generateCopyBoard(answerBoard, questionBoard);	//copies contents of answerBoard to questionBoard
			board_methods.generateQuestionBoard(questionBoard, emptySpaces);
			boolean hasWon = false;
			while(hasWon == false)
			{
				board_methods.boardPrint(questionBoard);
				sudoku_methods.changeValuePos(questionBoard);
				if(sudoku_methods.checkHasWon(questionBoard) == true)
				{
					hasWon = true;
					System.out.println("You got Sudoku!\nHere's the CPU's solution:");
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
				if(yesNo.equals("no") || yesNo.equals("No"))
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
    System.out.println();
    for(int i = 0; i < board.length; i++) //increments i in the y-direction
    {
      for(int j = 0; j < board[0].length; j++)  //increments j in the x-direction
      {
        if(j % 3 == 0)
        {
          System.out.print("|");
        }
        System.out.print(board[j][i]);
      }
      System.out.print("|\n");
      if(i % 3 == 2 && i != board.length-1)
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
	public static int [][] generateQuestionBoard(int [][] board, int numToClear)
  {
		for(int i = 0; i < numToClear; i++)
		{
			int xPos = (int) (Math.random() * 9 );
			int yPos = (int) (Math.random() * 9 );
			board[yPos][xPos] = 0;
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
	public static int [][] generateCopyBoard(int [][] boardOne, int [][] boardTwo)
	{
		for(int i = 0; i < boardOne.length; i++)
		{
			for(int j = 0; j < boardOne.length; j++)
			{
				boardTwo[j][i] = boardOne[j][i];
			}
		}
		return boardTwo;
	}
}

class sudoku_methods
{
	public static int [][] changeValuePos(int [][] board)
	{
		Scanner input = new Scanner(System.in);
		boolean xPosValid = false;
		boolean yPosValid = false;
		boolean squareValueValid = false;
		int xPos = 0;
		int yPos = 0;
		int squareValue = 0;
		List<Integer> possibleList = new ArrayList<Integer>();

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

		possibleList.add(0);
		for(int i = 1; i < board.length; i++)
		{
			if( board_methods.checkLinePos(i, board, yPos, xPos) == true && board_methods.checkSquarePos(i, board, yPos, xPos))
			{
				possibleList.add(i);
			}
		}

		while(squareValueValid == false)
		{
			System.out.println("What is the value you want to change the square to.");
			System.out.print("Here are the valid values[");
			for (Integer num : possibleList)
			{
				System.out.print(" " + num );
			}
			System.out.print("]: ");
			squareValue = input.nextInt();
			squareValueValid = true;
		}
		
		board[xPos][yPos] = squareValue;
		return board;
	}
	public static boolean checkHasWon(int [][] board)
	{
		for(int i = 0; i < board.length; i++)	//increments in y
		{
			for(int j = 0; j < board[0].length; j++)	//increments in x
			{
				if(board[j][i] == 0)
				{
					return false;
				}
			}
		}
		return true;
	}
	
}