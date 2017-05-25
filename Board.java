package app;


import java.util.ArrayList;
import java.util.List;

import static app.Utils.getPlayerColour;

/**
 * Created by korpa on 15.05.2017.
 */
@SuppressWarnings("Duplicates")
public class Board {
    int N = 8;
    // 0 = white
    // 1 = black
    // -1 = empty

    int[][] board;

    Board() {

    }

    public void fillStartingBoard(){
        fillBoard();
        setStartingPosition();
    }

    private void setStartingPosition() {
        board[3][3] = 0;
        board[3][4] = 1;
        board[4][3] = 1;
        board[4][4] = 0;
    }

    private void fillBoard() {
        board = new int[8][8];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
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

    public FieldState getState(CellCoordinates cellCoordinates) {
        return getState(cellCoordinates.getRow(), cellCoordinates.getColumn());
    }

    public void makeMove(PlayerTurn playerTurn, CellCoordinates cellCoordinates) {
        setFieldColour(playerTurn, cellCoordinates);
        claimFields(playerTurn, cellCoordinates);
    }

    private void setFieldColour(PlayerTurn playerTurn, CellCoordinates cellCoordinates) {
        if (playerTurn.equals(PlayerTurn.WHITE)) {
            board[cellCoordinates.getRow()][cellCoordinates.getColumn()] = 0;
        } else if (playerTurn.equals(PlayerTurn.BLACK)) {
            board[cellCoordinates.getRow()][cellCoordinates.getColumn()] = 1;
        }
    }

    public int getBlackScore() {
        int counter = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 1) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public int getWhiteScore() {
        int counter = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 0) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public int getN() {
        return N;
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
                if (getState(i, cellLocation.getColumn()).equals(FieldState.getOpposite(cellState))) {
                    areAllPiecesBeetwenOposites = true;
                } else {
                    areAllPiecesBeetwenOposites = false;
                    isCorrectCellStateFound = false;
                }
            }
            if (cellState == getState(i, cellLocation.getColumn())) {
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
                if (getState(i, cellLocation.getColumn()).equals(FieldState.getOpposite(cellState))) {
                    areAllPiecesBeetwenOposites = true;
                } else {
                    areAllPiecesBeetwenOposites = false;
                    isCorrectCellStateFound = false;
                }
            }
            if (cellState == getState(i, cellLocation.getColumn())) {
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
                if (getState(cellLocation.getRow(), i).equals(FieldState.getOpposite(cellState))) {
                    areAllPiecesBeetwenOposites = true;
                } else {
                    areAllPiecesBeetwenOposites = false;
                    isCorrectCellStateFound = false;
                }
            }
            if (cellState == getState(cellLocation.getRow(), i)) {
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
                if (getState(cellLocation.getRow(), i).equals(FieldState.getOpposite(cellState))) {
                    areAllPiecesBeetwenOposites = true;
                } else {
                    areAllPiecesBeetwenOposites = false;
                    isCorrectCellStateFound = false;
                }
            }
            if (cellState == getState(cellLocation.getRow(), i)) {
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
                if (getState(i, j).equals(FieldState.getOpposite(cellState))) {
                    areAllPiecesBeetwenOposites = true;
                } else {
                    areAllPiecesBeetwenOposites = false;
                    isCorrectCellStateFound = false;
                }
            }
            if (cellState == getState(i, j)) {
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
                if (getState(i, j).equals(FieldState.getOpposite(cellState))) {
                    areAllPiecesBeetwenOposites = true;
                } else {
                    areAllPiecesBeetwenOposites = false;
                    isCorrectCellStateFound = false;
                }
            }
            if (cellState == getState(i, j)) {
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
                if (getState(i, j).equals(FieldState.getOpposite(cellState))) {
                    areAllPiecesBeetwenOposites = true;
                } else {
                    areAllPiecesBeetwenOposites = false;
                    isCorrectCellStateFound = false;
                }
            }
            if (cellState == getState(i, j)) {
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
                if (getState(i, j).equals(FieldState.getOpposite(cellState))) {
                    areAllPiecesBeetwenOposites = true;
                } else {
                    areAllPiecesBeetwenOposites = false;
                    isCorrectCellStateFound = false;
                }
            }
            if (cellState == getState(i, j)) {
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

    public void claimFields(PlayerTurn playerTurn, CellCoordinates cellCoordinates) {
        FieldState fieldState = getPlayerColour(playerTurn);

        if (cellCoordinates.getColumn() != 0 && checkLeftToRight(fieldState, cellCoordinates)) {
            CellCoordinates coursor = new CellCoordinates(cellCoordinates.getRow(), cellCoordinates.getColumn() - 1);
            while (getState(coursor) == FieldState.getOpposite(fieldState)) {
                makeMove(playerTurn, coursor);
                coursor.column--;
            }
        }
        if (cellCoordinates.getColumn() != (N - 1) && checkRightToLeft(fieldState, cellCoordinates)) {
            CellCoordinates coursor = new CellCoordinates(cellCoordinates.getRow(), cellCoordinates.getColumn() + 1);
            while (getState(coursor) == FieldState.getOpposite(fieldState)) {
                makeMove(playerTurn, coursor);
                coursor.column++;
            }
        }
        if (checkTopToBottom(fieldState, cellCoordinates)) {
            CellCoordinates coursor = new CellCoordinates(cellCoordinates.getRow() - 1, cellCoordinates.getColumn());
            while (getState(coursor) == FieldState.getOpposite(fieldState)) {
                makeMove(playerTurn, coursor);
                coursor.row--;
            }
        }
        if (checkBottomToTop(fieldState, cellCoordinates)) {
            CellCoordinates coursor = new CellCoordinates(cellCoordinates.getRow() + 1, cellCoordinates.getColumn());
            while (getState(coursor) == FieldState.getOpposite(fieldState)) {
                makeMove(playerTurn, coursor);
                coursor.row++;
            }
        }
        if (checkTopLeftToRightBottom(fieldState, cellCoordinates)) {
            CellCoordinates coursor = new CellCoordinates(cellCoordinates.getRow() - 1, cellCoordinates.getColumn() - 1);
            while (getState(coursor) == FieldState.getOpposite(fieldState)) {
                makeMove(playerTurn, coursor);
                coursor.column--;
                coursor.row--;
            }
        }
        if (checkRightBottomToTopLeft(fieldState, cellCoordinates)) {
            CellCoordinates coursor = new CellCoordinates(cellCoordinates.getRow() + 1, cellCoordinates.getColumn() + 1);
            while (getState(coursor) == FieldState.getOpposite(fieldState)) {
                makeMove(playerTurn, coursor);
                coursor.column++;
                coursor.row++;
            }
        }
        if (checkBottomLeftToTopRight(fieldState, cellCoordinates)) {
            CellCoordinates coursor = new CellCoordinates(cellCoordinates.getRow() + 1, cellCoordinates.getColumn() - 1);
            while (getState(coursor) == FieldState.getOpposite(fieldState)) {
                makeMove(playerTurn, coursor);
                coursor.column--;
                coursor.row++;
            }
        }
        if (checkTopRightToBottomLeft(fieldState, cellCoordinates)) {
            CellCoordinates coursor = new CellCoordinates(cellCoordinates.getRow() - 1, cellCoordinates.getColumn() + 1);
            while (getState(coursor) == FieldState.getOpposite(fieldState)) {
                makeMove(playerTurn, coursor);
                coursor.column++;
                coursor.row--;
            }
        }
    }

    public boolean isMovePossible(PlayerTurn playerTurn, CellCoordinates cellLocation) {
        FieldState moveColour = getPlayerColour(playerTurn);
        return isFieldEmpty(cellLocation) && (isHorizontalMovePossible(moveColour, cellLocation) || isVerticalMovePossible(moveColour, cellLocation) || isDiagonalMovePossible(moveColour, cellLocation));
    }

    private boolean isFieldEmpty(CellCoordinates cellCoordinates) {
        return getState(cellCoordinates.getRow(), cellCoordinates.getColumn()) == FieldState.EMPTY;
    }

    public List<CellCoordinates> getPossibleMoves(PlayerTurn playerTurn){
        ArrayList<CellCoordinates> possibleMovesList = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if(isMovePossible(playerTurn, new CellCoordinates(i, j))){
                    possibleMovesList.add(new CellCoordinates(i, j));
                }
            }
        }
        return possibleMovesList;
    }

    public Board copy(){
        Board copy = new Board();
        copy.board = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                copy.board[i][j] = board[i][j];
            }
        }
        return copy;
    }

    public PlayerTurn getNextMovePlayer(PlayerTurn playerTurn) {
        return playerTurn.equals(PlayerTurn.BLACK) ? PlayerTurn.WHITE : PlayerTurn.BLACK;
    }
}
