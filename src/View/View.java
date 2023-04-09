package View;

import Controller.Exception.DictionaryException;
import Controller.*;
import Model.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.*;

public class View extends JFrame {

    private final int BUTTON_SIZE = 50;
    private final int WORD_SEARCH_TITLE_SIZE = 20;
    private final int BIG_FONT_SIZE = 16;

    private final Icon RESTING_BUTTON = new ImageIcon("button_blank.png");
    private final Icon PRESSED_BUTTON = new ImageIcon("button_pressed.png");
    private final Icon USED_BUTTON = new ImageIcon("button_letterused.png");
    private final Icon CURRENT_BUTTON = new ImageIcon("button_current.png");
    private final Icon DONE_BUTTON = new ImageIcon("button_done.png");

    private final String BLANK_SPACE = " ";

    private JButton[][] letterBoardButtons;
    private JLabel currentScore;
    private JLabel gameState;
    private int[][] buttonStates;
    private int maxScore;
    private int score;
    private SearchWord wordSearch;
    private int[][] posArray;
    private int posLength;

    
    public View(String fileName, String wordTextFile) {
        try {
            wordSearch = new SearchWord(fileName, wordTextFile);

        } catch (IOException e) {
            System.out.println("An error occurred when opening word search text file " + fileName + " or " + wordTextFile);
            System.exit(0);
        } catch (DictionaryException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        int size = wordSearch.getSize();
        maxScore = wordSearch.getMaxScore();
        posArray = new int[2][size];
        posLength = 0;

        JPanel mainPanel = new JPanel();
        JPanel scorePanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        scorePanel.setLayout(new GridLayout(2, 3));
        buttonPanel.setLayout(new GridLayout(size, size));

        JLabel totalScore = new JLabel("Maximum Score: " + maxScore);
        totalScore.setForeground(Color.MAGENTA);
        totalScore.setHorizontalAlignment(JLabel.LEFT);
        currentScore = new JLabel("Current Score: " + "0");
        currentScore.setForeground(Color.BLUE);
        currentScore.setHorizontalAlignment(JLabel.LEFT);
        JLabel nameOfGame = new JLabel("Minnie's Search Game");
        nameOfGame.setFont(new Font("Arial", Font.PLAIN, WORD_SEARCH_TITLE_SIZE));
        gameState = new JLabel("IN PLAY");
        gameState.setHorizontalAlignment(JLabel.CENTER);

        scorePanel.add(totalScore);
        scorePanel.add(nameOfGame);
        scorePanel.add(currentScore);
        scorePanel.add(gameState);

        ClickHandler handler = new ClickHandler(size);

        this.letterBoardButtons = new JButton[size][size];
        this.buttonStates = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.letterBoardButtons[i][j] = new JButton("", RESTING_BUTTON);
                this.letterBoardButtons[i][j].setBackground(Color.BLACK);
                this.letterBoardButtons[i][j].setForeground(Color.BLACK);
                this.letterBoardButtons[i][j].setFont(new Font("Arial", Font.PLAIN, BIG_FONT_SIZE));
                this.letterBoardButtons[i][j].setHorizontalTextPosition(JButton.CENTER);
                this.letterBoardButtons[i][j].setVerticalTextPosition(JButton.CENTER);
                this.letterBoardButtons[i][j].setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
                this.letterBoardButtons[i][j].setText(wordSearch.getLetter(i, j));
                this.letterBoardButtons[i][j].setEnabled(true);
                this.buttonStates[i][j] = 0;
                buttonPanel.add(this.letterBoardButtons[i][j]);
                this.letterBoardButtons[i][j].addActionListener(handler);
            }
        }
        scorePanel.setBackground(new Color(255, 204, 51));
        mainPanel.add(scorePanel);
        mainPanel.add(buttonPanel);
        mainPanel.setBackground(new Color(255, 204, 51));
        add(mainPanel);
    }

    

    private class ClickHandler implements ActionListener {

        private int boardSize;

        public ClickHandler(int size) {
            boardSize = size;
        }

        public void cleanButtonClicks() {

            for (int i = 0; i < posLength; i++) {

                if (buttonStates[posArray[0][i]][posArray[1][i]] == 1) {
                    buttonStates[posArray[0][i]][posArray[1][i]] = 0;
                }
            }
            posLength = 0;
            updateButtonStates(-1, -1);
        }

        public void actionPerformed(ActionEvent event) {
            if (!isGameFinished() && event.getSource() instanceof JButton) {
                int row = -1;
                int column = -1;
                for (int i = 0; i < boardSize; i++) {
                    for (int j = 0; j < boardSize; j++) {
                        if (event.getSource() == letterBoardButtons[i][j]) {
                            row = i;
                            column = j;
                            break;
                        }
                    }
                    if (row != -1) {
                        break;
                    }
                }

                int structureCase = -1;
                int diagonalValue = -1;

                ArrayList<LineParameters> lineList = new ArrayList<LineParameters>();

                if (buttonStates[row][column] == 0) {

                    boolean lineFound = true;

                    if (lineFound) {
                        boolean findMatch = false;
                        for (int i = 0; i < posLength && !findMatch; i++) {
                            if (row == posArray[0][i] + 1 && column == posArray[1][i]) {
                                findMatch = true;
                            } else if (row == posArray[0][i] - 1 && column == posArray[1][i]) {
                                findMatch = true;
                            } else if (row == posArray[0][i] && column == posArray[1][i] - 1) {
                                findMatch = true;
                            } else if (row == posArray[0][i] && column == posArray[1][i] + 1) {
                                findMatch = true;
                            } else if (row == posArray[0][i] + 1 && column == posArray[1][i] + 1) {
                                findMatch = true;
                            } else if (row == posArray[0][i] + 1 && column == posArray[1][i] - 1) {
                                findMatch = true;
                            } else if (row == posArray[0][i] - 1 && column == posArray[1][i] + 1) {
                                findMatch = true;
                            } else if (row == posArray[0][i] - 1 && column == posArray[1][i] - 1) {
                                findMatch = true;
                            }
                        }
                        if (!findMatch) {
                            if (row > 0) {
                                if (column > 0 && buttonStates[row - 1][column - 1] == 2) {
                                    findMatch = true;
                                }
                                if (buttonStates[row - 1][column] == 2) {
                                    findMatch = true;
                                }
                                if (column + 1 < wordSearch.getSize() && buttonStates[row - 1][column + 1] == 2) {
                                    findMatch = true;
                                }
                            }
                            if (row + 1 < wordSearch.getSize()) {
                                if (column > 0 && buttonStates[row + 1][column - 1] == 2) {
                                    findMatch = true;
                                }
                                if (buttonStates[row + 1][column] == 2) {
                                    findMatch = true;
                                }
                                if (column + 1 < wordSearch.getSize() && buttonStates[row + 1][column + 1] == 2) {
                                    findMatch = true;
                                }
                            }
                            if (column + 1 < wordSearch.getSize() && buttonStates[row][column + 1] == 2) {
                                findMatch = true;
                            }
                            if (column - 1 > 0 && buttonStates[row][column - 1] == 2) {
                                findMatch = true;
                            }
                        }

                        if (!findMatch) {
                            lineFound = false;
                            cleanButtonClicks();
                        }
                    }

                    if (detectRow(row) != -1 || posLength == 0) {
                        lineFound = true;
                        structureCase = 0;
                        lineList.add(new LineParameters(structureCase, row, column, -1));
                    } else {
                        lineFound = false;
                    }

                    if (!lineFound || posLength == 0) {
                        if (detectColumn(column) != -1) {
                            lineFound = true;
                            structureCase = 1;
                            lineList.add(new LineParameters(structureCase, row, column, -1));
                        } else {
                            lineFound = false;
                        }
                    }

                    if (!lineFound || posLength == 0) {
                        if (detectDiagonalTL(row, column) != -1) {
                            lineFound = true;
                            structureCase = 2;
                            diagonalValue = detectDiagonalTL(row, column);
                            lineList.add(new LineParameters(structureCase, row, column, diagonalValue));
                        } else {
                            lineFound = false;
                        }
                    }

                    if (!lineFound || posLength == 0) {
                        if (detectDiagonalTR(row, column) != -1) {
                            lineFound = true;
                            structureCase = 3;
                            diagonalValue = detectDiagonalTR(row, column);
                            lineList.add(new LineParameters(structureCase, row, column, diagonalValue));
                        } else {
                            lineFound = false;
                        }
                    }

                    if (!lineFound) {
                        cleanButtonClicks();
                    }

                }

                if (buttonStates[row][column] == 0) {
                    buttonStates[row][column] = 1;
                    posArray[0][posLength] = row;
                    posArray[1][posLength] = column;
                    posLength++;
                } else if (buttonStates[row][column] == 1) {
                    cleanButtonClicks();
                }

                if (buttonStates[row][column] <= 2 && structureCase <= 3 && structureCase != -1) {

                    boolean foundNewWord = false;

                    for (int i = 0; i < lineList.size(); i++) {
                        int caseValue = lineList.get(i).getCaseValue();
                        int rowValue = lineList.get(i).getRow();
                        int columnValue = lineList.get(i).getColumn();
                        int diagonalBoundary = lineList.get(i).getDiagonal();
                        String lineText = extractString(caseValue, rowValue, columnValue, diagonalBoundary);
                        int numWords = wordSearch.getNumWordsFound();
                        if (lineText != null) {
                            ArrayList<Word> words = wordSearch.findWords(lineText);
                            ArrayList<Word> newWords = null;
                            try {
                                newWords = wordSearch.updateWordList(words);
                            } catch (DictionaryException e) {
                                System.out.println("A Dictionary Exception was thrown..." + e.getMessage() + "; make sure that your updateWordList doesn't throw this exception.\n Hint: check to see if the key is in first, before applying put the Word in...");
                            }

                            fillLine(words, caseValue, rowValue, columnValue, diagonalBoundary);
                            updateScore(newWords);

                            if (isGameFinished()) {
                                gameFinished();
                            }
                        }

                        int numWordsAfter = wordSearch.getNumWordsFound();
                        if (numWordsAfter > numWords) {
                            foundNewWord = true;
                        }
                    }

                    if (foundNewWord) {
                        cleanButtonClicks();
                    }

                }

                updateButtonStates(row, column);
            }
        }

        public int detectRow(int row) {
            boolean lineFound = true;
            for (int i = 0; i < posLength; i++) {
                if (posArray[0][i] != row) {
                    lineFound = false;
                }
            }
            if (lineFound) {
                return row;
            } else {
                return -1;
            }
        }

        public int detectColumn(int column) {
            boolean lineFound = true;
            for (int j = 0; j < posLength; j++) {
                if (posArray[1][j] != column) {
                    lineFound = false;
                }
            }
            if (lineFound) {
                return column;
            } else {
                return -1;
            }
        }

        public int detectDiagonalTL(int row, int column) {
            int countElements = 0;
            int cornerVar = 0;
            for (int i = 0; i <= boardSize - (row + 1) && i + column < boardSize; i++) {
                for (int j = 0; j < posLength; j++) {
                    if (posArray[0][j] == i + row && posArray[1][j] == i + column) {
                        countElements++;
                    }
                }
            }
            cornerVar = row;
            for (int i = 0; i <= row - 1 && row - i - 1 >= 0; i++) {
                for (int j = 0; j < posLength; j++) {
                    if (posArray[0][j] == row - i - 1 && posArray[1][j] == column - i - 1) {
                        countElements++;
                    }
                }

                if (column - i - 1 >= 0) {
                    cornerVar = row - i - 1;
                }
            }
            if (countElements == posLength) {
                return cornerVar;
            } else {
                return -1;
            }
        }

        public int detectDiagonalTR(int row, int column) {
            int countElements = 0;
            int cornerVar = 0;
            for (int i = 0; i <= boardSize - (row + 1) && column - i >= 0; i++) {
                for (int j = 0; j < posLength; j++) {
                    if (posArray[0][j] == row + i && posArray[1][j] == column - i) {
                        countElements++;
                    }
                }
            }
            cornerVar = row;
            for (int i = 0; i <= row - 1 && column + i + 1 < boardSize; i++) {
                for (int j = 0; j < posLength; j++) {
                    if (posArray[0][j] == row - i - 1 && posArray[1][j] == column + i + 1) {
                        countElements++;
                    }
                }
                if (column + i + 1 <= boardSize - 1) {
                    cornerVar = row - i - 1;
                }
            }

            if (countElements == posLength) {
                return cornerVar;
            } else {
                return -1;
            }
        }
    }

    public void updateButtonStates(int row, int column) {
        boolean doneGame = isGameFinished();
        for (int i = 0; i < getBoardSize(); i++) {
            for (int j = 0; j < getBoardSize(); j++) {
                switch (this.buttonStates[i][j]) {
                    case 0:
                        if (!doneGame) {
                            letterBoardButtons[i][j].setIcon(RESTING_BUTTON);
                        }
                        break;
                    case 1:
                        if (!doneGame) {
                            if (row == i && column == j) {
                                letterBoardButtons[i][j].setIcon(CURRENT_BUTTON);
                            } else {
                                letterBoardButtons[i][j].setIcon(PRESSED_BUTTON);
                            }
                        }
                        break;
                    case 2:
                        letterBoardButtons[i][j].setIcon(USED_BUTTON);
                        break;
                }
                letterBoardButtons[i][j].paint(letterBoardButtons[i][j].getGraphics());
                validate();
                repaint();
            }
        }
    }

    private void fillLine(ArrayList<Word> words, int caseValue, int row, int column, int diag) {
        String line = "";
        int len = 0;
        LineData lineData = new LineData(wordSearch.getSize());
        if (caseValue == 0) {
            //horizontal line at "row".
            for (int i = 0; i < wordSearch.getSize(); i++) {
                if (buttonStates[row][i] > 0) {
                    line += wordSearch.getLetter(row, i);

                    len++;
                } else {
                    line += BLANK_SPACE;
                }
                lineData.addPosition(row, i);
            }
        } else if (caseValue == 1) {
            //vertical line at "column".
            for (int i = 0; i < wordSearch.getSize(); i++) {
                if (buttonStates[i][column] > 0) {
                    line += wordSearch.getLetter(i, column);
                    len++;
                } else {
                    line += BLANK_SPACE;
                }
                lineData.addPosition(i, column);
            }
        } else if (caseValue == 2) {

            for (int i = 0; i < wordSearch.getSize() && diag + i < wordSearch.getSize() && column - (row - diag) + i < wordSearch.getSize(); i++) {
                if (buttonStates[diag + i][column - (row - diag) + i] > 0) {
                    line += wordSearch.getLetter(diag + i, column - (row - diag) + i);
                    len++;
                } else {
                    line += BLANK_SPACE;
                }
                lineData.addPosition(diag + i, column - (row - diag) + i);
            }
        } else if (caseValue == 3) {

            for (int i = 0; i < wordSearch.getSize() && diag + i < wordSearch.getSize() && column + (row - diag) - i >= 0; i++) {

                if (buttonStates[diag + i][column + (row - diag) - i] > 0) {
                    line += wordSearch.getLetter(diag + i, column + (row - diag) - i);
                    len++;
                } else {
                    line += BLANK_SPACE;
                }
                //must add in reverse here...

                lineData.addPositionReverse(diag + i, column + (row - diag) - i);
            }
            //we interpret the String in reverse...
            String stringRev = "";
            for (int i = line.length() - 1; i >= 0; i--) {
                stringRev += line.charAt(i);
            }
            //also reverse the array of positions, but we need to be careful so as to keep the null references outside the range for the line.
            int countNull = 0;
            for (int i = 0; i < line.length(); i++) {
                if (lineData.getPosition(i) == null) {
                    countNull++;
                }
            }
            LineData temp = new LineData(wordSearch.getSize());
            for (int i = 0; i < wordSearch.getSize() - countNull; i++) {
                temp.setPosition(i, lineData.getPosition(i + countNull));
            }
            lineData = temp;
            line = stringRev;
        }

        //now, we try to find each word on the line... we can assume no repeats of the same sequence twice on a line.
        for (int i = 0; i < words.size(); i++) {
            int position = line.indexOf(words.get(i).getKey());
            int endOfWord = position + words.get(i).getKey().length();
            for (int j = position; j < endOfWord; j++) {
                BoardPosition gridPos = lineData.getPosition(j);
                buttonStates[gridPos.getX()][gridPos.getY()] = 2;
            }
        }

    }

    public void updateScore(ArrayList<Word> newWords) {
        int cScore = this.score;
        for (int i = 0; i < newWords.size(); i++) {
            cScore += newWords.get(i).getValue();
        }
        this.score = cScore;
        currentScore.setText("Current Score: " + this.score);
    }

    public boolean isGameFinished() {

        boolean doneGame = false;
        if (this.score >= this.maxScore) {
            doneGame = true;
        }
        return doneGame;
    }

    private void gameFinished() {
        gameState.setText("DONE GAME");
        for (int i = 0; i < wordSearch.getSize(); i++) {
            for (int j = 0; j < wordSearch.getSize(); j++) {
                if (buttonStates[i][j] < 2) {
                    letterBoardButtons[i][j].setIcon(DONE_BUTTON);
                    this.letterBoardButtons[i][j].setForeground(Color.WHITE);
                }
            }
        }
    }

    public String extractString(int caseValue, int row, int column, int diag) {
        String line = "";
        int len = 0;
        if (caseValue == 0) {
            for (int i = 0; i < wordSearch.getSize(); i++) {
                if (buttonStates[row][i] > 0) {
                    line += wordSearch.getLetter(row, i);
                    len++;
                } else {
                    line += BLANK_SPACE;
                }
            }
        } else if (caseValue == 1) {
            for (int i = 0; i < wordSearch.getSize(); i++) {
                if (buttonStates[i][column] > 0) {
                    line += wordSearch.getLetter(i, column);
                    len++;
                } else {
                    line += BLANK_SPACE;
                }
            }
        } else if (caseValue == 2) {
            for (int i = 0; i < wordSearch.getSize() && diag + i < wordSearch.getSize() && column - (row - diag) + i < wordSearch.getSize(); i++) {
                if (buttonStates[diag + i][column - (row - diag) + i] > 0) {
                    line += wordSearch.getLetter(diag + i, column - (row - diag) + i);
                    len++;
                } else {
                    line += BLANK_SPACE;
                }
            }
        } else if (caseValue == 3) {
            for (int i = 0; i < wordSearch.getSize() && diag + i < wordSearch.getSize() && column + (row - diag) - i >= 0; i++) {

                if (buttonStates[diag + i][column + (row - diag) - i] > 0) {
                    line += wordSearch.getLetter(diag + i, column + (row - diag) - i);
                    len++;
                } else {
                    line += BLANK_SPACE;
                }
            }
            String revString = "";
            for (int i = line.length() - 1; i >= 0; i--) {
                revString += line.charAt(i);
            }
            line = revString;
        }
        if (len >= 4) {
            return line;
        } else {
            return null;
        }
    }

    public int getBoardSize() {
        return wordSearch.getSize();
    }

    public int getButtonSize() {
        return BUTTON_SIZE;
    }
    
}
