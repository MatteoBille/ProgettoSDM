package units.progettosdm.backhandclass;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DotTest {

    @Test
    void dotInizializationIsCorrect() {
        Dot dot = new Dot(2, 3);
        assertArrayEquals(new int[]{2, 3}, dot.getDotIndex());
    }

    @Test
    void dotIsEqualWorkProperly() {
        Dot dot1 = new Dot(2, 3);
        Dot dot2 = new Dot(2, 3);
        Dot dot3 = new Dot(3, 3);

        assertEquals(dot1, dot2);
        assertEquals(dot2, dot1);
        assertNotEquals(dot2, dot3);
    }
}