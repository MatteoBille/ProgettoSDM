package units.progettosdm.backhandclass;

import org.junit.jupiter.api.Test;
import units.progettosdm.projectExceptions.BadArchDeclarationException;
import units.progettosdm.projectExceptions.BadDotDeclarationException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DotTest {

    @Test
    void dotInizializationIsCorrect() {
        Dot dot= null;
        try {
            dot = new Dot(2, 3);
        } catch (BadDotDeclarationException e) {
            e.printStackTrace();
        }
        assertArrayEquals(new int[]{2, 3}, dot.getDotIndex());
    }

    @Test
    void dotIsEqualWorkProperly() throws BadDotDeclarationException {
        Dot dot1 = new Dot(2, 3);
        Dot dot2 = new Dot(2, 3);
        Dot dot3 = new Dot(3, 3);

        assertEquals(dot1, dot2);
        assertEquals(dot2, dot1);
        assertNotEquals(dot2, dot3);
    }

    @Test
    void dotCannotBeNegativeException() {
        int x1=-1;
        int x2=0;
        BadDotDeclarationException badDotDeclarationException = assertThrows(BadDotDeclarationException.class, () -> new Dot(x1,x2));

        String expectedMessage = "Dots cannot have negative coordinates";
        String actualMessage = badDotDeclarationException.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void dotToStringWorkProperly() throws BadDotDeclarationException {
        Dot dot = new Dot(1,0);
        String expectedOutput="Dot{dotIndex=[1, 0]}";
        assertEquals(expectedOutput,dot.toString());
    }

}
