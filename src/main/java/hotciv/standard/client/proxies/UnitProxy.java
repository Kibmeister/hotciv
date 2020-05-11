package hotciv.standard.client.proxies;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.*;
import hotciv.standard.CityImpl;
import hotciv.standard.UnitImpl;
import hotciv.standard.client.OperationNames;

public class UnitProxy implements ClientProxy, Unit {
    private Requestor requestor;
    private String objectId;


public UnitProxy (String objectId,Requestor requestor){
    this.requestor = requestor;
    this.objectId = objectId;
}

    @Override
    public String getTypeString() {
        String type = requestor.sendRequestAndAwaitReply(objectId, OperationNames.UNIT_GET_TYPESTRING, String.class);
        return type;
    }

    @Override
    public Player getOwner() {
        String color = requestor.sendRequestAndAwaitReply(objectId, OperationNames.UNIT_GET_OWNER, String.class);
        return Player.valueOf(color);
    }

    @Override
    public int getMoveCount() {
        int moveCount = requestor.sendRequestAndAwaitReply(objectId, OperationNames.UNIT_GET_MOVECOUNT, Integer.class);
        return moveCount;
    }

    @Override
    public int getDefensiveStrength() {
        int defensiveStrength = requestor.sendRequestAndAwaitReply(objectId, OperationNames.UNIT_GET_DEFENSIVE_STRENGTH, Integer.class);
        return defensiveStrength;
    }

    @Override
    public int getAttackingStrength() {
        int attackingStrength = requestor.sendRequestAndAwaitReply(objectId, OperationNames.UNIT_GET_OFFENSIVE_STRENGTH, Integer.class);
        return attackingStrength;
    }

    @Override
    public void setMoveCount(int i) {

    }

    @Override
    public boolean getUnitAction() {
        return false;
    }

    @Override
    public String getId() {
        return objectId;
    }

}
