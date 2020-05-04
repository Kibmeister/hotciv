package hotciv.standard.client.proxies;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.City;
import hotciv.framework.Player;
import hotciv.standard.client.OperationNames;

public class CityProxy implements City, ClientProxy {

    private Requestor requestor;

    public CityProxy(Requestor requestor) {
        this.requestor = requestor;
    }

    @Override
    public Player getOwner() {
        Player owner = requestor.sendRequestAndAwaitReply("[no, parameters]", OperationNames.CITY_GET_OWNER, Player.class, null);
        return owner;
    }

    @Override
    public int getSize() {
        return requestor.sendRequestAndAwaitReply("[no, parameters]", OperationNames.CITY_GET_SIZE, Integer.class, null);
    }

    @Override
    public int getTreasury() {
        return requestor.sendRequestAndAwaitReply("[no, parameters]", OperationNames.CITY_GET_TREASURY, Integer.class, null);
    }

    @Override
    public String getProduction() {
        return requestor.sendRequestAndAwaitReply("[no, parameters]", OperationNames.CITY_GET_PRODUCTION,String.class, null);
    }

    @Override
    public String getWorkforceFocus() {
        return requestor.sendRequestAndAwaitReply("[no, parameters]", OperationNames.CITY_GET_WORKFORCE_FOCUS, String.class, null);
    }

    @Override
    public void setTreasury(int treasury) {

    }

    @Override
    public void setProduction(String t) {

    }
}
