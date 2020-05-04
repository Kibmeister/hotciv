package hotciv.stub;

import hotciv.framework.*;
import hotciv.standard.CityImpl;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;

import java.util.List;

public class StubGame3 implements Game {


    @Override
    public Tile getTileAt(Position p) {
        if (p.equals(new Position(10, 10))) {
            return new TileImpl(GameConstants.MOUNTAINS);
        } else if (p.equals(new Position(11, 11))) {
            return new TileImpl(GameConstants.FOREST);
        } else {
            return null;
        }
    }

    @Override
    public Unit getUnitAt(Position p) {
        if(p.equals(new Position(15,15))){
            return new UnitImpl(Player.BLUE, GameConstants.LEGION);
        }
        return null;
    }

    @Override
    public City getCityAt(Position p) {
        if(p.equals(new Position(9,9))){
            return new CityImpl(Player.BLUE);
        }
        return null;
    }

    @Override
    public Player getPlayerInTurn() {
        return Player.BLUE;
    }

    @Override
    public Player getWinner() {
        return Player.GREEN;
    }

    @Override
    public int getAge() {
        return -4000;
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
