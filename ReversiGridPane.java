package app;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Created by korpa on 14.05.2017.
 */
public class ReversiGridPane extends Pane {
    CellCoordinates cellCoordinates;

    public ReversiGridPane(int row, int column) {
        cellCoordinates = new CellCoordinates(row, column);
    }

    public int getRow() {
        return cellCoordinates.getRow();
    }

    public int getColumn() {
        return cellCoordinates.getColumn();
    }

    public CellCoordinates getCellCoordinates() {
        return cellCoordinates;
    }
}
