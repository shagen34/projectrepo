import java.io.*;
import java.util.*;
import java.text.*;
import java.sql.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) throws NumberFormatException, IOException {
      InputStreamReader ipr = new InputStreamReader(System.in);
      BufferedReader reader = new BufferedReader(ipr);
      ArrayList<Integer> inputs = new ArrayList<>();
      String current = "";
      do{
        current = reader.readLine();
        if(Integer.valueOf(current) != Integer.valueOf(-1)){
          inputs.add(Integer.valueOf(current));
        }
      }while(Integer.valueOf(current)!=Integer.valueOf(-1));
      ArrayList<Long> results = new ArrayList<>();
      countRollSequence(inputs, results);
      for(Long i : results) { System.out.println(i); }
    }

    static void countRollSequence(ArrayList<Integer> inputs, ArrayList<Long> results)
    {
      int max_value = 99;
      int[] dice_values = { 2, 3, 4, 5, 6 };
      int[] memo = new int[max_value];
      Arrays.fill(memo, 0);
      for(Integer i : inputs){
        results.add(countRolls(i, dice_values, memo));
      }
    }
    static long countRolls(Integer i, int[] values, int[] memo){
      //Base cases
      //i can only be reached with 1 roll
      if (i == 2 || i == 3){
        return 1;
      }
      //Recursion
      if(i > 3) {
        //Call function on all possible rolls ending at i
        for(int j = 0; j < values.length; j++){
            if(i - Integer.valueOf(values[j]) >= 2){
              memo[i] += countRolls(i - Integer.valueOf(values[j]), values, memo);
            }
        }
      }
      //Return count
      return memo[i];
      }
    }
