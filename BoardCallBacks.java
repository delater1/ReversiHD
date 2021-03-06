package app;

/**
 * Created by korpa on 14.05.2017.
 */
public interface BoardCallBacks {
    boolean isPlayerTurn();

    boolean isMovePossible(PlayerTurn playerTurn, CellCoordinates cellLocation);

    PlayerTurn moveOn(PlayerTurn playerTurn, CellCoordinates cellLocation);

    Board getBoard();
}
