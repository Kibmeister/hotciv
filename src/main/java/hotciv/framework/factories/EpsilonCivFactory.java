package hotciv.framework.factories;

import hotciv.framework.*;

public class EpsilonCivFactory implements GameFactory {
    @Override
    public WinnerStrategy createWinnerStrategy() {
        return null;
    }

    @Override
    public AgingStrategy createAgingStrategy() {
        return null;
    }

    @Override
    public UnitStrategy createUnitStrategy() {
        return null;
    }

    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {
        return null;
    }

    @Override
    public AttackStrategy createAttackStrategy() {
        return null;
    }
}
