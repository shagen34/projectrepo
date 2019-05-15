/*
Shane Hagen
CMPSC 463
Problem Set #1 - Most Common Sum (Iterative)
*/



import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
      Scanner s = new Scanner(System.in);
      int n = s.nextInt();
      int[] elements = new int[n];
      for(int i = 0; i < n; i++){
        elements[i] = s.nextInt();
      }
      System.out.println(getMostCommonSum(elements));
    }

    static String getMostCommonSum(int[] nums) {
      if((nums.length - 1) < 1 || (nums.length - 1) > 25){
        String empty = "";
        return empty;
      }
      else {
        //Hashtable to store sums as keys and repition as value
        Hashtable<String, Integer> results = new Hashtable<String, Integer>();
        mostCommonhelper(nums, results, 0);

        int mostCommon = 0;
        int temp = 0;
        String commonKey = "";
        for (Map.Entry<String, Integer> entry : results.entrySet()){
          if (entry.getValue() > mostCommon) {
            mostCommon = entry.getValue();
            commonKey = entry.getKey();
          }
          if (entry.getValue() == mostCommon) {   // If tie give to smaller sum
            if (Integer.parseInt(entry.getKey()) < Integer.parseInt(commonKey)){
              mostCommon = entry.getValue();
              commonKey = entry.getKey();
            }
          }
        }
        return (commonKey + " " + Integer.toString(mostCommon));
      }
    }

    static void mostCommonhelper(int[] nums, Hashtable<String, Integer> results, int sum) {
      Stack <Integer> sumStack = new Stack<>();
      Stack<Integer>indexStack = new Stack<>();

      sumStack.push(sum);
      indexStack.push(0);

      while (!sumStack.isEmpty()) {
        sum = sumStack.pop();
        int i = indexStack.pop();

        if(i == nums.length) {
          String str = Integer.toString(sum);
          if (results.containsKey(str)) {
            int sumCount = results.get(str);
            sumCount++;
            results.put(str, sumCount);
          }
          else {
            results.put(str, 1);
          }
        }
        else {
          sumStack.push(sum + nums[i]);
          indexStack.push(i+1);
          sumStack.push(sum);
          indexStack.push(i+1);
        }
      }
    }
  }
