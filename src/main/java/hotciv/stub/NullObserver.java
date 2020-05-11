package hotciv.stub;

import hotciv.framework.GameObserver;
import hotciv.framework.Player;
import hotciv.framework.Position;

import java.util.Observer;

public class NullObserver implements GameObserver {
    @Override
    public void worldChangedAt(Position pos) {

    }

    @Override
    public void turnEnds(Player nextPlayer, int age) {

    }

    @Override
    public void tileFocusChangedAt(Position position) {

    }
}