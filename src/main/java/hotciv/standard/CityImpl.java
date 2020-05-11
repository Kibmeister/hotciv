package hotciv.standard;

import hotciv.framework.City;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;

import java.util.UUID;

public class CityImpl implements City {

    private Player cityOwner;
    private String production;
    private int treasury, population;
    private final String id;

    public CityImpl(Player cityOwner){
        this.cityOwner = cityOwner;
        this.population = 1;
        this.production = GameConstants.ARCHER;
        this.id = UUID.randomUUID().toString();
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

    @Override
    public String getId() {
        return id;
    }


    public void deductTreasury(int i) {
        this.treasury = i;
    }

    public void setOwner(Player owner) {
        this.cityOwner = owner;
    }

    public int getPopulation() {
        return this.population;
    }

    public void incrementPopulation(int i) {
        this.population += i;
    }
}
