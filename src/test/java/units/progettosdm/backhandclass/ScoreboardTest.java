package units.progettosdm.backhandclass;

import org.junit.jupiter.api.Test;

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
        board.setBoxes();
        assertNotNull(board.getBoxes());
    }


    @Test
    void notNullScoreboardInitialization(){
        Scoreboard board = new Scoreboard(2);
        board.setArch();
        board.setBoxes();
        System.out.println(board);
        assertNotNull(board);
    }
}