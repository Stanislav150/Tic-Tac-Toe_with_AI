package tictactoe;

import java.util.*;
import java.util.stream.IntStream;

public class Main {
    final static Scanner scanner = new Scanner(System.in);
    private final static Random random = new Random();
    private final static String[] testArray = new String[3];
    private static int row = -1;
    private static int col = -1;
    private final static String[][] gridArray = new String[3][3];

    enum Difficulty {EASY, MEDIUM}


    public static void main(String[] args) {
        menuManagement();

    }

    /**
     * The method controls the game menu
     */
    private static void menuManagement() {
        System.out.print("Input command: ");
        boolean result = true;
        while (result) {
            String startCommand = scanner.nextLine();
            switch (startCommand) {
                case "exit":
                    result = false;
                    break;
                case "start easy easy":
                    playTwoAI(Difficulty.EASY);
                    System.out.print("Input command: ");
                    break;
                case "start medium medium":
                    playTwoAI(Difficulty.MEDIUM);
                    System.out.print("Input command: ");
                    break;
                case "start easy user":
                    playPlayerAndAI("O", Difficulty.EASY);
                    System.out.print("Input command: ");
                    break;
                case "start user easy":
                    playPlayerAndAI("X", Difficulty.EASY);
                    System.out.print("Input command: ");
                    break;
                case "start medium user":
                    playPlayerAndAI("O", Difficulty.MEDIUM);
                    System.out.print("Input command: ");
                    break;
                case "start user medium":
                    playPlayerAndAI("X", Difficulty.MEDIUM);
                    System.out.print("Input command: ");
                    break;
                case "start user user":
                    playTwoUser();
                    System.out.print("Input command: ");
                    break;
                default:
                    System.out.println("Bad parameters!");
                    System.out.print("Input command: ");
            }
        }
    }


    /**
     * The middle level begins with scanning the playing field and searching for cells for the next move
     * The scanner should return the cell on which you need to put a cross or a zero.
     *
     * @param difficulty the difficulty level of the game.
     */
    private static void playTwoAI(Difficulty difficulty) {
        drawGrid();
        if (difficulty == Difficulty.EASY) {
            boolean repeat = true;
            String XorO = "X";
            int count = 1;
            while (repeat) {
                repeat = makingAiMoveEase(XorO, difficulty);
                if (count % 2 == 0) XorO = "X";
                else XorO = "O";
                count++;

            }
            clearArray();
        } else if (difficulty == Difficulty.MEDIUM) {
            boolean repeat = true;
            String XorO = "X";
            int count = 1;
            while (repeat) {
                if (scanningGridToComplete(XorO)) repeat = makingAiMoveMedium(XorO, difficulty);
                else if (oppositeScanGrid(XorO)) repeat = makingAiMoveMedium(XorO, difficulty);
                else repeat = makingAiMoveEase(XorO, difficulty);
                if (count % 2 == 0) XorO = "X";
                else XorO = "O";
                count++;

            }
            clearArray();
        }
    }

