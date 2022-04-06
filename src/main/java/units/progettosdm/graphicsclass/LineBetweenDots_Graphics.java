package units.progettosdm.graphicsclass;

import javafx.scene.shape.Rectangle;

class LineBetweenDots_Graphics extends Rectangle {
    DotGraphics dot1;
    DotGraphics dot2;
    String direction;
    private boolean selected = false;

    public LineBetweenDots_Graphics(DotGraphics dot1, DotGraphics dot2) {

        if (dot1.getCenterX() == dot2.getCenterX()) {
            setLayoutX(dot1.getCenterX() - dot1.getRadius() / 2);
            setLayoutY(dot1.getCenterY() + dot1.getRadius() * 2);
            setHeight(dot2.getCenterY() - dot1.getCenterY() - dot1.getRadius() * 4);
            setWidth(dot1.getRadius());
            direction = "vertical";

        } else if (dot1.getCenterY() == dot2.getCenterY()) {

            setLayoutX(dot1.getCenterX() + dot1.getRadius() * 2);
            setLayoutY(dot1.getCenterY() - dot1.getRadius() / 2);
            setHeight(dot1.getRadius());
            setWidth(dot2.getCenterX() - dot1.getCenterX() - dot1.getRadius() * 4);
            direction = "horizontal";
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

    public void setSelected() {
        this.selected = true;
    }

    public boolean isSelected() {
        return selected;
    }
}
