package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.WorldLayoutStrategy;

import java.util.HashMap;
import java.util.HashSet;

public class DynamicWorldLayoutStrategy implements WorldLayoutStrategy {
    private HashMap<Position, TileImpl> world;
    private HashMap<Position, UnitImpl> units;
    private HashMap<Position, CityImpl> cities;
    private String[] layout;

    public DynamicWorldLayoutStrategy(String[] layout){
        world = new HashMap<>();
        units = new HashMap<>();
        cities = new HashMap<>();
        this.layout = layout;
    }
    public DynamicWorldLayoutStrategy(){
        world = new HashMap<>();
        units = new HashMap<>();
        cities = new HashMap<>();
        this.layout =
                new String[] {
                        "...ooMooooo.....",
                        "..ohhoooofffoo..",
                        ".oooooMooo...oo.",
                        ".ooMMMoooo..oooo",
                        "...ofooohhoooo..",
                        ".ofoofooooohhoo.",
                        "...ooo..........",
                        ".ooooo.ooohooM..",
                        ".ooooo.oohooof..",
                        "offfoooo.offoooo",
                        "oooooooo...ooooo",
                        ".ooMMMoooo......",
                        "..ooooooffoooo..",
                        "....ooooooooo...",
                        "..ooohhoo.......",
                        ".....ooooooooo..",
                };
    }




    @Override
    public HashMap<Position, TileImpl> getWorldLayout() {

        String line;
        for (int r = 0; r < GameConstants.WORLDSIZE; r++ ) {
            line = layout[r];
            for ( int c = 0; c < GameConstants.WORLDSIZE; c++ ) {
                char tileChar = line.charAt(c);
                String type = "error";
                if ( tileChar == '.' ) { type = GameConstants.OCEANS; }
                if ( tileChar == 'o' ) { type = GameConstants.PLAINS; }
                if ( tileChar == 'M' ) { type = GameConstants.MOUNTAINS; }
                if ( tileChar == 'f' ) { type = GameConstants.FOREST; }
                if ( tileChar == 'h' ) { type = GameConstants.HILLS; }
                Position p = new Position(r,c);
                world.put( p, new TileImpl(type));
            }
        }
        return world;
    }

    @Override
    public HashMap<Position, UnitImpl> getUnitsLayout() {
        return units;
    }

    @Override
    public HashMap<Position, CityImpl> getCitiesLayout() {
        cities.put(new Position(8,12), new CityImpl(Player.RED));
        cities.put(new Position(4,5), new CityImpl(Player.BLUE));
        return cities;
    }
}
