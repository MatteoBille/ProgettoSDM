package units.progettosdm.backhandclass;

import org.junit.jupiter.api.Test;
import units.progettosdm.projectExceptions.BadArchDeclarationException;
import units.progettosdm.projectExceptions.BadDotDeclarationException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreboardTest {
    @Test
    void correctNumberOfArches(){
        int n=2;
        Scoreboard board = new Scoreboard(n,n);
        board.setArch();
        board.totalArches.forEach(System.out::println);
        assertEquals((n+1)*2*n, board.totalArches.size());
    }

    @Test
    void notNullInitializationOfBoxes(){
        int n=2;
        Scoreboard board = new Scoreboard(n,n);
        board.setArch();
        board.setBox();
        assertNotNull(board.getBoxes());
    }


    @Test
    void notNullScoreboardInitialization(){
        Scoreboard board = new Scoreboard(2,2);
        board.setArch();
        board.setBox();
        System.out.println(board);
        assertNotNull(board);
    }

    @Test
    void selectArchTest() throws BadDotDeclarationException, BadArchDeclarationException {
        Scoreboard board = new Scoreboard(2,2);
        board.setArch();
        board.setBox();
        Dot dot1 = new Dot(0,0);
        Dot dot2 = new Dot(0,1);
        board.selectArch(new Arch(dot1,dot2));
        int index = board.totalArches.indexOf(new Arch(dot1,dot2));
        assertTrue(board.totalArches.get(index).selected);

    }

    @Test
    void closedBoxTest() throws BadDotDeclarationException, BadArchDeclarationException {
        Scoreboard board = new Scoreboard(2,2);
        board.setArch();
        board.setBox();
        Dot dot1 = new Dot(0,0);
        Dot dot2 = new Dot(0,1);
        Dot dot3 = new Dot(1,1);
        Dot dot4 = new Dot(1,0);
        board.selectArch(new Arch(dot1,dot2));
        assertEquals(0,board.checkPoint("Mario",1 ));
        board.selectArch(new Arch(dot2,dot3));
        assertEquals(0,board.checkPoint("Leandro", 2));
        board.selectArch(new Arch(dot4,dot3));
        assertEquals(0,board.checkPoint("Mario",1 ));
        board.selectArch(new Arch(dot1,dot4));

        Box box = board.getBoxes()[0][0];
        Arch[] arch = box.getArches();
        for(int i=0;i<4;i++){
            System.out.println(arch[i]+" "+box.getArchStatusByIndex(i));
        }
        assertEquals(1,board.checkPoint("Leandro", 2));
        System.out.println(box.getPlayerBox());
    }

    @Test
    void checkDoublePoint() throws BadArchDeclarationException, BadDotDeclarationException {
        Scoreboard scoreboard = new Scoreboard(2,2);
        Dot dot1 = new Dot(0, 0);
        Dot dot2 = new Dot(0, 1);
        Dot dot3 = new Dot(0, 2);
        Dot dot4 = new Dot(1, 0);
        Dot dot5 = new Dot(1, 1);
        Dot dot6 = new Dot(1, 2);
        List<Arch> arches = new ArrayList<>();
        arches.add(new Arch(dot1, dot2));
        arches.add(new Arch(dot5, dot6));
        arches.add(new Arch(dot6, dot3));
        arches.add(new Arch(dot2, dot3));
        arches.add(new Arch(dot1, dot4));
        arches.add(new Arch(dot4, dot5));
        arches.add(new Arch(dot2, dot5));
        for(Arch arch : arches){
            scoreboard.selectArch(arch);
        }
        assertEquals(2,scoreboard.checkPoint("A",1 ));
    }
}