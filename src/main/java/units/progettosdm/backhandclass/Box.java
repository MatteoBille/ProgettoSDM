package units.progettosdm.backhandclass;

import units.progettosdm.projectExceptions.BadDotDeclarationException;


public class Box {

    public void setArches(Arch[] arches) {
        this.arches = arches;
    }

    public Arch[] getArches() {
        return arches;
    }

    private Arch[] arches = new Arch[4];
    private String playerBox;


    private String playerBoxCharacter;


    private final Dot[] dots = new Dot[4];

    public Box(int x, int y) {
        try {
            dots[0] = new Dot(x, y);
            dots[1] = new Dot(x + 1, y);
            dots[2] = new Dot(x + 1, y + 1);
            dots[3] = new Dot(x, y + 1);
        } catch (BadDotDeclarationException e) {
            e.printStackTrace();
        }
        playerBox = null;

    }

    public Dot[][] getCouple() {
        Dot[][] boxSides = new Dot[4][2];
        boxSides[0] = new Dot[]{dots[0], dots[1]};
        boxSides[1] = new Dot[]{dots[1], dots[2]};
        boxSides[2] = new Dot[]{dots[3], dots[2]};
        boxSides[3] = new Dot[]{dots[0], dots[3]};
        return boxSides;
    }

    public String getPlayerBox() {
        return playerBox;
    }

    public boolean checkClosedBox() {
        for (Arch arch : arches) {
            if (!arch.getArchStatus()) return false;
        }
        return true;
    }

    public void setPlayerBox(String playerBox, int playerNumber) {
        this.playerBox = playerBox;
        playerBoxCharacter = playerNumber == 1 ? "B" : "R";
    }

    public boolean getArchStatusByIndex(int i) {
        return arches[i].getArchStatus();
    }

    public Dot[] getDots() {
        return dots;
    }

    public String getPlayerBoxCharacter() {
        return playerBoxCharacter;
    }


}
