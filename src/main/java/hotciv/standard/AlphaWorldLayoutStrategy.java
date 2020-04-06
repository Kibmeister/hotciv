package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.WorldLayoutStrategy;


import java.util.HashMap;

public class AlphaWorldLayoutStrategy implements WorldLayoutStrategy {

    private HashMap<Position, TileImpl> world;
    private HashMap<Position, UnitImpl> units;
    private HashMap<Position, CityImpl> cities;

    public AlphaWorldLayoutStrategy () {
        world = new HashMap<>();
        units = new HashMap<>();
        cities = new HashMap<>();
    }

    @Override
    public HashMap<Position, TileImpl> getWorldLayout() {

        for (int i = 0; i < GameConstants.WORLDSIZE; i++) {
            for (int j = 0; j < GameConstants.WORLDSIZE; j++) {
                world.put(new Position(i, j), new TileImpl(GameConstants.PLAINS));
            }
        }
        world.put(new Position(1, 0), new TileImpl(GameConstants.OCEANS));
        world.put(new Position(0, 1), new TileImpl(GameConstants.HILLS));
        world.put(new Position(2, 2), new TileImpl(GameConstants.MOUNTAINS));
        world.put(new Position(10,10), new TileImpl(GameConstants.FOREST));

        return world;
    }

    @Override
    public HashMap<Position, UnitImpl> getUnitsLayout() {
        units.put(new Position(3, 2), new UnitImpl(Player.BLUE, GameConstants.LEGION));
        units.put(new Position(4, 3), new UnitImpl(Player.RED, GameConstants.SETTLER));
        units.put(new Position(2, 0), new UnitImpl(Player.RED, GameConstants.ARCHER));
        units.put(new Position(1, 1), new UnitImpl(Player.RED, GameConstants.SETTLER));


        return units;
    }

    @Override
    public HashMap<Position, CityImpl> getCitiesLayout() {
        cities.put(new Position(1, 1), new CityImpl(Player.RED));
        cities.put(new Position(4, 1), new CityImpl(Player.BLUE));
        return cities;
    }
}
