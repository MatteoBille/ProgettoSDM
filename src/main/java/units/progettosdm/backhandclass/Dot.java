package units.progettosdm.backhandclass;

import java.util.Arrays;

public class Dot {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dot dot = (Dot) o;
        return Arrays.equals(dotIndex, dot.dotIndex);
    }


    final private int[] dotIndex = new int[2];

    public Dot(int x1, int x2) {
        this.dotIndex[0] = x1;
        this.dotIndex[1] = x2;
    }

    public int[] getDotIndex() {
        return dotIndex;
    }

    @Override
    public String toString() {
        return "Dot{" +
                "dotIndex=" + Arrays.toString(dotIndex) +
                '}';
    }
}
