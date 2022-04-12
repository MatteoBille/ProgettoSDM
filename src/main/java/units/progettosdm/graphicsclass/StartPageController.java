package units.progettosdm.graphicsclass;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class StartPageController {
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
        FXMLLoader fxmlLoader = new FXMLLoader(StartPageController.class.getResource("gamePage.fxml"));
        int n,m;
        String[] values = choicheGridDimensions.getValue().toString().split("x");
        n=Integer.parseInt(values[0]);
        m=Integer.parseInt(values[1]);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        GamePageController controller = fxmlLoader.getController();

        controller.initializePage(n,m,stage,namePlayer1.getText(),namePlayer2.getText());
    }



    @FXML
    void initialize(){
        choicheGridDimensions.getItems().add("2x2");
        choicheGridDimensions.getItems().add("3x3");
        choicheGridDimensions.getItems().add("5x5");
        choicheGridDimensions.getItems().add("10x10");
        choicheGridDimensions.getSelectionModel().selectFirst();
    }
}