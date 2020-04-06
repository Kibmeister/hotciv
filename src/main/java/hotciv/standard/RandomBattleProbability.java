package hotciv.standard;

import hotciv.framework.BattleProbability;

import java.util.Random;

public class RandomBattleProbability implements BattleProbability {

    @Override
    public int getAttackerProbability() {
        return new Random().nextInt((1 - 6) + 1) + 1;
    }

    @Override
    public int getDefenderProbability() {
        return new Random().nextInt((1 - 6) + 1) + 1;
    }
}
