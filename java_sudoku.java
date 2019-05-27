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
    board_methods.boardPrint(gameboard);
  }
}

class board_methods
{
  public static void boardPrint(int [][] board)
  {
    for(int i = 0; i < board.length; i++) //increments i in the y-direction
    {
      for(int j = 0; j < board[0].length; j++)  //increments j in the x-direction
      {
        System.out.print(board[j][i]);
      }
      System.out.println();
    }
  }
  public static void boardZero(int [][] board)
  {
    for(int i = 0; i < board.length; i++) //increments i in the y-direction
    {
      for(int j = 0; j < board[0].length; j++)  //increments j in the x-direction
      {
        board[j][i] = 0;
      }
    }
  }
}