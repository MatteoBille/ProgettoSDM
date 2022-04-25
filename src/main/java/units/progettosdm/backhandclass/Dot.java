package units.progettosdm.backhandclass;

import units.progettosdm.projectExceptions.BadDotDeclarationException;

import java.util.Arrays;

public class Dot {
    final private int[] dotIndex = new int[2];

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dot dot = (Dot) o;

        return Arrays.equals(dotIndex, dot.dotIndex);
    }

    @Override
    public int hashCode() {
        return this.dotIndex[0] * 1000 + this.dotIndex[1];
    }

    public Dot(int x, int y) throws BadDotDeclarationException {
        if ((x < 0) || (y < 0)) {
            throw new BadDotDeclarationException("Dots cannot have negative coordinates");
        }
        this.dotIndex[0] = x;
        this.dotIndex[1] = y;
    }

    public int[] getDotIndex() {
        return dotIndex;
    }

    @Override
    public String toString() {
        return "(" + dotIndex[0] + ", " + dotIndex[1] + ")";

    }
}
