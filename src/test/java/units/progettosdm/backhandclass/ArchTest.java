package units.progettosdm.backhandclass;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import units.progettosdm.projectExceptions.BadArchDeclarationException;
import units.progettosdm.projectExceptions.BadDotDeclarationException;
import units.progettosdm.projectExceptions.SelectArchAlreadySelectedException;

import static org.junit.jupiter.api.Assertions.*;

class ArchTest {


    @ParameterizedTest
    @CsvSource(
            {"0,0,1,0",
                    "1,1,1,3",
                    "2,2,2,2",
                    "0,2,4,2"}
    )
    void SelectArch() throws BadDotDeclarationException {
        Dot dot1 = new Dot(0, 0);
        Dot dot2 = new Dot(1, 0);
        Arch arch;
        try {
            arch = new Arch(dot1, dot2);
            arch.setArchSelected();
            assertTrue(arch.getArchStatus());
        } catch (BadArchDeclarationException | SelectArchAlreadySelectedException e) {
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @CsvSource(
            {"0,0,0,0",
                    "1,1,1,1",
                    "2,2,2,2"}
    )
    void ArchNodeMustBeDifferent(int a1, int b1, int a2, int b2) throws BadDotDeclarationException {
        Dot dot1 = new Dot(a1, b1);
        Dot dot2 = new Dot(a2, b2);

        BadArchDeclarationException badArchDeclarationException = assertThrows(BadArchDeclarationException.class, () -> new Arch(dot1, dot2));

        String expectedMessage = "Same node connection";
        String actualMessage = badArchDeclarationException.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void CannotSelectAnAlreadySelectedArch() throws BadDotDeclarationException {
        Dot dot1 = new Dot(2, 0);
        Dot dot2 = new Dot(2, 1);

        try {
            final Arch arch = new Arch(dot1, dot2);
            SelectArchAlreadySelectedException selectArchAlreadySelectedException = assertThrows(SelectArchAlreadySelectedException.class, () -> {
                        arch.setArchSelected();
                        arch.setArchSelected();
                    }
            );

            String expectedMessage = "Cannot select an Arch that is already selected";
            String actualMessage = selectArchAlreadySelectedException.getMessage();

            assertTrue(actualMessage.contains(expectedMessage));

        } catch (BadArchDeclarationException e) {
            e.printStackTrace();
        }

    }

    @Test
    void archToStringWorkProperly() throws BadDotDeclarationException, BadArchDeclarationException {
        Dot dot1 = new Dot(1, 0);
        Dot dot2 = new Dot(0, 0);
        Arch arch = new Arch(dot1, dot2);
        String expectedOutput = "Dot{dotIndex=[1, 0]}---->Dot{dotIndex=[0, 0]}";
        assertEquals(expectedOutput, arch.toString());
    }
}