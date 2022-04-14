package units.progettosdm.graphicsclass;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import units.progettosdm.backhandclass.Arch;
import units.progettosdm.backhandclass.Box;
import units.progettosdm.backhandclass.Dot;
import units.progettosdm.backhandclass.Game;
import units.progettosdm.projectExceptions.BadDotDeclarationException;

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
    private boolean playerHasChanged;
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
        player1Token="A";
        player2Token="B";

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
        System.out.println(nameOfplayerThatPlayTheTurn.playerName+" "+actualMatch.getPlayerTurn());
        if (nameOfplayerThatPlayTheTurn.playerName==null || !nameOfplayerThatPlayTheTurn.playerName.equals(actualMatch.getPlayerTurn())) {
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
        }));

    }

    private void victoryPopup(String winner) {
        Popup victoryPopup = new Popup();
        Pane victoryPopupPane = new Pane();
        victoryPopupPane.setPrefWidth(parentWidth * 0.5);
        victoryPopupPane.setPrefHeight(parentHeight * 0.5);
        victoryPopupPane.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 1), new CornerRadii(0.5), new Insets(0.0))));
        victoryPopupPane.setStyle("-fx-border-color: black");

        Label victoryMessage = new Label("HA VINTO " + winner.toUpperCase(Locale.ROOT));
        victoryMessage.setPrefWidth(victoryPopupPane.getPrefWidth());
        victoryMessage.setPrefHeight(victoryPopupPane.getPrefHeight());
        victoryMessage.setAlignment(Pos.CENTER);
        victoryMessage.setFont(new Font(30));


        victoryPopupPane.getChildren().add(victoryMessage);
        victoryPopup.getContent().add(victoryPopupPane);
        victoryPopup.show(parentPane.getScene().getWindow());
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
            listOfLabels.add(new GraphicalBoxLabel(firstAndThirdGraphicalDots, cell,player1Token,player2Token));
        }));
    }

    private void drawListOfNodeObjectAndInsertInAGroupTheList(List<? extends Node> graphicalList, Group graphicalGroup) {
        graphicalGroup.getChildren().clear();
        graphicalList.forEach(e -> graphicalGroup.getChildren().add(e));
        tablePane.getChildren().add(graphicalGroup);
    }
}
