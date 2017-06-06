package app;

import java.util.List;

/**
 * Created by korpa on 24.05.2017.
 */
public class MinMaxNode {
    PositionValueHeuteristic positionValueHeuteristic;

    MinMaxNode() {
        positionValueHeuteristic = new PositionValueHeuteristic();
    }


    public Result run(Board board, PlayerTurn playerTurn, int depth, CellCoordinates cellCoordinates) {
        board.makeMove(playerTurn, cellCoordinates);
        int boardEvaluation = evaluateBoard(board, playerTurn);
        if (depth == 0) {
            return new Result(boardEvaluation, cellCoordinates);
        } else {
            List<CellCoordinates> possibleMoves = board.getPossibleMoves(playerTurn);
            if (isMaximizing(playerTurn)) {
                Result resultMax = new Result (Integer.MIN_VALUE, cellCoordinates) ;
                for (int i = 0; i < possibleMoves.size(); i++) {
                    Result result = new MinMaxNode().run(board.copy(), board.getNextMovePlayer(playerTurn), depth - 1, possibleMoves.get(i));
                    if (result.value > resultMax.value) {
                        resultMax.value = result.value;
                    }
                }
                return resultMax;
            } else {
                Result resultMin = new Result(Integer.MAX_VALUE, cellCoordinates);
                for (int i = 0; i < possibleMoves.size(); i++) {
                    Result result = new MinMaxNode().run(board.copy(), board.getNextMovePlayer(playerTurn), depth - 1, possibleMoves.get(i));
                    if (result.value < resultMin.value) {
                        resultMin.value = result.value;
                    }
                }
                return resultMin;
            }
        }
    }

    private int evaluateBoard(Board board, PlayerTurn playerTurn) {
        return GameEngine.getAlHeuterestic0().evaluate(board, playerTurn);
    }


    private boolean isMaximizing(PlayerTurn playerTurn) {
        return playerTurn == MinMax.side;
    }
}
