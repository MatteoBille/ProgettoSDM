package units.progettosdm.graphicsclass;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;
import units.progettosdm.backhandclass.Arch;
import units.progettosdm.backhandclass.Box;
import units.progettosdm.backhandclass.Dot;
import units.progettosdm.backhandclass.Game;
import units.progettosdm.projectExceptions.BadDotDeclarationException;

import java.io.IOException;
import java.util.*;

public class GamePageController {

    public final Color player1BackgroundColor = Color.rgb(0, 0, 255, 0.5);
    public final Color player2BackgroundColor = Color.rgb(255, 0, 0, 0.5);
    public final Color player1TextColor = Color.rgb(0, 0, 255, 1);
    public final Color player2TextColor = Color.rgb(255, 0, 0, 1);

    @FXML
    private Pane parentPane;
    @FXML
    private Pane tablePane;
    @FXML
    private Pane gameViewPane;

    private PlayerTurnSlider nameOfplayerThatPlayTheTurn;
    private PointCounter totalPointsOfPlayer1;
    private PointCounter totalPointsOfPlayer2;

    List<GraphicalArchesBetweenDots> listOfGraphicalArches = new ArrayList<>();
    Map<Dot, GraphicalDot> mapOfDotsAndGraphicalDots = new HashMap<>();
    List<GraphicalBoxLabel> listOfLabels = new ArrayList<>();

    Group dotsGroup = new Group();
    Group archesBetweenDotsGroup = new Group();
    Group labelsGroup = new Group();

    String player1;
    String player2;
    String player1Token;
    String player2Token;

    private double parentWidth;
    private double parentHeight;
    private double widthTablePane;
    private double heightTablePane;
    private double widthGameViewPane;
    private double heightGameViewPane;
    private double border;
    private Game actualMatch;

    int N;
    int M;

    @FXML
    void initialize() {
        Image img = new Image(String.valueOf(StartPageController.class.getResource("sfondoCarta1.jpg")));
        BackgroundImage bImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        parentPane.setBackground(bGround);
    }

    public void initializePage(int n, int m, String player1, String player2) {
        actualMatch = new Game(n, player1, player2);
        N = actualMatch.getScoreboardSize()[0];
        M = actualMatch.getScoreboardSize()[1];

        this.player1 = player1;
        this.player2 = player2;
        player1Token = "A";
        player2Token = "B";

        try {
            initializeDotArchesAndLabels();
        } catch (BadDotDeclarationException e) {
            e.printStackTrace();
        }

        changePlayerNameOnTopLabel();


    }

    public void initializeDotArchesAndLabels() throws BadDotDeclarationException {
        drawGameField();
        createNxMGridOfGraphicalDots();
        createGraphicalArchesFromBackhandArches();
        createGraphicalLabelsFromBackhandBoxes();

        drawListOfNodeObjectAndInsertInAGroupTheList(listOfLabels, labelsGroup);
        drawListOfNodeObjectAndInsertInAGroupTheList(mapOfDotsAndGraphicalDots.values().stream().toList(), dotsGroup);
        drawListOfNodeObjectAndInsertInAGroupTheList(listOfGraphicalArches, archesBetweenDotsGroup);

        setClickLineListenerOnArches();
        setMouseHoverArchesListener();

    }

    private void drawGameField() {

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

        nameOfplayerThatPlayTheTurn = new PlayerTurnSlider();
        nameOfplayerThatPlayTheTurn.setLayoutX(0);
        nameOfplayerThatPlayTheTurn.setPrefHeight(17);
        nameOfplayerThatPlayTheTurn.setLayoutY(0);
        nameOfplayerThatPlayTheTurn.setPrefWidth(widthGameViewPane);
        nameOfplayerThatPlayTheTurn.setAlignment(Pos.CENTER);

        totalPointsOfPlayer1 = new PointCounter(17, widthGameViewPane / 4, 0, heightGameViewPane - 17, player1, player1BackgroundColor);

        totalPointsOfPlayer2 = new PointCounter(17, widthGameViewPane / 4, widthGameViewPane * 3 / 4, heightGameViewPane - 17, player2, player2BackgroundColor);

        gameViewPane.getChildren().add(nameOfplayerThatPlayTheTurn);
        gameViewPane.getChildren().add(totalPointsOfPlayer1);
        gameViewPane.getChildren().add(totalPointsOfPlayer2);

        gameViewPane.getChildren().add(tablePane);

        tablePane.setPrefWidth(widthTablePane);
        tablePane.setPrefHeight(heightTablePane);
        tablePane.setLayoutX(widthGameViewPane / 2 - widthTablePane / 2);
        tablePane.setLayoutY(heightGameViewPane / 2 - heightTablePane / 2);

    }

