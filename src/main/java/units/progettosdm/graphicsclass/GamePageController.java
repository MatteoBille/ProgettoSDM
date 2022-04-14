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
import javafx.stage.Stage;
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

    private Label nameOfplayerThatPlayTheTurn;
    private PointCounter totalPointsOfPlayer1;
    private PointCounter totalPointsOfPlayer2;

    List<LineBetweenDotsGraphics> listOfGraphicalArches = new ArrayList<>();
    Map<Dot, DotGraphics> mapOfDotsAndGraphicalDots = new HashMap<>();
    List<PointLabelGraphics> listOfGraphicalBoxes = new ArrayList<>();

    Group dotsGroup = new Group();
    Group archesBetweenDotsGroup = new Group();
    Group labelsGroup = new Group();

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
        Image img = new Image(String.valueOf(StartPageController.class.getResource("sfondoCarta1.jpg")));
        BackgroundImage bImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        parentPane.setBackground(bGround);
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

        nameOfplayerThatPlayTheTurn = new Label();
        nameOfplayerThatPlayTheTurn.setLayoutX(0);
        nameOfplayerThatPlayTheTurn.setPrefHeight(17);
        nameOfplayerThatPlayTheTurn.setLayoutY(0);
        nameOfplayerThatPlayTheTurn.setPrefWidth(widthGameViewPane);
        nameOfplayerThatPlayTheTurn.setAlignment(Pos.CENTER);

        totalPointsOfPlayer1 = new PointCounter(17,widthGameViewPane / 4,0,heightGameViewPane - 17,player1, player1BackgroundColor);

        totalPointsOfPlayer2 = new PointCounter(17,widthGameViewPane / 4,widthGameViewPane *3 / 4 ,heightGameViewPane - 17,player2, player2BackgroundColor);

        gameViewPane.getChildren().add(nameOfplayerThatPlayTheTurn);
        gameViewPane.getChildren().add(totalPointsOfPlayer1);
        gameViewPane.getChildren().add(totalPointsOfPlayer2);

        gameViewPane.getChildren().add(tablePane);

        tablePane.setPrefWidth(widthTablePane);
        tablePane.setPrefHeight(heightTablePane);
        tablePane.setLayoutX(widthGameViewPane / 2 - widthTablePane / 2);
        tablePane.setLayoutY(heightGameViewPane / 2 - heightTablePane / 2);

    }

    private void setMouseHoverListener() {
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

    private void changePlayerTurnOnTopLabel() {
        if(match.getPlayerTurn().equals(player1)){
            nameOfplayerThatPlayTheTurn.setTextFill(player1TextColor);
            nameOfplayerThatPlayTheTurn.setOpacity(1);
        }else{
            nameOfplayerThatPlayTheTurn.setTextFill(player2TextColor);
        }
        nameOfplayerThatPlayTheTurn.setText("Turno di " + match.getPlayerTurn());
    }

    private void setClickLineListener() {
        listOfGraphicalArches.forEach(lin -> lin.setOnMouseClicked(ev -> {
            match.playTurn(lin.backhandArch);
            refreshGraphicLines();
            lin.setOnMouseEntered(e -> {
                tablePane.getScene().setCursor(Cursor.DEFAULT);
            });
            listOfGraphicalBoxes.forEach(lab->lab.setBoxSelected());
            totalPointsOfPlayer1.setPoint(match.getScorePlayer1());
            totalPointsOfPlayer2.setPoint(match.getScorePlayer2());
            System.out.println(match.checkVictory());
            if(match.checkVictory()!=null){
                setVictory(match.checkVictory());
            }
            changePlayerTurnOnTopLabel();
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
        listOfGraphicalArches.forEach(lin -> {
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

        changePlayerTurnOnTopLabel();


    }

    private void setLines() throws BadDotDeclarationException {
        List<Arch> arches = match.getScoreboard().totalArches;
        listOfGraphicalArches = new ArrayList<>();

        arches.forEach(e -> {
            listOfGraphicalArches.add(new LineBetweenDotsGraphics(mapOfDotsAndGraphicalDots.get(e.getFirstDot()), mapOfDotsAndGraphicalDots.get(e.getSecondDot()), e));
        });

        listOfGraphicalArches.forEach(e -> {
            if (e.isSelected()) {
                e.setOpacity(1);
            } else {
                e.setOpacity(0.2);
            }
        });
    }

    private void drawLines() {

        archesBetweenDotsGroup.getChildren().clear();
        listOfGraphicalArches.forEach(e -> archesBetweenDotsGroup.getChildren().add(e));
        tablePane.getChildren().add(archesBetweenDotsGroup);
    }

    private void setDots(int n, int m) throws BadDotDeclarationException {
        double max = Math.max(n, m);
        border = widthTablePane * 0.05;
        double distanceX = (widthTablePane - border * 2) / (max);
        double distanceY = (heightTablePane - border * 2) / (max);
        double circleSize = distanceX / 20;

        mapOfDotsAndGraphicalDots = new HashMap<>();

        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < m + 1; j++) {
                double tempX = border + (distanceX * i);
                double tempY = border + (distanceY * j);
                mapOfDotsAndGraphicalDots.put(new Dot(i, j), new DotGraphics(new Dot(i, j), tempX, tempY, circleSize));
            }
        }
    }

    private void drawDots() {
        dotsGroup.getChildren().clear();
        mapOfDotsAndGraphicalDots.values().forEach(e -> dotsGroup.getChildren().add(e));
        tablePane.getChildren().add(dotsGroup);
    }

    private void setLabels(){
        listOfGraphicalBoxes = new ArrayList<>();
        Box[][] boxes= match.getScoreboard().getBoxes();
        Arrays.stream(boxes).forEach(line-> Arrays.stream(line).forEach(cell->{
            DotGraphics firstDot = mapOfDotsAndGraphicalDots.get(cell.getDots()[0]);
            DotGraphics thirdDot = mapOfDotsAndGraphicalDots.get(cell.getDots()[2]);
            DotGraphics[] firstAndThirdGraphicalDots = {firstDot,thirdDot};
            listOfGraphicalBoxes.add(new PointLabelGraphics(firstAndThirdGraphicalDots,cell));
        }));
    }

    private void drawLabels() {
        labelsGroup.getChildren().clear();
        listOfGraphicalBoxes.forEach(e -> labelsGroup.getChildren().add(e));
        tablePane.getChildren().add(labelsGroup);
    }
}
