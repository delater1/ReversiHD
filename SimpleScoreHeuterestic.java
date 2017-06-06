package app;

/**
 * Created by korpa on 02.06.2017.
 */
public class SimpleScoreHeuterestic implements Heuterestic {
    @Override
    public int evaluate(Board board, PlayerTurn playerTurn) {
        return playerTurn.equals(PlayerTurn.BLACK) ? board.getBlackScore() : board.getWhiteScore();
    }
}
