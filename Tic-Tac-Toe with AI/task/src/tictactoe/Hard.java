package tictactoe;

import java.util.ArrayList;
import java.util.List;

import static tictactoe.Main.*;
import static tictactoe.Utils.*;


public class Hard extends Medium {
    protected static List<Point> availablePoints;
    //Создаём переменную для хранения координат и очков доступных клеток
    protected static List<Move> moves;
    protected static List<Move> outMoves;
    public static int numberOfFunctionCalls = 0;

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * В классе храним координаты доступных точек и очки
     */
     static class Move {
        int score;
        Point point;

        Move() {
        }

        Move(int score, Point point) {
            this.score = score;
            this.point = point;
        }

        public String toString() {
            return "[" + (point.x + 1) + ", " + (point.y + 1) + "]  счёт: " + score;
        }
    }


    public static boolean makingAiMoveHard(String XorO) {
        System.out.println("Making move level \"hard\"");
        gridArray[row][col] = XorO;
        redrawGrid();
        return findWinner(XorO);
    }

    /**
     * Сделать ход, в качестве источника данных класс Point. (перегруженная функция)
     *  @param XorO       символ игрока, или компьютера
     * @param point      координаты хода
     */
    public static boolean makingAiMoveHard(String XorO, Point point) {
        System.out.println("Making move level \"hard\"");
        row = point.x;
        col = point.y;
        gridArray[row][col] = XorO;
        redrawGrid();
        return findWinner(XorO);

    }

    /**
     * Получить доступные состояния ходов
     *
     * @return Возврат доступных точек для хода
     */
    public static List<Point> getAvailablePoints() {
        availablePoints = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gridArray[i][j].equals(EMPTY_SELL)) {
                    availablePoints.add(new Point(i, j));
                }
            }
        }
        return availablePoints;
    }


    /**
     * Вызов метода МиниМакс
     *
     * @param depth глубина хода
     * @param turn очередь ходов игроков
     * @param XorO каким символом играет игрок
     */
    public static int callMiniMax(int depth, int turn, String XorO) {
        moves = new ArrayList<>();
        outMoves = new ArrayList<>();

        miniMax(depth, 1, XorO);
        int bestScore;
        int bestMove = 0;
        if (turn == 1) {
            bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < outMoves.size(); i++) {
                if (outMoves.get(i).score > bestScore) {
                    bestScore = outMoves.get(i).score;
                    bestMove = i;
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < outMoves.size(); i++) {
                if (outMoves.get(i).score < bestScore) {
                    bestScore = outMoves.get(i).score;
                    bestMove = i;
                }
            }
        }

        return bestMove;
    }

    public static int miniMax(int depth, int turn, String XorO) {
        //numberOfFunctionCalls++;

        if (winning(gridArray, SYMBOL_X)) return 10;
        else if (winning(gridArray, SYMBOL_O)) return -10;
        // List of available points
        List<Point> pointsAvailable = getAvailablePoints();
        if (pointsAvailable.isEmpty()) return 0;
        for (int i = 0; i < pointsAvailable.size(); ++i) {
            Move move = new Move();
            move.point = pointsAvailable.get(i);
            makingMove(move.point, XorO);
            if (turn == 1) {
                move.score = miniMax(+1, 2, switchPlayers(XorO));
         } else {
                move.score = miniMax(+1, 1, switchPlayers(XorO));
          }
            gridArray[move.point.x][move.point.y] = EMPTY_SELL;

            moves.add(move);

            if (depth == 0) {
                Move outMove = new Move();
                for (Move m : moves) {
                    //We sum up the score, the coordinates remain the last ones we remembered.
                    outMove.score += m.score;
                    outMove.point = m.point;
                }
                outMoves.add(outMove);
                moves.clear();
            }

        }
        return 0;
    }

}

