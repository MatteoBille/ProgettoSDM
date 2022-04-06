package units.progettosdm.graphicsclass;

import javafx.scene.shape.Circle;
import units.progettosdm.backhandclass.Dot;

class DotGraphics extends Circle {
        Dot dot;

    public DotGraphics(Dot dot, double x, double y, double radius) {
        super(x, y, radius);
        this.dot = dot;
    }

}
