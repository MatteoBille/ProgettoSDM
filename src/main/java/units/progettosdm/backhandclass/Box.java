package units.progettosdm.backhandclass;

import units.progettosdm.projectExceptions.BadDotDeclarationException;

import java.util.Arrays;

public class Box {

    public void setArches(Arch[] arches) {
        this.arches = arches;
    }

    public Arch[] getArches() {
        return arches;
    }

    private Arch[] arches = new Arch[4];
    private String playerBox;
    private int x, y;
    private Dot[] dots = new Dot[4];

    public Box(int x, int y) {
        this.x = x;
        this.y = y;
        try {
            dots[0] = new Dot(x,y);
            dots[1] = new Dot(x+1,y);
            dots[2] = new Dot(x+1,y+1);
            dots[3] = new Dot(x,y+1);
        } catch (BadDotDeclarationException e) {
            e.printStackTrace();
        }
        playerBox = null;

        /*for (int i = 0; i < arches.length; i++) {
            this.arches[i] = arches[i];
        }*/
    }
    public Dot[][] getCouple(){
        Dot[][] boxSides = new Dot[4][2];
        boxSides[0]=new Dot[]{dots[0],dots[1]};
        boxSides[1]=new Dot[]{dots[1],dots[2]};
        boxSides[2]=new Dot[]{dots[3],dots[2]};
        boxSides[3]=new Dot[]{dots[0],dots[3]};
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
    public void setPlayerBox(String playerBox) {
        this.playerBox = playerBox;
    }

    public boolean getArchStatusByIndex(int i){
        return arches[i].getArchStatus();
    }
}
