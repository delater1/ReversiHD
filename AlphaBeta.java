package app;

import java.util.List;

/**
 * Created by korpa on 26.05.2017.
 */
public class AlphaBeta implements Runnable {
    public static PlayerTurn side;
    int depth;
    Board board;
    AiCallBacks aiCallBacks;

    AlphaBeta(int depth, PlayerTurn s, Board board, AiCallBacks aiCallBacks) {
        side = s;
        this.depth = depth;
        this.board = board;
        this.aiCallBacks = aiCallBacks;
    }

    @Override
    public void run() {
        List<CellCoordinates> list = board.getPossibleMoves(side);
        CellCoordinates bestCellCoordinates = list.get(0);
        int best = Integer.MIN_VALUE;
        for (int i = 0; i < list.size(); i++) {
            Integer res = new AlphaBetaNode().run(board.copy(), side, depth - 1, list.get(i), Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (res!= null && res > best) {
                best = res;
                bestCellCoordinates = list.get(i);
            }
        }
        aiCallBacks.onFinished(bestCellCoordinates, side);
    }
}
