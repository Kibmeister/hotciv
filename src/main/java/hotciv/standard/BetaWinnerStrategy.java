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
            System.out.println(c.getOwner().toString());
            if(!c.getOwner().equals(winner)){             // hvis eier av byen ikke er  LIK vinner
                winner = c.getOwner();              // setter eier av byen som vinner
                System.out.println("hvis eiernen ikke er lik winneren: " + c.getOwner().toString());
            }
            System.out.println("dette er hvis utenfor if'en: " + c.getOwner().toString());
        }
        System.out.println("Dette er winneren: " + winner);
        return winner;
    }

    @Override
    public void setWinner(Player battleWinner) {

    }
}
