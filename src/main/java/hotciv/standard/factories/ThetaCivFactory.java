package hotciv.standard.factories;

import hotciv.framework.*;
import hotciv.standard.*;

public class ThetaCivFactory implements GameFactory {
    @Override
    public WinnerStrategy createWinnerStrategy() {
        return new Red3000BCWinnerStrategy();
    }

    @Override
    public AgingStrategy createAgingStrategy() {
        return new LinearAgeStrategy();
    }

    @Override
    public UnitStrategy createUnitStrategy() {
        return new B25UnitStrategy();
    }

    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {
        return new RegularWorldLayoutStrategy();
    }

    @Override
    public AttackStrategy createAttackStrategy() {
        return new NoActionAttackStrategy();
    }
}
