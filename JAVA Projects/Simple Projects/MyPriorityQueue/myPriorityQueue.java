/*
Shane Hagen
CMPSC 460
Final Project - done with Java
*/
// Define class for generic queue elements
class Element<T>{
  T element;
  int priority;
  Element(T element, int priority){
    this.element = element;
    this.priority = priority;
  }
}

// Define class for priority queue
class myPriorityQueue{
  private Element[] elements;
  public int queueSize, queueCap;

  public myPriorityQueue(int capacity){
    this.queueCap = queueCap + 1;
    elements = new Element[this.queueSize];
    queueSize = 0;
  }
  @SuppressWarnings("unchecked")
  public void insert(Element element, int priority){
    Element newElement = new Element(element, priority);
    elements[++queueSize] = newElement;
    int position = queueSize;
    while(position != 1 && newElement.priority > elements[position/2].priority){
      elements[position] = elements[position/2];
      position = position / 2;
    }
    elements[position] = newElement;
  }

  public void removeHighestPriority(){

    int newSize = queueSize - 1;
    Element[] newQueue = new Element[newSize];
    int i = 0;
    while(i < newSize){
      newQueue[i] = elements[i+1];
    }
    elements = newQueue;
  }

  public Object getHighestPriorityElement(){
    // Element with highest priority is first of array
    return this.elements[0].element;
  }
  public int getHighestPriority(){
    return this.elements[0].priority;
  }
  public int getQueueSize(){
    return this.queueSize;
  }
}
