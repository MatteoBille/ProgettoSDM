package units.progettosdm.backendclass;

import org.junit.jupiter.api.Test;
import units.progettosdm.projectExceptions.BadArchDeclarationException;
import units.progettosdm.projectExceptions.BadDotDeclarationException;
import units.progettosdm.projectExceptions.SelectArchAlreadySelectedException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoxTest {

    @Test
    void closingBox() {
        Arch[] arch = new Arch[4];
        try {
            Dot dot1 = new Dot(0, 0);
            Dot dot2 = new Dot(0, 1);

            arch[0] = new Arch(dot1, dot2);
            arch[0].setArchSelected();
            assertTrue(arch[0].getArchStatus());
        } catch (BadArchDeclarationException | SelectArchAlreadySelectedException | BadDotDeclarationException e) {
            e.printStackTrace();
        }
        try {
            Dot dot3 = new Dot(0, 1);
            Dot dot4 = new Dot(1, 1);

            arch[1] = new Arch(dot3, dot4);
            arch[1].setArchSelected();
            assertTrue(arch[1].getArchStatus());
        } catch (BadArchDeclarationException | SelectArchAlreadySelectedException | BadDotDeclarationException e) {
            e.printStackTrace();
        }
        try {
            Dot dot5 = new Dot(1, 1);
            Dot dot6 = new Dot(1, 0);

            arch[2] = new Arch(dot5, dot6);
            arch[2].setArchSelected();
            assertTrue(arch[2].getArchStatus());
        } catch (BadArchDeclarationException | SelectArchAlreadySelectedException | BadDotDeclarationException e) {
            e.printStackTrace();
        }
        try {
            Dot dot7 = new Dot(1, 0);
            Dot dot8 = new Dot(0, 0);

            arch[3] = new Arch(dot7, dot8);
            arch[3].setArchSelected();
            assertTrue(arch[3].getArchStatus());
        } catch (BadArchDeclarationException | SelectArchAlreadySelectedException | BadDotDeclarationException e) {
            e.printStackTrace();
        }

        Box box = new Box(0, 0);
        box.setArches(arch);
        assertTrue(box.checkClosedBox());

    }

    @Test
    void testBoxSides() throws BadArchDeclarationException {
        Box box = new Box(0, 0);
        box.getBoxSides();
    }
}
