package units.progettosdm.graphicsclass;

import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class PointCounter extends HBox {
    String playerName;
    int points;

    Label name;
    Label point;

    public PointCounter(double height, double width, double layoutX, double layoutY, String player,Color color) {
        super();
        this.setLayoutX(layoutX);
        this.setLayoutY(layoutY);
        this.setPrefWidth(width);
        this.setPrefHeight(height);

        this.playerName = player;
        this.points = 0;

        name = new Label();
        name.setPrefHeight(this.getPrefHeight());
        name.setPrefWidth(this.getPrefWidth() * 0.75);
        name.setBackground(new Background(new BackgroundFill(color, new CornerRadii(0.5), new Insets(0))));
        name.setAlignment(Pos.CENTER);
        name.setText(playerName);

        point = new Label();
        point.setPrefHeight(this.getPrefHeight());
        point.setPrefWidth(this.getPrefWidth() * 0.25);
        point.setBackground(new Background(new BackgroundFill(Color.AQUA, new CornerRadii(0.5), new Insets(0))));
        point.setAlignment(Pos.CENTER);
        point.setText(Integer.toString(points));

        this.getChildren().add(name);
        this.getChildren().add(point);

    }

    public void setPoint(int score) {
        if (score != points) {
            points = score;
            point.setText(Integer.toString(points));
            slidingEffect();
        }
    }

    private void slidingEffect() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300), point);
        translateTransition.setFromY(-20);
        translateTransition.setToY(0);
        translateTransition.play();
    }
}
