package units.progettosdm.backhandclass;

public class Game {
    private String playerName1;
    private String playerName2;
    private int scorePlayer1;
    private int scorePlayer2;
    private String playerTurn;

    public Game(int n, String playerName1, String playerName2){
        throwCoin();
        Scoreboard scoreboard = new Scoreboard(n);
        this.playerName1=playerName1;
        this.playerName2=playerName2;
    }

    public void playTurn(){
        //Scoreboard.selectArch(dot1,dot2,playerName1);
    }

    public String throwCoin() {
        if(Math.random() < 0.5) {return playerName1;}
        return playerName2;
    }

    public void checkVictory(){

    }
}
