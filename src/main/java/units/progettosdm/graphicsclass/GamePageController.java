package units.progettosdm.graphicsclass;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import units.progettosdm.backhandclass.Arch;
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
    @FXML
    private Label playerTurn;
    @FXML
    private Label pointsPlayer1;
    @FXML
    private Label pointsPlayer2;

    List<LineBetweenDotsGraphics> lines = new ArrayList<>();
    Map<Dot, DotGraphics> dots = new HashMap<>();
    Group dotsPoint = new Group();
    Group tableLines = new Group();

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
        dots = setDots(N, M);
        lines = setLines(N, M);

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

        playerTurn.setLayoutX(0);
        playerTurn.setPrefHeight(17);
        playerTurn.setLayoutY(0);
        playerTurn.setPrefWidth(gameViewPane.getWidth());
        playerTurn.setAlignment(Pos.CENTER);

        pointsPlayer1.setLayoutX(0);
        pointsPlayer1.setPrefHeight(17);
        pointsPlayer1.setLayoutY(gameViewPane.getHeight() - pointsPlayer1.getPrefHeight());
        pointsPlayer1.setPrefWidth(gameViewPane.getWidth() / 2);
        pointsPlayer1.setAlignment(Pos.CENTER_LEFT);

        pointsPlayer2.setLayoutX(gameViewPane.getWidth() / 2);
        pointsPlayer2.setPrefHeight(17);
        pointsPlayer2.setLayoutY(gameViewPane.getHeight() - pointsPlayer2.getPrefHeight());
        pointsPlayer2.setPrefWidth(gameViewPane.getWidth() / 2);
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
            pointsPlayer1.setText(player1 + match.getScorePlayer1());
            pointsPlayer2.setText(player2 + match.getScorePlayer2());
            changeTurn();
        }));

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

        pointsPlayer1.setText("Punti " + player1 + " :");
        pointsPlayer2.setText("Punti " + player2 + " :");
        playerTurn.setText("Turno di " + player1);

        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            drawField();
            try {
                setDots(N, M);
                setLines(N, M);
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
                setLines(N, M);
            } catch (BadDotDeclarationException e) {
                e.printStackTrace();
            }

            setClickLineListener();
            setMouseHoverListener();
        });

        try {
            initializeGrid(n, m);
        } catch (BadDotDeclarationException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<LineBetweenDotsGraphics> setLines(int n, int m) throws BadDotDeclarationException {
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
        drawLines();
        return (ArrayList<LineBetweenDotsGraphics>) lines;
    }

    private void drawLines() {

        tableLines.getChildren().clear();
        lines.forEach(e -> tableLines.getChildren().add(e));
        tablePane.getChildren().add(tableLines);
    }

    private Map<Dot, DotGraphics> setDots(int n, int m) throws BadDotDeclarationException {
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
        drawDots();
        return dots;
    }

    private void drawDots() {
        dotsPoint.getChildren().clear();
        dots.values().forEach(e -> dotsPoint.getChildren().add(e));
        tablePane.getChildren().add(dotsPoint);
    }
}
