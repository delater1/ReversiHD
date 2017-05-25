package app;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(final Stage stage) throws Exception {
        GameEngine gameEngine = new GameEngine();
        BoardView boardView  = new BoardView(80, gameEngine);
        stage.setScene(boardView.createScene());
        stage.setTitle("Reversi");
        stage.show();
        gameEngine.setUiCallBacks(boardView);
        boardView.setStartingPosition();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
