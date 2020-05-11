package hotciv.standard.client.proxies;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.*;
import hotciv.standard.CityImpl;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;
import hotciv.standard.client.OperationNames;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class GameProxy implements Game, ClientProxy {
    private Requestor requestor;
    private List<GameObserver> observers;

    public GameProxy (Requestor requestor){
        this.requestor = requestor;
        this.observers = new ArrayList<>();
    }
    @Override
    public Tile getTileAt(Position p) {
        String id = requestor.sendRequestAndAwaitReply("", OperationNames.GET_TILE_AT, String.class, p);
        TileProxy tile = new TileProxy(id, requestor);
        return tile;
    }

    @Override
    public Unit getUnitAt(Position p) {
        String id = requestor.sendRequestAndAwaitReply("[no, parameters]", OperationNames.GET_UNIT_AT,String.class, p);
        return new UnitProxy(id, requestor);
    }

    @Override
    public City getCityAt(Position p) {
        String id = requestor.sendRequestAndAwaitReply("", OperationNames.GET_CITY_AT, String.class, p);
        City city = new CityProxy(id, requestor);
        return city;
    }

    @Override
    public Player getPlayerInTurn() {
        Player p = requestor.sendRequestAndAwaitReply("[no, parameters]", OperationNames.GET_PLAYER_IN_TURN, Player.class);
        return p;
    }

    @Override
    public Player getWinner() {
        Player winner = requestor.sendRequestAndAwaitReply("[no, parameters]", OperationNames.GET_WINNER, Player.class);

        return winner;
    }

    @Override
    public int getAge() {
        int age = requestor.sendRequestAndAwaitReply("[no, parameters]" , OperationNames.GET_AGE, Integer.class);
        return age;
    }

    @Override
    public boolean moveUnit(Position from, Position to) {
        Boolean move = requestor.sendRequestAndAwaitReply("[no, parameters]", OperationNames.MOVE_UNIT, Boolean.class, from, to);

        for(GameObserver o: observers){
            o.tileFocusChangedAt(from);
            o.tileFocusChangedAt(to);
        }
        return move;
    }

    @Override
    public void endOfTurn() {
        requestor.sendRequestAndAwaitReply("[no, parameters]", OperationNames.END_OF_TURN, Player.class);

        for(GameObserver o: observers){
            o.turnEnds(getPlayerInTurn(), getAge());
        }

    }

    @Override
    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        requestor.sendRequestAndAwaitReply(
                "",
                OperationNames.CHANGE_WORKFORCE_FOCUS_IN_CITY_AT,
                null, p, balance);

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
        observers.add(observer);
    }

    @Override
    public void setTileFocus(Position position) {
        for(GameObserver o: observers){
            o.tileFocusChangedAt(position);
        }

    }
}
