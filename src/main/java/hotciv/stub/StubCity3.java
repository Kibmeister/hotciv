package hotciv.stub;

import hotciv.framework.City;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;

public class StubCity3 implements City {


    @Override
    public Player getOwner() {
        return Player.YELLOW;
    }

    @Override
    public int getSize() {
       return 3;
    }

    @Override
    public int getTreasury() {
        return 10;
    }

    @Override
    public String getProduction() {
        return GameConstants.B52;
    }

    @Override
    public String getWorkforceFocus() {
        return GameConstants.productionFocus;
    }

    @Override
    public void setTreasury(int treasury) {

    }

    @Override
    public void setProduction(String t) {

    }
}
