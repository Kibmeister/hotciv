package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.WorldLayoutStrategy;
import thirdparty.ThirdPartyFractalGenerator;

import java.util.HashMap;

public class FractalMapWorldLayoutStrategy implements WorldLayoutStrategy {
    private WorldLayoutStrategy worldLayoutStrategy;
    private ThirdPartyFractalGenerator generator = new ThirdPartyFractalGenerator();
    private String[] layout = new String[GameConstants.WORLDSIZE];


    public FractalMapWorldLayoutStrategy (){
        worldLayoutStrategy = new DynamicWorldLayoutStrategy(getFractalLayout());
    }

    private String[] getFractalLayout() {
        StringBuilder line;
        for(int r = 0; r < GameConstants.WORLDSIZE;r ++){
            line = new StringBuilder();
            for(int c = 0; c < GameConstants.WORLDSIZE; c ++){
                char tile = this.generator.getLandscapeAt(r,c);
                line.append(tile);
            }
            layout[r] = line.toString();
        }
        return layout;
    }


    @Override
    public HashMap<Position, TileImpl> getWorldLayout() {
        return worldLayoutStrategy.getWorldLayout();
    }

    @Override
    public HashMap<Position, UnitImpl> getUnitsLayout() {
        return worldLayoutStrategy.getUnitsLayout();
    }

    @Override
    public HashMap<Position, CityImpl> getCitiesLayout() {
        return worldLayoutStrategy.getCitiesLayout();
    }
}
