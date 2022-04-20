package units.progettosdm.backhandclass;

import units.progettosdm.projectExceptions.BadArchDeclarationException;
import units.progettosdm.projectExceptions.BadBoardSizeDeclarationException;
import units.progettosdm.projectExceptions.BadDotDeclarationException;
import units.progettosdm.projectExceptions.SelectArchAlreadySelectedException;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {
    public List<Arch> totalArches = new ArrayList<>();
    public int gridNSize;
    public int gridMSize;

    private final Box[][] boxes;

    public Scoreboard(int nSize,int mSize) throws BadBoardSizeDeclarationException {
        if ((nSize<2)&&(mSize<2)){
            throw new BadBoardSizeDeclarationException("Cannot create a board with a size less than 2");
        }
        this.gridNSize = nSize;
        this.gridMSize = mSize;
        boxes = new Box[nSize][mSize];
        setArch();
        setBox();
    }

    public void selectArch(Arch selectedArch) {
        try {
            int index = totalArches.indexOf(selectedArch);
            totalArches.get(index).setArchSelected();
        } catch (SelectArchAlreadySelectedException e) {
            e.printStackTrace();
        }
    }

    public int checkPoint(String playerName, int playerNumber) {
        int count = 0;
        for (Box[] box : boxes) {
            for (int j = 0; j < box.length; j++) {
                if (box[j].checkClosedBox() && box[j].getPlayerBox() == null) {
                    count++;
                    box[j].setPlayerBox(playerName, playerNumber);
                }
            }
        }
        return count;
    }

    public void setBox() {
        for (int i = 0; i < gridNSize; i++) {
            for (int j = 0; j < gridMSize; j++) {
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
        for (int i = 0; i < gridNSize + 1; i++) {
            for (int j = 0; j < gridMSize + 1; j++) {

                try {
                    Dot dot = new Dot(i, j);
                    if (i + 1 < gridNSize + 1) {
                        try {
                            totalArches.add(new Arch(dot, new Dot(i + 1, j)));
                        } catch (BadDotDeclarationException e) {
                            e.printStackTrace();
                        }
                    }
                    if (j + 1 < gridMSize + 1) {
                        try {
                            totalArches.add(new Arch(dot, new Dot(i, j + 1)));
                        } catch (BadDotDeclarationException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (BadArchDeclarationException | BadDotDeclarationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getBoxesToString() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < gridNSize; i++) {
            for (int j = 0; j < gridMSize; j++) {
                output.append(boxes[i][j].checkClosedBox() ? boxes[i][j].getPlayerBox() : "O ");
            }
            output.append("\n");
        }
        return output.toString();
    }


    @Override
    public String toString() {
        return "Scoreboard{" + "\n" +
                "totalArches=" + totalArches + "\n" +
                "gridSize=" + gridNSize + "x" + gridMSize + "\n" +
                "boxes=" + "\n" + getBoxesToString() + "\n" +
                '}';
    }

    public Box[][] getBoxes() {
        return boxes;
    }
}
