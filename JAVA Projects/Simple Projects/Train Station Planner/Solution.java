import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    public static void main(String[] args) throws NumberFormatException, IOException {
       BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
       int n = Integer.valueOf(reader.readLine());

       SimpleDateFormat format = new SimpleDateFormat("HH:mm");
       Date[] startTimes = new Date[n];
       Date[] endTimes = new Date[n];
       String[] times = new String[2*n];
       String[] temp = new String[2];

       int count = 0;
       for (int i = 0; i < n; i++) {
           temp = reader.readLine().split(" ");
           times[count] = temp[0];
           count++;
           times[count] = temp[1];
           count++;
       }

       count = 0;
       try {
         for (int i = 0; i < n; i++) {
             startTimes[i] = format.parse(times[count]);
             count++;
             endTimes[i] = format.parse(times[count]);
             count++;
         }
       }
       catch(Exception e){
         System.out.println(e);
       }

       requiredPlatforms(startTimes, endTimes);
       /*
       for (int i = 0; i < n; i++) {
         System.out.println(startTimes[i] + " " + endTimes[i]);
       }
       */
    }

  static void sortTimes(Date[] times){
     sortHelper(times, 0, times.length - 1);
   }

  static void sortHelper(Date[] times, int lowIndex, int highIndex) {
     SimpleDateFormat format = new SimpleDateFormat("HH:mm");
     int i = lowIndex;
     int j = highIndex;

     Date pivot = times[lowIndex+(highIndex-lowIndex)];

     while(i <= j) {
       while(times[i].before(pivot)){
         i++;
       }
       while(times[j].after(pivot)){
         j--;
       }
       if(i <= j) {
         Date temp = times[i];
         times[i] = times[j];
         times[j] = temp;

         i++;
         j--;
        }
       }
     if(lowIndex < j) {
       sortHelper(times, lowIndex, j);
     }
     if(i < highIndex){
       sortHelper(times, i, highIndex);
     }
   }

  static void requiredPlatforms(Date[] startSet, Date[] endSet){

    sortTimes(startSet);
    sortTimes(endSet);
    platformCounter(startSet, endSet);
/*
    for (int i = 0; i < startSet.length; i++) {
      System.out.println(startSet[i] + " " + endSet[i]);
    }
*/
  }

  static void platformCounter(Date[] startSet, Date[] endSet){
    int current = 1;
    int max = 1;

    //Counters for arrival/depature times
    int i = 1;
    int j = 0;

    //Use current to track how many platsforms needed at that time
    while(i < startSet.length){
      if (startSet[i].before(endSet[j])){
        current++;
        i++;

        if(current > max)
        {
          max = current;
        }
      }
      else {
        current--;
        j++;
      }
    }
    System.out.println(max);
  }
}
