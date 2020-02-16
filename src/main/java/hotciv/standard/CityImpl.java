package hotciv.standard;

import hotciv.framework.City;
import hotciv.framework.Player;

public class CityImpl implements City {

    private Player cityOwner;

    public CityImpl(Player cityOwner){
        this.cityOwner = cityOwner;
    }

    @Override
    public Player getOwner() {
        return cityOwner;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public int getTreasury() {
        return 0;
    }

    @Override
    public String getProduction() {
        return null;
    }

    @Override
    public String getWorkforceFocus() {
        return null;
    }
}
