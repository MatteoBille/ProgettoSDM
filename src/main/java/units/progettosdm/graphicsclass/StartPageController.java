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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;


import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import units.progettosdm.projectExceptions.BadArchDeclarationException;
import units.progettosdm.projectExceptions.BadBoardSizeDeclarationException;


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
    Label errorGridSize;

    Stage stage;

    @FXML
    protected void onStartGameButtonClick(ActionEvent event) throws IOException {
        //if (validateTextField() && validateGridSizeInput()) {
            FXMLLoader fxmlLoader = new FXMLLoader(StartPageController.class.getResource("gamePage.fxml"));
            int n, m;

            n = Integer.parseInt(numberOfColumn.getText());
            m = Integer.parseInt(numberOfRows.getText());
            Scene scene;
            try {
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(fxmlLoader.load());
                scene.getStylesheets().add(StartPageController.class.getResource("styleGamePage.css") + "");
                stage.setScene(scene);
                stage.show();
                GamePageController controller = fxmlLoader.getController();
                stage.setScene(scene);
                controller.initializeGame(n, m, namePlayer1.getText(), namePlayer2.getText());
            } catch (BadBoardSizeDeclarationException e) {
                e.printStackTrace();
                setErrorStyleAndErrorText(numberOfColumn, errorGridSize, "La dimensione della griglia è minore di due");
                setErrorStyleAndErrorText(numberOfRows, errorGridSize, "La dimensione della griglia è minore di due");
                scene = mainPane.getScene();
                stage.setScene(scene);
            } catch (BadArchDeclarationException e) {
                e.printStackTrace();
            }
            stage.show();
        //}
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
            setErrorStyleAndErrorText(namePlayer1, errorPlayer1, "Inserisci un nome");
            response = false;
        }
        if (textInTextFieldIsEqual(namePlayer2, "")) {
            setErrorStyleAndErrorText(namePlayer2, errorPlayer2, "Inserisci un nome");
            response = false;
        }
        if (namePlayer1.getText().length() >= 10) {
            setErrorStyleAndErrorText(namePlayer1, errorPlayer1, "Nome troppo lungo");
            response = false;
        }
        if (namePlayer2.getText().length() >= 10) {
            setErrorStyleAndErrorText(namePlayer2, errorPlayer2, "Nome troppo lungo");
            response = false;
        }

        if ((!namePlayer1.getText().matches("[a-zA-Z]+"))&&(!namePlayer1.getText().equals(""))) {
            setErrorStyleAndErrorText(namePlayer1, errorPlayer1, "Il nome può contenere solo lettere");
            response = false;
        }

        if ((!namePlayer2.getText().matches("[a-zA-Z]+"))&&(!namePlayer2.getText().equals(""))) {
            setErrorStyleAndErrorText(namePlayer2, errorPlayer2, "Il nome può contenere solo lettere");
            response = false;
        }

        if (textInTextFieldIsEqual(namePlayer1, namePlayer2.getText())) {
            setErrorStyleAndErrorText(namePlayer1, errorPlayer1, "Inserisci un nome diverso dall'altro giocatore");
            setErrorStyleAndErrorText(namePlayer2, errorPlayer2, "Inserisci un nome diverso dall'altro giocatore");

            response = false;
        }


        return response;
    }

    private boolean validateGridSizeInput() {
        boolean response = true;
        int maxSize = 20;
        if (containErrorStyle(numberOfColumn)) {
            removeErrorStyleAndSetErrorLabelEmpty(numberOfColumn, errorGridSize);
        }
        if (containErrorStyle(numberOfRows)) {
            removeErrorStyleAndSetErrorLabelEmpty(numberOfRows, errorGridSize);
        }

        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if (numberOfColumn.getText().equals("")) {
            setErrorStyleAndErrorText(numberOfColumn, errorGridSize, "Inserisci la dimensione della griglia");
            return false;
        }
        if (numberOfRows.getText().equals("")) {
            setErrorStyleAndErrorText(numberOfRows, errorGridSize, "Inserisci la dimensione della griglia");
            return false;
        }
        if (!pattern.matcher(numberOfColumn.getText()).matches()) {
            setErrorStyleAndErrorText(numberOfColumn, errorGridSize, "Il valore inserito non è un numero");
            response = false;
        } else {
            if (Integer.parseInt(numberOfColumn.getText()) > maxSize) {
                setErrorStyleAndErrorText(numberOfColumn, errorGridSize, "Il valore inserito è maggiore di " + maxSize);
                response = false;
            }
        }
        if (!pattern.matcher(numberOfRows.getText()).matches()) {
            setErrorStyleAndErrorText(numberOfRows, errorGridSize, "Il valore inserito non è un numero");
            response = false;
        } else {
            if (Integer.parseInt(numberOfRows.getText()) > maxSize) {
                setErrorStyleAndErrorText(numberOfRows, errorGridSize, "Il valore inserito è maggiore di " + maxSize);
                response = false;
            }
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

    private void setErrorStyleAndErrorText(TextField namePlayer2, Label errorPlayer2, String text) {
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

