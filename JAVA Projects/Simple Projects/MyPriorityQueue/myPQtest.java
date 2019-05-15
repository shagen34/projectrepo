// Define class for testing queue
import java.util.Random;

@SuppressWarnings("unchecked")
public class myPQtest{
  public static void main(String[] args){
    myPriorityQueue mpq = new myPriorityQueue(100);

    String alphabet = "abcdefghijklmnopqrstuvwxyz";
    int i = 0;

    while(i < mpq.queueCap){
      char randChar1 = alphabet.charAt((int)(Math.random() * 25));
      char randChar2 = alphabet.charAt((int)(Math.random() * 25));
      char randChar3 = alphabet.charAt((int)(Math.random() * 25));
      String randElement = new StringBuilder().append(randChar1)
                                              .append(randChar2)
                                              .append(randChar3)
                                              .toString();
      int randPriority = (int)(Math.random() * 100);
      Element addElement = new Element(randElement, randPriority);
      mpq.insert(addElement, randPriority);
    }
    System.out.println(mpq);
  }
}
