package hotciv.standard.TestProxy;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.*;
import hotciv.standard.server.GameRootInvoker;
import hotciv.standard.server.NameServiceImpl;
import hotciv.stub.LocalMethodCallClientRequestHandler;
import hotciv.standard.server.UnitJSONInvoker;
import hotciv.standard.client.proxies.UnitProxy;
import hotciv.stub.StubGame3;
import hotciv.stub.StubUnit3;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


public class TestUnitClientProxy {

    private Game gameServant;
    private UnitProxy unitProxy;
    private GameRootInvoker invoker;


    @Before
    public void setUp(){
        this.gameServant = new StubGame3();
        this.invoker = new GameRootInvoker(gameServant);

        ClientRequestHandler clientRequestHandler = new LocalMethodCallClientRequestHandler(invoker);
        Requestor requestor = new StandardJSONRequestor(clientRequestHandler);
        invoker.getNameService().putUnit("id", new StubUnit3());
        this.unitProxy = new UnitProxy("id", requestor);
    }

    @Test
    public void getOwner() {
        assertThat(unitProxy.getOwner(), is(Player.BLUE));
    }
    @Test
    public void getMoveCount () {
        assertThat(unitProxy.getMoveCount(), is(1900));
    }
    @Test
    public void getDefensiveStrength() {
        assertThat(unitProxy.getDefensiveStrength(), is (333));
    }
    @Test
    public void getTypeString () {
        assertThat(unitProxy.getTypeString(), is(GameConstants.SETTLER));
    }
    @Test
    public void getAttackingStrength () {
        assertThat(unitProxy.getAttackingStrength(), is(999));
    }

}
