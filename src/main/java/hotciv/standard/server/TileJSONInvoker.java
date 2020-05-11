package hotciv.standard.server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.NameService;
import hotciv.framework.Tile;
import hotciv.standard.client.OperationNames;

import javax.servlet.http.HttpServletResponse;

public class TileJSONInvoker implements Invoker {

    private Gson gson;
    private NameService nameService;


    public TileJSONInvoker(NameService nameService) {
        this.nameService = nameService;
        this.gson = new Gson();
    }

    @Override
    public ReplyObject handleRequest(String objectId, String operationName, String payload) {
        ReplyObject replyObject = new ReplyObject(HttpServletResponse.SC_NO_CONTENT, "");
        //JsonParser parser = new JsonParser();
        //JsonArray array = parser.parse(payload).getAsJsonArray();
        Tile tile = nameService.getTile(objectId);

        if (tile != null) {
            if (tile.getId() != null) {
                if (operationName.equals(OperationNames.TILE_GET_TYPESTRING)) {
                    String type = tile.getTypeString();
                    replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(type));
                }

            }
        }


        return replyObject;
    }
}