    private void setMouseHoverArchesListener() {
        listOfGraphicalArches.forEach(lin -> {
            if (!lin.isSelected()) {
                lin.setOnMouseEntered(ev -> {
                    tablePane.getScene().setCursor(Cursor.HAND);
                });
            }
        });
        listOfGraphicalArches.forEach(lin -> {
            if (!lin.isSelected()) {
                lin.setOnMouseExited(ev -> {
                    tablePane.getScene().setCursor(Cursor.DEFAULT);
                });
            }
        });
    }

    private void changePlayerNameOnTopLabel() {
        System.out.println(nameOfplayerThatPlayTheTurn.playerName + " " + actualMatch.getPlayerTurn());
        if (nameOfplayerThatPlayTheTurn.playerName == null || !nameOfplayerThatPlayTheTurn.playerName.equals(actualMatch.getPlayerTurn())) {
            if (actualMatch.getPlayerTurn().equals(player1)) {
                nameOfplayerThatPlayTheTurn.setTextFill(player1TextColor);

            } else {
                nameOfplayerThatPlayTheTurn.setTextFill(player2TextColor);
            }
            nameOfplayerThatPlayTheTurn.setPlayerAndSlide(actualMatch.getPlayerTurn());
        }
    }

    private void setClickLineListenerOnArches() {
        listOfGraphicalArches.forEach(lin -> lin.setOnMouseClicked(ev -> {
            actualMatch.playTurn(lin.backhandArch);
            changeColorIfArchIsSelected();
            lin.setOnMouseEntered(e -> {
                tablePane.getScene().setCursor(Cursor.DEFAULT);
            });
            listOfLabels.forEach(lab -> lab.setBoxSelected());
            totalPointsOfPlayer1.setPoint(actualMatch.getScorePlayer1());
            totalPointsOfPlayer2.setPoint(actualMatch.getScorePlayer2());
            System.out.println(actualMatch.checkVictory());
            if (actualMatch.checkVictory() != null) {
                victoryPopup(actualMatch.checkVictory());
            }
            changePlayerNameOnTopLabel();
            lin.setOnMouseClicked(null);
        }));

    }

