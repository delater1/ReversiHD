package app;

/**
 * Created by korpa on 23.05.2017.
 */
public class PositionValueHeuteristic implements Heuterestic {

    int[][] positionValues =
                    {{99, -8, 8, 6, 6, 8, -8, 99},
                    {-8, -24, -4, -3, -3, -4, -24, -8},
                    {8, -4, 7, 4, 4, 7, -4, 8},
                    {6, -3, 4, 0, 0, 4, -3, 6},
                    {6, -3, 4, 0, 0, 4, -3, 6},
                    {8, -4, 7, 4, 4, 7, -4, 8},
                    {-8, -24, -4, -3, -3, -4, -24, -8},
                    {99, -8, 8, 6, 6, 8, -8, 99}};

    public int evaluate(Board board, PlayerTurn playerTurn) {
        int result = 0;
        FieldState fieldState = Utils.getPlayerColour(playerTurn);
        for (int i = 0; i < board.getN(); i++) {
            for (int j = 0; j < board.getN(); j++) {
                if (board.getState(i, j).equals(fieldState)) {
                    result += positionValues[i][j];
                }
            }
        }
        return result;
    }
}
