package units.progettosdm.backhandclass;

public class Box {
    private Arch[] arches = new Arch[4];
    private char playerBox;

    public Box(Arch[] arches) {
        for (int i = 0; i < arches.length; i++) {
            this.arches[i] = arches[i];
        }
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
