package hotciv.standard.TestProxy;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.City;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.standard.client.proxies.CityProxy;
import hotciv.stub.LocalMethodCallClientRequestHandler;
import hotciv.standard.server.CityJSONInvoker;
import hotciv.stub.StubCity3;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestCityClientProxy {

    private City city = new StubCity3();
    private CityProxy cityProxy;

    @Before
    public void setUp () {
        City cityServant = this.city;
        Invoker invoker = new CityJSONInvoker(cityServant);
        ClientRequestHandler clientRequestHandler = new LocalMethodCallClientRequestHandler(invoker);

        Requestor requestor = new StandardJSONRequestor(clientRequestHandler);

        cityProxy = new CityProxy(requestor);
    }

    @Test
    public void getSize (){
        assertThat(cityProxy.getSize(), is(3));
    }
    @Test
    public void getOwner () {
        assertThat(cityProxy.getOwner(), is(Player.YELLOW));
    }
    @Test
    public void getProduction () {
        assertThat(cityProxy.getProduction(), is(GameConstants.B52));
    }
    @Test
    public void getTreasury() {
        assertThat(cityProxy.getTreasury(), is (10));
    }
    @Test
    public void getWorkForceFocus () {
        assertThat(cityProxy.getWorkforceFocus(),is(GameConstants.productionFocus));
    }

}
