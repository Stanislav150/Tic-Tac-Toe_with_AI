package tictactoe;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import static tictactoe.Main.*;


public class Utils {

    /**
     * The method scans the grid by rows, columns and diagonals and returns
     * the coordinates on which to put a cross or a zero.
     *
     * @param XorO determines who will play with a cross and who will play with a zero
     *
     * @return true if there is a free cell for a move
     */
    public static boolean scanningGridToComplete(String XorO) {
        String[] testArray = new String[3];
        // Scanning the line
        int lineIndex = 0;
        for (String[] rowArray : gridArray) {
            if ((int) Arrays.stream(rowArray).filter(x -> x.equals(XorO)).count() == 2) {
                List<String> list = Arrays.asList(rowArray);
                Main.row = lineIndex;
                col = list.indexOf(" "); //indexOf returns -1 in case of failure
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
                row = list.indexOf(" "); //indexOf returns -1 in case of failure
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
            row = list.indexOf(" "); //indexOf returns -1 in case of failure
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
            row = list.indexOf(" "); //indexOf returns -1 in case of failure
            col = j + 1;
            return (row != -1);
        }
        return false;
    }

    /**
     * If its opponent can win with one move, it plays the move necessary to block this.
     * @param XorO determines who will play with a cross and who will play with a zero
     * @return true if there is such a cell and its coordinates are written in row and col
     */
     public static boolean oppositeScanGrid(String XorO) {
        // Calls scanningGridToComplete(), but we pass the opposite symbol to the method,
        // if we play for X, then O and vice versa.
        if (XorO.equals("X")) return (scanningGridToComplete("O"));
        else if (XorO.equals("O")) return (scanningGridToComplete("X"));
        return false;
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
                if (XorO.equals("X")) System.out.println("X wins");
                else if (XorO.equals("O")) System.out.println("O wins");
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
                if (XorO.equals("X")) System.out.println("X wins");
                else if (XorO.equals("O")) System.out.println("O win");
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
            if (XorO.equals("X")) System.out.println("X wins");
            else if (XorO.equals("O")) System.out.println("O win");
            return false;
        }

        int j = 2;
        for (int i = 0; i < 3; i++) {
            testArray[i] = gridArray[i][j];
            j--;
        }
        if ((int) Arrays.stream(testArray).filter(x -> x.equals(XorO)).count() == 3) {
            if (XorO.equals("X")) System.out.println("X wins");
            else if (XorO.equals("O")) System.out.println("O win");
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
                if ((gridArray[i][j].equals("X")) || (gridArray[i][j].equals("O"))) availableSpace[s] = 1;
                s++;
            }
        }
        if ((int) Arrays.stream(availableSpace).filter(x -> x == 1).count() == 9) {
            System.out.println("Draw");
            return false;
        }
        return true;
    }

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
     *
     * @param row - row number
     * @return - returns a formatted string for rendering the grid
     */
    public static String drawRow(int row) {
        StringBuilder rowStr = new StringBuilder();
        for (String s : gridArray[row]) {
            rowStr.append((s.equals(" ")) ? "  " : (s.equals("X")) ? " X" : " O");
        }
        return rowStr.toString();
    }
    public static void clearArray() {
        IntStream.range(0, gridArray.length)
                .forEach(x -> Arrays.setAll(gridArray[x], (y) -> " "));

    }
}
