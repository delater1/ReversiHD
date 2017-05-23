package app;

/**
 * Created by korpa on 14.05.2017.
 */
@SuppressWarnings("Duplicates")
public class GameEngine implements BoardCallBacks {
    UiCallBacks uiCallBacks;
    static final int N = 8;
    Board board;


    GameEngine() {
        board = new Board();
    }


    @Override
    public boolean isPlayerTurn() {
        return true;
    }

    @Override
    public boolean isMovePossible(PlayerTurn playerTurn, CellCoordinates cellLocation) {
        FieldState moveColour = getPlayerColour(playerTurn);
        return isFieldEmpty(cellLocation) && (isHorizontalMovePossible(moveColour, cellLocation) || isVerticalMovePossible(moveColour, cellLocation) || isDiagonalMovePossible(moveColour, cellLocation));
    }

    private FieldState getPlayerColour(PlayerTurn playerTurn) {
        if (playerTurn.equals(PlayerTurn.BLACK))
            return FieldState.BLACK;
        return FieldState.WHITE;
    }

    private boolean isFieldEmpty(CellCoordinates cellCoordinates) {
        return board.getState(cellCoordinates.getRow(), cellCoordinates.getColumn()) == FieldState.EMPTY;
    }

    private boolean isHorizontalMovePossible(FieldState cellState, CellCoordinates cellLocation) {
        boolean leftToRight = checkLeftToRight(cellState, cellLocation);
        boolean rightToLeft = checkRightToLeft(cellState, cellLocation);
        return leftToRight || rightToLeft;
    }


    private boolean checkBottomToTop(FieldState cellState, CellCoordinates cellLocation) {
        boolean isCorrectCellStateFound = false;
        boolean areAllPiecesBeetwenOposites = false;
        for (int i = N - 1; i > cellLocation.getRow(); i--) {
            if (isCorrectCellStateFound) {
                if (board.getState(i, cellLocation.getColumn()).equals(FieldState.getOpposite(cellState))) {
                    areAllPiecesBeetwenOposites = true;
                } else {
                    areAllPiecesBeetwenOposites = false;
                    isCorrectCellStateFound = false;
                }
            }
            if (cellState == board.getState(i, cellLocation.getColumn())) {
                isCorrectCellStateFound = true;
            }
        }
        return areAllPiecesBeetwenOposites;
    }


    private boolean checkTopToBottom(FieldState cellState, CellCoordinates cellLocation) {
        boolean isCorrectCellStateFound = false;
        boolean areAllPiecesBeetwenOposites = false;
        for (int i = 0; i < cellLocation.getRow(); i++) {
            if (isCorrectCellStateFound) {
                if (board.getState(i, cellLocation.getColumn()).equals(FieldState.getOpposite(cellState))) {
                    areAllPiecesBeetwenOposites = true;
                } else {
                    areAllPiecesBeetwenOposites = false;
                    isCorrectCellStateFound = false;
                }
            }
            if (cellState == board.getState(i, cellLocation.getColumn())) {
                isCorrectCellStateFound = true;
            }
        }
        return areAllPiecesBeetwenOposites;
    }


    private boolean checkRightToLeft(FieldState cellState, CellCoordinates cellLocation) {
        boolean isCorrectCellStateFound = false;
        boolean areAllPiecesBeetwenOposites = false;
        for (int i = N - 1; i > cellLocation.getColumn(); i--) {
            if (isCorrectCellStateFound) {
                if (board.getState(cellLocation.getRow(), i).equals(FieldState.getOpposite(cellState))) {
                    areAllPiecesBeetwenOposites = true;
                } else {
                    areAllPiecesBeetwenOposites = false;
                    isCorrectCellStateFound = false;
                }
            }
            if (cellState == board.getState(cellLocation.getRow(), i)) {
                isCorrectCellStateFound = true;
            }
        }
        return areAllPiecesBeetwenOposites;
    }

    private boolean checkLeftToRight(FieldState cellState, CellCoordinates cellLocation) {
        boolean isCorrectCellStateFound = false;
        boolean areAllPiecesBeetwenOposites = false;
        for (int i = 0; i < cellLocation.getColumn(); i++) {

            if (isCorrectCellStateFound) {
                if (board.getState(cellLocation.getRow(), i).equals(FieldState.getOpposite(cellState))) {
                    areAllPiecesBeetwenOposites = true;
                } else {
                    areAllPiecesBeetwenOposites = false;
                    isCorrectCellStateFound = false;
                }
            }
            if (cellState == board.getState(cellLocation.getRow(), i)) {
                isCorrectCellStateFound = true;
            }
        }
        return areAllPiecesBeetwenOposites;
    }

    private boolean isVerticalMovePossible(FieldState cellState, CellCoordinates cellLocation) {
        return checkTopToBottom(cellState, cellLocation) || checkBottomToTop(cellState, cellLocation);
    }

