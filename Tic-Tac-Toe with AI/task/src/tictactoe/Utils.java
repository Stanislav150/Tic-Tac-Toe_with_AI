package tictactoe;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static tictactoe.Main.*;
import static tictactoe.Hard.*;


public class Utils {

    /**
     * The method analyzes user input and displays an error message
     *
     * @param inputStr - the string entered by the user
     * @return true if the user entered the coordinates of his move correctly
     */
    protected static boolean checkCoordinates(String inputStr) {
        String[] inputArray = inputStr.split(EMPTY_SELL);
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
            // We bring the coordinates to the normal form
            row -= 1;
            col -= 1;
            if ((gridArray[row][col].equals(SYMBOL_X))
                    || (gridArray[row][col].equals(SYMBOL_O))) {
                System.out.println("This cell is occupied! Choose another one!");
                return false;
            }
            return true;
        }

    }

    public static String switchPlayers(String XorO) {
        if (XorO.equals(SYMBOL_X)) return SYMBOL_O;
        else return SYMBOL_X;
    }

    /**
     * The method is used to simulate a move. The move occurs only in
     * the array describing the state of the playing field according to
     * the coordinates obtained from MiniMax.
     * The progress is not displayed in the console
     *
     * @param point cell coordinates obtained in the miniMax method
     * @param XorO  the symbol that is placed in the coordinates of the cell
     */
    public static void makingMove(Point point, String XorO) {
        row = point.x;
        col = point.y;
        if (XorO.equals(SYMBOL_X)) gridArray[row][col] = SYMBOL_X;
        else if (XorO.equals(SYMBOL_O)) gridArray[row][col] = SYMBOL_O;
    }

    /**
     * The method scans the grid by rows, columns and diagonals and returns
     * the coordinates on which to put a cross or a zero.
     *
     * @param XorO determines who will play with a cross and who will play with a zero
     * @return true if there is a free cell for a move
     */
    public static boolean scanningGridToComplete(String XorO) {
        String[] testArray = new String[3];
        // Scanning the line
        int lineIndex = 0;
        for (String[] rowArray : gridArray) {
            if ((int) Arrays.stream(rowArray).filter(x -> x.equals(XorO)).count() == 2) {
                List<String> list = Arrays.asList(rowArray);
                row = lineIndex;
                col = list.indexOf(EMPTY_SELL); //indexOf returns -1 in case of failure
                return (col != -1);
            } else lineIndex++;
        }
        // Scanning columns
        int s = 0;
        lineIndex = 0;
        while (s < 3) {
            for (int i = 0; i < 3; i++) {
                testArray[i] = gridArray[i][s];
            }
            if ((int) Arrays.stream(testArray).filter(x -> x.equals(XorO)).count() == 2) {
                List<String> list = Arrays.asList(testArray);
                row = list.indexOf(EMPTY_SELL); //indexOf returns -1 in case of failure
                col = lineIndex;
                return (row != -1);
            } else lineIndex++;

            s++;
        }
        // Scan the diagonals.
        for (int i = 0; i < 3; i++) {
            testArray[i] = gridArray[i][i];

        }
        if ((int) Arrays.stream(testArray).filter(x -> x.equals(XorO)).count() == 2) {
            List<String> list = Arrays.asList(testArray);
            row = list.indexOf(EMPTY_SELL); //indexOf returns -1 in case of failure
            col = row;
            return (row != -1);
        }
        int j = 2;
        for (int i = 0; i < 3; i++) {
            testArray[i] = gridArray[i][j];
            j--;
        }
        if ((int) Arrays.stream(testArray).filter(x -> x.equals(XorO)).count() == 2) {
            List<String> list = Arrays.asList(testArray);
            row = list.indexOf(EMPTY_SELL); //indexOf returns -1 in case of failure
            col = 2 - row;
            return (row != -1);
        }
        return false;
    }


    /**
     * If its opponent can win with one move, it plays the move necessary to block this.
     * Calls scanningGridToComplete(), but we pass the opposite symbol to the method,
     * if we play for X, then O and vice versa.
     *
     * @param XorO determines who will play with a cross and who will play with a zero
     * @return true if there is such a cell and its coordinates are written in row and col
     */
    public static boolean oppositeScanGrid(String XorO) {
        if (XorO.equals(SYMBOL_X)) return (scanningGridToComplete(SYMBOL_O));
        else if (XorO.equals(SYMBOL_O)) return (scanningGridToComplete(SYMBOL_X));
        return false;
    }

    /**
     * @param board - array describing the playing field
     * @param XorO  determines who will play with a cross and who will play with a zero
     * @return if there is a winning combination, if not, then the game continues
     */
    // winning combinations using the board indexes
    public static boolean winning(String[][] board, String XorO) {
        return ((board[0][0].equals(XorO) && board[0][1].equals(XorO) && board[0][2].equals(XorO) ||
                (board[1][0].equals(XorO) && board[1][1].equals(XorO) && board[1][2].equals(XorO)) ||
                (board[2][0].equals(XorO) && board[2][1].equals(XorO) && board[2][2].equals(XorO)) ||
                (board[0][0].equals(XorO) && board[1][0].equals(XorO) && board[2][0].equals(XorO)) ||
                (board[0][1].equals(XorO) && board[1][1].equals(XorO) && board[2][1].equals(XorO)) ||
                (board[0][2].equals(XorO) && board[1][2].equals(XorO) && board[2][2].equals(XorO)) ||
                (board[0][0].equals(XorO) && board[1][1].equals(XorO) && board[2][2].equals(XorO)) ||
                (board[2][0].equals(XorO) && board[1][1].equals(XorO) && board[0][2].equals(XorO))));
    }

    /**
     * Checks that the row is filled with X or 0
     *
     * @param XorO determines who makes the move X or O
     * @return the value is true if the line is not filled with Xor 0
     */
    public static boolean checkRow(String XorO) {
        for (String[] rowArray : gridArray) {
            if ((int) Arrays.stream(rowArray).filter(x -> x.equals(XorO)).count() == 3) {
                if (XorO.equals(SYMBOL_X)) System.out.println("X wins");
                else if (XorO.equals(SYMBOL_O)) System.out.println("O wins");
                return false;
            }
        }
        return true;
    }

    /**
     * Checks that the column is filled with X or 0
     *
     * @param XorO determines who makes the move X or O
     * @return true if the column is not filled with X or 0
     */
    public static boolean checkCol(String XorO) {
        int s = 0;
        String[] testArray = new String[3];
        while (s < 3) {
            for (int i = 0; i < 3; i++) {
                testArray[i] = gridArray[i][s];
            }
            if ((int) Arrays.stream(testArray).filter(x -> x.equals(XorO)).count() == 3) {
                if (XorO.equals(SYMBOL_X)) System.out.println("X wins");
                else if (XorO.equals(SYMBOL_O)) System.out.println("O win");
                return false;
            }
            s++;
        }
        return true;
    }

    /**
     * Checks that the diagonal is filled with X or 0
     *
     * @param XorO determines who makes the move X or O
     * @return true if the diagonal is not filled with X or 0
     */
    public static boolean checkDiagonals(String XorO) {
        String[] testArray = new String[3];
        for (int i = 0; i < 3; i++) {
            testArray[i] = gridArray[i][i];

        }
        if ((int) Arrays.stream(testArray).filter(x -> x.equals(XorO)).count() == 3) {
            if (XorO.equals(SYMBOL_X)) System.out.println("X wins");
            else if (XorO.equals(SYMBOL_O)) System.out.println("O win");
            return false;
        }

        int j = 2;
        for (int i = 0; i < 3; i++) {
            testArray[i] = gridArray[i][j];
            j--;
        }
        if ((int) Arrays.stream(testArray).filter(x -> x.equals(XorO)).count() == 3) {
            if (XorO.equals(SYMBOL_X)) System.out.println("X wins");
            else if (XorO.equals(SYMBOL_O)) System.out.println("O win");
            return false;
        }
        return true;
    }

    /**
     * The method determines that there is a place in the
     * grid for the next move
     *
     * @return true if there is still free space in the grid
     */
    public static boolean checkAvailableSpace() {
        int s = 0;
        int[] availableSpace = new int[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ((gridArray[i][j].equals(SYMBOL_X)) || (gridArray[i][j].equals(SYMBOL_O))) availableSpace[s] = 1;
                s++;
            }
        }
        if ((int) Arrays.stream(availableSpace).filter(x -> x == 1).count() == 9) {
            System.out.println("Draw");
            return false;
        }
        return true;
    }

    /**
     * Generalized victory conditions
     *
     * @param XorO determines who makes the move X or O
     * @return true if the victory conditions are not met
     */
    public static boolean findWinner(String XorO) {
        return ((checkRow(XorO) && checkCol(XorO)
                && checkDiagonals(XorO) && checkAvailableSpace()));
    }

    public static void drawGrid() {
        clearArray();
        System.out.println("---------");
        IntStream.range(0, 3).forEach(x -> System.out.printf("|%s |%n", drawRow(x)));
        System.out.println("---------");
    }

    /**
     * The method redraws the grid after the move is made
     */
    public static void redrawGrid() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) System.out.printf("|%s |%n", drawRow(i));
        System.out.println("---------");
    }

    /**
     * @param row - row number
     * @return - returns a formatted string for rendering the grid
     */
    public static String drawRow(int row) {
        StringBuilder rowStr = new StringBuilder();
        for (String s : gridArray[row]) {
            rowStr.append((s.equals(EMPTY_SELL)) ? "  " : (s.equals(SYMBOL_X)) ? " X" : " O");
        }
        return rowStr.toString();
    }

    public static void clearArray() {
        IntStream.range(0, gridArray.length)
                .forEach(x -> Arrays.setAll(gridArray[x], (y) -> EMPTY_SELL));

    }
}
