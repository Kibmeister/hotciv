package hotciv.standard.client.proxies;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.Tile;
import hotciv.standard.client.OperationNames;

public class TileProxy implements Tile, ClientProxy {
    private Requestor requestor;

    public TileProxy(Requestor requestor){
        this.requestor = requestor;
    }
    @Override
    public String getTypeString() {
        String type = requestor.sendRequestAndAwaitReply("[no, parameters]", OperationNames.TILE_GET_TYPESTRING, String.class, null);
        return type;
    }
}
