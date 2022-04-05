package units.progettosdm.backhandclass;

import java.util.Arrays;

public class Dot {
    final private int[] dotIndex = new int[2];

    @Override
    public boolean equals(Object o) {
        System.out.println(this+" == "+o);
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dot dot = (Dot) o;

        return Arrays.equals(dotIndex, dot.dotIndex);
    }

    @Override
    public int hashCode() {
        return this.dotIndex[0]*1000+this.dotIndex[1]*1;
    }

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
