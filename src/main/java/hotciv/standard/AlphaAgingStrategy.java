package hotciv.standard;

import hotciv.framework.AgingStrategy;

public class AlphaAgingStrategy implements AgingStrategy {
    @Override
    public int worldAges() {
        return 100;
    }
}
