package tictactoe;

import java.util.ArrayList;
import java.util.Random;

import static tictactoe.Utils.*;
import static tictactoe.Main.*;

public class Easy {
    private final static Random random = new Random();

    public static boolean makingAiMoveEase(String XorO, Difficulty difficulty) {
        if (difficulty == Main.Difficulty.EASY) System.out.println("Making move level \"easy\"");
        else if (difficulty == Main.Difficulty.MEDIUM) System.out.println("Making move level \"medium\"");
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
}
