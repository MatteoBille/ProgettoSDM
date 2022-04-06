package units.progettosdm.backhandclass;

import units.progettosdm.projectExceptions.BadArchDeclarationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Scoreboard {
    public List<Arch> totalArches = new ArrayList<>();
    private int gridSize;


    private Box[][] boxes;

    /*public Scoreboard(Box[][] boxes, int gridSize) {
        this.gridSize = gridSize;
        this.boxes = new Box[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                this.boxes[i][j] = boxes[i][j];
            }
        }
    }*/
    public Scoreboard(int gridSize) {
        this.gridSize = gridSize;
        boxes = new Box[gridSize][gridSize];
    }


    /*public void selectArch(Dot dot1, Dot dot2, ) {
    }*/


    public boolean checkPoint() {
        //return boxes.checkClosedBox();
        return true;
    }

    public void setBoxes() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                boxes[i][j] = new Box(i, j);
                Dot[][] boxSides = boxes[i][j].getCouple();
                Arch[] arches = new Arch[4];
                try {

                    for (int k = 0; k < boxSides.length; k++) {
                        Arch tempArch = new Arch(boxSides[k][0], boxSides[k][1]);
                        int index = totalArches.indexOf(tempArch);
                        arches[k] = totalArches.get(index);
                    }
                    boxes[i][j].setArches(arches);
                } catch (BadArchDeclarationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setArch() {
        totalArches = new ArrayList<>();
        for (int i = 0; i < gridSize + 1; i++) {
            for (int j = 0; j < gridSize + 1; j++) {
                Dot dot = new Dot(i, j);
                try {
                    if (i + 1 < gridSize + 1) {
                        totalArches.add(new Arch(dot, new Dot(i + 1, j)));
                    }
                    if (j + 1 < gridSize + 1) {
                        totalArches.add(new Arch(dot, new Dot(i, j + 1)));
                    }
                } catch (BadArchDeclarationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getBoxesToString() {
        String output = "";
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                output+=boxes[i][j].checkClosedBox()?"X ":"O ";
            }
            output+="\n";
        }
        return output;
    }

    @Override
    public String toString() {
        return "Scoreboard{" +"\n"+
                "totalArches=" + totalArches +"\n"+
                "gridSize=" + gridSize+"x"+gridSize +"\n"+
                "boxes=" +"\n"+ getBoxesToString() +"\n"+
                '}';
    }

    public Box[][] getBoxes() {
        return boxes;
    }
}
