package app;

/**
 * Created by korpa on 15.05.2017.
 */
public class CellCoordinates {
    int row;
    int column;

    public CellCoordinates(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
