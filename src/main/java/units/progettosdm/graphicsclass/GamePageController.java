package units.progettosdm.graphicsclass;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import units.progettosdm.backhandclass.Arch;
import units.progettosdm.backhandclass.Box;
import units.progettosdm.backhandclass.Dot;
import units.progettosdm.backhandclass.Game;
import units.progettosdm.projectExceptions.BadDotDeclarationException;

import java.util.*;

public class GamePageController {

    @FXML
    private Pane parentPane;
    @FXML
    private Pane tablePane;
    @FXML
    private Pane gameViewPane;

    private Label playerTurn;
    private Label pointsPlayer1;
    private Label pointsPlayer2;

    List<LineBetweenDotsGraphics> lines = new ArrayList<>();
    Map<Dot, DotGraphics> dots = new HashMap<>();
    List<PointLabelGraphics> labels = new ArrayList<>();

    Group dotsPoint = new Group();
    Group tableLines = new Group();
    Group labelsGrid = new Group();

    String player1;
    String player2;

    private double parentWidth;
    private double parentHeight;
    private double widthTablePane;
    private double heightTablePane;
    private double widthGameViewPane;
    private double heightGameViewPane;
    private double border;
    Game match;

    int N;
    int M;

    @FXML
    void initialize() {
        double dimension = Math.min(parentHeight, parentWidth);

    }


    public void initializeGrid(int n, int m) throws BadDotDeclarationException {
        N = n;
        M = m;

        drawField();
        setDots(N, M);
        setLines();
        setLabels();

        drawLabels();
        drawDots();
        drawLines();

        setClickLineListener();
        setMouseHoverListener();
    }

    private void drawField() {

        tablePane.getChildren().clear();
        gameViewPane.getChildren().clear();

        parentWidth = parentPane.getWidth();
        parentHeight = parentPane.getHeight();

        double dimension = Math.min(parentHeight, parentWidth);


        widthTablePane = dimension * 0.7;
        heightTablePane = dimension * 0.7;
        widthGameViewPane = dimension * 0.8;
        heightGameViewPane = dimension * 0.8;

        gameViewPane.setPrefWidth(widthGameViewPane);
        gameViewPane.setPrefHeight(heightGameViewPane);
        gameViewPane.setLayoutX(parentWidth / 2 - widthGameViewPane / 2);
        gameViewPane.setLayoutY(parentHeight / 2 - heightGameViewPane / 2);

        playerTurn = new Label();
        playerTurn.setLayoutX(0);
        playerTurn.setPrefHeight(17);
        playerTurn.setLayoutY(0);
        playerTurn.setPrefWidth(widthGameViewPane);
        playerTurn.setAlignment(Pos.CENTER);
        playerTurn.setStyle( "-fx-background-color: rgb(252,252,252)");

        pointsPlayer1 = new Label();
        pointsPlayer1.setLayoutX(0);
        pointsPlayer1.setPrefHeight(17);
        pointsPlayer1.setLayoutY(heightGameViewPane - pointsPlayer1.getPrefHeight());
        pointsPlayer1.setPrefWidth(widthGameViewPane / 2);
        pointsPlayer1.setAlignment(Pos.CENTER_LEFT);

        pointsPlayer2 = new Label();
        pointsPlayer2.setLayoutX(widthGameViewPane / 2);
        pointsPlayer2.setPrefHeight(17);
        pointsPlayer2.setLayoutY(heightGameViewPane - pointsPlayer2.getPrefHeight());
        pointsPlayer2.setPrefWidth(widthGameViewPane / 2);
        pointsPlayer2.setAlignment(Pos.CENTER_RIGHT);

        gameViewPane.getChildren().add(playerTurn);
        gameViewPane.getChildren().add(pointsPlayer1);
        gameViewPane.getChildren().add(pointsPlayer2);

        gameViewPane.getChildren().add(tablePane);

        tablePane.setPrefWidth(widthTablePane);
        tablePane.setPrefHeight(heightTablePane);
        tablePane.setLayoutX(widthGameViewPane / 2 - widthTablePane / 2);
        tablePane.setLayoutY(heightGameViewPane / 2 - heightTablePane / 2);

        Group borderLine = new Group();


        Line line1 = new Line(0, 0, 0, heightTablePane);
        Line line2 = new Line(0, 0, widthTablePane, 0);
        Line line3 = new Line(widthTablePane, 0, widthTablePane, heightTablePane);
        Line line4 = new Line(0, heightTablePane, widthTablePane, heightTablePane);
        borderLine.getChildren().add(line1);
        borderLine.getChildren().add(line2);
        borderLine.getChildren().add(line3);
        borderLine.getChildren().add(line4);

        borderLine.getChildren().forEach(e -> ((Line) e).setStrokeWidth(widthTablePane * 0.01));
        tablePane.getChildren().add(borderLine);


    }

    private void setMouseHoverListener() {
        lines.forEach(lin -> {
            if (!lin.isSelected()) {
                lin.setOnMouseEntered(ev -> {
                    tablePane.getScene().setCursor(Cursor.HAND);
                });
            }
        });
        lines.forEach(lin -> {
            if (!lin.isSelected()) {
                lin.setOnMouseExited(ev -> {
                    tablePane.getScene().setCursor(Cursor.DEFAULT);
                });
            }
        });
    }

    private void changeTurn() {
        playerTurn.setText("Turno di :" + match.getPlayerTurn());
    }

