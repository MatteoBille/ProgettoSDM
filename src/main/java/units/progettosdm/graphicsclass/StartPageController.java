package units.progettosdm.graphicsclass;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;


public class StartPageController {
    @FXML
    Pane mainPane;

    @FXML
    TextField namePlayer1;
    @FXML
    Label errorPlayer1;

    @FXML
    TextField namePlayer2;
    @FXML
    Label errorPlayer2;

    @FXML
    TextField numberOfColumn;
    @FXML
    TextField numberOfRows;


    @FXML
    protected void onStartGameButtonClick(ActionEvent event) throws IOException {
        if (validateTextField()) {
            FXMLLoader fxmlLoader = new FXMLLoader(StartPageController.class.getResource("gamePage.fxml"));
            int n, m;
            n = Integer.parseInt(numberOfColumn.getText());
            m = Integer.parseInt(numberOfRows.getText());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(StartPageController.class.getResource("styleGamePage.css") + "");
            stage.setScene(scene);
            stage.show();
            GamePageController controller = fxmlLoader.getController();
            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            stage.setFullScreen(true);


            controller.initializePage(n, m, namePlayer1.getText(), namePlayer2.getText());
        }
    }

    private boolean validateTextField() {
        boolean response = true;
        if (containErrorStyle(namePlayer1)) {
            removeErrorStyleAndSetErrorLabelEmpty(namePlayer1, errorPlayer1);
        }
        if (containErrorStyle(namePlayer2)) {
            removeErrorStyleAndSetErrorLabelEmpty(namePlayer2, errorPlayer2);
        }

        if (textInTextFieldIsEqual(namePlayer1, "")) {
            setErrorStyleAndErrorText(namePlayer1, errorPlayer1,"Inserisci un nome");
            response = false;
        }
        if (textInTextFieldIsEqual(namePlayer2, "")) {
            setErrorStyleAndErrorText(namePlayer2, errorPlayer2,"Inserisci un nome");
            response = false;
        }
        if (textInTextFieldIsEqual(namePlayer1, namePlayer2.getText())) {
            setErrorStyleAndErrorText(namePlayer1, errorPlayer1,"Inserisci un nome diverso dall'altro giocatore");
            setErrorStyleAndErrorText(namePlayer2, errorPlayer2,"Inserisci un nome diverso dall'altro giocatore");

            response = false;
        }
        return response;
    }

    private boolean textInTextFieldIsEqual(TextField namePlayer1, String s) {
        return namePlayer1.getText().equals(s);
    }

    private void removeErrorStyleAndSetErrorLabelEmpty(TextField namePlayer1, Label errorPlayer1) {
        namePlayer1.getStyleClass().removeAll(Collections.singleton("error"));
        errorPlayer1.setText("");
    }

    private boolean containErrorStyle(TextField namePlayer1) {
        return namePlayer1.getStyleClass().contains("error");
    }

    private void setErrorStyleAndErrorText(TextField namePlayer2, Label errorPlayer2,String text) {
        namePlayer2.getStyleClass().add("error");
        errorPlayer2.setText(text);
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
        mainPane.setBackground(bGround);
    }
}

