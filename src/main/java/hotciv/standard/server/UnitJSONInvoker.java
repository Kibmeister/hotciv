package hotciv.standard.server;

import com.google.gson.Gson;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.Unit;
import hotciv.standard.client.OperationNames;

import javax.servlet.http.HttpServletResponse;

public class UnitJSONInvoker implements Invoker {

    private Unit unitServant;
    private Gson gson;

    public UnitJSONInvoker(Unit unitServant) {
        this.unitServant = unitServant;
        this.gson = new Gson();
    }

    @Override
    public ReplyObject handleRequest(String objectId, String operationName, String payload) {
        ReplyObject replyObject = null;

        if (operationName.equals(OperationNames.UNIT_GET_OWNER)) {
            String owner = unitServant.getOwner().toString();
            replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(owner));

        } else if (operationName.equals(OperationNames.UNIT_GET_MOVECOUNT)) {
            int moveCount = unitServant.getMoveCount();

            replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(moveCount));
        } else if (operationName.equals(OperationNames.UNIT_GET_DEFENSIVE_STRENGTH)) {
            int defensiveStrength = unitServant.getDefensiveStrength();
            replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(defensiveStrength));
        } else if (operationName.equals(OperationNames.UNIT_GET_TYPESTRING)) {
            String type = unitServant.getTypeString();
            replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(type));
        } else if (operationName.equals(OperationNames.UNIT_GET_OFFENSIVE_STRENGTH)) {
            int offensiveStrength = unitServant.getAttackingStrength();
            replyObject = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(offensiveStrength));
        }


        return replyObject;
    }
}
