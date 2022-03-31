package units.progettosdm.backhandclass;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DotTest {

    @Test
    void dotInizializationIsCorrect(){
        Dot dot = new Dot(2,3);
        assertArrayEquals(new int[]{2,3},dot.getDotIndex());
    }

}