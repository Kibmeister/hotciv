package hotciv.standard.server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.NameService;
import hotciv.framework.Unit;
import hotciv.standard.client.OperationNames;

import javax.naming.Name;
import javax.servlet.http.HttpServletResponse;

public class UnitJSONInvoker implements Invoker {

    private Gson gson;
    private NameService nameService;

    public UnitJSONInvoker(NameService nameService) {
        this.nameService = nameService;
        this.gson = new Gson();
    }


    @Override
    public ReplyObject handleRequest(String objectId, String operationName, String payload) {
        ReplyObject replyObject = new ReplyObject(HttpServletResponse.SC_NO_CONTENT, "");
        Unit unit = nameService.getUnit(objectId);

        if (unit != null) {
            if (unit.getId() != null) {
                if (operationName.equals(OperationNames.UNIT_GET_OWNER)) {
                    String owner = unit.getOwner().toString();
                    if(owner != null){
                        replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(owner));
                    }
                } else if (operationName.equals(OperationNames.UNIT_GET_MOVECOUNT)) {
                    int moveCount = unit.getMoveCount();
                    replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(moveCount));

                } else if (operationName.equals(OperationNames.UNIT_GET_DEFENSIVE_STRENGTH)) {
                    int defensiveStrength = unit.getDefensiveStrength();
                    replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(defensiveStrength));

                } else if (operationName.equals(OperationNames.UNIT_GET_TYPESTRING)) {
                    String type = unit.getTypeString();
                    replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(type));

                } else if (operationName.equals(OperationNames.UNIT_GET_OFFENSIVE_STRENGTH)) {
                    int offensiveStrength = unit.getAttackingStrength();
                    replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(offensiveStrength));
                }
            }
        }


        return replyObject;
    }
}
