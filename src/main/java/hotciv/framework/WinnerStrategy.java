package hotciv.framework;

import hotciv.standard.GameImpl;

public interface WinnerStrategy {
    public Player getWinner(GameImpl game);

    void setWinner(Player battleWinner);
}
