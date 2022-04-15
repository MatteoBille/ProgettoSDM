package units.progettosdm.graphicsclass;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Collections;

public class StartPageController {
    @FXML
    Pane parentPane;
    @FXML
    TextField namePlayer1;
    @FXML
    TextField namePlayer2;
    @FXML
    TextField nDimension;
    @FXML
    TextField mDimension;
    @FXML
    Label errorPlayer1;
    @FXML
    Label errorPlayer2;
    @FXML
    private Stage stage;

    @FXML
    protected void onStartGameButtonClick(ActionEvent event) throws IOException {
        if (validateTextField()) {
            FXMLLoader fxmlLoader = new FXMLLoader(StartPageController.class.getResource("gamePage.fxml"));
            int n, m;
            n = Integer.parseInt(nDimension.getText());
            m = Integer.parseInt(mDimension.getText());

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), Math.max(n,m) >= 10 ? 1200 : 600, Math.max(n,m) >= 10 ? 1000 : 500);
            scene.getStylesheets().add(StartPageController.class.getResource("style.css") + "");
            stage.setScene(scene);
            stage.show();
            GamePageController controller = fxmlLoader.getController();

            controller.initializePage(n, m, namePlayer1.getText(), namePlayer2.getText());
        }
    }

    private boolean validateTextField() {
        boolean response = true;
        if (namePlayer1.getStyleClass().contains("error")) {
            namePlayer1.getStyleClass().removeAll(Collections.singleton("error"));
            errorPlayer1.setText("");
        }
        if (namePlayer2.getStyleClass().contains("error")) {
            namePlayer2.getStyleClass().removeAll(Collections.singleton("error"));
            errorPlayer2.setText("");
        }

        if (namePlayer1.getText().equals("")) {
            response = noNameError(namePlayer1, errorPlayer1);
        }
        if (namePlayer2.getText().equals("")) {
            response = noNameError(namePlayer2, errorPlayer2);
        }
        if (namePlayer1.getText().equals(namePlayer2.getText())) {
            namePlayer1.getStyleClass().add("error");
            namePlayer2.getStyleClass().add("error");
            errorPlayer1.setText("Inserisci un nome diverso dall'altro giocatore");
            errorPlayer2.setText("Inserisci un nome diverso dall'altro giocatore");

            response = false;
        }
        return response;
    }

    private boolean noNameError(TextField namePlayer2, Label errorPlayer2) {
        namePlayer2.getStyleClass().add("error");
        errorPlayer2.setText("Inserisci un nome");
        return false;
    }


    @FXML
    void initialize() {
        Image img = new Image(String.valueOf(StartPageController.class.getResource("sfondoCarta1.jpg")));
        BackgroundImage bImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        parentPane.setBackground(bGround);
    }
}

