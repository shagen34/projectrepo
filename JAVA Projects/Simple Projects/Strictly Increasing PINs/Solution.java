/*
Shane Hagen
CMPSC 463
Problem Set #1 - Strictly Increasing PINs, used Generate Strings code as reference
*/

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
  public static void main(String[] args) {
      Scanner s = new Scanner(System.in);
      int length = s.nextInt();
      int base = s.nextInt();
      ArrayList<String> PINs = generatePINs(length, base);
      for(String str : PINs) System.out.println(str);
  }

  static ArrayList<String> generatePINs(int n, int b) {
    if (n < 1 || b < n || b > 62) { return new ArrayList<String>(); } // Constraints for base rules and alphabet domain
    String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".substring(0, b);
    ArrayList<String> result = new ArrayList<>();
    StringBuilder start = new StringBuilder();
    int z = 0;
    generatePINhelper(alphabet, n, z, start, result);
    return result;
  }

  static void generatePINhelper(String alphabet, int n, int k, StringBuilder prefix, ArrayList<String> result) {
    if(prefix.length() == n) {
      if(!result.contains(prefix.toString())){
        result.add(prefix.toString());
      }
      return;
    }
    if(k > alphabet.length() - n) {
      return;
    }
    if(prefix != null && prefix.length()>0){
      if ((n - prefix.length()) > ((alphabet.length()-1) - (alphabet.indexOf(prefix.charAt(prefix.length() - 1))))){
        return;
      }
    }

    if (prefix == null || prefix.length()<=0) {  //If null StringBuilder, start with char at counter k
      prefix.append(alphabet.charAt(k));

      generatePINhelper(alphabet, n, k, prefix, result);
      prefix.setLength(prefix.length() - 1);
      generatePINhelper(alphabet, n, k + 1, prefix, result); //Start new null StringBuilder with position increment
    }
    else {
      for (int i = 0; i < alphabet.length(); i++) {
          int last = prefix.length() - 1;
          char c = prefix.charAt(last);

          if (alphabet.indexOf(alphabet.charAt(i)) > alphabet.indexOf(c)){
            prefix.append(alphabet.charAt(i));
            generatePINhelper(alphabet, n, k, prefix, result);
            prefix.setLength(prefix.length() - 1);
          }
      }

    }

  }
}
