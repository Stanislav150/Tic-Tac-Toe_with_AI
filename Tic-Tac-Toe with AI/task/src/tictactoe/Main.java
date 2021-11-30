package tictactoe;

import java.util.BitSet;
import java.util.Scanner;

public class Main {
    final static Scanner scanner = new Scanner(System.in);
    private final static BitSet bitSet = new BitSet();
    private static int row = -1;
    private static int col = -1;
    //private static boolean gameOver = false;
    private final static String[][] gridArray = new String[3][3];


    public static void main(String[] args) {
        System.out.print("Enter the cells: ");
        String inputGrid = scanner.nextLine();
        // We believe that the user enters the grid without error
        drawGrid(inputGrid);
        boolean repeat = true;
        while (repeat) {
            System.out.print("Enter the coordinates: ");
            String inputStr = scanner.nextLine();
            if (checkCoordinates(inputStr)) {
                redrawGrid(row, col);
            }
            repeat = !findWinner();
        }
    }


    private static void drawGrid(String inputGrid) {
        String[] inputGridArray = inputGrid.split("");
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gridArray[i][j] = inputGridArray[k];
                k++;
            }
        }

        System.out.println("---------");
        for (int i = 0; i < 3; i++) System.out.printf("|%s |%n", drawRow(i));
        System.out.println("---------");

    }

    /**
     * @param row - row number
     * @param col - column number
     */
    private static void redrawGrid(int row, int col) {
        int countX = 0;
        int countO = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gridArray[i][j].equals("X")) countX++;
                else if (gridArray[i][j].equals("O")) countO++;
            }
        }
        if (countX == countO) gridArray[row - 1][col - 1] = "X";
        else gridArray[row - 1][col - 1] = "O";

        System.out.println("---------");
        for (int i = 0; i < 3; i++) System.out.printf("|%s |%n", drawRow(i));
        System.out.println("---------");

    }

    /**
     * @param row - row number
     * @return - returns a formatted string for rendering the grid
     */
    private static String drawRow(int row) {
        StringBuilder rowStr = new StringBuilder();
        for (String s : gridArray[row]) {
            rowStr.append((s.equals("_")) ? "  " : (s.equals("X")) ? " X" : " O");
        }
        return rowStr.toString();
    }

    /**
     * The method analyzes user input and displays an error message
     *
     * @param inputStr - the string entered by the user
     * @return true if the user entered the coordinates of his move correctly
     */
    private static boolean checkCoordinates(String inputStr) {
        String[] inputArray = inputStr.split(" ");
        if (inputArray.length < 2) {
            System.out.println("You should enter numbers!");
            return false;
        } else {
            String inputRow = inputArray[0];
            String inputCol = inputArray[1];
            try {
                row = Integer.parseInt(inputRow);
            } catch (NumberFormatException nfe) {
                System.out.println("You should enter numbers!");
                return false;
            }
            try {
                col = Integer.parseInt(inputCol);
            } catch (NumberFormatException nfe) {
                System.out.println("You should enter numbers!");
                return false;
            }
            if ((row < 1) || (row > 3) || (col < 1) || (col > 3)) {
                System.out.println("Coordinates should be from 1 to 3!");
                return false;
            }
            if (!gridArray[row - 1][col - 1].equals("_")) {
                System.out.println("This cell is occupied! Choose another one!");
                return false;
            }
            return true;
        }

    }

    private static boolean findWinner() {
        if ((checkRow() || checkCol() || checkDiagonals() || !checkAvailableSpace())) return true;
        else {
            System.out.println("Game not finished");
            return false;
        }
    }


    /**
     * Checks that the row is filled with X or 0
     *
     * @return true if the row is filled with X or 0
     */
    private static boolean checkRow() {
        int s = 0;
        while (s < 3) {
            for (int i = 0; i < 3; i++) {
                if (gridArray[s][i].equals("X")) bitSet.set(i);
            }
            if (bitSet.cardinality() == 3) {
                System.out.println("X wins");
                return true;
            }
            bitSet.clear();
            s++;
        }
        s = 0;
        while (s < 3) {
            for (int i = 0; i < 3; i++) {
                if (gridArray[s][i].equals("O")) bitSet.set(i);
            }
            if (bitSet.cardinality() == 3) {
                System.out.println("O wins");
                return true;
            }
            bitSet.clear();
            s++;
        }
        return false;
    }

    /**
     * Checks that the column is filled with X or 0
     *
     * @return true if the column is filled with X or 0
     */
    private static boolean checkCol() {
        int s = 0;
        while (s < 3) {
            for (int i = 0; i < 3; i++) {
                if (gridArray[i][s].equals("X")) bitSet.set(i);
            }
            if (bitSet.cardinality() == 3) {
                System.out.println("X wins");
                return true;
            }
            bitSet.clear();
            s++;
        }
        s = 0;
        while (s < 3) {
            for (int i = 0; i < 3; i++) {
                if (gridArray[i][s].equals("O")) bitSet.set(i);
            }
            if (bitSet.cardinality() == 3) {
                System.out.println("O wins");
                return true;
            }
            bitSet.clear();
            s++;
        }
        return false;
    }

    private static boolean checkDiagonals() {

        for (int i = 0; i < 3; i++) {
            if (gridArray[i][i].equals("X")) bitSet.set(i);
        }
        if (bitSet.cardinality() == 3) {
            System.out.println("X wins");
            return true;
        }
        bitSet.clear();
        for (int i = 0; i < 3; i++) {
            if (gridArray[i][i].equals("O")) bitSet.set(i);
        }
        if (bitSet.cardinality() == 3) {
            System.out.println("O wins");
            return true;
        }
        bitSet.clear();

        int j = 2;
        for (int i = 0; i < 3; i++) {
            if (gridArray[i][j].equals("X")) bitSet.set(i);
            j--;
        }
        if (bitSet.cardinality() == 3) {
            System.out.println("X wins");
            return true;
        }
        bitSet.clear();

        j = 2;
        for (int i = 0; i < 3; i++) {
            if (gridArray[i][j].equals("O")) bitSet.set(i);
            j--;
        }
        if (bitSet.cardinality() == 3) {
            System.out.println("O wins");
            return true;
        }
        bitSet.clear();
        return false;
    }

    /**
     * The method determines that there is a place in the
     * grid for the next move
     *
     * @return true if there is still free space in the grid
     */
    private static boolean checkAvailableSpace() {
        int s = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gridArray[i][j].equals("_")) bitSet.set(s);
                s++;
            }
        }
        if (bitSet.cardinality() > 0) {
            bitSet.clear();
            return true;
        }
        System.out.println("Draw");
        return false;
    }
}
