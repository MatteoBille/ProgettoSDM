package units.progettosdm.backhandclass;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreboardTest {
    @Test
    void correctNumberOfArches(){
        int n=2;
        Scoreboard board = new Scoreboard(n);
        board.setArch();
        assertEquals((n+1)*2*n, board.totalArches.size());
    }
}