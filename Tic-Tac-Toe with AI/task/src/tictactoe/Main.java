package tictactoe;

import java.util.*;

import static tictactoe.Player.*;
import static tictactoe.Utils.*;
import static tictactoe.Easy.*;
import static tictactoe.Medium.*;


public class Main {
    final static Scanner scanner = new Scanner(System.in);

    protected static int row = -1;
    protected static int col = -1;
    protected final static String[][] gridArray = new String[3][3];

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
                if (scanningGridToComplete(XorO))
                    repeat = makingAiMoveMedium(XorO, difficulty);
                else if (oppositeScanGrid(XorO))
                    repeat = makingAiMoveMedium(XorO, difficulty);
                else repeat = makingAiMoveEase(XorO, difficulty);
                if (count % 2 == 0) XorO = "X";
                else XorO = "O";
                count++;

            }
            clearArray();
        }
    }

    /**
     * @param XorO       determines who will play with a cross and who will play with a zero
     * @param difficulty определяет сложность игры.
     */
    private static void playPlayerAndAI(String XorO, Difficulty difficulty) {
        drawGrid();
        if (difficulty == Difficulty.EASY) {
            boolean repeat = true;
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
                            if (scanningGridToComplete(XorO))
                                repeat = makingAiMoveMedium("O", difficulty);
                            else if (oppositeScanGrid(XorO))
                                repeat = makingAiMoveMedium("O", difficulty);
                            else repeat = makingAiMoveEase("O", difficulty);
                        }
                    }

                } else if (XorO.equals("O")) {
                    if (scanningGridToComplete(XorO))
                        repeat = makingAiMoveMedium("X", difficulty);
                    else if (oppositeScanGrid(XorO))
                        repeat = makingAiMoveMedium("X", difficulty);
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

}
