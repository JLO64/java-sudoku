//Made by Julian Lopez

import java.io.*;
import java.util.*;

public class java_sudoku
{
  private static int [][] gameboard = new int [9][9];
  public static void main(String[] args)
  {
    System.out.println("Here's a board!");
    board_methods.boardZero(gameboard);
    
    board_methods.generateBoard(gameboard);
    System.out.println();
    board_methods.boardPrint(gameboard);
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
  public static int [][] generateBoard(int [][] board)
  {
    for(int nineNum = 1; nineNum <= 9; nineNum++)
    {
      board = generateSquares(nineNum, board);
    }
    return board;
  }
  public static int [][] generateSquares(int nineNum, int [][] board)
  {
    for(int i = 0; i < 9; i+=3)
    {
      for(int j = 0; j < 9; j+=3)
      {
        boolean validPos = false;
        while( validPos == false )
        {
          int xPos = (int) (Math.random() * 3 );
          int yPos = (int) (Math.random() * 3 );
          if(board[yPos+i][xPos+j] == 0 )
          {
            board[yPos+i][xPos+j] = nineNum;
            validPos = true;
            boardPrint(board);
          }
        }   
      }
    }
    return board;
  }
  public static boolean checkPos(int nineNum, int [][] board, int xPos, int yPos)
  {
    for(int i = 0; i < board.length; i++)
    {
      if(board[i][xPos] == nineNum && i != yPos)
      {
        return false;
      }
    }
    return true;
  }
}