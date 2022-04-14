package units.progettosdm.graphicsclass;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import units.progettosdm.backhandclass.Box;
import units.progettosdm.backhandclass.Dot;

public class GraphicalBoxLabel extends Label {
    Box backhandBox;
    private String player1Token;
    private String player2Token;
    Dot[] dots;


    Color color;

    public GraphicalBoxLabel(GraphicalDot[] dots, Box box,String player1Token,String player2Token) {
        super();
        this.backhandBox=box;
        this.player1Token = player1Token;
        this.player2Token = player2Token;
        this.setLayoutX(dots[0].getCenterX());
        this.setLayoutY(dots[0].getCenterY());
        this.setPrefWidth(dots[1].getCenterX() - dots[0].getCenterX());
        this.setPrefHeight(dots[1].getCenterY() - dots[0].getCenterY());
        this.setAlignment(Pos.CENTER);
        this.getStyleClass().add("boxes");
        System.out.println(this.getLayoutX() + " " + this.getLayoutY() + " " + this.getWidth() + " " + this.getHeight() + " " + this.getBackground());
    }

    public void setBoxSelected() {
        if (backhandBox.checkClosedBox()) {
            if (backhandBox.getPlayerBoxCharacter().equals(player1Token)) {
                this.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 255, 0.5), new CornerRadii(0.5), new Insets(0.0))));
            } else if (backhandBox.getPlayerBoxCharacter().equals(player2Token)) {
                this.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 00, 0.5), new CornerRadii(0.5), new Insets(0.0))));
            }
            this.setText(backhandBox.getPlayerBoxCharacter());
        }
    }
}