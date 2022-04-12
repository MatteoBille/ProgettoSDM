package units.progettosdm.backhandclass;

import javafx.scene.shape.Arc;
import org.junit.jupiter.api.Test;
import units.progettosdm.projectExceptions.BadArchDeclarationException;
import units.progettosdm.projectExceptions.BadDotDeclarationException;
import units.progettosdm.projectExceptions.SelectArchAlreadySelectedException;

import static org.junit.jupiter.api.Assertions.*;

class ScoreboardTest {
    @Test
    void correctNumberOfArches(){
        int n=2;
        Scoreboard board = new Scoreboard(n);
        board.setArch();
        board.totalArches.forEach(System.out::println);
        assertEquals((n+1)*2*n, board.totalArches.size());
    }

    @Test
    void notNullInitializationOfBoxes(){
        int n=2;
        Scoreboard board = new Scoreboard(n);
        board.setArch();
        board.setBox();
        assertNotNull(board.getBoxes());
    }


    @Test
    void notNullScoreboardInitialization(){
        Scoreboard board = new Scoreboard(2);
        board.setArch();
        board.setBox();
        System.out.println(board);
        assertNotNull(board);
    }

    @Test
    void selectArchTest() throws BadDotDeclarationException, BadArchDeclarationException, SelectArchAlreadySelectedException {
        Scoreboard board = new Scoreboard(2);
        board.setArch();
        board.setBox();
        Dot dot1 = new Dot(0,0);
        Dot dot2 = new Dot(0,1);
        board.selectArch(new Arch(dot1,dot2),"Mario");
        int index = board.totalArches.indexOf(new Arch(dot1,dot2));
        assertTrue(board.totalArches.get(index).selected);

    }
}