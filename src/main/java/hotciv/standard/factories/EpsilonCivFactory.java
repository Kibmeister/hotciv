package hotciv.standard.factories;

import hotciv.framework.*;
import hotciv.standard.*;

public class EpsilonCivFactory implements GameFactory {
    public BattleProbability battleProbability;

    public EpsilonCivFactory () {
        this.battleProbability = new RandomBattleProbability();
    }
    public EpsilonCivFactory (BattleProbability battleProbability){
        this.battleProbability = battleProbability;
    }


    @Override
    public WinnerStrategy createWinnerStrategy() {
        return new ThreeAttacksWinnerStrategy();
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
        return new BattleAttackStrategy(battleProbability) ;
    }
}
