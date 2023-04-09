package Controller;

import Controller.Exception.DictionaryException;
import Model.*;
import java.io.*;
import java.util.*;

public class SearchWord {

    private String line;
    private Word word;
    private String input = "";
    private String score = "";
    private int boardSize;
    private int maxScore = 0;
    private char[][] letters;

    Model.Dictionary dict = new Model.Dictionary(80000);
    Model.Dictionary foundedWords = new Model.Dictionary(100);

    public SearchWord(String fileName, String wordTextFile) throws IOException, DictionaryException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(wordTextFile));
            while ((line = reader.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    if (Character.isLetter(line.charAt(i))) {
                        input += line.charAt(i);
                    } else if (Character.isDigit(line.charAt(i))) {
                        score += line.charAt(i);
                    }
                }

                word = new Word(input, Integer.parseInt(score));
                dict.put(word);
                input = "";
                score = "";
            }
            reader.close();
        } catch (IOException e) {
            throw new DictionaryException("The file doesn't exist or is invalid");
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            this.boardSize = Integer.parseInt(reader.readLine());
            this.letters = new char[this.boardSize][this.boardSize];

            int k = 0;
            while (((line = reader.readLine()) != null) && k < boardSize) {
                for (int j = 0; j < boardSize; j++) {
                    letters[k][j] = line.charAt(j);
                }
                k++;
            }

            while (line != null) {
                Word word = dict.get(line);
                maxScore += word.getValue();
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            throw new DictionaryException("The file doesn't exist or is invalid");
        }
    }

    public int getSize() {
        return this.boardSize;
    }

    public int getMaxScore() {
        return this.maxScore;
    }

    public String getLetter(int i, int j) {
        String str = "";
        str += letters[i][j];
        return str;
    }

    public int getNumWordsFound() {
        return foundedWords.size();
    }

    public ArrayList<Word> updateWordList(ArrayList<Word> words) throws DictionaryException {
        ArrayList<Word> list = new ArrayList<Word>();

        for (int i = 0; i < words.size(); i++) {
            if (foundedWords.get(words.get(i).getKey()) != null) {
            } else {
                foundedWords.put(words.get(i));
                list.add(words.get(i));
            }
        }
        return list;
    }

    public ArrayList<Word> findWords(String line) {
        String[] list = line.split("\\s+");
        String string = "";
        ArrayList<String> arr = new ArrayList<>();

        for (int i = 0; i < list.length; i++) {
            arr.add(list[i]);
        }

        for (String str : arr) {
            string += str;
        }
        return checkWords(string);

    }

    public ArrayList<Word> checkWords(String string) {

        int max = 8;
        int size = string.length();
        String substr = "";
        ArrayList<Word> words = new ArrayList<>();

        int i, j;
        for (i = 0; i <= size - 2; i++) {
            for (j = 0; j < 3; j++) {
                int k = 4 + i;
                while ((k - i) < max && (k <= size)) {
                    substr = string.substring(i, k);
                    if (dict.get(substr) != null) {
                        words.add(dict.get(substr));
                    }
                    k++;
                }
            }
        }
        return words;
    }
}
