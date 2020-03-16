package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;

public class UnitImpl implements Unit {

    private Player unitOwner;
    private String unitType;
    private boolean unitAction;
    private int moveCount, defensiveStrength, attackingStrength;

    public UnitImpl (Player unitOwner, String unitType){
        this.unitOwner = unitOwner;
        this.unitType = unitType;
        moveCount = 1;
        unitAction = true;
        if(unitType.equals(GameConstants.ARCHER)){
            defensiveStrength = 3;
        } else if (unitType.equals(GameConstants.LEGION)){
            defensiveStrength = 2;
        } else if(unitType.equals(GameConstants.SETTLER)){
            defensiveStrength = 3;
        }
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
        return defensiveStrength;
    }

    @Override
    public int getAttackingStrength() {
        return attackingStrength;
    }

    @Override
    public void setMoveCount(int i) {
        moveCount = i;
    }
    public void setDefensiveStrength(int i) {
        defensiveStrength = i;
    }

    public void setUnitAction(boolean b) {
        unitAction = b;
    }

    public boolean getUnitAction() {
        return unitAction;
    }
}
