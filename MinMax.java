package app;

import java.util.List;

/**
 * Created by korpa on 24.05.2017.
 */
public class MinMax {
    public static PlayerTurn side;
    int depth;

    MinMax(int depth, PlayerTurn s) {
        side = s;
        this.depth = depth;
    }

    public CellCoordinates run(PlayerTurn playerTurn, Board board) {
        List<CellCoordinates> list =  board.getPossibleMoves(playerTurn);
        Result result = new Result(Integer.MIN_VALUE, null);
        for (int i = 0; i < list.size(); i++) {
            Result res= new MinMaxNode().run(board.copy(), playerTurn, depth, list.get(i));
            if(res.value > result.value){
                result = res;
            }
        }
        return result.cellCoordinates;
    }
}
