/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Exception;

/**
 *
 * @author Dell
 */
public class DictionaryException extends Exception {
    
	public DictionaryException(String message){
		super("Two entries have the word: " + message);
	}
}