    private boolean isDiagonalMovePossible(FieldState fieldState, CellCoordinates cellLocation) {
        return checkTopLeftToRightBottom(fieldState, cellLocation) ||
                checkRightBottomToTopLeft(fieldState, cellLocation) ||
                checkTopRightToBottomLeft(fieldState, cellLocation) ||
                checkBottomLeftToTopRight(fieldState, cellLocation);
    }

    private boolean checkTopLeftToRightBottom(FieldState cellState, CellCoordinates cellLocation) {
        boolean isCorrectCellStateFound = false;
        boolean areAllPiecesBeetwenOposites = false;
        CellCoordinates borderCoordinates = topLeftBorderCoordinates(cellLocation);
        for (int i = borderCoordinates.getRow(), j = borderCoordinates.getColumn(); i < cellLocation.getRow(); i++, j++) {
//            System.out.println("i: " + i + " j: " + j);
            if (isCorrectCellStateFound) {
                if (board.getState(i, j).equals(FieldState.getOpposite(cellState))) {
                    areAllPiecesBeetwenOposites = true;
                } else {
                    areAllPiecesBeetwenOposites = false;
                    isCorrectCellStateFound = false;
                }
            }
            if (cellState == board.getState(i, j)) {
                isCorrectCellStateFound = true;
            }
        }
//        System.out.println("============================");
        return areAllPiecesBeetwenOposites;
    }

    private boolean checkRightBottomToTopLeft(FieldState cellState, CellCoordinates cellLocation) {
        boolean isCorrectCellStateFound = false;
        boolean areAllPiecesBeetwenOposites = false;
        CellCoordinates borderCoordinates = bottomRightBorderCoordinates(cellLocation);
        for (int i = borderCoordinates.getRow(), j = borderCoordinates.getColumn(); i > cellLocation.getRow(); i--, j--) {
//            System.out.println("i: " + i + " j: " + j);
            if (isCorrectCellStateFound) {
                if (board.getState(i, j).equals(FieldState.getOpposite(cellState))) {
                    areAllPiecesBeetwenOposites = true;
                } else {
                    areAllPiecesBeetwenOposites = false;
                    isCorrectCellStateFound = false;
                }
            }
            if (cellState == board.getState(i, j)) {
                isCorrectCellStateFound = true;
            }
        }
//        System.out.println("============================");
        return areAllPiecesBeetwenOposites;
    }

    private boolean checkTopRightToBottomLeft(FieldState cellState, CellCoordinates cellLocation) {
        boolean isCorrectCellStateFound = false;
        boolean areAllPiecesBeetwenOposites = false;
        CellCoordinates borderCoordinates = topRightBorderCoordinates(cellLocation);
        for (int i = borderCoordinates.getRow(), j = borderCoordinates.getColumn(); i < cellLocation.getRow(); i++, j--) {
//            System.out.println("i: " + i + " j: " + j);
            if (isCorrectCellStateFound) {
                if (board.getState(i, j).equals(FieldState.getOpposite(cellState))) {
                    areAllPiecesBeetwenOposites = true;
                } else {
                    areAllPiecesBeetwenOposites = false;
                    isCorrectCellStateFound = false;
                }
            }
            if (cellState == board.getState(i, j)) {
                isCorrectCellStateFound = true;
            }
        }
//        System.out.println("============================");
        return areAllPiecesBeetwenOposites;
    }

    private boolean checkBottomLeftToTopRight(FieldState cellState, CellCoordinates cellLocation) {
        boolean isCorrectCellStateFound = false;
        boolean areAllPiecesBeetwenOposites = false;
        CellCoordinates borderCoordinates = bottomLeftBorderCoordinates(cellLocation);
        for (int i = borderCoordinates.getRow(), j = borderCoordinates.getColumn(); i > cellLocation.getRow(); i--, j++) {
//            System.out.println("i: " + i + " j: " + j);
            if (isCorrectCellStateFound) {
                if (board.getState(i, j).equals(FieldState.getOpposite(cellState))) {
                    areAllPiecesBeetwenOposites = true;
                } else {
                    areAllPiecesBeetwenOposites = false;
                    isCorrectCellStateFound = false;
                }
            }
            if (cellState == board.getState(i, j)) {
                isCorrectCellStateFound = true;
            }
        }
//        System.out.println("============================");
        return areAllPiecesBeetwenOposites;
    }

    private CellCoordinates topLeftBorderCoordinates(CellCoordinates cellCoordinates) {
        CellCoordinates result = new CellCoordinates(cellCoordinates.getRow(), cellCoordinates.getColumn());
        while (result.getRow() != 0 && result.getColumn() != 0) {
            result.row--;
            result.column--;
        }
        return result;
    }

