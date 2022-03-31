package units.progettosdm.backhandclass;

import units.progettosdm.projectExceptions.BadArchDeclarationException;
import units.progettosdm.projectExceptions.SelectArchAlreadySelectedException;

public class Arch {
    Dot dot1;
    Dot dot2;
    boolean selected;

    public Arch(Dot dot1, Dot dot2) throws BadArchDeclarationException {
        if(dot1.equals(dot2)){
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
        if(this.selected){
            throw new SelectArchAlreadySelectedException("Cannot select an Arch that is already selected");
        }
        this.selected = true;
    }

}
