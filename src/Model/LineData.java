
package Model;

public class LineData{

	private String lineText;
	private BoardPosition[] positions;
	private int size;
	
	public LineData(int length){
		lineText = null;
		positions = new BoardPosition[length];
		size=0;
	}
	
	public void addPosition(int x, int y){
		BoardPosition pos = new BoardPosition(x,y,"");
		positions[size] = pos;
		size++;
	}
	
	public void addPositionReverse(int x, int y){
		BoardPosition pos = new BoardPosition(x,y,"");
		size++;
		positions[positions.length - size] = pos;
	}
	
	public int getSize(){
		return size;
	}
	
	public BoardPosition getPosition(int pos){
		return positions[pos];
	}
	
	public void setPosition(int pos, BoardPosition bPos){
		positions[pos] = bPos;
	}
	
	public void setLineText(String lineText){
		this.lineText=lineText;
	}
	
	public String getLineText(){
		return lineText;
	}
	
        @Override
	public String toString(){
		String lineDataString = "";
		for(int i=0; i<positions.length; i++){
			lineDataString += "[" + positions[i] + "]";
		}
		return lineDataString;
	}
}

