package units.progettosdm.graphicsclass;

import javafx.scene.shape.Circle;
import units.progettosdm.backendclass.Dot;

class GraphicalDot extends Circle {
    Dot dot;

    public GraphicalDot(Dot dot, double x, double y, double radius) {
        super(x, y, radius);
        this.dot = dot;
    }

}
