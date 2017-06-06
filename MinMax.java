package app;

import java.util.List;

/**
 * Created by korpa on 24.05.2017.
 */
public class MinMax implements Runnable {
    public static PlayerTurn side;
    int depth;
    Board board;
    AiCallBacks aiCallBacks;

    MinMax(int depth, PlayerTurn s, Board board, AiCallBacks aiCallBacks) {
        side = s;
        this.depth = depth;
        this.board = board;
        this.aiCallBacks = aiCallBacks;
    }

    @Override
    public void run() {
        List<CellCoordinates> list = board.getPossibleMoves(side);
        Result result = new Result(Integer.MIN_VALUE, list.get(0));
        for (int i = 0; i < list.size(); i++) {
            Result res = new MinMaxNode().run(board.copy(), side, depth - 1, list.get(i));
            if (res.value > result.value) {
                result = res;
            }
        }
        aiCallBacks.onFinished(result.cellCoordinates, side);
    }
}
