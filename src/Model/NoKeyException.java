package Model;

public class NoKeyException extends Exception {

	public NoKeyException(String key){
		super("Key " + key + " is not in the dictionary.");
	}
}
