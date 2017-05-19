package app;

/**
 * Created by korpa on 15.05.2017.
 */
public class Board {
    // 0 = white
    // 1 = black
    // -1 = empty

    int[][] board;

    Board() {
        fillBoard();
        setStartingPosition();
    }

    private void setStartingPosition() {
        board[3][3] = 0;
        board[3][4] = 1;
        board[4][3] = 1;
        board[4][4] = 0;
        board[4][5] = 0;
    }

    private void fillBoard() {
        board = new int[8][8];
        int n = board.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = -1;
            }
        }
    }

    public FieldState getState(int row, int column) {
        switch (board[row][column]) {
            case 0:
                return FieldState.WHITE;
            case 1:
                return FieldState.BLACK;
            case -1:
            default:
                return FieldState.EMPTY;
        }
    }

    public void makeMove(PlayerTurn playerTurn, CellCoordinates cellCoordinates){
        if(playerTurn.equals(PlayerTurn.WHITE)){
            board[cellCoordinates.getRow()][cellCoordinates.getColumn()] = 0;
        }
        else if(playerTurn.equals(PlayerTurn.BLACK)){
            board[cellCoordinates.getRow()][cellCoordinates.getColumn()] = 1;
        }
    }
}
