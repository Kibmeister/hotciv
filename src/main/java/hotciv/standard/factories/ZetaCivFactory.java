package hotciv.standard.factories;

import hotciv.framework.*;
import hotciv.standard.*;

public class ZetaCivFactory implements GameFactory {
    @Override
    public WinnerStrategy createWinnerStrategy() {
        return new ShiftingWinnerStrategy(new AllCitiesWinnerStrategy(), new ThreeAttacksWinnerStrategy());
    }

    @Override
    public AgingStrategy createAgingStrategy() {
        return new LinearAgeStrategy();
    }

    @Override
    public UnitStrategy createUnitStrategy() {
        return new NoActionUnitStrategy();
    }

    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {
        return new RegularWorldLayoutStrategy();
    }

    @Override
    public AttackStrategy createAttackStrategy() {
        return new NoActionAttackStrategy ();
    }
}
