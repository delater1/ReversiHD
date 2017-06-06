package app;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * Created by korpa on 14.05.2017.
 */
public class BoardView implements UiCallBacks {
    int nodeSize;
    int N = 8;
    boolean gameFinished;
    ReversiGridPane[][] gameBoard;
    BoardCallBacks boardCallBacks;
    PlayerTurn currentPlayerTurn = GameSettings.playerColour;
    Stage stage;


    public BoardView(int nodeSize, BoardCallBacks boardCallBacks, Stage stage) {
        this.nodeSize = nodeSize;
        this.boardCallBacks = boardCallBacks;
        this.stage = stage;
        gameBoard = new ReversiGridPane[N][N];
    }

    public Scene createScene() {
        GridPane grid = new GridPane();
        grid.getStyleClass().add("game-grid");
        addConstraints(grid);
        createRowPanes(grid);
        Scene scene = new Scene(grid, (N * nodeSize) + 10, (N * nodeSize) + 10, Color.WHITE);
        scene.getStylesheets().add("app/game.css");
        return scene;
    }

    public void setStartingPosition() {
        boardUpdate(boardCallBacks.getBoard());
    }

    private void createRowPanes(GridPane grid) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                ReversiGridPane pane = new ReversiGridPane(i, j);
                pane.setOnMouseReleased((MouseEvent e) -> {
                    mouseClick(pane, boardCallBacks);
                });
                pane.getStyleClass().add("game-grid-cell");
                grid.add(pane, j, i);
                gameBoard[i][j] = pane;
            }
        }
    }

    private void addConstraints(GridPane grid) {
        for (int i = 0; i < N; i++) {
            ColumnConstraints column = new ColumnConstraints(nodeSize);
            grid.getColumnConstraints().add(column);
        }

        for (int i = 0; i < N; i++) {
            RowConstraints row = new RowConstraints(nodeSize);
            grid.getRowConstraints().add(row);
        }
    }

    public void mouseClick(ReversiGridPane reversiGridPane, BoardCallBacks boardCallBacks) {
        if (!gameFinished && boardCallBacks.isPlayerTurn() && boardCallBacks.isMovePossible(currentPlayerTurn, reversiGridPane.getCellCoordinates())) {
            setCircle(reversiGridPane, currentPlayerTurn.equals(PlayerTurn.BLACK) ? Color.BLACK : Color.WHITE);
            currentPlayerTurn = boardCallBacks.moveOn(currentPlayerTurn, reversiGridPane.getCellCoordinates());
            if (!gameFinished) {
                stage.setTitle(currentPlayerTurn.toString());
            }
        }
    }

    private void setCircle(ReversiGridPane reversiGridPane, Color color) {
        Group group = createCircle(reversiGridPane, color);
        reversiGridPane.getChildren().addAll(group);
    }

    private Group createCircle(ReversiGridPane reversiGridPane, Color color) {
        Circle circle = new Circle(reversiGridPane.getHeight() / 2, reversiGridPane.getWidth() / 2, reversiGridPane.getHeight() / 3);
        circle.setFill(color);
        Group group = new Group();
        group.getChildren().add(circle);
        return group;
    }

    @Override
    public void boardUpdate(Board board) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                gameBoard[i][j].getChildren().removeAll();
                if (board.getState(i, j).equals(FieldState.WHITE)) {
                    setCircle(gameBoard[i][j], Color.WHITE);
                }
                if (board.getState(i, j).equals(FieldState.BLACK)) {
                    setCircle(gameBoard[i][j], Color.BLACK);
                }
                if (boardCallBacks.isMovePossible(currentPlayerTurn, new CellCoordinates(i, j))) {
                    setHighlighted(gameBoard[i][j]);
                } else {
                    setNotHighlighted(gameBoard[i][j]);
                }
            }
        }
    }

    @Override
    public void gameEnd(String result) {
        stage.setTitle(result);
        gameFinished = true;
    }

    private void setNotHighlighted(ReversiGridPane reversiGridPane) {
        reversiGridPane.setStyle("-fx-background-color: black, #4d8f3d; -fx-background-insets: 0, 0 1 1 0;");
    }

    private void setHighlighted(ReversiGridPane reversiGridPane) {
        reversiGridPane.setStyle("-fx-background-color: black, #6aba3e; -fx-background-insets: 0, 0 1 1 0;");
    }

}
