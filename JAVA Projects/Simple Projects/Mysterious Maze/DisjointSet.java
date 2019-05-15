/*
Shane Hagen
CMPSC 463
Problem Set #8
*/
public class DisjointSet {
  int[] sets;

  public DisjointSet(int n) //n = size of the universal set U = { 1, 2, ... , n}
  {
    sets = new int[n+1];
  }

  public void checkIndex(int x)
  {
    if(x < 1 || x >= sets.length) throw new IndexOutOfBoundsException();
  }

  public void makeSet(int x)
  {
    checkIndex(x);
    if(sets[x] != 0)  throw new IllegalArgumentException("Item " + x + " is already in a set");
    sets[x] = -1;
  }

  public int find(int x)
  {
    checkIndex(x);
    if(sets[x] == 0)  throw new IllegalArgumentException("Item " + x + " is not in a set");

    //Implement function recursively
    if(sets[x] < 0) return x; //x is in the root and it is the name of the sets

    int root = find(sets[x]); //call find on the parent
    sets[x] = root; //path compression
    return root;
  }

  public void union(int x, int y)
  {
    checkIndex(x);
    checkIndex(y);

    int xRoot = find(x);
    int yRoot = find(y);

    if(sets[xRoot] >= 0 || sets[yRoot] >= 0 || xRoot==yRoot)  throw new IllegalArgumentException("union must take the roots of two distinct trees as arguments.");

    if(sets[xRoot] > sets[yRoot]){
      //x is smaller sets
      sets[yRoot] = sets[yRoot] + sets[xRoot]; //Update size of y
      sets[xRoot] = yRoot;
    }
    else {
      sets[xRoot] += sets[yRoot];
      sets[yRoot] = xRoot;
    }
  }
}
