package units.progettosdm.graphicsclass;

import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class PlayerTurnSlider extends Label {
    public String playerName;

    public PlayerTurnSlider() {
    }

    public void setPlayerAndSlide(String text) {
        playerName=text;
        setText("Turno di "+text);
        slidingEffect();
    }
    private void slidingEffect() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), this);
        translateTransition.setFromY(-15);
        translateTransition.setToY(0);
        translateTransition.play();
    }
}
