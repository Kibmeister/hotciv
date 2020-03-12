package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.WinnerStrategy;

public class BetaWinnerStrategy implements WinnerStrategy {
    public Player winner;
    @Override
    public Player getWinner(GameImpl game) {
        winner = Player.RED;
        for(Position p: game.getCities().keySet()){ //løper gjennom alle cities
            CityImpl c = game.getCityAt(p);

            if(c.getOwner() == winner){  // hvis eier av byen ikke er  LIK vinner
                winner = c.getOwner();    // setter eier av byen som vinner
            }
        }
        return winner;
    }
}
