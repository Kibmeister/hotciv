package hotciv.stub;

import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;

public class StubUnit3 implements Unit {
    @Override
    public String getTypeString() {
        return GameConstants.SETTLER;
    }

    @Override
    public Player getOwner() {
        return Player.BLUE;
    }

    @Override
    public int getMoveCount() {
        return 1900;
    }

    @Override
    public int getDefensiveStrength() {
        return 333;
    }

    @Override
    public int getAttackingStrength() {
        return 999;
    }

    @Override
    public void setMoveCount(int i) {

    }

    @Override
    public boolean getUnitAction() {
        return true;
    }
}
