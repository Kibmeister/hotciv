package hotciv.standard;

import hotciv.framework.Player;
import hotciv.framework.WinnerStrategy;

import java.util.ArrayList;
import java.util.HashMap;

public class ThreeAttacksWinnerStrategy implements WinnerStrategy {
    // private ArrayList<Player> battleWinners;
    private HashMap<Player, Integer> winners;
    private Player winner;

    public ThreeAttacksWinnerStrategy() {
        // this.battleWinners = new ArrayList<>();
        this.winners = new HashMap<>();
        //this.winner = Player.RED;
    }

    @Override
    public Player getWinner(GameImpl game) {
        if (winner == null) {
            this.winner = Player.GREEN;
        }
        return winner;
    }

    @Override
    public void setWinner(Player battleWinner) {

        /*battleWinners.add(battleWinner);*/

        if (winners.containsKey(battleWinner)) { // se om spilleren har vunnet før
            int currentNumberOfWins = winners.get(battleWinner); // isåfall, finn ut hvor mange wins han har
            winners.put(battleWinner, currentNumberOfWins + 1); // legg til antall wins + leg til et win
        } else {
            winners.put(battleWinner, 1); // hvis han ikke har vunnet før, leg til winnere og en win
        }

        calculateWinner(battleWinner);



       /* boolean threeWinsOrMore = battleWinners.size() >= 3;
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

        }*/


    }

    public void calculateWinner(Player battleWinner) {
        boolean weHaveAWinner = winners.get(battleWinner) >= 3;
        if (weHaveAWinner) {
            winner = battleWinner;
        }
    }

    @Override
    public void roundEnded(GameImpl game) {

    }

}

