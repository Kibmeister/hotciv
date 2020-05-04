package hotciv.standard.TestProxy;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;
import hotciv.standard.client.LocalMethodCallClientRequestHandler;
import hotciv.standard.server.UnitJSONInvoker;
import hotciv.standard.client.proxies.UnitProxy;
import hotciv.stub.StubUnit3;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


public class TestUnitClientProxy {

    private Unit unit = new StubUnit3();
    private UnitProxy unitProxy;


    @Before
    public void setUp(){
        Unit unitServant = this.unit;
        Invoker invoker = new UnitJSONInvoker(unitServant);

        ClientRequestHandler clientRequestHandler = new LocalMethodCallClientRequestHandler(invoker);
        Requestor requestor = new StandardJSONRequestor(clientRequestHandler);

        this.unitProxy = new UnitProxy(requestor);
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
