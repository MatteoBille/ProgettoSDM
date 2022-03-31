package units.progettosdm.backhandclass;

import java.util.Arrays;
import java.util.Iterator;

public class Dot {

    private int[] dotIndex= new int[2];

    public Dot(int x1, int x2) {
        this.dotIndex[0] = x1;
        this.dotIndex[1] = x2;
    }

    public int[] getDotIndex() {
        return dotIndex;
    }
}
