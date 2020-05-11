package hotciv.standard.TestProxy;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.NameService;
import hotciv.framework.Tile;
import hotciv.standard.server.GameRootInvoker;
import hotciv.standard.server.NameServiceImpl;
import hotciv.stub.LocalMethodCallClientRequestHandler;
import hotciv.standard.server.TileJSONInvoker;
import hotciv.standard.client.proxies.TileProxy;
import hotciv.stub.StubGame3;
import hotciv.stub.StubTile3;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


public class TestTileProxy  {

    private Game gameServant;
    private TileProxy tileProxy;
    private GameRootInvoker invoker;


    @Before
    public void setUp(){
        this.gameServant = new StubGame3();
        invoker = new GameRootInvoker(gameServant);
        ClientRequestHandler clientRequestHandler = new LocalMethodCallClientRequestHandler(invoker);
        Requestor requestor = new StandardJSONRequestor(clientRequestHandler);

        invoker.getNameService().putTile("id", new StubTile3());

        tileProxy = new TileProxy("id", requestor);
    }

    @Test
    public void getTypeString () {
        assertThat(tileProxy.getTypeString(), is(GameConstants.MOUNTAINS));
    }

}
