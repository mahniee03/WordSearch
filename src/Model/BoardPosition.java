package Model;

public class BoardPosition {
    private int x; //row
    private int y; //column
	
    private String character;

    public BoardPosition(int x, int y, String character){
	this.x = x;
	this.y = y;
	this.character = character;
    }
	
    public int getX(){
	return x;
    }
    public int getY(){
        return y;
    }
    
    public String getCharacter(){
	return character;
    }
    
    @Override
    public String toString(){
	return "x:" + x + ", y:" + y;
    }
	
}
