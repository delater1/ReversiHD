package app;

/**
 * Created by korpa on 15.05.2017.
 */
public enum FieldState {
    EMPTY(-1),
    WHITE(0),
    BLACK(1);

    FieldState(int i) {
    }

    public static FieldState getOpposite(FieldState cellState) {
        if (cellState.equals(EMPTY)) {
            throw new Error("Trying to find opposite to empty CellState");
        }
        if (cellState.equals(WHITE)) {
            return BLACK;
        }
        return WHITE;
    }
}
