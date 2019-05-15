/*
Shane Hagen
CMPSC 463
Problem Set #8
*/

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
  public static void main(String[] args) throws NumberFormatException, IOException{
    InputStreamReader ipr = new InputStreamReader(System.in);
    BufferedReader reader = new BufferedReader(ipr);
    Integer n = Integer.valueOf(reader.readLine());
    //Maze is size n x n

    Integer[][] maze = new Integer[n][n];
    ArrayList<String> openRooms = new ArrayList<>();
    boolean check = true;
    while(check == true){
      String line = reader.readLine();

      if(line.equals("-1")){
        check = false;
      }
      else {
      openRooms.add(String.valueOf(line));
      }
    }
    System.out.println(iterateRooms(openRooms, n));
  }

  static Integer iterateRooms(ArrayList<String> rooms, Integer n){
    //Initialize maze
    Integer[][] maze = new Integer[n][n];
    for (int i = 0; i < n; i++){
      for (int j = 0; j < n; j++){
        maze[i][j] = 0;
      }
    }

    //Initialize DisjointSet where U = # of rooms
    int rows = n;
    int columns = n;
    DisjointSet ds = new DisjointSet(rows*columns);
    boolean path = false;
    Integer rowVal, columnVal, rowIndex, columnIndex = 0;

    for(int k = 0; k < rooms.size(); k++){
      String room = rooms.get(k);
      String[]split = room.split(" ");


      rowVal = Integer.valueOf(split[0]);
      columnVal = Integer.valueOf(split[1]);
      rowIndex = rowVal - 1;
      columnIndex = columnVal - 1;

      //Open room
      maze[rowIndex][columnIndex] = 1;
      Integer currentRoom = rowIndex * n + columnVal;
      ds.makeSet(currentRoom);

      //Check adjacent rooms and call union if one is open
      //Room to right
      if(rowIndex + 1 < rows && maze[rowIndex + 1][columnIndex] == 1) {
        if(ds.find(currentRoom) != ds.find((rowIndex + 1) * (n) + (columnVal))){
          ds.union(currentRoom, (rowIndex + 1) * (n) + columnVal);
        }
      }
      //Room to left
      if(rowIndex - 1 >= 0 && maze[rowIndex - 1][columnIndex] == 1) {
        if(ds.find(currentRoom) != ds.find((rowIndex - 1) * (n) + (columnVal))){
          ds.union(currentRoom, (rowIndex-1) * (n) + columnVal);
        }
      }
      //Room above
      if(columnIndex + 1 < columns && maze[rowIndex][columnIndex + 1] == 1) {
        if(ds.find(currentRoom) != ds.find((rowIndex) * (n) + (columnVal + 1))){
          ds.union(currentRoom, (rowIndex) * (n) + (columnVal + 1));
        }
      }
      //Room below
      if(columnIndex - 1 >= 0 && maze[rowIndex][columnIndex - 1] == 1) {
        if(ds.find(currentRoom) != ds.find((rowIndex) * (n) + (columnVal - 1))){
          ds.union(currentRoom, (rowIndex) * (n) + (columnVal - 1));
        }
      }
      //Check path after opening a room
      path = checkPath(maze, ds, n);
      if(path == true){
        return k;
      }
    }
    return -1;
  }

  static boolean checkPath(Integer maze[][], DisjointSet ds, Integer n){
    //Check open rooms in first row to determine if they're root is in the last row
    for(int x = 0; x < maze.length; x++){
      if(maze[0][x] == 1){
        //If root is greater than or equal to n*n - (n-1) then root is in last row
        if(ds.find(x + 1) >= (n * n) - (n - 1)){
          return true;
        }
      }
    }
    return false;
  }
}
