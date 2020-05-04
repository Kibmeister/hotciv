package hotciv.standard.server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.*;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;
import hotciv.standard.client.OperationNames;
import jdk.nashorn.internal.parser.JSONParser;
import sun.awt.shell.ShellFolder;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GameJSONInvoker implements Invoker {
    private Game gameServant;
    private Gson gson;

    public GameJSONInvoker(Game game) {
        this.gameServant = game;
        this.gson = new Gson();
    }


    @Override
    public ReplyObject handleRequest(String objectId, String operationName, String payload) {
        ReplyObject replyObject = null;
        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(payload).getAsJsonArray(); // demarshall into JSON array

        if (operationName.equals(OperationNames.GET_TILE_AT)) {
            // parameters array[0] = position row
            // array[1] = position columm
            Position p = new Position(array.get(0).getAsInt(), array.get(1).getAsInt());

            String tileType = gameServant.getTileAt(p).getTypeString();

            replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(tileType));

        }
        if (operationName.equals(OperationNames.GET_WINNER)) {
            String winner = gameServant.getWinner().toString();

            if (winner != null) {
                replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(winner));
            } else{
                replyObject = new ReplyObject(HttpServletResponse.SC_NOT_FOUND, objectId);
            }
        }
        if(operationName.equals(OperationNames.GET_AGE)){
            Integer age = gameServant.getAge();

            replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(age));
        } if(operationName.equals(OperationNames.GET_PLAYER_IN_TURN)){
            String playerInTurn = gameServant.getPlayerInTurn().toString();

            replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(playerInTurn));

        }if(operationName.equals(OperationNames.GET_UNIT_AT)){
            Position p = new Position(array.get(0).getAsInt(), array.get(1).getAsInt());
            Unit unit = gameServant.getUnitAt(p);

            String[] arguments = {unit.getOwner().toString(), unit.getTypeString()};

            replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(arguments));
        }
        if(operationName.equals(OperationNames.GET_CITY_AT)){
            Position p = new Position(array.get(0).getAsInt(), array.get(1).getAsInt());

            String owner  = gameServant.getCityAt(p).getOwner().toString();

            replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(owner));

        }


        return replyObject;
    }
}
