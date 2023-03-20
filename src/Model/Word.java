package Model;

public class Word {


    private String word; 
    private int score; 


    public Word (String word, int score){
    	this.word = word; 
    	this.score = score; 
    }

    public String getKey(){
        return word;
    }

    public int getValue() { 
        return score;
    }

}

