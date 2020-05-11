package hotciv.standard.client.proxies;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.City;
import hotciv.framework.Player;
import hotciv.standard.client.OperationNames;

public class CityProxy implements City, ClientProxy {

    private Requestor requestor;
    private String objectId;


    public CityProxy(String objectId, Requestor requestor) {
        this.objectId = objectId;
        this.requestor = requestor;
    }

    @Override
    public Player getOwner() {
       return requestor.sendRequestAndAwaitReply(objectId, OperationNames.CITY_GET_OWNER, Player.class);

    }

    @Override
    public int getSize() {
        return requestor.sendRequestAndAwaitReply(objectId, OperationNames.CITY_GET_SIZE, Integer.class, null);
    }

    @Override
    public int getTreasury() {
        return requestor.sendRequestAndAwaitReply(objectId, OperationNames.CITY_GET_TREASURY, Integer.class, null);
    }

    @Override
    public String getProduction() {
        return requestor.sendRequestAndAwaitReply(objectId, OperationNames.CITY_GET_PRODUCTION,String.class, null);
    }

    @Override
    public String getWorkforceFocus() {
        return requestor.sendRequestAndAwaitReply(objectId, OperationNames.CITY_GET_WORKFORCE_FOCUS, String.class, null);
    }

    @Override
    public void setTreasury(int treasury) {

    }

    @Override
    public void setProduction(String t) {

    }

    @Override
    public String getId() {
        return objectId;
    }
}
