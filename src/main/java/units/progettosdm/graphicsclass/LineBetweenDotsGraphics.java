package units.progettosdm.graphicsclass;

import javafx.scene.shape.Rectangle;
import units.progettosdm.backhandclass.Arch;
import units.progettosdm.projectExceptions.SelectArchAlreadySelectedException;

class LineBetweenDotsGraphics extends Rectangle {
    DotGraphics dot1;
    DotGraphics dot2;
    String direction;
    private boolean selected = false;
    Arch backhandArch;

    public LineBetweenDotsGraphics(DotGraphics dot1, DotGraphics dot2, Arch backhandArch) {
        this.backhandArch = backhandArch;
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
    }

    @Override
    public String toString() {
        return "LineBetweenDotsGraphics{" +
                "xStart=" + getLayoutX() +
                ", yStart=" + getLayoutY() +
                ",width=" + getWidth() +
                ",height=" + getHeight() +
                ",direction=" + direction +
                '}';
    }

    public void setSelected() {
        try {
            backhandArch.setArchSelected();
        } catch (SelectArchAlreadySelectedException e) {
            e.printStackTrace();
        }
    }

    public boolean isSelected() {
        return backhandArch.getArchStatus();
    }
}
