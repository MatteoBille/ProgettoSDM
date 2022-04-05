package units.progettosdm.graphicsclass;

import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.*;

public class GamePageController {

    @FXML
    private Pane tablePane;
    @FXML
    private Pane parentPane;
    @FXML
    private Stage stage;

    List<LineBetweenDots_Graphics> lines = new ArrayList<>();
    Map<String, DotGraphics> dots = new HashMap<>();
    Group dotsPoint = new Group();
    Group tableLines = new Group();

    int N;
    int M;

    @FXML
    void initialize() {
    }
    private double parentWidth;
    private double parentHeight;
    private double width;
    private double height;
    private double border;

    public void initializeGrid(int n, int m) {
        N=n;
        M=m;
        width = tablePane.getWidth();
        height = tablePane.getHeight();

        drawField();
        dots = setDots(N, M);
        lines = setLines(N, M);

        setClickLineListener();
        setMouseHoverListener();
    }

    private void drawField() {
        Group borderLine = new Group();
        tablePane.getChildren().clear();

        parentWidth=parentPane.getWidth();
        parentHeight=parentPane.getHeight();

        double dimension = Math.min(parentHeight,parentWidth);


        width=dimension*0.7;
        height=dimension*0.7;

        System.out.println(dimension);
        tablePane.setPrefWidth(width);
        tablePane.setPrefHeight(height);
        tablePane.setLayoutX(parentWidth/2-width/2);
        tablePane.setLayoutY(parentHeight/2-height/2);
        Line line1 = new Line(0,0,0,height);
        Line line2 = new Line(0,0,width,0);
        Line line3 = new Line(width,0,width,height);
        Line line4 = new Line(0,height,width,height);
        borderLine.getChildren().add(line1);
        borderLine.getChildren().add(line2);
        borderLine.getChildren().add(line3);
        borderLine.getChildren().add(line4);

        borderLine.getChildren().forEach(e->((Line)e).setStrokeWidth(width*0.01));
        tablePane.getChildren().add(borderLine);

    }

    private void setMouseHoverListener() {
        lines.forEach(lin->lin.setOnMouseEntered(ev->{
            tablePane.getScene().setCursor(Cursor.HAND);
        }));
        lines.forEach(lin->lin.setOnMouseExited(ev->{
            tablePane.getScene().setCursor(Cursor.DEFAULT);
        }));
    }

    private void setClickLineListener() {
        lines.forEach(lin->lin.setOnMouseClicked(ev->{
            lin.setOpacity(1);
            lin.setOnMouseEntered(e->{tablePane.getScene().setCursor(Cursor.DEFAULT);});
        }));

    }

    public void initializePage(int n, int m,Stage stage) {

        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            drawField();
            setDots(N,M);
            setLines(N,M);
            setClickLineListener();
            setMouseHoverListener();
        });

        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            drawField();
            setDots(N,M);
            setLines(N,M);
            System.out.println(tablePane.getChildren());
            setClickLineListener();
            setMouseHoverListener();
        });

        initializeGrid(n,m);
    }

    class LineBetweenDots_Graphics extends Rectangle{
        DotGraphics dot1;
        DotGraphics dot2;
        String direction;

        public LineBetweenDots_Graphics(DotGraphics dot1, DotGraphics dot2) {

            if(dot1.getCenterX()==dot2.getCenterX()){
                setLayoutX(dot1.getCenterX()-dot1.getRadius()/2);
                setLayoutY(dot1.getCenterY()+dot1.getRadius()*2);
                setHeight(dot2.getCenterY()-dot1.getCenterY()-dot1.getRadius()*4);
                setWidth(dot1.getRadius());
                direction ="vertical";

            }else if(dot1.getCenterY()==dot2.getCenterY()){

                setLayoutX(dot1.getCenterX()+dot1.getRadius()*2);
                setLayoutY(dot1.getCenterY()-dot1.getRadius()/2);
                setHeight(dot1.getRadius());
                setWidth(dot2.getCenterX()-dot1.getCenterX()-dot1.getRadius()*4);
                direction ="horizontal";
            }

            this.dot1 = dot1;
            this.dot2 = dot2;

            System.out.println(this);
        }

        @Override
        public String toString() {
            return "LineBetweenDots_Graphics{" +
                    "xStart=" + getLayoutX() +
                    ", yStart=" + getLayoutY() +
                    ",width=" + getWidth() +
                    ",height=" + getHeight() +
                    ",direction=" + direction +
                    '}';
        }
    }

    class DotGraphics extends Circle{

        public DotGraphics(double x, double y, double radius) {
            super(x,y,radius);
        }
    }

    private ArrayList<LineBetweenDots_Graphics> setLines(int n, int m) {
        lines = new ArrayList<>();
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < m + 1; j++) {
                DotGraphics d = dots.get(i+","+j);
                if (d != null) {
                    if (i + 1 < n + 1) {
                        System.out.println(i+","+j+"->"+(i+1)+","+j);
                        lines.add(new LineBetweenDots_Graphics(d,dots.get((i+1)+","+j)));
                    }
                    if (j + 1 < m + 1) {
                        System.out.println(i+","+j+"->"+i+","+(j+1));
                        lines.add(new LineBetweenDots_Graphics(d,dots.get(i+","+(j+1))));
                    }
                }
            }
        }
        lines.forEach(e->e.setOpacity(0.2));
        drawLines();
        return (ArrayList<LineBetweenDots_Graphics>) lines;
    }

    private void drawLines() {

        tableLines.getChildren().clear();
        System.out.println(tableLines.getChildren().size());
        lines.forEach(e->tableLines.getChildren().add(e));
        System.out.println(tableLines.getChildren().size());
        tablePane.getChildren().add(tableLines);
    }

    private Map<String, DotGraphics> setDots(int n, int m) {
        double max = Math.max(n, m);
        border = width * 0.05;
        double distanceX = (width - border * 2) / (max);
        double distanceY = (height - border * 2) / (max);
        double circleSize = distanceX / 20;

        dots = new HashMap<>();

        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < m + 1; j++) {
                double tempX = border + (distanceX * i);
                double tempY = border + (distanceY * j);
                dots.put(i+","+j, new DotGraphics(tempX, tempY,circleSize));
            }
        }
        drawDots();
        return dots;
    }

    private void drawDots() {
        dotsPoint.getChildren().clear();
        System.out.println(dotsPoint.getChildren().size());
        dots.values().forEach(e->dotsPoint.getChildren().add(e));
        System.out.println(dotsPoint.getChildren().size());
        tablePane.getChildren().add(dotsPoint);
    }
}
