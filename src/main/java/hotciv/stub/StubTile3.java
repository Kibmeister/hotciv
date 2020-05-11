package hotciv.stub;

import hotciv.framework.GameConstants;
import hotciv.framework.Tile;

public class StubTile3 implements Tile {
    @Override
    public String getTypeString() {
        return GameConstants.MOUNTAINS;
    }

    @Override
    public String getId() {
        return null;
    }
}
