package tictactoe;

import java.util.ArrayList;
import static tictactoe.Utils.*;
import static tictactoe.Main.*;

public class Easy {
   public static boolean makingAiMoveEase(String XorO, Difficulty difficulty) {
        if (difficulty == Difficulty.EASY) System.out.println("Making move level \"easy\"");
        else if (difficulty == Difficulty.MEDIUM) System.out.println("Making move level \"medium\"");
        int k = 0;
        var freeCells = new ArrayList<Integer>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gridArray[i][j].equals(EMPTY_SELL)) {
                    freeCells.add(k);
                }
                k++;
            }
        }
        k = 0;
        int randomInt = freeCells.get(random.nextInt(freeCells.size()));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ((k == randomInt) && (XorO.equals(SYMBOL_O))) gridArray[i][j] = SYMBOL_O;
                else if ((k == randomInt) && (XorO.equals(SYMBOL_X))) gridArray[i][j] = SYMBOL_X;
                k++;
            }
        }
        redrawGrid();
        return findWinner(XorO);

    }
}
