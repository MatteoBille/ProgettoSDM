package units.progettosdm.backhandclass;

import units.progettosdm.projectExceptions.BadDotDeclarationException;


public class Box {

    private Arch[] arches = new Arch[4];
    private String playerBox;


    private String boxCharacter;


    private final Dot[] boxVertexes = new Dot[4];

    public Box(int x, int y) {
        try {
            boxVertexes[0] = new Dot(x, y);
            boxVertexes[1] = new Dot(x + 1, y);
            boxVertexes[2] = new Dot(x + 1, y + 1);
            boxVertexes[3] = new Dot(x, y + 1);
        } catch (BadDotDeclarationException e) {
            e.printStackTrace();
        }
        playerBox = null;

    }

    public Dot[][] getCouple() {
        Dot[][] boxSides = new Dot[4][2];
        boxSides[0] = new Dot[]{boxVertexes[0], boxVertexes[1]};
        boxSides[1] = new Dot[]{boxVertexes[1], boxVertexes[2]};
        boxSides[2] = new Dot[]{boxVertexes[3], boxVertexes[2]};
        boxSides[3] = new Dot[]{boxVertexes[0], boxVertexes[3]};
        return boxSides;
    }
    public void setArches(Arch[] arches) {
        this.arches = arches;
    }

    public Arch[] getArches() {
        return arches;
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
        boxCharacter = playerNumber == 1 ? "B" : "R";
    }

    public boolean getArchStatusByIndex(int i) {
        return arches[i].getArchStatus();
    }

    public Dot[] getBoxVertexes() {
        return boxVertexes;
    }

    public String getBoxCharacter() {
        return boxCharacter;
    }


}
