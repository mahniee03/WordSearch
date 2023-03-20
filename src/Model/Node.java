
package Model;


public class Node {

    private Word word;
    private Node next;


    public Node (Word word) {
        this.word = word;
        this.next = null;
    }

    public Node getNext() {
	return this.next;
    }

    public void setNext(Node node) {
	this.next = node;
    }

    public Word getNode(){
	return word;
    }

}