package units.progettosdm.backhandclass;

public class Game {
    private String playerName1;
    private String playerName2;
    private int scorePlayer1;
    private int scorePlayer2;
    private String playerTurn;
    private Scoreboard scoreboard;
    private int n;

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
        boolean temp = scoreboard.checkPoint(playerTurn);
        if (temp) {
            if (playerTurn.equals(playerName1)) {
                scorePlayer1++;
            } else {
                scorePlayer2++;
            }
        }
        if (!temp) {
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

    public String checkVictory() {
        if (scorePlayer1 > ((scoreboard.gridSize) / 2)) {
            return playerName1;
        } else if (scorePlayer2 > ((scoreboard.gridSize) / 2)) {
            return playerName2;
        } else {
            return "Pareggio";
        }
    }
}