package units.progettosdm.backhandclass;

public class Arch {
    int[] point1;
    int[] point2;
    boolean selected;

    public Arch(int[] point1, int[] point2) throws BadArchDeclarationException {
        if(point1[0]==point2[0] && point1[1]==point2[1]){
            throw new BadArchDeclarationException("Same node connection");
        }
        if(point1.length!=2 || point2.length!=2){
            throw new BadArchDeclarationException("Error in dots length");
        }
        this.point1 = point1;
        this.point2 = point2;
        this.selected = false;
    }

    public boolean getArchStatus() {
        return selected;
    }

    public void setArchSelected() {
        this.selected = true;
    }

    public int[] getPoint1Size() {
        return point1;
    }

    public int[] getPoint2Size() {
        return point2;
    }
}
