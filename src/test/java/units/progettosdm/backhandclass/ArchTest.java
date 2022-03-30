package units.progettosdm.backhandclass;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ArchTest {

    @Test
    void SelectArch() {
        int[] dot1 = {0, 0};
        int[] dot2 = {1, 0};
        Arch arch;
        try {
            arch = new Arch(dot1, dot2);
            arch.setArchSelected();
            assertEquals(true, arch.getArchStatus());
        } catch (BadArchDeclarationException e) {
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @CsvSource(
            {"0,0,0,0",
                    "1,1,1,1",
                    "2,2,2,2"}
    )
    void ArchNodeMustBeDifferent(int a1, int b1, int a2, int b2) {
        int[] dot1 = {0, 0};
        int[] dot2 = {0, 0};

        BadArchDeclarationException badArchDeclarationException = assertThrows(BadArchDeclarationException.class, () -> {
            new Arch(dot1, dot2);
        });

        String expectedMessage = "Same node connection";
        String actualMessage = badArchDeclarationException.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void archCannotHaveLengthDifferentThanTwo() {
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            int size1 = rand.nextInt(10)+1;
            int size2 = rand.nextInt(10)+1;

            System.out.println(size1);
            System.out.println(size2);
            int[] dot1 = rand.ints(size1, 0, 10).toArray();
            int[] dot2 = rand.ints(size2, 0, 10).toArray();
            Arrays.stream(dot1).forEach(e-> System.out.print(e+" "));
            System.out.println();
            Arrays.stream(dot2).forEach(e-> System.out.print(e+" "));
            System.out.println();



            if (size1 != 2 || size2 != 2) {
                BadArchDeclarationException badArchDeclarationException = assertThrows(BadArchDeclarationException.class, () -> {
                    new Arch(dot1, dot2);
                });

                String expectedMessage= "Error in dots length";
                String actualMessage = badArchDeclarationException.getMessage();
                assertTrue(actualMessage.contains(expectedMessage));
            }


        }
    }
}