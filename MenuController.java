package app;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Created by korpa on 26.05.2017.
 */
public class MenuController {
    @FXML
    ChoiceBox playerColourChoiceBox;
    @FXML
    Label playerColourLabel;
    @FXML
    ChoiceBox<Integer> depth;
    @FXML
    ChoiceBox<String> mode;
    @FXML
    Label heuteristicLabel;
    @FXML
    ChoiceBox<String> heuteristicChoiceBox;
    @FXML
    Label heuteristic1Label;
    @FXML
    ChoiceBox<String> heuteristic1ChoiceBox;
    @FXML
    Button okButton;

    @FXML
    public void initialize() {
        selectFirstOptionsAsDafault();
        setVisibiltyChanger();
        setOnButtonClickListener();
        setVisibillities();
    }

    private void setOnButtonClickListener() {
        okButton.setOnMouseClicked(event -> {
            setGameSettingsVals();
            Stage stage = (Stage) okButton.getScene().getWindow();
            GameEngine gameEngine = new GameEngine();
            BoardView boardView = new BoardView(80, gameEngine, stage);
            stage.setScene(boardView.createScene());
            stage.setTitle("Reversi");
            stage.show();
            gameEngine.setUiCallBacks(boardView);
            boardView.setStartingPosition();
        });
    }

    private void setGameSettingsVals() {
        GameSettings.mode = mode.getSelectionModel().getSelectedItem();
        GameSettings.depth = depth.getSelectionModel().getSelectedItem();
        GameSettings.heuteristic = heuteristicChoiceBox.getSelectionModel().getSelectedItem();
        GameSettings.heuteristic1 = heuteristic1ChoiceBox.getSelectionModel().getSelectedItem();
        GameSettings.playerColour = playerColourChoiceBox.getSelectionModel().getSelectedItem().equals("Białe") ? PlayerTurn.WHITE : PlayerTurn.BLACK;
    }

    private void setVisibiltyChanger() {
        mode.setOnAction(event -> {
            setVisibillities();
        });
    }

    private void setVisibillities() {
        switch (mode.getSelectionModel().getSelectedItem()) {
            case "Gracz vs Ai": {
                heuteristic1ChoiceBox.setVisible(false);
                heuteristic1Label.setVisible(false);
                playerColourChoiceBox.setVisible(true);
                playerColourLabel.setVisible(true);
                break;
            }
            case "Ai vs Ai": {
                heuteristic1ChoiceBox.setVisible(true);
                heuteristic1Label.setVisible(true);
                playerColourChoiceBox.setVisible(false);
                playerColourLabel.setVisible(false);
                break;
            }
        }
    }

    private void selectFirstOptionsAsDafault() {
        mode.getSelectionModel().selectFirst();
        depth.getSelectionModel().selectFirst();
        heuteristicChoiceBox.getSelectionModel().selectFirst();
        heuteristic1ChoiceBox.getSelectionModel().selectFirst();
        playerColourChoiceBox.getSelectionModel().selectFirst();
    }
}
