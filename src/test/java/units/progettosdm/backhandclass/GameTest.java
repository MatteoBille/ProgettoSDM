package units.progettosdm.backhandclass;

import org.junit.jupiter.api.Test;
import units.progettosdm.projectExceptions.BadArchDeclarationException;
import units.progettosdm.projectExceptions.BadDotDeclarationException;
import units.progettosdm.projectExceptions.SelectArchAlreadySelectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    @Test
    void playTurnMethod() throws BadDotDeclarationException, BadArchDeclarationException, SelectArchAlreadySelectedException {
        Scoreboard scoreboard;

        String playerName1 = "Mario";
        String playerName2 = "Giovanni";
        String playerTurn;
        Game newGame = new Game(3, playerName1, playerName2);
        scoreboard = newGame.getScoreboard();
        List<Arch> totalArches = scoreboard.totalArches;
        Dot dot1 = new Dot(0,0);
        Dot dot2 = new Dot(1,0);
        Arch arch = new Arch(dot1,dot2);
        newGame.playTurn(arch);
        int index = totalArches.indexOf(arch);
        assertTrue(totalArches.get(index).getArchStatus());
    }
    @Test
    void checkVictoryWorkProperly(){
        int gridSize = 10;
        String playerName1 = "mario";
        String playerName2 = "Giovanni";
        Game game = new Game(gridSize, playerName1, playerName2);
        game.setScorePlayer1(20);
        game.setScorePlayer2(60);
        game.checkVictory();
    }
}
