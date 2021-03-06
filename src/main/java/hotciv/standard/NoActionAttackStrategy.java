package hotciv.standard;

import hotciv.framework.AttackStrategy;
import hotciv.framework.Game;
import hotciv.framework.Position;

public class NoActionAttackStrategy implements AttackStrategy {

    @Override
    public boolean battleOutcome(Position from, Position to, GameImpl game) {
        return true;
    }
}
