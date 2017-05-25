package app;

/**
 * Created by korpa on 14.05.2017.
 */
@SuppressWarnings("Duplicates")
public class GameEngine implements BoardCallBacks {
    UiCallBacks uiCallBacks;
    static final int N = 8;
    Board board;
    MinMax minMax;


    GameEngine() {
        board = new Board();
        board.fillStartingBoard();
        minMax = new MinMax(2, PlayerTurn.WHITE);
    }


    @Override
    public boolean isPlayerTurn() {
        return true;
    }

    @Override
    public boolean isMovePossible(PlayerTurn playerTurn, CellCoordinates cellLocation) {
        return board.isMovePossible(playerTurn, cellLocation);
    }

    public void setUiCallBacks(UiCallBacks uiCallBacks) {
        this.uiCallBacks = uiCallBacks;
    }

    @Override
    public PlayerTurn moveOn(PlayerTurn playerTurn, CellCoordinates cellCoordinates) {
        if (!isMovePossible(playerTurn, cellCoordinates)) {
            throw new Error("Wrong move forwarded to backend");
        }
        board.makeMove(playerTurn, cellCoordinates);
        uiCallBacks.boardUpdate(board);
        CellCoordinates cellCoordinates1 = minMax.run(PlayerTurn.WHITE, board);
        System.out.println("-- row: " + cellCoordinates1.getRow() + "  column: " + cellCoordinates1.getColumn());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {


        }
        board.makeMove(PlayerTurn.WHITE, cellCoordinates1);

        uiCallBacks.boardUpdate(board);
        return playerTurn.BLACK;
    }


    @Override
    public Board getBoard() {
        return board;
    }


}
