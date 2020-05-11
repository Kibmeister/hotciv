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
import javafx.geometry.Pos;
import jdk.nashorn.internal.parser.JSONParser;
import sun.awt.shell.ShellFolder;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.List;

public class GameJSONInvoker implements Invoker {
    private Game gameServant;
    private Gson gson;
    private NameService nameService;

    public GameJSONInvoker(Game game, NameService nameService) {
        this.gameServant = game;
        this.nameService = nameService;
        this.gson = new Gson();
    }


    @Override
    public ReplyObject handleRequest(String objectId, String operationName, String payload) {
        ReplyObject replyObject = new ReplyObject(HttpServletResponse.SC_NO_CONTENT, "null object");
        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(payload).getAsJsonArray(); // demarshall into JSON array


        if (operationName.equals(OperationNames.GET_TILE_AT)) {
            // parameters array[0] = position row
            // array[1] = position columm
            Position p = gson.fromJson(array.get(0), Position.class);
            Tile tile = gameServant.getTileAt(p);
            if(tile != null){
                String id = tile.getId();
                nameService.putTile(id, tile);
                replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(id));
            }


        }
        if (operationName.equals(OperationNames.GET_WINNER)) {
            String winner = gameServant.getWinner().toString();

            if (winner != null) {
                replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(winner));
            } else {
                replyObject = new ReplyObject(HttpServletResponse.SC_NOT_FOUND, objectId);
            }
        }
        if (operationName.equals(OperationNames.GET_AGE)) {
            Integer age = gameServant.getAge();

            replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(age));

        }
        if (operationName.equals(OperationNames.GET_PLAYER_IN_TURN)) {
            String playerInTurn = gameServant.getPlayerInTurn().toString();

            replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(playerInTurn));

        }
        if (operationName.equals(OperationNames.GET_UNIT_AT)) {
            Position p = gson.fromJson(array.get(0), Position.class);
            Unit unit = gameServant.getUnitAt(p);
            if (unit != null) {
                String id = unit.getId();
                nameService.putUnit(id, unit);
                replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(id));
            }
        }
        if (operationName.equals(OperationNames.GET_CITY_AT)) {
            Position p = gson.fromJson(array.get(0), Position.class);
            City city = gameServant.getCityAt(p);
            if (city != null) {
                String id = city.getId();
                nameService.putCity(id, city);
                replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(id));
            }


        }
        if (operationName.equals(OperationNames.MOVE_UNIT)) {
            Position from = gson.fromJson(array.get(0), Position.class);
            Position to = gson.fromJson(array.get(1), Position.class);
            Boolean moveUnit = gameServant.moveUnit(from, to);
            replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(moveUnit));
        }
        if (operationName.equals(OperationNames.END_OF_TURN)) {
            gameServant.endOfTurn();
            replyObject = new ReplyObject(HttpServletResponse.SC_OK, null);

        }


        return replyObject;
    }
}
