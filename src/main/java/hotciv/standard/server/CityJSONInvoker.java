package hotciv.standard.server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.City;
import hotciv.framework.NameService;
import hotciv.framework.Player;
import hotciv.standard.client.OperationNames;

import javax.servlet.http.HttpServletResponse;

public class CityJSONInvoker implements Invoker {

    private Gson gson;
    private NameService nameService;

    public CityJSONInvoker(NameService nameService) {
        this.nameService = nameService;
        this.gson = new Gson();
    }

    @Override
    public ReplyObject handleRequest(String objectId, String operationName, String payload) {
        ReplyObject replyObject = new ReplyObject(HttpServletResponse.SC_NO_CONTENT, "");
        // JsonParser parser = new JsonParser();
        //JsonArray array = parser.parse(payload).getAsJsonArray();
        City city = nameService.getCity(objectId);

        if (city != null) {
            if (city.getId() != null) {
                if (operationName.equals(OperationNames.CITY_GET_SIZE)) {
                    int size = city.getSize();

                    replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(size));

                } else if (operationName.equals(OperationNames.CITY_GET_OWNER)) {
                    Player owner = city.getOwner();
                    if (owner != null) {
                        replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(owner));

                    }
                } else if (operationName.equals(OperationNames.CITY_GET_PRODUCTION)) {
                    String production = city.getProduction();

                    replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(production));
                } else if (operationName.equals(OperationNames.CITY_GET_TREASURY)) {
                    int treasury = city.getTreasury();

                    replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(treasury));
                } else if (operationName.equals(OperationNames.CITY_GET_WORKFORCE_FOCUS)) {
                    String focus = city.getWorkforceFocus();

                    replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(focus));
                }
            }

        }
        return replyObject;
    }
}
