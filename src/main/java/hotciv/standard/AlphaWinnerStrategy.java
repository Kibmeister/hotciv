package hotciv.standard;

import hotciv.framework.Player;
import hotciv.framework.WinnerStrategy;

public class AlphaWinnerStrategy implements WinnerStrategy {


    @Override
    public Player getWinner(int gameAge) {
        if(gameAge == -3000){
            return Player.RED;
        }
        else {
            return null;
        }
    }
}