    private void setClickLineListener() {
        lines.forEach(lin -> lin.setOnMouseClicked(ev -> {
            match.playTurn(lin.backhandArch);
            refreshGraphicLines();
            lin.setOnMouseEntered(e -> {
                tablePane.getScene().setCursor(Cursor.DEFAULT);
            });
            labels.forEach(lab->lab.setBoxSelected());
            pointsPlayer1.setText(player1 + match.getScorePlayer1());
            pointsPlayer2.setText(player2 + match.getScorePlayer2());
            slidingEffect(pointsPlayer1);
            slidingEffect(pointsPlayer2);
            System.out.println(match.checkVictory());
            if(match.checkVictory()!=null){
                setVictory(match.checkVictory());
            }
            changeTurn();
        }));

    }

    private void setVictory(String winner) {
        Popup victoryPopup = new Popup();
        Pane victoryPopupPane = new Pane();
        victoryPopupPane.setPrefWidth(parentWidth*0.5);
        victoryPopupPane.setPrefHeight(parentHeight*0.5);
        victoryPopupPane.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 1), new CornerRadii(0.5), new Insets(0.0))));
        victoryPopupPane.setStyle("-fx-border-color: black");

        Label victoryMessage = new Label("HA VINTO "+ winner.toUpperCase(Locale.ROOT));
        victoryMessage.setPrefWidth(victoryPopupPane.getPrefWidth());
        victoryMessage.setPrefHeight(victoryPopupPane.getPrefHeight());
        victoryMessage.setAlignment(Pos.CENTER);
        victoryMessage.setFont(new Font(30));


        victoryPopupPane.getChildren().add(victoryMessage);
        victoryPopup.getContent().add(victoryPopupPane);
        victoryPopup.show(parentPane.getScene().getWindow());
    }

    private void refreshGraphicLines() {
        lines.forEach(lin -> {
            if (lin.isSelected()) {
                lin.setOpacity(1);
            } else {
                lin.setOpacity(0.2);
            }
        });
    }

    public void initializePage(int n, int m, Stage stage, String player1, String player2) {
        match = new Game(n, player1, player2);
        this.player1 = player1;
        this.player2 = player2;

        try {
            initializeGrid(n, m);
        } catch (BadDotDeclarationException e) {
            e.printStackTrace();
        }

        pointsPlayer1.setText("Punti " + player1 + " :");
        pointsPlayer2.setText("Punti " + player2 + " :");
        playerTurn.setText("Turno di " + player1);

        slidingEffect(playerTurn);
        slidingEffect(pointsPlayer1);
        slidingEffect(pointsPlayer2);
        //System.out.println(pointsPlayer1.getLayoutY());
        //System.out.println(pointsPlayer1.getLayoutX());
        //slidingEffect(pointsPlayer2);

        /*stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            drawField();
            try {
                setDots(N, M);
                setLines();
            } catch (BadDotDeclarationException e) {
                e.printStackTrace();
            }

            setClickLineListener();
            setMouseHoverListener();
        });

        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            drawField();
            try {
                setDots(N, M);
                setLines();
            } catch (BadDotDeclarationException e) {
                e.printStackTrace();
            }

            setClickLineListener();
            setMouseHoverListener();
        });*/


    }

    private void slidingEffect(Node node) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300),node);
        System.out.println(node);
        translateTransition.setFromY(-40);
        translateTransition.setToY(0);
        translateTransition.play();
    }

    private void setLines() throws BadDotDeclarationException {
        List<Arch> arches = match.getScoreboard().totalArches;
        lines = new ArrayList<>();

        arches.forEach(e -> {
            lines.add(new LineBetweenDotsGraphics(dots.get(e.getFirstDot()), dots.get(e.getSecondDot()), e));
        });

        lines.forEach(e -> {
            if (e.isSelected()) {
                e.setOpacity(1);
            } else {
                e.setOpacity(0.2);
            }
        });
    }

    private void drawLines() {

        tableLines.getChildren().clear();
        lines.forEach(e -> tableLines.getChildren().add(e));
        tablePane.getChildren().add(tableLines);
    }

    private void setDots(int n, int m) throws BadDotDeclarationException {
        double max = Math.max(n, m);
        border = widthTablePane * 0.05;
        double distanceX = (widthTablePane - border * 2) / (max);
        double distanceY = (heightTablePane - border * 2) / (max);
        double circleSize = distanceX / 20;

        dots = new HashMap<>();

        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < m + 1; j++) {
                double tempX = border + (distanceX * i);
                double tempY = border + (distanceY * j);
                dots.put(new Dot(i, j), new DotGraphics(new Dot(i, j), tempX, tempY, circleSize));
            }
        }
    }

    private void drawDots() {
        dotsPoint.getChildren().clear();
        dots.values().forEach(e -> dotsPoint.getChildren().add(e));
        tablePane.getChildren().add(dotsPoint);
    }

    private void setLabels(){
        labels = new ArrayList<>();
        Box[][] boxes= match.getScoreboard().getBoxes();
        Arrays.stream(boxes).forEach(line-> Arrays.stream(line).forEach(cell->{
            DotGraphics firstDot = dots.get(cell.getDots()[0]);
            DotGraphics thirdDot = dots.get(cell.getDots()[2]);
            DotGraphics[] firstAndThirdGraphicalDots = {firstDot,thirdDot};
            labels.add(new PointLabelGraphics(firstAndThirdGraphicalDots,cell));
        }));
    }
    private void drawLabels() {
        labelsGrid.getChildren().clear();
        labels.forEach(e -> labelsGrid.getChildren().add(e));
        tablePane.getChildren().add(labelsGrid);
    }
}
