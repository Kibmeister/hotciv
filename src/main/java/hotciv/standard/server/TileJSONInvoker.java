package hotciv.standard.server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.Tile;
import hotciv.standard.client.OperationNames;

import javax.servlet.http.HttpServletResponse;

public class TileJSONInvoker implements Invoker {

    private Tile tileServant;
    private Gson gson;


    public TileJSONInvoker(Tile tile) {
        this.tileServant = tile;
        this.gson = new Gson();
    }

    @Override
    public ReplyObject handleRequest(String objectId, String operationName, String payload) {
        ReplyObject replyObject = null;
        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(payload).getAsJsonArray();

        if(operationName.equals(OperationNames.TILE_GET_TYPESTRING)){
            String type = tileServant.getTypeString();
            replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(type));
        }
        return replyObject;
    }
}
