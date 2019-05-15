/*
Shane Hagen
EE 456
Constrained Optimization Network
*/

import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;

public class copt {
  public static void main(String[] args) {
    // 10 campuses - Behrend, DuBois, Altoona, University Park, Hazelton, Harrisburg, Berks, Scranton, York, Abington
    String[] campuses = new String[]{"Behrend", "DuBois", "Altoona", "University Park", "Hazelton", "Harrisburg", "Berks", "York", "Abington", "Great Valley"};
    // Tour matrix
    int[][] tour = new int [10][10];
    tour = intializeTourMatrix(tour);
    // Distance matrix
    int[][] distance = new int[10][10];
    distance = intializeDistanceMatrix(distance);
    //Optimize Tour
    tour = optimizeTour(distance, tour);
    //Print tour matrix
    for(int i = 0; i < 10; i++)
    {
      for(int j = 0; j < 10; j++)
      {
        System.out.print(tour[i][j] + ", ");
      }
      System.out.println();
    }
  }

  public static int[][] intializeDistanceMatrix(int[][] distance)
  {
    // Fill distance matrix
    for(int i = 0; i < 10; i++)
    {
      for(int j = 0; j < 10; j++)
      {
        if(i == j)
        {
          distance[i][j] = 0;
        }
      }
    }
    // Manually enter distances
    // Behrend <-> Dubois
    distance[0][1] = 152;
    distance[1][0] = 152;
    //Behrend <-> Altoona
    distance[0][2] = 201;
    distance[2][0] = 201;
    //Behrend <-> UP
    distance[0][3] = 213;
    distance[3][0] = 213;
    //Behrend <-> Hazelton
    distance[0][4] = 314;
    distance[4][0] = 314;
    //Behrend <-> HBG
    distance[0][5] = 314;
    distance[5][0] = 314;
    //Behrend <-> Berks
    distance[0][6] = 356;
    distance[6][0] = 356;
    //Behrend <-> York
    distance[0][7] = 330;
    distance[7][0] = 330;
    //Behrend <-> Abington
    distance[0][8] = 420;
    distance[8][0] = 420;
    //Behrend <-> Great Valley
    distance[0][9] = 345;
    distance[9][0] = 345;
    //Dubois <-> Altoona
    distance[1][2] = 55;
    distance[2][1] = 55;
    //Dubois <-> UP
    distance[1][3] = 61;
    distance[3][1] = 61;
    //Dubois <-> Hazelton
    distance[1][4] = 162;
    distance[4][1] = 162;
    //Dubois <-> Harrisburg
    distance[1][5] = 161;
    distance[5][1] = 161;
    //Dubois <-> Berks
    distance[1][6] = 204;
    distance[6][1] = 204;
    //Dubois <-> York
    distance[1][7] = 178;
    distance[7][1] = 178;
    //Dubois <-> Abington
    distance[1][8] = 268;
    distance[8][1] = 268;
    //Dubois <-> Great Valley
    distance[1][9] = 231;
    distance[9][1] = 231;
    //Altoona <-> UP
    distance[2][3] = 42;
    distance[3][2] = 42;
    //Altoona <-> Hazelton
    distance[2][4] = 155;
    distance[4][2] = 155;
    //Altoona <-> Harrisburg
    distance[2][5] = 146;
    distance[5][2] = 146;
    //Altoona <-> Berks
    distance[2][6] = 194;
    distance[6][2] = 194;
    //Altoona <-> York
    distance[2][7] = 149;
    distance[7][2] = 149;
    //Altoona <-> Abington
    distance[2][8] = 241;
    distance[8][2] = 241;
    //Altoona <-> Great Valley
    distance[2][9] = 215;
    distance[9][2] = 215;
    //University Park <-> Hazelton
    distance[3][4] = 114;
    distance[4][3] = 114;
    //University Park <-> Harrisburg
    distance[3][5] = 98;
    distance[5][3] = 98;
    //University Park <-> Berks
    distance[3][6] = 141;
    distance[6][3] = 141;
    //University Park <-> York
    distance[3][7] = 115;
    distance[7][3] = 115;
    //University Park <-> Abington
    distance[3][8] = 194;
    distance[8][3] = 194;
    //University Park <-> Great Valley
    distance[3][9] = 168;
    distance[9][3] = 168;
    // Hazelton <-> Harrisburg
    distance[4][5] = 88;
    distance[5][4] = 88;
    // Hazelton <-> Berks
    distance[4][6] = 54;
    distance[6][4] = 54;
    // Hazelton <-> York
    distance[4][7] = 110;
    distance[7][4] = 110;
    // Hazelton <-> Abington
    distance[4][8] = 113;
    distance[8][4] = 113;
    // Hazelton <-> Great Valley
    distance[4][9] = 114;
    distance[9][4] = 114;
    // Harrisburg <-> Berks
    distance[5][6] = 56;
    distance[6][5] = 56;
    // Harrisburg <-> York
    distance[5][7] = 32;
    distance[7][5] = 32;
    // Harrisburg <-> Abington
    distance[5][8] = 105;
    distance[8][5] = 105;
    // Harrisburg <-> Great Valley
    distance[5][9] = 78;
    distance[9][5] = 78;
    // Berks <-> York
    distance[6][7] = 55;
    distance[7][6] = 55;
    // Berks <-> Abington
    distance[6][8] = 67;
    distance[8][6] = 67;
    // Berks <-> Great Valley
    distance[6][9] = 41;
    distance[9][6] = 41;
    // York <-> Abington
    distance[7][8] = 77;
    distance[8][7] = 77;
    // York <-> Great Valley
    distance[7][9] = 77;
    distance[9][7] = 77;
    // Abington <-> Great Valley
    distance[8][9] = 26;
    distance[9][8] = 26;

    return distance;
  }

  public static int[][] intializeTourMatrix(int[][] tour)
  {
    //Fill tour matrix
    for(int i = 0; i < 10; i++)
    {
      for(int j = 0; j < 10; j++)
      {
        // Generate random 0 or 1
        int random = (int)(Math.random() * 2 + 0) % 2;
        tour[i][j] = random;
      }
    }
    return tour;
  }

  public static int[][] optimizeTour(int[][] distance, int[][] tour)
  {
    // Calculate penalty
    int pen = 0;
    pen = getPenalty(distance, tour);

    for(int n = 0; n < 100; n++)
    {
      // Flip a cell at random
      int random1 = (int)(Math.random() * 10) % 10;
      int random2 = (int)(Math.random() * 10) % 10;
      if(tour[random1][random2] == 0) { tour[random1][random2] = 1; }
      if(tour[random1][random2] == 1) { tour[random1][random2] = 0; }
      // Calculate new penalty
      new_pen = getPenalty(distance, tour)
      // Calculate delta penalty
      delta_pen = new_pen - pen;
    }
    return tour;
  }

  public static int getPenalty(int[][] distance, int[][] tour)
  {
    int pen = 0;

    for(int i = 0; i < 10; i++)
    {
      int days = 0;
      int cities = 0;
      for(int j = 0; j < 10; j++)
      {
        days += tour[i][j];
        cities += tour[j][i];
      }
      System.out.println(days);
      System.out.println(cities);
      // Multiple days in a city
      if(days > 1)
      {
        pen += 2*days;
      }
      // Multiple cities in a day
      if(cities > 1)
      {
        pen += 2* cities;
      }
      // No cities in a day
      if(cities == 0)
      {
        pen += 10;
      }
      // No days in a city
      if(days == 0)
      {
        pen += 10;
      }
    }
    System.out.println(pen);
    return pen;
  }
}
