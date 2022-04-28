package units.progettosdm.graphicsclass;

import javafx.scene.shape.Rectangle;
import units.progettosdm.backendclass.Arch;

class GraphicalArchesBetweenDots extends Rectangle {

    private final GraphicalDot dot1;
    private final GraphicalDot dot2;
    private final String direction;
    private final Arch backendArch;

    public GraphicalArchesBetweenDots(GraphicalDot dot1, GraphicalDot dot2, Arch backendArch) {
        this.backendArch = backendArch;
        if (dot1.getCenterX() == dot2.getCenterX()) {
            setLayoutX(dot1.getCenterX() - dot1.getRadius() / 2);
            setLayoutY(dot1.getCenterY() + dot1.getRadius() * 2);
            setHeight(dot2.getCenterY() - dot1.getCenterY() - dot1.getRadius() * 4);
            setWidth(dot1.getRadius());
            this.direction = "vertical";

        } else {

            setLayoutX(dot1.getCenterX() + dot1.getRadius() * 2);
            setLayoutY(dot1.getCenterY() - dot1.getRadius() / 2);
            setHeight(dot1.getRadius());
            setWidth(dot2.getCenterX() - dot1.getCenterX() - dot1.getRadius() * 4);
            this.direction = "horizontal";
        }

        this.dot1 = dot1;
        this.dot2 = dot2;
    }

    @Override
    public String toString() {
        return "GraphicalArchesBetweenDots{" +
                "xStart=" + getLayoutX() +
                ", yStart=" + getLayoutY() +
                ",width=" + getWidth() +
                ",height=" + getHeight() +
                ",direction=" + direction +
                '}';
    }

    public Arch getBackendArch() {
        return backendArch;
    }

    public boolean isSelected() {
        return backendArch.getArchStatus();
    }
}
