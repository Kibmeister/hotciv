package hotciv.standard.factories;

import hotciv.framework.*;
import hotciv.standard.*;

public class BetaCivFactory implements GameFactory {
    @Override
    public WinnerStrategy createWinnerStrategy() {
        return new AllCitiesWinnerStrategy();
    }

    @Override
    public AgingStrategy createAgingStrategy() {
        return new VariableAgeStrategy ();
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
        return new NoActionAttackStrategy();
    }
}
