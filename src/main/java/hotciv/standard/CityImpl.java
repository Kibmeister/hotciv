package hotciv.standard;

import hotciv.framework.City;
import hotciv.framework.Player;

public class CityImpl implements City {

    private Player cityOwner;
    private String production;
    private int treasury, population;

    public CityImpl(Player cityOwner){
        this.cityOwner = cityOwner;
        this.population = 1;
    }

    @Override
    public Player getOwner() {
        return cityOwner;
    }

    @Override
    public int getSize() {
        return population;
    }

    @Override
    public int getTreasury() {
        return treasury;
    }

    @Override
    public String getProduction() {
        return production;
    }

    @Override
    public String getWorkforceFocus() {
        return null;
    }

    public void setTreasury (int treasury){
        this.treasury += treasury;
    }

    @Override
    public void setProduction(String t) {
        this.production = t;
    }

}
