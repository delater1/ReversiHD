package app;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Created by korpa on 14.05.2017.
 */
public class BoardView implements UiCallBacks {
    int nodeSize;
    int N = 8;
    ReversiGridPane[][] gameBoard;
    BoardCallBacks boardCallBacks;


    public BoardView(int nodeSize, BoardCallBacks boardCallBacks) {
        this.nodeSize = nodeSize;
        this.boardCallBacks = boardCallBacks;
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
        setCircle(gameBoard[3][3], Color.WHITE);
        setCircle(gameBoard[3][4], Color.BLACK);
        setCircle(gameBoard[4][3], Color.BLACK);
        setCircle(gameBoard[4][4], Color.WHITE);
    }

    private void createRowPanes(GridPane grid) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                ReversiGridPane pane = new ReversiGridPane(i, j);
                pane.setOnMouseReleased((MouseEvent e) -> {
                    mouseClick(pane, boardCallBacks);
                });
                pane.setOnMouseEntered(e -> mouseOver(pane, boardCallBacks));
                pane.setOnMouseExited(e -> mouseExit(pane));
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

    public static void mouseClick(ReversiGridPane reversiGridPane, BoardCallBacks boardCallBacks) {
        if (boardCallBacks.isPlayerTurn() && boardCallBacks.isMovePossible(PlayerTurn.BLACK, reversiGridPane.getCellCoordinates())) {
            boardCallBacks.moveOn(PlayerTurn.BLACK, reversiGridPane.getCellCoordinates());
        }
    }

    private static void setCircle(ReversiGridPane reversiGridPane, Color color) {
        Group group = createCircle(reversiGridPane, color);
        reversiGridPane.getChildren().addAll(group);
    }

    private static Group createCircle(ReversiGridPane reversiGridPane, Color color) {
        Circle circle = new Circle(reversiGridPane.getHeight() / 2, reversiGridPane.getWidth() / 2, reversiGridPane.getHeight() / 3);
        circle.setFill(color);
        Group group = new Group();
        group.getChildren().add(circle);
        return group;
    }

    public static void mouseOver(ReversiGridPane reversiGridPane, BoardCallBacks boardCallBacks) {
        if (boardCallBacks.isPlayerTurn() && boardCallBacks.isMovePossible(PlayerTurn.BLACK, reversiGridPane.getCellCoordinates())) {
            reversiGridPane.setStyle("-fx-background-color: black, #6aba3e; -fx-background-insets: 0, 0 1 1 0;");
        }
    }

    public static void mouseExit(ReversiGridPane reversiGridPane) {
        reversiGridPane.setStyle("-fx-background-color: black, #4d8f3d; -fx-background-insets: 0, 0 1 1 0;");
    }


    @Override
    public void boardUpdate(Board board) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                gameBoard[i][j].getChildren().removeAll();
                if(board.getState(i,j).equals(FieldState.WHITE)){
                    setCircle(gameBoard[i][j], Color.WHITE);
                }
                if(board.getState(i,j).equals(FieldState.BLACK)){
                    setCircle(gameBoard[i][j], Color.BLACK);
                }
            }
        }
    }
}
