package hotciv.standard.client.proxies;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.*;
import hotciv.standard.CityImpl;
import hotciv.standard.UnitImpl;
import hotciv.standard.client.OperationNames;

public class UnitProxy implements ClientProxy, Unit {
    private Requestor requestor;


public UnitProxy (Requestor requestor){
    this.requestor = requestor;
}

    @Override
    public String getTypeString() {
        String type = requestor.sendRequestAndAwaitReply("[no, parameters]", OperationNames.UNIT_GET_TYPESTRING, String.class, null);
        return type;
    }

    @Override
    public Player getOwner() {
        String owner = requestor.sendRequestAndAwaitReply("[no, parameters]", OperationNames.UNIT_GET_OWNER, String.class
        , null);
        return Player.valueOf(owner);
    }

    @Override
    public int getMoveCount() {
        int moveCount = requestor.sendRequestAndAwaitReply("[no, parameters]", OperationNames.UNIT_GET_MOVECOUNT, Integer.class, null);
        return moveCount;
    }

    @Override
    public int getDefensiveStrength() {
        int defensiveStrength = requestor.sendRequestAndAwaitReply("[no, parameters]", OperationNames.UNIT_GET_DEFENSIVE_STRENGTH, Integer.class, null);
        return defensiveStrength;
    }

    @Override
    public int getAttackingStrength() {
        int attackingStrength = requestor.sendRequestAndAwaitReply("[no, parameters]", OperationNames.UNIT_GET_OFFENSIVE_STRENGTH, Integer.class, null);
        return attackingStrength;
    }

    @Override
    public void setMoveCount(int i) {

    }

    @Override
    public boolean getUnitAction() {
        return false;
    }

}