    /**
     * The method scans the grid by rows, columns and diagonals and returns
     * the coordinates on which to put a cross or a zero.
     *
     * @param XorO determines who will play with a cross and who will play with a zero
     */
    private static boolean scanningGridToComplete(String XorO) {
        // Scanning the line
        int lineIndex = 0;
        for (String[] rowArray : gridArray) {
            if ((int) Arrays.stream(rowArray).filter(x -> x.equals(XorO)).count() == 2) {
                List<String> list = Arrays.asList(rowArray);
                row = lineIndex;
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
     *
     * @param XorO determines who will play with a cross and who will play with a zero
     * @return true if there is such a cell and its coordinates are written in row and col
     */
    private static boolean oppositeScanGrid(String XorO) {
        // Calls scanningGridToComplete(), but we pass the opposite symbol to the method,
        // if we play for X, then O and vice versa.
        if (XorO.equals("X")) return (scanningGridToComplete("O"));
        else if (XorO.equals("O")) return (scanningGridToComplete("X"));
        return false;
    }

    /**
     * @param XorO       determines who will play with a cross and who will play with a zero
     * @param difficulty определяет сложность игры.
     */
    private static void playPlayerAndAI(String XorO, Difficulty difficulty) {
        drawGrid();
        if (difficulty == Difficulty.EASY) {
            boolean repeat = true;
            // Если ИИ ходит первым то сначала рисуем сетку, затем ИИ делает ход,
            // затем опрашиваем сканер и делаем ход игрока
            while (repeat) {
                if (XorO.equals("X")) {
                    System.out.print("Enter the coordinates: ");
                    String inputStr = scanner.nextLine();
                    if (checkCoordinates(inputStr)) {
                        repeat = makingPlayerMove("X");
                        if (repeat) repeat = makingAiMoveEase("O", Difficulty.EASY);
                    }

                } else if (XorO.equals("O")) {
                    repeat = makingAiMoveEase("X", Difficulty.EASY);
                    System.out.print("Enter the coordinates: ");
                    String inputStr = scanner.nextLine();
                    if (checkCoordinates(inputStr)) {
                        repeat = makingPlayerMove("O");
                    }
                }
            }
            clearArray();
        } else if (difficulty == Difficulty.MEDIUM) {
            boolean repeat = true;
            while (repeat) {
                if (XorO.equals("X")) {
                    System.out.print("Enter the coordinates: ");
                    String inputStr = scanner.nextLine();
                    if (checkCoordinates(inputStr)) {
                        repeat = makingPlayerMove("X");
                        if (repeat) {
                            if (scanningGridToComplete("O")) repeat = makingAiMoveMedium("O", difficulty);
                            else if (oppositeScanGrid("O")) repeat = makingAiMoveMedium("O", difficulty);
                            else repeat = makingAiMoveEase("O", difficulty);
                        }
                    }

                } else if (XorO.equals("O")) {
                    if (scanningGridToComplete("X")) repeat = makingAiMoveMedium("X", difficulty);
                    else if (oppositeScanGrid("X")) repeat = makingAiMoveMedium("X", difficulty);
                    else repeat = makingAiMoveEase("X", difficulty);
                    if (repeat) {
                        System.out.print("Enter the coordinates: ");
                        String inputStr = scanner.nextLine();
                        if (checkCoordinates(inputStr)) {
                            repeat = makingPlayerMove("O");
                        }
                    }
                }
            }
            clearArray();

        }
    }

    private static void playTwoUser() {
        drawGrid();
        String XorO = "X";
        int count = 1;
        boolean repeat = true;
        while (repeat) {
            System.out.print("Enter the coordinates: ");
            String inputStr = scanner.nextLine();
            if (checkCoordinates(inputStr)) {
                repeat = makingPlayerMove(XorO);
                if (count % 2 == 0) XorO = "X";
                else XorO = "O";
                count++;
            }
        }
        clearArray();

    }


    private static void drawGrid() {
        clearArray();
        System.out.println("---------");
        IntStream.range(0, 3).forEach(x -> System.out.printf("|%s |%n", drawRow(x)));
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

    private static boolean makingPlayerMove(String XorO) {
        if (XorO.equals("X")) gridArray[row - 1][col - 1] = "X";
        else if (XorO.equals("O")) gridArray[row - 1][col - 1] = "O";
        redrawGrid();
        return findWinner(XorO);
    }


    private static boolean makingAiMoveEase(String XorO, Difficulty difficulty) {
        if (difficulty == Difficulty.EASY) System.out.println("Making move level \"easy\"");
        else if (difficulty == Difficulty.MEDIUM) System.out.println("Making move level \"medium\"");
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
                if ((k == randomInt) && (XorO.equals("O"))) gridArray[i][j] = "O";
                else if ((k == randomInt) && (XorO.equals("X"))) gridArray[i][j] = "X";
                k++;
            }
        }
        redrawGrid();
        return findWinner(XorO);

    }

    private static boolean makingAiMoveMedium(String XorO, Difficulty difficulty) {
        System.out.println("Making move level \"medium\"");
        gridArray[row][col] = XorO;
        redrawGrid();
        return findWinner(XorO);
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

    private static boolean findWinner(String XorO) {
        return ((checkRow(XorO) && checkCol(XorO) && checkDiagonals(XorO) && checkAvailableSpace()));
    }


    /**
     * Checks that the row is filled with X or 0
     *
     * @param XorO determines who makes the move X or O
     * @return the value is true if the line is not filled with Xor 0
     */
    private static boolean checkRow(String XorO) {
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
    private static boolean checkCol(String XorO) {
        int s = 0;
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

    private static boolean checkDiagonals(String XorO) {
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
    private static boolean checkAvailableSpace() {
        int s = 0;
        int[] avaibleSpace = new int[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ((gridArray[i][j].equals("X")) || (gridArray[i][j].equals("O"))) avaibleSpace[s] = 1;
                s++;
            }
        }
        if ((int) Arrays.stream(avaibleSpace).filter(x -> x == 1).count() == 9) {
            System.out.println("Draw");
            return false;
        }
        return true;
    }

    private static void clearArray() {
        IntStream.range(0, gridArray.length)
                .forEach(x -> Arrays.setAll(gridArray[x], (y) -> " "));

    }
}
