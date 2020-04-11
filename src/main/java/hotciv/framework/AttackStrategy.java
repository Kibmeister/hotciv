package hotciv.framework;

import hotciv.standard.GameImpl;
import javafx.geometry.Pos;

public interface AttackStrategy {
    boolean battleOutcome(Position from, Position to, GameImpl game);

}
