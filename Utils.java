package app;

/**
 * Created by korpa on 23.05.2017.
 */
public class Utils {
    public static FieldState getPlayerColour(PlayerTurn playerTurn) {
        if (playerTurn.equals(PlayerTurn.BLACK))
            return FieldState.BLACK;
        return FieldState.WHITE;
    }
}
