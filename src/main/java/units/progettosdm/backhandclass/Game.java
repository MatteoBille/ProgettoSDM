package units.progettosdm.backhandclass;

public class Game {
    private String playerName1;
    private String playerName2;
    private int scorePlayer1;
    private int scorePlayer2;
    private String playerTurn;
    private Scoreboard scoreboard;

    public Game(int n, String playerName1, String playerName2) {
        this.playerName1 = playerName1;
        this.playerName2 = playerName2;
        playerTurn = throwCoin();
        this.scoreboard = new Scoreboard(n);
        scoreboard.setArch();
        scoreboard.setBox();
    }

    //farò un count inizializzato a zero e che si incrementa di uno ogni volta che invoco playTurn() e quando arrivo a count= (N+1)x2N faccio checkVictory()
    public void playTurn(Arch arch) {
        scoreboard.selectArch(arch);
        scoreboard.checkPoint(playerTurn);
        if (scoreboard.checkPoint(playerTurn)) {
            if (playerTurn.equals(playerName1)) {
                scorePlayer1++;
            } else {
                scorePlayer2++;
            }
        }
        if (!scoreboard.checkPoint(playerTurn)) {
            if (playerTurn.equals(playerName1)) {
                playerTurn = playerName2;
            } else {
                playerTurn = playerName1;
            }
        }
    }

    public String throwCoin() {
        if (Math.random() < 0.5) {
            return playerName1;
        }
        return playerName2;
    }

    public void checkVictory() {

    }
}