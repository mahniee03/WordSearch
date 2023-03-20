
package Model;


public class LineParameters {
	
	private int caseValue;
	private int row;
	private int column;
	private int diagonal;
	
	public LineParameters(int caseValue, int row, int column, int diagonal){
		this.caseValue = caseValue;
		this.row = row;
		this.column = column;
		this.diagonal = diagonal;
	}
	
	public int getCaseValue(){
		return caseValue;
	}

	public int getRow(){
		return row;
	}

	public int getColumn(){
		return column;
	}

	public int getDiagonal(){
		return diagonal;
	}
	
}

