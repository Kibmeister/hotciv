package hotciv.standard.factories;

import hotciv.framework.*;
import hotciv.standard.*;

public class SemiCivFactory implements GameFactory {

    @Override
    public WinnerStrategy createWinnerStrategy() {
        return new ThreeAttacksWinnerStrategy();
    }

    @Override
    public AgingStrategy createAgingStrategy() {
        return new VariableAgeStrategy();
    }

    @Override
    public UnitStrategy createUnitStrategy() {
        return new ActiveUnitStrategy();
    }

    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {
        return new DynamicWorldLayoutStrategy();
    }

    @Override
    public AttackStrategy createAttackStrategy() {
        return new BattleAttackStrategy (new RandomBattleProbability());
    }
}
