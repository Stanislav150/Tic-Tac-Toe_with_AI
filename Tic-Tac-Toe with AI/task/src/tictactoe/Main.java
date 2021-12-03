package tictactoe;

import java.util.*;
import java.util.stream.IntStream;

public class Main {
    final static Scanner scanner = new Scanner(System.in);
    //private final static BitSet bitSet = new BitSet();
    private final static Random random = new Random();
    private final static String[] testArray = new String[3];
    private static int row = -1;
    private static int col = -1;
    private final static String[][] gridArray = new String[3][3];


    public static void main(String[] args) {
        menuManagement();

    }

    /**
     * The method controls the game menu
     */
    @SuppressWarnings(value = "repeat")
    private static void menuManagement() {
        System.out.print("Input command: ");
        boolean repeat = true;
        while (repeat) {
            String startCommand = scanner.nextLine();
            if (startCommand.equals("exit")) {
                repeat =  false;
                break;
            }
            else if (startCommand.equals("start easy easy")) playTwoAI();
            else if (startCommand.equals("start easy user")) playPlayerAndAI("O");
            else if (startCommand.equals("start user easy")) playPlayerAndAI("X");
            else if (startCommand.equals("start user user")) playTwoUser();
            else System.out.println("Bad parameters!");
            System.out.print("Input command: ");

        }
   }


   private static void playTwoAI() {
        //Делаем случайные ходы до тех пор пока не появится признак победы.
       // Нужно сделать чтобы ходы были то крестиком, то ноликом.
       drawGrid();
       boolean repeat = true;
       String XorO = "X";
       int count = 1;
       while (repeat) {
           repeat = makingAiMove(XorO);
           if (count % 2 == 0) XorO = "X";
           else XorO = "O";
           count ++;

       }
       clearArray();

   }
    // Чем ходит пользователь и кто первый ходит
    private static void playPlayerAndAI(String XorO) {
        drawGrid();
        boolean repeat = true;
        // Если ИИ ходит первым то сначала рисуем сетку, затем ИИ делает ход,
        // затем опрашиваем сканер и делаем ход игрока
        while (repeat) {
            if (XorO.equals("X")) {
                System.out.print("Enter the coordinates: ");
                String inputStr = scanner.nextLine();
                if (checkCoordinates(inputStr)) {
                    repeat = makingPlayerMove("X");
                    if (repeat) repeat = makingAiMove("O");
                }

            }
            else if (XorO.equals("O")) {
                repeat = makingAiMove("X");
                System.out.print("Enter the coordinates: ");
                String inputStr = scanner.nextLine();
                if (checkCoordinates(inputStr)) {
                    repeat = makingPlayerMove("O");
                }
            }
        }
        clearArray();
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
                count ++;
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
        return findWinner();
    }

    private static boolean makingAiMove(String XorO) {
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
                if ((k == randomInt) && (XorO.equals("O"))) gridArray[i][j] = "O";
                else if ((k == randomInt) && (XorO.equals("X"))) gridArray[i][j] = "X";
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
        for (String[] row : gridArray) {
            if ((int) Arrays.stream(row).filter(x -> x.equals("X")).count() == 3) {
                System.out.println("X win");
                return false;
            }
        }
        for (String[] row : gridArray) {
            if ((int) Arrays.stream(row).filter(x -> x.equals("O")).count() == 3) {
                System.out.println("O win");
                return false;
            }
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
                testArray[i] = gridArray[i][s];
            }
            if ((int) Arrays.stream(testArray).filter(x -> x.equals("X")).count() == 3) {
                System.out.println("X win");
                return false;
            }

            if ((int) Arrays.stream(testArray).filter(x -> x.equals("O")).count() == 3) {
                System.out.println("O win");
                return false;
            }
            s++;
        }
        return true;
    }

    private static boolean checkDiagonals() {
        for (int i = 0; i < 3; i++) {
            testArray[i] = gridArray[i][i];

        }
        if ((int) Arrays.stream(testArray).filter(x -> x.equals("X")).count() == 3) {
            System.out.println("X win");
            return false;
        }

        if ((int) Arrays.stream(testArray).filter(x -> x.equals("O")).count() == 3) {
            System.out.println("O win");
            return false;
        }

        int j = 2;
        for (int i = 0; i < 3; i++) {
            testArray[i] = gridArray[i][j];
            j--;
        }
        if ((int) Arrays.stream(testArray).filter(x -> x.equals("X")).count() == 3) {
            System.out.println("X win");
            return false;
        }

        if ((int) Arrays.stream(testArray).filter(x -> x.equals("O")).count() == 3) {
            System.out.println("O win");
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
