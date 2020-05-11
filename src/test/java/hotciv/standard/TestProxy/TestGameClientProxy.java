package hotciv.standard.TestProxy;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.*;
import hotciv.framework.GameConstants;
import hotciv.framework.GameObserver;
import hotciv.framework.Position;
import hotciv.standard.client.proxies.GameProxy;
import hotciv.standard.server.GameRootInvoker;
import hotciv.standard.server.NameServiceImpl;
import hotciv.stub.LocalMethodCallClientRequestHandler;
import hotciv.standard.server.GameJSONInvoker;
import hotciv.stub.NullObserver;
import hotciv.stub.StubGame3;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;


public class TestGameClientProxy {
    private Game game = new StubGame3();
    private GameProxy gameProxy;


    @Before
    public void setUp() {
        Game gameServant = this.game;
        Invoker invoker =  new GameRootInvoker(gameServant);
        GameObserver nullObserver = new NullObserver();
        ClientRequestHandler clientRequestHandler = new LocalMethodCallClientRequestHandler(invoker);
        Requestor requestor = new StandardJSONRequestor(clientRequestHandler);

        gameProxy = new GameProxy(requestor);
        gameProxy.addObserver(nullObserver);
    }

    @Test
    public void tileAt10dot10ShouldBeTypeMountain (){
        assertThat(gameProxy.getTileAt(new Position(10,10)).getTypeString(), is(GameConstants.MOUNTAINS));
        assertThat(gameProxy.getTileAt(new Position(11,11)).getTypeString(), is(GameConstants.FOREST));
    }
    @Test
    public void winnerShouldBePlayerGreen(){
        assertThat(gameProxy.getWinner(), is(Player.GREEN));
    }
    @Test
    public void ageShouldBe4000BC(){
        assertThat(gameProxy.getAge(), is(-4000));
    }
    @Test
    public void playerInTurnShouldBePlayerBlue (){
        assertThat(gameProxy.getPlayerInTurn(), is (Player.BLUE));
    }
    @Test
    public void unitAt15dot15ShouldBeABlueLegion (){
        assertThat(gameProxy.getUnitAt(new Position(15,15)).getOwner(), is(Player.BLUE));
    }
    @Test
    public void cityAt9dot9HasBluePlayerAsOwner() {
        assertThat(gameProxy.getCityAt(new Position(9,9)).getOwner(), is(Player.BLUE));
    }
}