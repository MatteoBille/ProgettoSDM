package units.progettosdm.backhandclass;

import org.junit.jupiter.api.Test;
import units.progettosdm.projectExceptions.BadArchDeclarationException;
import units.progettosdm.projectExceptions.BadDotDeclarationException;

import java.util.ArrayList;
import java.util.List;

public class PlayMatchTest {
    @Test
    void playMatch() throws BadDotDeclarationException {
        Scoreboard scoreboard;
        String playerName1 = "A ";
        String playerName2 = "B ";
        Game newGame = new Game(2, playerName1, playerName2);
        scoreboard = newGame.getScoreboard();
        List<Arch> totalArches = scoreboard.totalArches;
        Dot dot1 = new Dot(0, 0);
        Dot dot2 = new Dot(0, 1);
        Dot dot3 = new Dot(0, 2);
        Dot dot4 = new Dot(1, 0);
        Dot dot5 = new Dot(1, 1);
        Dot dot6 = new Dot(1, 2);
        Dot dot7 = new Dot(2, 0);
        Dot dot8 = new Dot(2, 1);
        Dot dot9 = new Dot(2, 2);

        List<Arch> arch = new ArrayList<>();
        try {
            arch.add(new Arch(dot1, dot2));
            arch.add(new Arch(dot6, dot3));
            arch.add(new Arch(dot4, dot7));
            arch.add(new Arch(dot7, dot8));
            arch.add(new Arch(dot6, dot9));
            arch.add(new Arch(dot2, dot3));
            arch.add(new Arch(dot1, dot4));
            arch.add(new Arch(dot4, dot5));
            arch.add(new Arch(dot2, dot5));
            arch.add(new Arch(dot5, dot8));
            arch.add(new Arch(dot8, dot9));
            arch.add(new Arch(dot5, dot6));

        } catch (BadArchDeclarationException e) {
            e.printStackTrace();
        }
        arch.forEach(archIndex -> {
            newGame.playTurn(archIndex);
            System.out.println(scoreboard);
        });
    }
}
