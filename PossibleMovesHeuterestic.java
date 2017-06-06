package app;

/**
 * Created by korpa on 02.06.2017.
 */
public class PossibleMovesHeuterestic implements Heuterestic {
    @Override
    public int evaluate(Board board, PlayerTurn playerTurn) {
        return board.getPossibleMoves(playerTurn).size();
    }
}
