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

public class StartPageController {
    @FXML
    Pane parentPane;
    @FXML
    TextField namePlayer1;
    @FXML
    TextField namePlayer2;
    @FXML
    ChoiceBox choicheGridDimensions;
    @FXML
    private Stage stage;

    @FXML
    protected void onStartGameButtonClick(ActionEvent event) throws IOException {
            if(validateTextField()) {
                FXMLLoader fxmlLoader = new FXMLLoader(StartPageController.class.getResource("gamePage.fxml"));
                int n, m;
                String[] values = choicheGridDimensions.getValue().toString().split("x");
                n = Integer.parseInt(values[0]);
                m = Integer.parseInt(values[1]);

                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load());
                scene.getStylesheets().add(StartPageController.class.getResource("style.css") + "");
                stage.setScene(scene);
                stage.show();
                GamePageController controller = fxmlLoader.getController();

                controller.initializePage(n, m, stage, namePlayer1.getText(), namePlayer2.getText());
            }
    }

    private boolean validateTextField() {
        boolean response=true;
        if(namePlayer1.getText().equals("")){
            namePlayer1.getStyleClass().add("error");
            response=false;
        }
        if(namePlayer2.getText().equals("")){
            namePlayer2.getStyleClass().add("error");
            response=false;
        }
        if(namePlayer1.getText().equals(namePlayer2.getText())){
            namePlayer1.getStyleClass().add("error");
            namePlayer2.getStyleClass().add("error");
            response=false;
        }
        return response;
    }


    @FXML
    void initialize(){
        Image img = new Image(String.valueOf(StartPageController.class.getResource("sfondoCarta1.jpg")));
        BackgroundImage bImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        parentPane.setBackground(bGround);

        choicheGridDimensions.getItems().add("2x2");
        choicheGridDimensions.getItems().add("3x3");
        choicheGridDimensions.getItems().add("5x5");
        choicheGridDimensions.getItems().add("10x10");
        choicheGridDimensions.getSelectionModel().selectFirst();
    }
}

