package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.WinnerStrategy;

public class AlphaWinnerStrategy implements WinnerStrategy {

    @Override
    public Player getWinner(GameImpl game) {
        if(game.getAge() == -3000){
            return Player.RED;
        }
        else {
            return null;
        }
    }

    @Override
    public void setWinner(Player battleWinner) {

    }
}
