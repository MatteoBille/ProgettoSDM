package units.progettosdm.backhandclass;

public class Game {
    private final String playerName1;
    private final String playerName2;
    private int scorePlayer1;
    private int scorePlayer2;
    private String playerTurn;
    private final Scoreboard scoreboard;

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public Game(int n, String playerName1, String playerName2) {
        this.playerName1 = playerName1;
        this.playerName2 = playerName2;
        playerTurn = throwCoin();
        this.scoreboard = new Scoreboard(n);
    }

    //farò un count inizializzato a zero e che si incrementa di uno ogni volta che invoco playTurn() e quando arrivo a count= (N+1)x2N faccio checkVictory()
    public void playTurn(Arch arch) {
        scoreboard.selectArch(arch);
        int temp = scoreboard.checkPoint(playerTurn);
        if (temp>0) {
            if (playerTurn.equals(playerName1)) {
                scorePlayer1++;
            } else {
                scorePlayer2++;
            }
        }
        if (temp==0) {
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
        if (scorePlayer1 > ((scoreboard.gridSize*scoreboard.gridSize) / 2)) {
            return playerName1;
        } else if (scorePlayer2 > ((scoreboard.gridSize*scoreboard.gridSize) / 2)) {
            return playerName2;
        } else {
            return "Pareggio";
        }
    }
    public int getScorePlayer1() {
        return scorePlayer1;
    }

    public void setScorePlayer1(int scorePlayer1) {
        this.scorePlayer1 = scorePlayer1;
    }

    public int getScorePlayer2() {
        return scorePlayer2;
    }

    public void setScorePlayer2(int scorePlayer2) {
        this.scorePlayer2 = scorePlayer2;
    }
}