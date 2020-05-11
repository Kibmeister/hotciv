package hotciv.standard;

import hotciv.framework.Tile;

import java.util.UUID;

public class TileImpl implements Tile {


    private String tileType;
    private final String id;

    public TileImpl(String tileType) {
        this.tileType = tileType;
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public String getTypeString() {
        return tileType;
    }

    @Override
    public String getId() {
        return id;
    }
}
