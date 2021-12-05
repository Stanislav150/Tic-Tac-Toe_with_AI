package tictactoe;

import static tictactoe.Main.*;
import static tictactoe.Utils.*;

public class Medium extends Easy{
    public static boolean makingAiMoveMedium(String XorO, Difficulty difficulty) {
        System.out.println("Making move level \"medium\"");
        gridArray[row][col] = XorO;
        redrawGrid();
        return findWinner(XorO);
    }
}
