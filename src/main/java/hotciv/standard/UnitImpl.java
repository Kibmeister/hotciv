package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;

import java.util.UUID;

public class UnitImpl implements Unit {

    private Player unitOwner;
    private String unitType;
    private boolean unitAction;
    private int moveCount, defensiveStrength, attackingStrength;
    private final String id;

    public UnitImpl (Player unitOwner, String unitType){
        this.unitOwner = unitOwner;
        this.unitType = unitType;
        setUpMoveCount();
        unitAction = true;
        setUpStrength();
        this.id = UUID.randomUUID().toString();
    }

    private void setUpMoveCount() {
        if(unitType.equals(GameConstants.B52)){
            moveCount = 2;
        } else {
            moveCount = 1;
        }
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
            case GameConstants.B52:
                defensiveStrength = GameConstants.B52_DEFENCE;
                attackingStrength = GameConstants.B52_ATTACK;
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

    @Override
    public String getId() {
        return id;
    }
}
