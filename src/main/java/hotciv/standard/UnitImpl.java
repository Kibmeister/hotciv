package hotciv.standard;

import hotciv.framework.Player;
import hotciv.framework.Unit;

public class UnitImpl implements Unit {

    private Player unitOwner;
    private String unitType;
    private int moveCount;

    public UnitImpl (Player unitOwner, String unitType){
        this.unitOwner = unitOwner;
        this.unitType = unitType;
        moveCount = 1;
    }

    @Override
    public String getTypeString() {
        return unitType;
    }

    @Override
    public Player getOwner() {
        return unitOwner;
    }

    @Override
    public int getMoveCount() {
        return moveCount;
    }

    @Override
    public int getDefensiveStrength() {
        return 0;
    }

    @Override
    public int getAttackingStrength() {
        return 0;
    }

    @Override
    public void setMoveCount(int i) {
        moveCount = i;
    }
}