    private void victoryPopup(String winner) {
        String loser;

        Pane popupPane=null;
        try {
            popupPane = FXMLLoader.load(getClass().getResource("winnerPopup.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        popupPane.setLayoutY(gameViewPane.getHeight()/2-popupPane.getPrefHeight()/2);
        popupPane.setLayoutX(gameViewPane.getWidth()/2-popupPane.getPrefWidth()/2);
        int winnerPoints;
        int loserPoints;

        Color winnerColorText;
        Color loserColorText;
        Color winnerColorColumn;


        Color loserColorColumn;
        Label winnerName = (Label)popupPane.lookup("#nameWinner");
        winnerName.setText(player1);
        winnerName.setTextFill(player1TextColor);
        Label loserName= (Label)popupPane.lookup("#nameLoser");
        loserName.setText(player2);
        loserName.setTextFill(player2TextColor);

        Rectangle firstPlayerRectangle= (Rectangle) popupPane.lookup("#columnPlayer1");
        firstPlayerRectangle.setFill(player1BackgroundColor);
        Rectangle secondPlayerRectangle= (Rectangle) popupPane.lookup("#columnPlayer2");
        secondPlayerRectangle.setFill(player2BackgroundColor);
        firstPlayerRectangle.setHeight(0);
        secondPlayerRectangle.setHeight(0);

        Label labelPointsPlayer1 = (Label)popupPane.lookup("#player1Points");
        labelPointsPlayer1.setText(actualMatch.getScorePlayer1()+"");
        labelPointsPlayer1.setTextFill(player1TextColor);
        Label labelPointsPlayer2 = (Label)popupPane.lookup("#player2Points");
        labelPointsPlayer2.setText(actualMatch.getScorePlayer2()+"");
        labelPointsPlayer2.setTextFill(player2TextColor);

        Circle cirlePlayer1 =(Circle)popupPane.lookup("#circlePlayer1");
        Circle cirlePlayer2 =(Circle)popupPane.lookup("#circlePlayer2");

        String winnerTitle;
        if(winner.equals(player1)){
            winnerColorText=player1TextColor;
            setRectanglesHeight(firstPlayerRectangle, secondPlayerRectangle, firstPlayerRectangle.getLayoutY(), 76, 46);
            winnerTitle ="HA VINTO "+ winner.toUpperCase(Locale.ROOT);
        }else if(winner.equals(player2)){
            winnerColorText=player2TextColor;
            setRectanglesHeight(secondPlayerRectangle, firstPlayerRectangle, firstPlayerRectangle.getLayoutY(), 76, 46);
            winnerTitle = "HA VINTO "+winner.toUpperCase(Locale.ROOT);
        }else{
            winnerColorText=Color.GREEN;
            setRectanglesHeight(secondPlayerRectangle, firstPlayerRectangle, firstPlayerRectangle.getLayoutY(), 60, 60);
            winnerTitle = winner.toUpperCase(Locale.ROOT);
        }

        labelPointsPlayer1.setLayoutY(firstPlayerRectangle.getLayoutY() + firstPlayerRectangle.getHeight()/2-labelPointsPlayer1.getPrefHeight()/2);
        labelPointsPlayer2.setLayoutY(secondPlayerRectangle.getLayoutY() + secondPlayerRectangle.getHeight()/2-labelPointsPlayer2.getPrefHeight()/2);
        cirlePlayer1.setLayoutY(firstPlayerRectangle.getLayoutY()+firstPlayerRectangle.getHeight()/2);
        cirlePlayer2.setLayoutY(secondPlayerRectangle.getLayoutY()+secondPlayerRectangle.getHeight()/2);

        Label winnerLabel = (Label)popupPane.lookup("#winnerTitle");
        winnerLabel.setText(winnerTitle);
        winnerLabel.setTextFill(winnerColorText);

        Button newMatchButton= (Button) popupPane.lookup("#newMatch");
        newMatchButton.setOnMouseClicked(event -> newMatch());

        gameViewPane.getChildren().add(popupPane);

    }

    private void setRectanglesHeight(Rectangle firstPlayerRectangle, Rectangle secondPlayerRectangle, double layoutY, int i, int i2) {
        firstPlayerRectangle.setLayoutY(layoutY - i);
        secondPlayerRectangle.setLayoutY(secondPlayerRectangle.getLayoutY() - i2);
        firstPlayerRectangle.setHeight(i);
        secondPlayerRectangle.setHeight(i2);
    }


    public void newMatch(){
        FXMLLoader fxmlLoader = new FXMLLoader(GamePageController.class.getResource("startScene.fxml"));
        Stage stage = (Stage)gameViewPane.getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        scene.getStylesheets().add(GamePageController.class.getResource("style.css") + "");
        stage.setScene(scene);
        stage.show();

        StartPageController startPage = fxmlLoader.getController();


    }

    private void changeColorIfArchIsSelected() {
        listOfGraphicalArches.forEach(lin -> {
            if (lin.isSelected()) {
                lin.setOpacity(1);
            } else {
                lin.setOpacity(0.2);
            }
        });
    }


    private void createGraphicalArchesFromBackhandArches() throws BadDotDeclarationException {
        List<Arch> arches = actualMatch.getScoreboard().totalArches;
        listOfGraphicalArches = new ArrayList<>();

        arches.forEach(e -> {
            listOfGraphicalArches.add(new GraphicalArchesBetweenDots(mapOfDotsAndGraphicalDots.get(e.getFirstDot()), mapOfDotsAndGraphicalDots.get(e.getSecondDot()), e));
        });

        listOfGraphicalArches.forEach(e -> {
            if (e.isSelected()) {
                e.setOpacity(1);
            } else {
                e.setOpacity(0.2);
            }
        });
    }


    private void createNxMGridOfGraphicalDots() throws BadDotDeclarationException {
        double max = Math.max(N, M);
        border = widthTablePane * 0.05;
        double distanceX = (widthTablePane - border * 2) / (max);
        double distanceY = (heightTablePane - border * 2) / (max);
        double circleSize = distanceX / 20;

        mapOfDotsAndGraphicalDots = new HashMap<>();
        for (int i = 0; i < N + 1; i++) {
            for (int j = 0; j < M + 1; j++) {
                double tempX = border + (distanceX * i);
                double tempY = border + (distanceY * j);
                mapOfDotsAndGraphicalDots.put(new Dot(i, j), new GraphicalDot(new Dot(i, j), tempX, tempY, circleSize));
            }
        }
    }

    private void createGraphicalLabelsFromBackhandBoxes() {
        listOfLabels = new ArrayList<>();
        Box[][] boxes = actualMatch.getScoreboard().getBoxes();
        Arrays.stream(boxes).forEach(line -> Arrays.stream(line).forEach(cell -> {
            GraphicalDot firstDot = mapOfDotsAndGraphicalDots.get(cell.getDots()[0]);
            GraphicalDot thirdDot = mapOfDotsAndGraphicalDots.get(cell.getDots()[2]);
            GraphicalDot[] firstAndThirdGraphicalDots = {firstDot, thirdDot};
            listOfLabels.add(new GraphicalBoxLabel(firstAndThirdGraphicalDots, cell, player1Token, player2Token));
        }));
    }

    private void drawListOfNodeObjectAndInsertInAGroupTheList(List<? extends Node> graphicalList, Group graphicalGroup) {
        graphicalGroup.getChildren().clear();
        graphicalList.forEach(e -> graphicalGroup.getChildren().add(e));
        tablePane.getChildren().add(graphicalGroup);
    }
}
