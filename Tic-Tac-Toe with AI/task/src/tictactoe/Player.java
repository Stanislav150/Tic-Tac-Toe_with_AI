package tictactoe;

import static tictactoe.Utils.*;
import static tictactoe.Main.*;


public class Player {
    public static boolean makingPlayerMove(String XorO) {
        if (XorO.equals("X")) gridArray[row - 1][col - 1] = "X";
        else if (XorO.equals("O")) gridArray[row - 1][col - 1] = "O";
        redrawGrid();
        return findWinner(XorO);
    }
}
