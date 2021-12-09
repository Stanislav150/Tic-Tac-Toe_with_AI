package tictactoe;

import static tictactoe.Utils.*;
import static tictactoe.Main.*;
import static tictactoe.Hard.*;


public class Player {
    public static boolean makingPlayerMove(String XorO) {
        if (XorO.equals(SYMBOL_X)) gridArray[row][col] = SYMBOL_X;
        else if (XorO.equals(SYMBOL_O)) gridArray[row][col] = SYMBOL_O;
        redrawGrid();
        return findWinner(XorO);
    }
    public static boolean makingPlayerMove(String XorO, Point point) {
        point.x = row;
        point.y = col;
        if (XorO.equals(SYMBOL_X)) gridArray[row][col] = SYMBOL_X;
        else if (XorO.equals(SYMBOL_O)) gridArray[row][col] = SYMBOL_O;
        redrawGrid();
        return findWinner(XorO);
    }
}
