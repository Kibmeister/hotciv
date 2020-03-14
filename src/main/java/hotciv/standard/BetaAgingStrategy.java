package hotciv.standard;

import hotciv.framework.AgingStrategy;

public class BetaAgingStrategy implements AgingStrategy {

    int roundNumber;

    @Override
    public int worldAges() {
        roundNumber++;
        if (roundNumber <= 39) {
            return 100;
        } else if (roundNumber <= 40) {
            return 99;
        } else if (roundNumber <= 41) {
            return 2;
        } else if (roundNumber <= 42) {
            return 49;
        } else if (roundNumber <= 43 || roundNumber <= 76) {
            return 50;
        } else if (roundNumber <= 77 || roundNumber <= 82) {
            return 25;
        } else if (roundNumber <= 83 || roundNumber <= 96) {
            return 5;
        }
        return 1;
    }
}
