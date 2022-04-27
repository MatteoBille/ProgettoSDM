package units.progettosdm.backhandclass;

import units.progettosdm.projectExceptions.BadDotDeclarationException;

import java.util.Arrays;

public class Dot {
    final private int[] dotIndexes = new int[2];

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dot dot = (Dot) o;

        return Arrays.equals(dotIndexes, dot.dotIndexes);
    }

    @Override
    public int hashCode() {
        return this.dotIndexes[0] * 1000 + this.dotIndexes[1];
    }

    public Dot(int x, int y) throws BadDotDeclarationException {
        if ((x < 0) || (y < 0)) {
            throw new BadDotDeclarationException("Dots cannot have negative coordinates");
        }
        this.dotIndexes[0] = x;
        this.dotIndexes[1] = y;
    }

    public int[] getDotIndexes() {
        return dotIndexes;
    }

    @Override
    public String toString() {
        return "(" + dotIndexes[0] + ", " + dotIndexes[1] + ")";
    }
}
