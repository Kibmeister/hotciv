package hotciv.standard.TestProxy;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.GameConstants;
import hotciv.framework.Tile;
import hotciv.stub.LocalMethodCallClientRequestHandler;
import hotciv.standard.server.TileJSONInvoker;
import hotciv.standard.client.proxies.TileProxy;
import hotciv.stub.StubTile3;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


public class TestTileProxy  {

    private Tile tile = new StubTile3();
    private TileProxy tileProxy;

    @Before
    public void setUp(){
        Tile tile = this.tile;
        Invoker invoker = new TileJSONInvoker(tile);
        ClientRequestHandler clientRequestHandler = new LocalMethodCallClientRequestHandler(invoker);
        Requestor requestor = new StandardJSONRequestor(clientRequestHandler);

        tileProxy = new TileProxy(requestor);
    }

    @Test
    public void getTypeString () {
        assertThat(tileProxy.getTypeString(), is(GameConstants.MOUNTAINS));
    }

}
