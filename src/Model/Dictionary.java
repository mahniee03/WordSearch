
package Model;

import Controller.Exception.NoKeyException;
import Controller.Exception.DictionaryException;

public class Dictionary implements DictionaryADT{
    private Node head, prev = new Node(null);
    private Word word;
    private Node dict[];
    private final static int SIZE = 75000;
    int M = 0;
    private int size;
    
    public Dictionary(int size) {
	dict = new Node[size];
	this.M = size;
        for (int i = 0; i < size; i++) {
            dict[i] = null;
        }
    }
    
    public int put(Word word) throws DictionaryException {
	int collision = 0;
	Word wrd;
	Word head;
	Node prev = null;
	this.word = word;

	int index = function(word.getKey());

	Node node = dict[index];

	if (node == null) {
            Node node1 = new Node(word);
            dict[index] = node1;
            this.size++;
            return collision;
	} 
        else{
            head = node.getNode();
            if (head.getKey().equals(word.getKey())) {
                collision++;
		throw new DictionaryException("Dictionary Exception: Key already exists in table");
            }
            else{
		while(node!=null) {
                    prev = node;
                    wrd =  node.getNode();

                    if (wrd.getKey().equals(word.getKey())) {
                        collision++;
                        throw new DictionaryException("Dictionary Exception: Key already exists in the hash table");
                    }
                    node = node.getNext();

		}
                Node newNode = new Node(word);
                prev.setNext(newNode);
		this.size++;
		return collision;
            }
	}

    }
    public Word get(String inputWord) {
	int index = function(inputWord);
	Node node;
	node = dict[index];
        
        if (node == null) return null;

	while(node!=null){
            if (node.getNode().getKey().equals(inputWord)) return node.getNode();
            else node = node.getNext();
	}
	return null;
    }

    public Word remove(String inputWord) throws NoKeyException {
	int index = function(inputWord);
	Node node = dict[index];
	Node  prev = null;

	while (node != null){
            if (node.getNode().getKey().equals(inputWord)) {
		if (prev != null) prev.setNext(node.getNext());
		else {
                    dict[index] = node.getNext();
                    this.size--;
                    return node.getNode();

				}

            } 
            else {
		prev = node;
		node = node.getNext();
            }
        }
        throw new NoKeyException("NoKeyException thrown: cannot not remove word with key given because its not in the table");
    }
    
    public int size() {
	return this.size;
    }
    
    private int function(String str) {
	int h = 0, hash = 0;
		// looping from 0 to the length of the string -1
	for (int i = 0; i<str.length(); i++)
            h = (31 * hash) + str.charAt(i);
	hash = h % M;
	return hash;
	}
}
