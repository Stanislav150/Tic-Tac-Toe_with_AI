package tictactoe;

import java.util.*;

public class Main {
    final static Scanner scanner = new Scanner(System.in);
    private final static BitSet bitSet = new BitSet();
    private final static Random random = new Random();
    private static int row = -1;
    private static int col = -1;
    //private static boolean gameOver = false;
    private final static String[][] gridArray = new String[3][3];


    public static void main(String[] args) {
        //System.out.print("Enter the cells: ");
        //String inputGrid = scanner.nextLine();
        // We believe that the user enters the grid without error
        drawGrid();
        boolean repeat = true;
        while (repeat) {
            System.out.print("Enter the coordinates: ");
            String inputStr = scanner.nextLine();
            if (checkCoordinates(inputStr)) {
                repeat = makingPlayerMove();
            }
            if (repeat) repeat = makingAiMove();
        }
    }


    private static void drawGrid() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) gridArray[i][j] = " ";
        }
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
            rowStr.append((s.equals(" ")) ? "  " : (s.equals("X")) ? " X" : " O");
        }
        return rowStr.toString();
    }

    private static boolean makingPlayerMove() {
        gridArray[row - 1][col - 1] = "X";
        redrawGrid();
        return findWinner();
    }

    private static boolean makingAiMove() {
        System.out.println("Making move level \"easy\"");
        int k = 0;
        var freeCells = new ArrayList<Integer>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gridArray[i][j].equals(" ")) {
                    freeCells.add(k);
                }
                k++;
            }
        }
        k = 0;
        int randomInt = freeCells.get(random.nextInt(freeCells.size()));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (k == randomInt) gridArray[i][j] = "O";
                k++;
            }
        }
        redrawGrid();
        return findWinner();

    }

    /**
     * The method redraws the grid after the move is made
     */
    private static void redrawGrid() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) System.out.printf("|%s |%n", drawRow(i));
        System.out.println("---------");

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

            if ((gridArray[row - 1][col - 1].equals("X"))
                    || (gridArray[row - 1][col - 1].equals("O"))) {
                System.out.println("This cell is occupied! Choose another one!");
                return false;
            }
            return true;
        }

    }

    private static boolean findWinner() {
        return ((checkRow() && checkCol() && checkDiagonals() && checkAvailableSpace()));
    }


    /**
     * Checks that the row is filled with X or 0
     *
     * @return the value is true if the line is not filled with Xor 0
     */
    private static boolean checkRow() {
        int s = 0;
        while (s < 3) {
            for (int i = 0; i < 3; i++) {
                if (gridArray[s][i].equals("X")) bitSet.set(i);
            }
            if (bitSet.cardinality() == 3) {
                System.out.println("X wins");
                return false;
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
                return false;
            }
            bitSet.clear();
            s++;
        }
        return true;
    }

    /**
     * Checks that the column is filled with X or 0
     *
     * @return true if the column is not filled with X or 0
     */
    private static boolean checkCol() {
        int s = 0;
        while (s < 3) {
            for (int i = 0; i < 3; i++) {
                if (gridArray[i][s].equals("X")) bitSet.set(i);
            }
            if (bitSet.cardinality() == 3) {
                System.out.println("X wins");
                return false;
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
                return false;
            }
            bitSet.clear();
            s++;
        }
        return true;
    }

    private static boolean checkDiagonals() {

        for (int i = 0; i < 3; i++) {
            if (gridArray[i][i].equals("X")) bitSet.set(i);
        }
        if (bitSet.cardinality() == 3) {
            System.out.println("X wins");
            return false;
        }
        bitSet.clear();
        for (int i = 0; i < 3; i++) {
            if (gridArray[i][i].equals("O")) bitSet.set(i);
        }
        if (bitSet.cardinality() == 3) {
            System.out.println("O wins");
            return false;
        }
        bitSet.clear();

        int j = 2;
        for (int i = 0; i < 3; i++) {
            if (gridArray[i][j].equals("X")) bitSet.set(i);
            j--;
        }
        if (bitSet.cardinality() == 3) {
            System.out.println("X wins");
            return false;
        }
        bitSet.clear();

        j = 2;
        for (int i = 0; i < 3; i++) {
            if (gridArray[i][j].equals("O")) bitSet.set(i);
            j--;
        }
        if (bitSet.cardinality() == 3) {
            System.out.println("O wins");
            return false;
        }
        bitSet.clear();
        return true;
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
                if (gridArray[i][j].equals(" ")) bitSet.set(s);
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
