
package Model;

import Controller.Exception.*;

public interface DictionaryADT {

    public Word get(String inputWord);

    public int put(Word word) throws DictionaryException;

    public Word remove(String inputWord) throws NoKeyException;

    public int size();

}
