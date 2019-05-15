/*
Shane Hagen
EE 456
Homework Assignment - Competitive Network
*/

import java.io.*;
import java.util.*;
import java.math.*;
import java.text.*;

public class hw {

  public static void main(String[] args) {
    double[] nodes;
    nodes = new double[4];
    DecimalFormat decim = new DecimalFormat("#.##");

    //get values
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter initial node values: ");
    for( int input = 0 ; input < nodes.length; input++){
      double i = sc.nextDouble();
      nodes[input] = i;
    }
    System.out.print("Enter epsilon value: ");
    double epsilon = sc.nextDouble();
    System.out.print("Enter alpha value: ");
    double alpha = sc.nextDouble();
    System.out.print("Enter number of iterations: ");
    int iter = sc.nextInt();

    //Print initial values
    System.out.print("Initial node values: [ ");
    for(double x : nodes){
      System.out.print(x + " ");
    }
    System.out.println("]");

    double newValue = 0.0;
    double nodeSum = 0;

    //Perform cycle 'iter' times
    int count = 0;
    double[] temp;
    temp = new double[nodes.length];
    while(count < iter){
      //Network cycle
     for( int x = 0; x < nodes.length; x++ ){
       double sumDiff = sum(nodes);
       sumDiff -= nodes[x];
       newValue = alpha*nodes[x] + epsilon*(sumDiff);

       //Unipolar step activation
       if(newValue < 0){
         temp[x] = 0;
       }
       else {
         temp[x] = newValue;
       }
     }
     count++;
     //Update after full iteration
     for( int y = 0; y < nodes.length; y++){
       nodes[y] = temp[y];
     }
    }
    System.out.print("Final node values: [ ");
    for(double x : nodes){
      String output = decim.format(x);
      System.out.print(output + " ");
    }
    System.out.println("]");
  }

  static double sum(double[] array){
    double sum = 0;
    for (int i = 0; i < array.length; i++){
      sum += array[i];
    }
    return sum;
  }
}
