import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) throws NumberFormatException, IOException {
        InputStreamReader ipr = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(ipr);
        int n = Integer.valueOf(reader.readLine());

        ArrayList<String> vStrains = new ArrayList<>();
        ArrayList<String> knownEdges = new ArrayList<>();
        ArrayList<String> unknownEdges = new ArrayList<>();


        for (int i = 0; i < n; i++){
          String[] line = reader.readLine().split(" ");

          String strain1 = String.valueOf(line[0]);
          String relation = String.valueOf(line[1]);
          String strain2 = String.valueOf(line[2]);

          if(!vStrains.contains(strain1)){
            vStrains.add(strain1);
          }
          if(!vStrains.contains(strain2)){
            vStrains.add(strain2);
          }

          if(relation.equals("->")){
            knownEdges.add(strain1 + " " + strain2);
          }
          else {
            unknownEdges.add(strain1 + " " + strain2);
          }
        }

        ArrayList<String> candidates = new ArrayList<>();
        boolean[] check = new boolean[vStrains.size()];
        String[] parents = new String[vStrains.size()];
        Arrays.fill(parents, "");

        checkChildren(vStrains, check, parents, knownEdges);
        checkRelation(vStrains, check, parents, unknownEdges);

        for(int j = 0; j < vStrains.size(); j++){
          if(check[j] != true){
            candidates.add(vStrains.get(j));
          }
        }
        Collections.sort(candidates);

        for(String str : candidates) System.out.println(str);
    }
    //Function checks known edges and sets check[strains.Index] to true if strain is a child
    static void checkChildren(ArrayList<String> strains, boolean[] check, String[] parent, ArrayList<String> known){
      for(int i = 0; i < strains.size(); i++){
        for(String edg : known){
          String[] split = edg.split(" ");

          if(split[1].equals(strains.get(i))){
            parent[i] = split[0];
            check[i] = true;
          }
        }
      }
    }
    //Function checks unknown edges and sets check[strains.Index] to true if strain has relationship with another strain that is not its parent
    static void checkRelation(ArrayList<String> strains, boolean[] check, String[] parent, ArrayList<String> unknown){
      for(String unedg : unknown){
        String[] split = unedg.split(" ");
        if(check[strains.indexOf(split[0])] == true){
          if(split[1] != parent[strains.indexOf(split[0])] && parent[strains.indexOf(split[0])] != ""){
            check[strains.indexOf(split[1])] = true;
            }
        }
        if(check[strains.indexOf(split[1])] == true){
          if(split[0] != parent[strains.indexOf(split[1])] && parent[strains.indexOf(split[1])] != ""){
            check[strains.indexOf(split[0])] = true;
          }
        }
      }
    }
  }
