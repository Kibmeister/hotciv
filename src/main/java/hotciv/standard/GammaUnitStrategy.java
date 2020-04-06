package hotciv.standard;

import hotciv.framework.*;


public class GammaUnitStrategy implements UnitStrategy {
    @Override
    public void unitAction(Position p, GameImpl game) {
        UnitImpl u = game.getUnitAt(p);
        if(u.getTypeString().equals(GameConstants.SETTLER)){
            game.createCity(p, new CityImpl(u.getOwner()));
            game.removeUnit(p);
        }
        else if(u.getTypeString().equals(GameConstants.ARCHER)){
            if(u.getUnitAction()){
                u.setDefensiveStrength(GameConstants.ARCHER_DEFENCE*2);
                u.setUnitAction(false);
            } else {
                u.setUnitAction(true);
            }

        }


    }
}
