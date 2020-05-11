package hotciv.framework;

public interface NameService {

     void putTile (String objectID, Tile tile);
     void putUnit (String objectID, Unit unit);

     void putCity (String objectID, City city);

     Tile getTile (String objectID);

     Unit getUnit (String objectID);

     City getCity (String objectID);
    }

