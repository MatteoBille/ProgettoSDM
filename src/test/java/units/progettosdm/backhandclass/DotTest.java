package units.progettosdm.backhandclass;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DotTest {

    @Test
    void dotInizializationIsCorrect(){
        Dot dot = new Dot(2,3);
        assertArrayEquals(new int[]{2,3},dot.getDotIndex());
    }

    @Test
    void dotIsEqualWorkProperly(){
        Dot dot1 = new Dot(2,3);
        Dot dot2 = new Dot(2,3);
        Dot dot3 = new Dot(3,3);

        assertTrue(dot1.equals(dot2));
        assertTrue(dot2.equals(dot1));
        assertFalse(dot2.equals(dot3));
        assertFalse(dot2.equals(null));
    }
}