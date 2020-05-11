package hotciv.standard.server;

import hotciv.framework.City;
import hotciv.framework.NameService;
import hotciv.framework.Tile;
import hotciv.framework.Unit;
import hotciv.standard.TileImpl;

import javax.naming.Name;
import java.util.HashMap;

public class NameServiceImpl implements NameService {

    private HashMap<String, Tile> tileMap;
    private HashMap<String, City> cityMap;
    private HashMap<String, Unit> unitMap;

    public NameServiceImpl(){
        this.tileMap = new HashMap<>();
        this.cityMap = new HashMap<>();
        this.unitMap = new HashMap<>();
    }


    @Override
    public void putTile(String objectID, Tile tile) {
        tileMap.put(objectID, tile);
    }

    @Override
    public void putUnit(String objectID, Unit unit) {
        unitMap.put(objectID, unit);

    }

    @Override
    public void putCity(String objectID, City city) {
        cityMap.put(objectID, city);
    }

    @Override
    public Tile getTile(String objectID) {
        return tileMap.get(objectID);
    }

    @Override
    public Unit getUnit(String objectID) {
        return unitMap.get(objectID);
    }

    @Override
    public City getCity(String objectID) {
        return cityMap.get(objectID);
    }
}
