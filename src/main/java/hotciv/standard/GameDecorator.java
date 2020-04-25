package hotciv.standard;

import hotciv.framework.*;
import hotciv.standard.factories.AlphaCivFactory;

public class GameDecorator implements Game {
    public Game gameImpl;

    public GameDecorator(Game game) {
        gameImpl = game;
    }

    @Override
    public Tile getTileAt(Position p) {
        return gameImpl.getTileAt(p);
    }

    @Override
    public Unit getUnitAt(Position p) {
        return gameImpl.getUnitAt(p);
    }

    @Override
    public City getCityAt(Position p) {
        return gameImpl.getCityAt(p);
    }

    @Override
    public Player getPlayerInTurn() {
        return gameImpl.getPlayerInTurn();
    }

    @Override
    public Player getWinner() {
        return gameImpl.getWinner();
    }

    @Override
    public int getAge() {
        return gameImpl.getAge();
    }

    @Override
    public boolean moveUnit(Position from, Position to) {
        System.out.println(gameImpl.getPlayerInTurn() + "  moves " + gameImpl.getUnitAt(from).getTypeString() +
                " from "  + from.toString() + " to "+ to.toString());
        return gameImpl.moveUnit(from, to);
    }

    @Override
    public void endOfTurn() {
        System.out.println(gameImpl.getPlayerInTurn() + " ends turn.");
        gameImpl.endOfTurn();

    }

    @Override
    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        gameImpl.changeProductionInCityAt(p, balance);

    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType) {
        /* RED changes production in city at (1,1) to Legion.*/
        System.out.println(gameImpl.getPlayerInTurn() + " changes production in a city at " + p.toString() +
                " to " + unitType);
        gameImpl.changeProductionInCityAt(p, unitType);
    }

    @Override
    public void performUnitActionAt(Position p) {
        gameImpl.performUnitActionAt(p);
    }

    @Override
    public void createUnit(Position p, UnitImpl u) {
        gameImpl.createUnit(p, u);
    }

    @Override
    public void createCity(Position p, CityImpl c) {
        gameImpl.createCity(p, c);
    }
}
