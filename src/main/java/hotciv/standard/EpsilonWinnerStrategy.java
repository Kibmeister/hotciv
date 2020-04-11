package hotciv.standard;

import hotciv.framework.Player;
import hotciv.framework.WinnerStrategy;

import java.util.ArrayList;

public class EpsilonWinnerStrategy implements WinnerStrategy {
    private ArrayList<Player> battleWinners;
    private Player winner;

    public EpsilonWinnerStrategy (){
        this.battleWinners = new ArrayList<>();
    }
    @Override
    public Player getWinner(GameImpl game) { return winner; }

    @Override
    public void setWinner(Player battleWinner) {

        battleWinners.add(battleWinner);
        boolean threeWinsOrMore = battleWinners.size() >= 3;
        int redWinner = 0;
        int blueWinner = 0;

        if(threeWinsOrMore){
            for(Player p: battleWinners){
                Player potentialWinner = Player.RED;
                if (p == potentialWinner){
                    redWinner ++;
                    if(redWinner == 3){
                        winner = Player.RED;
                    }
                } else {
                    blueWinner ++;
                    if(blueWinner == 3){
                        winner = Player.BLUE;
                    }
                }
            }

        }


    }

}

