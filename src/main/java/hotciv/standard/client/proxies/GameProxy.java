package hotciv.standard.client.proxies;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.*;
import hotciv.standard.CityImpl;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;
import hotciv.standard.client.OperationNames;

import java.util.List;

public class GameProxy implements Game, ClientProxy {
    private Requestor requestor;

    public GameProxy (Requestor requestor){
        this.requestor = requestor;
    }
    @Override
    public Tile getTileAt(Position p) {
        String type = requestor.sendRequestAndAwaitReply(p.toString(), OperationNames.GET_TILE_AT, String.class, null);

        return new TileImpl(type);
    }

    @Override
    public Unit getUnitAt(Position p) {
        List unit = requestor.sendRequestAndAwaitReply(p.toString(), OperationNames.GET_UNIT_AT, List.class, null);
        return new UnitImpl(Player.valueOf(unit.get(0).toString()), unit.get(1).toString());
    }

    @Override
    public City getCityAt(Position p) {
        Player owner= requestor.sendRequestAndAwaitReply("[no, parameters]", OperationNames.GET_CITY_AT, Player.class, p);
        return new CityImpl(owner);
    }

    @Override
    public Player getPlayerInTurn() {
        Player playerInTurn = requestor.sendRequestAndAwaitReply("[no, parameters]", OperationNames.GET_PLAYER_IN_TURN, Player.class, (Object) null);
        return playerInTurn;
    }

    @Override
    public Player getWinner() {
        Player winner = requestor.sendRequestAndAwaitReply("[no, parameters]", OperationNames.GET_WINNER, Player.class, (Object) null);

        return winner;
    }

    @Override
    public int getAge() {
        int age = requestor.sendRequestAndAwaitReply("[no, parameters]" , OperationNames.GET_AGE, Integer.class, null);
        return age;
    }

    @Override
    public boolean moveUnit(Position from, Position to) {
        return false;
    }

    @Override
    public void endOfTurn() {

    }

    @Override
    public void changeWorkForceFocusInCityAt(Position p, String balance) {

    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType) {

    }

    @Override
    public void performUnitActionAt(Position p) {

    }

    @Override
    public void createUnit(Position p, UnitImpl u) {

    }

    @Override
    public void createCity(Position p, CityImpl c) {

    }

    @Override
    public void addObserver(GameObserver observer) {

    }

    @Override
    public void setTileFocus(Position position) {

    }
}
