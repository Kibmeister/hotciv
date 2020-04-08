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
        setUpStrength();
    }

    private void setUpStrength() {
        switch (unitType){
            case GameConstants.ARCHER:
                defensiveStrength = GameConstants.ARCHER_DEFENCE;
                attackingStrength = GameConstants.ARCHER_ATTACK;
                break;
            case GameConstants.LEGION:
                defensiveStrength = GameConstants.LEGION_DEFENCE;
                attackingStrength = GameConstants.LEGION_ATTACK;
                break;
            case GameConstants.SETTLER:
                defensiveStrength = GameConstants.SETTLER_DEFENCE;
                attackingStrength = GameConstants.SETTLER_ATTACK;
                break;
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
