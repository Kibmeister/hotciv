package hotciv.standard.server;

import hotciv.framework.City;
import hotciv.framework.Tile;
import hotciv.framework.Unit;
import hotciv.standard.TileImpl;

import javax.naming.Name;
import java.util.HashMap;

public class NameService {

    private HashMap<String, Tile> tileMap;
    private HashMap<String, City> cityMap;
    private HashMap<String, Unit> unitMap;

    public NameService (){
        this.tileMap = new HashMap<>();
        this.cityMap = new HashMap<>();
        this.unitMap = new HashMap<>();
    }
    public void putTile (String objectID, Tile tile){
        tileMap.put(objectID, tile);
    }
    public void putUnit (String objectID, Unit unit){
        unitMap.put(objectID, unit);
    }
    public void putCity (String objectID, City city){
        cityMap.put(objectID, city);
    }
    public Tile getTile (String objectID){
        return tileMap.get(objectID);
    }
    public Unit getUnit (String objectID){
        return unitMap.get(objectID);
    }
    public City getCity (String objectID){
        return cityMap.get(objectID);
    }



}
