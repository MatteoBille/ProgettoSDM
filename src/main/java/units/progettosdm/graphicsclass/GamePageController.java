package units.progettosdm.graphicsclass;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import units.progettosdm.backhandclass.Dot;

import java.util.*;

public class GamePageController {

    @FXML
    private Pane parentPane;
    @FXML
    private Pane tablePane;
    @FXML
    private Pane gameViewPane;
    @FXML
    private Stage stage;
    @FXML
    private Label playingTurn;
    @FXML
    private Label pointsPlayer1;
    @FXML
    private Label pointsPlayer2;

    List<LineBetweenDots_Graphics> lines = new ArrayList<>();
    Map<Dot, DotGraphics> dots = new HashMap<>();
    Group dotsPoint = new Group();
    Group tableLines = new Group();

    String player1;
    String player2;

    boolean turn = true;

    private double parentWidth;
    private double parentHeight;
    private double widthTablePane;
    private double heightTablePane;
    private double widthGameViewPane;
    private double heightGameViewPane;
    private double border;


    int N;
    int M;

    @FXML
    void initialize() {
        double dimension = Math.min(parentHeight, parentWidth);


    }


    public void initializeGrid(int n, int m) {
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

        pointsPlayer1.setLayoutX(0);
        pointsPlayer1.setPrefHeight(17);
        pointsPlayer1.setLayoutY(gameViewPane.getHeight()-pointsPlayer1.getPrefHeight());
        pointsPlayer1.setPrefWidth(gameViewPane.getWidth()/2);
        pointsPlayer1.setAlignment(Pos.CENTER_LEFT);

        pointsPlayer2.setLayoutX(gameViewPane.getWidth()/2);
        pointsPlayer2.setPrefHeight(17);
        pointsPlayer2.setLayoutY(gameViewPane.getHeight()-pointsPlayer2.getPrefHeight());
        pointsPlayer2.setPrefWidth(gameViewPane.getWidth()/2);
        pointsPlayer2.setAlignment(Pos.CENTER_RIGHT);

        gameViewPane.getChildren().add(pointsPlayer1);
        gameViewPane.getChildren().add(pointsPlayer2);

        gameViewPane.getChildren().add(tablePane);

        System.out.println(dimension);
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
        lines.forEach(lin -> lin.setOnMouseEntered(ev -> {

            tablePane.getScene().setCursor(Cursor.HAND);
        }));
        lines.forEach(lin -> lin.setOnMouseExited(ev -> {
            tablePane.getScene().setCursor(Cursor.DEFAULT);
        }));
    }

    private void changeTurn() {
        turn = turn ? false : true;
        playingTurn.setText("Turno di " + (turn ? player1 : player2));
    }

    private void setClickLineListener() {
        lines.forEach(lin -> lin.setOnMouseClicked(ev -> {
            lin.setOpacity(1);
            lin.setOnMouseEntered(e -> {
                tablePane.getScene().setCursor(Cursor.DEFAULT);
            });
            System.out.println(lin);
            changeTurn();
        }));

    }

    public void initializePage(int n, int m, Stage stage, String player1, String player2) {
        this.player1 = player1;
        this.player2 = player2;

        pointsPlayer1.setText("Punti " + player1 + " :");
        pointsPlayer2.setText("Punti " + player2 + " :");
        playingTurn.setText("Turno di " + player1);

        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            drawField();
            setDots(N, M);
            setLines(N, M);
            setClickLineListener();
            setMouseHoverListener();
        });

        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            drawField();
            setDots(N, M);
            setLines(N, M);
            System.out.println(tablePane.getChildren());
            setClickLineListener();
            setMouseHoverListener();
        });

        initializeGrid(n, m);
    }

    private ArrayList<LineBetweenDots_Graphics> setLines(int n, int m) {
        lines = new ArrayList<>();
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < m + 1; j++) {
                System.out.println(dots.keySet());
                Dot comparingDot = new Dot(i, j);
                System.out.println(comparingDot);
                DotGraphics d = dots.get(comparingDot);
                System.out.println(d);
                if (d != null) {
                    if (i + 1 < n + 1) {
                        lines.add(new LineBetweenDots_Graphics(d, dots.get(new Dot(i + 1, j))));
                    }
                    if (j + 1 < m + 1) {
                        System.out.println(i + "," + j + "->" + i + "," + (j + 1));
                        lines.add(new LineBetweenDots_Graphics(d, dots.get(new Dot(i, j + 1))));
                    }
                }
            }
        }
        lines.forEach(e -> e.setOpacity(0.2));
        drawLines();
        return (ArrayList<LineBetweenDots_Graphics>) lines;
    }

    private void drawLines() {

        tableLines.getChildren().clear();
        System.out.println(tableLines.getChildren().size());
        lines.forEach(e -> tableLines.getChildren().add(e));
        System.out.println(tableLines.getChildren().size());
        tablePane.getChildren().add(tableLines);
    }

    private Map<Dot, DotGraphics> setDots(int n, int m) {
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
        System.out.println(dotsPoint.getChildren().size());
        dots.values().forEach(e -> dotsPoint.getChildren().add(e));
        System.out.println(dotsPoint.getChildren().size());
        tablePane.getChildren().add(dotsPoint);
    }
}
