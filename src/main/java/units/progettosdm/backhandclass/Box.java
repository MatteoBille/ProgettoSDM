package units.progettosdm.backhandclass;

import java.util.Arrays;

public class Box {

    public void setArches(Arch[] arches) {
        this.arches = arches;
    }

    private Arch[] arches = new Arch[4];
    private char playerBox;
    private int x, y;
    private Dot[] dots = new Dot[4];

    public Box(int x, int y) {
        this.x = x;
        this.y = y;
        dots[0] = new Dot(x,y);
        dots[1] = new Dot(x+1,y);
        dots[2] = new Dot(x+1,y+1);
        dots[3] = new Dot(x,y+1);
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
        Arrays.stream(boxSides).forEach(e-> Arrays.stream(e).forEach(System.out::println));
        return boxSides;
    }

    public char getPlayerBox() {
        return playerBox;
    }

    public boolean checkClosedBox() {
        for (int i = 0; i < arches.length; i++) {
            if (!arches[i].getArchStatus()) {
                return false;
            }
        }
        return true;
    }
}
