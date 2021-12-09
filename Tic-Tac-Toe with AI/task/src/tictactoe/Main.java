package tictactoe;

import java.util.*;

import static tictactoe.Player.*;
import static tictactoe.Utils.*;
import static tictactoe.Easy.*;
import static tictactoe.Medium.*;
import static tictactoe.Hard.*;


public class Main {
    final static Scanner scanner = new Scanner(System.in);
    final static Random random = new Random();

    static final String SYMBOL_X = "X";
    static final String SYMBOL_O = "O";
    static final String EMPTY_SELL = " ";
    protected static int row = -1;
    protected static int col = -1;
    protected final static String[][] gridArray = new String[3][3];

    enum Difficulty {EASY, MEDIUM, HARD}

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
                    playPlayerAndAI(SYMBOL_O, Difficulty.EASY);
                    System.out.print("Input command: ");
                    break;
                case "start user easy":
                    playPlayerAndAI(SYMBOL_X, Difficulty.EASY);
                    System.out.print("Input command: ");
                    break;
                case "start medium user":
                    playPlayerAndAI(SYMBOL_O, Difficulty.MEDIUM);
                    System.out.print("Input command: ");
                    break;
                case "start user medium":
                    playPlayerAndAI(SYMBOL_X, Difficulty.MEDIUM);
                    System.out.print("Input command: ");
                    break;
                case "start hard hard":
                    playTwoAI(Difficulty.HARD);
                    System.out.print("Input command: ");
                    break;
                case "start hard user":
                    playPlayerAndAI(SYMBOL_O, Difficulty.HARD);
                    System.out.print("Input command: ");
                    break;
                case "start user hard":
                    playPlayerAndAI(SYMBOL_X, Difficulty.HARD);
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
            String XorO = SYMBOL_X;
            int count = 1;
            while (repeat) {
                repeat = makingAiMoveEase(XorO, difficulty);
                if (count % 2 == 0) XorO = SYMBOL_X;
                else XorO = SYMBOL_O;
                count++;

            }
            clearArray();
        } else if (difficulty == Difficulty.MEDIUM) {
            boolean repeat = true;
            String XorO = SYMBOL_X;
            int count = 1;
            while (repeat) {
                if (scanningGridToComplete(XorO))
                    repeat = makingAiMoveMedium(XorO, difficulty);
                else if (oppositeScanGrid(XorO))
                    repeat = makingAiMoveMedium(XorO, difficulty);
                else repeat = makingAiMoveEase(XorO, difficulty);
                if (count % 2 == 0) XorO = SYMBOL_X;
                else XorO = SYMBOL_O;
                count++;

            }
            clearArray();
        } else if (difficulty == Difficulty.HARD) {
            boolean repeat = true;
            String XorO = SYMBOL_X;
            int count = 1;
            while (repeat) {
                if (scanningGridToComplete(XorO))
                    repeat = makingAiMoveHard(XorO);
                else if (oppositeScanGrid(XorO))
                    repeat = makingAiMoveHard(XorO);
                else if (XorO.equals(SYMBOL_X)) {
                    int index = callMiniMax(0, 1, SYMBOL_X);
                    repeat = makingAiMoveHard(SYMBOL_X, outMoves.get(index).point);
                    //System.out.println(numberOfFunctionCalls);
                } else {
                    int index = callMiniMax(0, 2, SYMBOL_O);
                    repeat = makingAiMoveHard(SYMBOL_O, outMoves.get(index).point);
                    //System.out.println(numberOfFunctionCalls);
                }
               if (count % 2 == 0) XorO = SYMBOL_X;
                else XorO = SYMBOL_O;
                count++;
            }
            clearArray();
        }
    }

    /**
     * @param XorO       determines who will play with a cross and who will play with a zero
     * @param difficulty determines the difficulty of the game.
     */
    private static void playPlayerAndAI(String XorO, Difficulty difficulty) {
        drawGrid();
        if (difficulty == Difficulty.EASY) {
            boolean repeat = true;
            while (repeat) {
                if (XorO.equals(SYMBOL_X)) {
                    System.out.print("Enter the coordinates: ");
                    String inputStr = scanner.nextLine();
                    if (checkCoordinates(inputStr)) {
                        repeat = makingPlayerMove(SYMBOL_X);
                        if (repeat) repeat = makingAiMoveEase(SYMBOL_O, Difficulty.EASY);
                    }

                } else if (XorO.equals(SYMBOL_O)) {
                    repeat = makingAiMoveEase(SYMBOL_X, Difficulty.EASY);
                    System.out.print("Enter the coordinates: ");
                    String inputStr = scanner.nextLine();
                    if (checkCoordinates(inputStr)) {
                        repeat = makingPlayerMove(SYMBOL_O);
                    }
                }
            }
            clearArray();
        } else if (difficulty == Difficulty.MEDIUM) {
            boolean repeat = true;
            while (repeat) {
                if (XorO.equals(SYMBOL_X)) {
                    System.out.print("Enter the coordinates: ");
                    String inputStr = scanner.nextLine();
                    if (checkCoordinates(inputStr)) {
                        repeat = makingPlayerMove(SYMBOL_X);
                        if (repeat) {
                            if (scanningGridToComplete(XorO))
                                repeat = makingAiMoveMedium(SYMBOL_O, difficulty);
                            else if (oppositeScanGrid(XorO))
                                repeat = makingAiMoveMedium(SYMBOL_O, difficulty);
                            else repeat = makingAiMoveEase(SYMBOL_O, difficulty);
                        }
                    }

                } else if (XorO.equals(SYMBOL_O)) {
                    if (scanningGridToComplete(XorO))
                        repeat = makingAiMoveMedium(SYMBOL_X, difficulty);
                    else if (oppositeScanGrid(XorO))
                        repeat = makingAiMoveMedium(SYMBOL_X, difficulty);
                    else repeat = makingAiMoveEase(SYMBOL_X, difficulty);
                    if (repeat) {
                        System.out.print("Enter the coordinates: ");
                        String inputStr = scanner.nextLine();
                        if (checkCoordinates(inputStr)) {
                            repeat = makingPlayerMove(SYMBOL_O);
                        }
                    }
                }
            }
            clearArray();

        }
        else if (difficulty == Difficulty.HARD) {
            boolean repeat = true;
            while (repeat) {
                // Игрок ходит Х
                if (XorO.equals(SYMBOL_X)) {
                    System.out.print("Enter the coordinates: ");
                    String inputStr = scanner.nextLine();
                    if (checkCoordinates(inputStr)) {
                        repeat = makingPlayerMove(SYMBOL_X);
                        if (repeat) {
                            if (scanningGridToComplete(XorO))
                                repeat = makingAiMoveHard(SYMBOL_O);
                            else if (oppositeScanGrid(XorO))
                                repeat = makingAiMoveHard(SYMBOL_O);
                           else {
                                int index = callMiniMax(0, 2, SYMBOL_O);
                                repeat = makingAiMoveHard(SYMBOL_O, outMoves.get(index).point);
                                System.out.println(numberOfFunctionCalls);
                            }
                        }
                    }
                }
                // The first move is made by Ai, with a cross
                else if (XorO.equals(SYMBOL_O)) {
                    if (scanningGridToComplete(XorO))
                        repeat = makingAiMoveHard(SYMBOL_X);
                    else if (oppositeScanGrid(XorO))
                        repeat = makingAiMoveHard(SYMBOL_X);
                    else {
                        int index = callMiniMax(0, 1, SYMBOL_X);
                        repeat = makingAiMoveHard(SYMBOL_X, outMoves.get(index).point);
                        System.out.println(numberOfFunctionCalls);
                    }
                    if (repeat) {
                        System.out.print("Enter the coordinates: ");
                        String inputStr = scanner.nextLine();
                        if (checkCoordinates(inputStr)) {
                            repeat = makingPlayerMove(SYMBOL_O);
                        }
                    }
                }
            }
            clearArray();
        }
    }

    private static void playTwoUser() {
        drawGrid();
        String XorO = SYMBOL_X;
        int count = 1;
        boolean repeat = true;
        while (repeat) {
            System.out.print("Enter the coordinates: ");
            String inputStr = scanner.nextLine();
            if (checkCoordinates(inputStr)) {
                repeat = makingPlayerMove(XorO);
                if (count % 2 == 0) XorO = SYMBOL_X;
                else XorO = SYMBOL_O;
                count++;
            }
        }
        clearArray();
    }
}
