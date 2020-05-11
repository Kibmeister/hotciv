package hotciv.standard.server;

import com.google.gson.Gson;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.*;
import hotciv.standard.client.OperationNames;
import hotciv.stub.StubGame3;

import java.util.HashMap;

public class GameRootInvoker implements Invoker {

    private HashMap<String, Invoker> subInvokers;
    private Gson gson;
    private Game gameServant;
    private NameServiceImpl nameService;
    private Invoker subInvoker;

    public GameRootInvoker(Game gameServant) {
        subInvokers = new HashMap<>();
        gson = new Gson();
        this.gameServant = gameServant;
        this.nameService = new NameServiceImpl();
        setUpInvokers();
    }

    private void setUpInvokers() {
        Invoker gameJSONInvoker = new GameJSONInvoker(gameServant, nameService);
        subInvokers.put(OperationNames.GAME_PREFIX, gameJSONInvoker);
        Invoker cityJSONInvoker = new CityJSONInvoker(nameService);
        subInvokers.put(OperationNames.CITY_PREFIX, cityJSONInvoker);
        Invoker unitJSONInvoker = new UnitJSONInvoker(nameService);
        subInvokers.put(OperationNames.UNIT_PREFIX, unitJSONInvoker);
        Invoker tileJSONInvoker = new TileJSONInvoker(nameService);
        subInvokers.put(OperationNames.TILE_PREFIX, tileJSONInvoker);
    }


    @Override
    public ReplyObject handleRequest(String objectId, String operationName, String payload) {
        ReplyObject replyObject = null;

        String type = operationName.substring(0, operationName.indexOf("-"));
        subInvoker = subInvokers.get(type);
        if (subInvoker != null) {
            replyObject = subInvoker.handleRequest(objectId, operationName, payload);
        }
        return replyObject;

    }

    public NameService getNameService () {
        return nameService;
    }

}