    private CellCoordinates topRightBorderCoordinates(CellCoordinates cellCoordinates) {
        CellCoordinates result = new CellCoordinates(cellCoordinates.getRow(), cellCoordinates.getColumn());
        while (result.getRow() != 0 && result.getColumn() != (N - 1)) {
            result.row--;
            result.column++;
        }
        return result;
    }

    private CellCoordinates bottomLeftBorderCoordinates(CellCoordinates cellCoordinates) {
        CellCoordinates result = new CellCoordinates(cellCoordinates.getRow(), cellCoordinates.getColumn());
        while (result.getRow() != (N - 1) && result.getColumn() != 0) {
            result.row++;
            result.column--;
        }
        return result;
    }

    private CellCoordinates bottomRightBorderCoordinates(CellCoordinates cellCoordinates) {
        CellCoordinates result = new CellCoordinates(cellCoordinates.getRow(), cellCoordinates.getColumn());
        while (result.getRow() != (N - 1) && result.getColumn() != (N - 1)) {
            result.row++;
            result.column++;
        }
        return result;
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
        claimFields(playerTurn, cellCoordinates);
        uiCallBacks.boardUpdate(board);
        return getNextMovePlayer(playerTurn);
    }

    private void claimFields(PlayerTurn playerTurn, CellCoordinates cellCoordinates) {
        FieldState fieldState = getPlayerColour(playerTurn);

        if (cellCoordinates.getColumn() != 0 && checkLeftToRight(fieldState, cellCoordinates)) {
            CellCoordinates coursor = new CellCoordinates(cellCoordinates.getRow(), cellCoordinates.getColumn() - 1);
            while (board.getState(coursor) == FieldState.getOpposite(fieldState)) {
                board.makeMove(playerTurn, coursor);
                coursor.column--;
            }
        }
        if (cellCoordinates.getColumn() != (N - 1) && checkRightToLeft(fieldState, cellCoordinates)) {
            CellCoordinates coursor = new CellCoordinates(cellCoordinates.getRow(), cellCoordinates.getColumn() + 1);
            while (board.getState(coursor) == FieldState.getOpposite(fieldState)) {
                board.makeMove(playerTurn, coursor);
                coursor.column++;
            }
        }
        if (checkTopToBottom(fieldState, cellCoordinates)) {
            CellCoordinates coursor = new CellCoordinates(cellCoordinates.getRow() - 1, cellCoordinates.getColumn());
            while (board.getState(coursor) == FieldState.getOpposite(fieldState)) {
                board.makeMove(playerTurn, coursor);
                coursor.row--;
            }
        }
        if (checkBottomToTop(fieldState, cellCoordinates)) {
            CellCoordinates coursor = new CellCoordinates(cellCoordinates.getRow() + 1, cellCoordinates.getColumn());
            while (board.getState(coursor) == FieldState.getOpposite(fieldState)) {
                board.makeMove(playerTurn, coursor);
                coursor.row++;
            }
        }
        if (checkTopLeftToRightBottom(fieldState, cellCoordinates)) {
            CellCoordinates coursor = new CellCoordinates(cellCoordinates.getRow() - 1, cellCoordinates.getColumn() - 1);
            while(board.getState(coursor) == FieldState.getOpposite(fieldState)){
                board.makeMove(playerTurn, coursor);
                coursor.column--;
                coursor.row--;
            }
        }
        if (checkRightBottomToTopLeft(fieldState, cellCoordinates)) {
            CellCoordinates coursor = new CellCoordinates(cellCoordinates.getRow() + 1, cellCoordinates.getColumn() + 1);
            while(board.getState(coursor) == FieldState.getOpposite(fieldState)){
                board.makeMove(playerTurn, coursor);
                coursor.column++;
                coursor.row++;
            }
        }
        if (checkBottomLeftToTopRight(fieldState, cellCoordinates)) {
            CellCoordinates coursor = new CellCoordinates(cellCoordinates.getRow() + 1, cellCoordinates.getColumn() - 1);
            while(board.getState(coursor) == FieldState.getOpposite(fieldState)){
                board.makeMove(playerTurn, coursor);
                coursor.column--;
                coursor.row++;
            }
        }
        if (checkTopRightToBottomLeft(fieldState, cellCoordinates)) {
            CellCoordinates coursor = new CellCoordinates(cellCoordinates.getRow() - 1, cellCoordinates.getColumn() + 1);
            while(board.getState(coursor) == FieldState.getOpposite(fieldState)){
                board.makeMove(playerTurn, coursor);
                coursor.column++;
                coursor.row--;
            }
        }
    }

    private PlayerTurn getNextMovePlayer(PlayerTurn playerTurn) {
        return playerTurn.equals(PlayerTurn.BLACK) ? PlayerTurn.WHITE : PlayerTurn.BLACK;
    }

    @Override
    public Board getBoard() {
        return board;
    }
}
