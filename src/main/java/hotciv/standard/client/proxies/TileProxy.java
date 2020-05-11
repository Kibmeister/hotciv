package hotciv.standard.client.proxies;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.Tile;
import hotciv.standard.client.OperationNames;

public class TileProxy implements Tile, ClientProxy {
    private Requestor requestor;
    private String objectId;


    public TileProxy(String objectId, Requestor requestor){
        this.objectId = objectId;
        this.requestor = requestor;
    }
    @Override
    public String getTypeString() {
        String type = requestor.sendRequestAndAwaitReply(objectId, OperationNames.TILE_GET_TYPESTRING, String.class);
        return type;
    }

    @Override
    public String getId() {
        return objectId;
    }
}
