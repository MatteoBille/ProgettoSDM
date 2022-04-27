package units.progettosdm.backhandclass;

import units.progettosdm.projectExceptions.BadArchDeclarationException;
import units.progettosdm.projectExceptions.BadBoardSizeDeclarationException;
import units.progettosdm.projectExceptions.BadDotDeclarationException;
import units.progettosdm.projectExceptions.SelectArchAlreadySelectedException;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {
    public List<Arch> totalArches = new ArrayList<>();
    public int boardWidthSize;
    public int boardHeightSize;

    private final Box[][] boxes;

    public Scoreboard(int widthSize, int heightSize) throws BadBoardSizeDeclarationException, BadArchDeclarationException {
        if ((widthSize < 2) || (heightSize < 2)) {
            throw new BadBoardSizeDeclarationException("Cannot create a board with a size less than 2");
        }
        this.boardWidthSize = widthSize;
        this.boardHeightSize = heightSize;
        boxes = new Box[widthSize][heightSize];
        createAllArches();
        createAllBoxes();
    }

    public void selectArch(Arch selectedArch) {
        try {
            int index = totalArches.indexOf(selectedArch);
            totalArches.get(index).setArchSelected();
        } catch (SelectArchAlreadySelectedException e) {
            e.printStackTrace();
        }
    }

    public int checkClosedBoxAndGivePoints(String playerName, int playerNumber) {
        int count = 0;
        for (Box[] box : boxes) {
            for (Box value : box) {
                if (value.checkClosedBox() && value.getPlayerBox() == null) {
                    count++;
                    value.setPlayerBoxAndSetBoxCharacter(playerName, playerNumber);
                }
            }
        }
        return count;
    }

    public void createAllBoxes() throws BadArchDeclarationException {
        for (int i = 0; i < boardWidthSize; i++) {
            for (int j = 0; j < boardHeightSize; j++) {
                boxes[i][j] = new Box(i, j);
                Arch[] boxSides = boxes[i][j].getBoxSides();
                Arch[] arches = new Arch[4];
                for (int k = 0; k < boxSides.length; k++) {
                    Arch tempArch = boxSides[k];
                    int index = totalArches.indexOf(tempArch);
                    arches[k] = totalArches.get(index);
                }
                boxes[i][j].setArches(arches);
            }
        }
    }

    public void createAllArches() {
        totalArches = new ArrayList<>();
        for (int i = 0; i < boardWidthSize + 1; i++) {
            for (int j = 0; j < boardHeightSize + 1; j++) {

                try {
                    Dot tempDot = new Dot(i, j);
                    if (i + 1 < boardWidthSize + 1) {
                        try {
                            totalArches.add(new Arch(tempDot, new Dot(i + 1, j)));
                        } catch (BadDotDeclarationException e) {
                            e.printStackTrace();
                        }
                    }
                    if (j + 1 < boardHeightSize + 1) {
                        try {
                            totalArches.add(new Arch(tempDot, new Dot(i, j + 1)));
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
        for (int i = 0; i < boardWidthSize; i++) {
            for (int j = 0; j < boardHeightSize; j++) {
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
                "gridSize=" + boardWidthSize + "x" + boardHeightSize + "\n" +
                "boxes=" + "\n" + getBoxesToString() + "\n" +
                '}';
    }

    public Box[][] getBoxes() {
        return boxes;
    }
}
