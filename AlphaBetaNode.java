package app;

import java.util.List;

/**
 * Created by korpa on 26.05.2017.
 */
public class AlphaBetaNode {
    PositionValueHeuteristic positionValueHeuteristic;

    AlphaBetaNode() {
        positionValueHeuteristic = new PositionValueHeuteristic();
    }

    public Integer run(Board board, PlayerTurn playerTurn, int depth, CellCoordinates cellCoordinates, int alpha, int beta) {
        board.makeMove(playerTurn, cellCoordinates);
        int boardEvaluation = evaluateBoard(board, playerTurn);
        if (depth == 0) {
            return boardEvaluation;
        } else {
            List<CellCoordinates> possibleMoves = board.getPossibleMoves(playerTurn);
            if (isMaximizing(playerTurn)) {
                for (int i = 0; i < possibleMoves.size(); i++) {
                    Integer result = new AlphaBetaNode().run(board.copy(), board.getNextMovePlayer(playerTurn), depth - 1, possibleMoves.get(i), alpha, beta);
                    if (result != null) {
                        alpha = Math.max(alpha, result);
                        if (beta >= alpha) {
                            return null;
                        }
                    }
                }
                return alpha;
            } else {
                int resultMin = Integer.MAX_VALUE;
                for (int i = 0; i < possibleMoves.size(); i++) {
                    Integer result = new AlphaBetaNode().run(board.copy(), board.getNextMovePlayer(playerTurn), depth - 1, possibleMoves.get(i), alpha, beta);
                    if (result != null) {
                        beta = Math.min(beta, result);
                        if (beta >= alpha) {
                            return null;
                        }
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
