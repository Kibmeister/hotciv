package hotciv.framework;

import hotciv.standard.CityImpl;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;

import java.util.*;

public interface WorldLayoutStrategy  {

    HashMap<Position, TileImpl> getWorldLayout();
    HashMap<Position, UnitImpl> getUnitsLayout();
    HashMap<Position, CityImpl> getCitiesLayout();

}
