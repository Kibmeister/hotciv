package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.UnitStrategy;

public class B25UnitStrategy implements UnitStrategy {
    private UnitStrategy unitStrategy = new ActiveUnitStrategy();

    @Override
    public void unitAction(Position p, GameImpl game) {
        boolean b52AtPosition = game.getUnitAt(p).getTypeString().equals(GameConstants.B52);
        boolean cityAtLocation = game.getCities().containsKey(p);
        boolean forrestTerrain = game.getTileAt(p).getTypeString().equals(GameConstants.FOREST);

        if (b52AtPosition) {
            if (cityAtLocation) {
                bombCity(p, game);
            } else if (forrestTerrain) {
                changeTerrain(p, game);
            }
        } else {
            unitStrategy.unitAction(p, game);
        }


    }

    public void bombCity(Position p, GameImpl game) {
        boolean friendlyCity = game.getCityAt(p).getOwner().equals(game.getUnitAt(p).getOwner());
        if (!friendlyCity) {
            game.getCityAt(p).incrementPopulation(-1);
            if (game.getCityAt(p).getPopulation() < 1) {
                game.removeCity(p);
            }
        }
    }

    public void changeTerrain(Position p, GameImpl game) {
        game.changeTerrain(p, GameConstants.PLAINS);
    }
}
