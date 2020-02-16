package hotciv.standard;

import hotciv.framework.Tile;

public class TileImpl implements Tile {
    private String tileType;

    public TileImpl(String tileType){
        this.tileType = tileType;
    }
    @Override
    public String getTypeString() {
        return tileType;
    }
}
