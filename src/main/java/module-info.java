module progettoSDM.main {
    requires javafx.controls;
    requires javafx.fxml;

    opens units.progettosdm.graphicsclass to javafx.fxml;
    exports units.progettosdm.graphicsclass;
}