package wordsearch;

import java.awt.event.*;
import javax.swing.*;
import Model.*;
import View.*;
import Controller.*;

public class WordSearch {

    public static void main(String[] args) {

        int textWidth = 80;
        JFrame board = new View("F:\\Hoc Code cung Manh\\Project\\GameJava\\WordSearch\\0002.txt", "F:\\\\Hoc Code cung Manh\\\\Project\\\\GameJava\\\\WordSearch\\\\words.txt");
        int buttonSize = ((View) board).getButtonSize();
        int size = ((View) board).getBoardSize();
        int width = Math.max(size * buttonSize + textWidth, 450);
        board.setSize(width, (size + 1) * buttonSize + 56);
        board.setLocationRelativeTo(null);
        board.setVisible(true);

        board.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });

    }
}
