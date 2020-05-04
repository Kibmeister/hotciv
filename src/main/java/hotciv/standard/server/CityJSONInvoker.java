package hotciv.standard.server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.City;
import hotciv.framework.Player;
import hotciv.standard.client.OperationNames;

import javax.servlet.http.HttpServletResponse;

public class CityJSONInvoker implements Invoker {

    private City cityServant;
    private Gson gson;

    public CityJSONInvoker(City cityServant) {
        this.cityServant = cityServant;
        this.gson = new Gson();
    }

    @Override
    public ReplyObject handleRequest(String objectId, String operationName, String payload) {
        ReplyObject replyObject = null;
        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(payload).getAsJsonArray();

        if (operationName.equals(OperationNames.CITY_GET_SIZE)) {
            int size = cityServant.getSize();

            replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(size));
        } else if (operationName.equals(OperationNames.CITY_GET_OWNER)) {
            Player owner = cityServant.getOwner();

            replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(owner));

        } else if (operationName.equals(OperationNames.CITY_GET_PRODUCTION)){
            String production = cityServant.getProduction();

            replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(production));
        } else if (operationName.equals(OperationNames.CITY_GET_TREASURY)){
            int treasury = cityServant.getTreasury();

            replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(treasury));
        } else if (operationName.equals(OperationNames.CITY_GET_WORKFORCE_FOCUS)){
            String focus = cityServant.getWorkforceFocus();

            replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(focus));
        }

        return replyObject;
    }
}
