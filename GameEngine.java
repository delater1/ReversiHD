package app;

/**
 * Created by korpa on 14.05.2017.
 */
@SuppressWarnings("Duplicates")
public class GameEngine implements BoardCallBacks, AiCallBacks {
    static PositionValueHeuteristic positionValueHeuteristic;
    static PossibleMovesHeuterestic possibleMovesHeuterestic;
    static SimpleScoreHeuterestic simpleScoreHeuterestic;
    UiCallBacks uiCallBacks;
    Board board;
    boolean isPlayerTurn = true;

    GameEngine() {
        board = new Board();
        board.fillStartingBoard();
        positionValueHeuteristic = new PositionValueHeuteristic();
        possibleMovesHeuterestic = new PossibleMovesHeuterestic();
        simpleScoreHeuterestic = new SimpleScoreHeuterestic();
    }

    @Override
    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    @Override
    public boolean isMovePossible(PlayerTurn playerTurn, CellCoordinates cellLocation) {
        return board.isMovePossible(playerTurn, cellLocation);
    }

    public void setUiCallBacks(UiCallBacks uiCallBacks) {
        this.uiCallBacks = uiCallBacks;
        if (GameSettings.playerColour.equals(PlayerTurn.WHITE)) {
            runAi(PlayerTurn.BLACK);
        }
    }

    @Override
    public PlayerTurn moveOn(PlayerTurn playerTurn, CellCoordinates cellCoordinates) {
        if (!isMovePossible(playerTurn, cellCoordinates)) {
            throw new Error("Wrong move forwarded to backend");
        }
        board.makeMove(playerTurn, cellCoordinates);
        uiCallBacks.boardUpdate(board);
        isPlayerTurn = false;
        runAi(board.getOppositePlayer(playerTurn));
        if (board.isGameFinished()) {
            gameFinished();
        }
        isPlayerTurn = true;
        return playerTurn;
    }

    private void runAi(PlayerTurn playerTurn) {
        if (hasMoves((playerTurn))) {
            runAlphaBeta(playerTurn);
            while (!hasMoves(board.getOppositePlayer(playerTurn)) && hasMoves(playerTurn)) {
                runAlphaBeta(playerTurn);
            }
        }
        uiCallBacks.boardUpdate(board);
    }

    private boolean hasMoves(PlayerTurn playerTurn) {
        return board.getPossibleMoves(playerTurn).size() != 0;
    }

    private void runMinMax(PlayerTurn playerTurn) {
        MinMax minMax = new MinMax(GameSettings.depth, playerTurn, board, this);
        minMax.run();
    }

    private void runAlphaBeta(PlayerTurn playerTurn) {
        AlphaBeta alphaBeta = new AlphaBeta(GameSettings.depth, playerTurn, board, this);
        alphaBeta.run();
    }

    private void gameFinished() {
        if (board.isGameFinished()) {
            System.out.println("Game finished");
            if (board.getBlackScore() > board.getWhiteScore()) {
                System.out.println("Black won: " + board.getBlackScore() + " - " + board.getWhiteScore());
                uiCallBacks.gameEnd("Black won: " + board.getBlackScore() + " - " + board.getWhiteScore());
            } else if (board.getBlackScore() < board.getWhiteScore()) {
                System.out.println("White won: " + board.getWhiteScore() + " - " + board.getBlackScore());
                uiCallBacks.gameEnd("White won: " + board.getWhiteScore() + " - " + board.getBlackScore());
            } else {
                System.out.println("Draw: " + board.getWhiteScore() + " - " + board.getBlackScore());
                uiCallBacks.gameEnd("Draw: " + board.getWhiteScore() + " - " + board.getBlackScore());
            }
        }
    }


    @Override
    public Board getBoard() {
        return board;
    }


    @Override
    public void onFinished(CellCoordinates cellCoordinates, PlayerTurn playerTurn) {
        board.makeMove(playerTurn, cellCoordinates);
    }

    public static Heuterestic getAlHeuterestic0(){
        return getAlHeuterestic(GameSettings.heuteristic);
    }

    public static Heuterestic getAlHeuterestic1(){
        return getAlHeuterestic(GameSettings.heuteristic1);
    }

    private static Heuterestic getAlHeuterestic(String heuteristic){
        if(heuteristic.equals("Statyczna tablica"))
            return positionValueHeuteristic;
        if(heuteristic.equals("Zajete pola"))
            return simpleScoreHeuterestic;
        if(heuteristic.equals("Liczba możliwych ruchow"))
            return possibleMovesHeuterestic;
        return null;
    }


}
