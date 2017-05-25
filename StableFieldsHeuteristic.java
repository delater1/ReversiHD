package app;

/**
 * Created by korpa on 23.05.2017.
 */
//TODO :
public class StableFieldsHeuteristic {

    public StableFieldsHeuteristic(){

    }

    public int getNumberOfStablePieces(Board board, PlayerTurn playerTurn){
        FieldState fieldState = Utils.getPlayerColour(playerTurn);
        return 0;
    }
    
    
    private int getStableFieldsInTopLeftCorner(Board board,FieldState fieldState){
        int stableFields = 0;
        boolean isTopClaimed = checkRow(board, fieldState, 0);
        boolean isLeftClaimed = checkColumn(board, fieldState, 0);
        if (isTopClaimed){
            stableFields += 8;
        }
        if(isLeftClaimed){
            stableFields +=8;
        }



        return stableFields;
    }

    private boolean checkRow(Board board, FieldState fieldState, int row) {
        for (int i = 0; i < board.getN(); i++) {
            if(board.getState(row, i) != fieldState){
                return false;
            }
        }
        return true;
    }

    private boolean checkColumn(Board board, FieldState fieldState, int column) {
        for (int i = 0; i < board.getN(); i++) {
            if(board.getState(i, column) != fieldState){
                return false;
            }
        }
        return true;
    }
}
