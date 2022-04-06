package units.progettosdm.backhandclass;

import units.progettosdm.projectExceptions.BadArchDeclarationException;
import units.progettosdm.projectExceptions.SelectArchAlreadySelectedException;

import java.util.Arrays;

public class Arch {
    Dot dot1;
    Dot dot2;
    boolean selected;

    public Arch(Dot dot1, Dot dot2) throws BadArchDeclarationException {
        if (dot1.equals(dot2)) {
            throw new BadArchDeclarationException("Same node connection");
        }
        this.dot1 = dot1;
        this.dot2 = dot2;
        this.selected = false;
    }


    public boolean getArchStatus() {
        return selected;
    }

    public void setArchSelected() throws SelectArchAlreadySelectedException {
        if (this.selected) {
            throw new SelectArchAlreadySelectedException("Cannot select an Arch that is already selected");
        }
        this.selected = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arch arch = (Arch) o;
        return this.dot1.equals(arch.dot1) && this.dot2.equals(arch.dot2);
    }

}
