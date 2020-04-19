package hotciv.standard;

import hotciv.framework.AgingStrategy;

public class LinearAgeStrategy implements AgingStrategy {
    @Override
    public int worldAges() {
        return 100;
    }
}
