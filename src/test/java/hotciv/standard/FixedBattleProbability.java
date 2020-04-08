package hotciv.standard;

import hotciv.framework.BattleProbability;

public class FixedBattleProbability implements BattleProbability {

    private int attackerProbability, defenderProbability;

    public FixedBattleProbability() {
        this.attackerProbability = 1;
        this.defenderProbability = 1;
    }
    public FixedBattleProbability (int attackerProbability, int defenderProbability){
        this.attackerProbability = attackerProbability;
        this.defenderProbability = defenderProbability;
    }


    @Override
    public int getAttackerProbability() {
        return attackerProbability;
    }

    @Override
    public int getDefenderProbability() {
        return defenderProbability;
    }
}
