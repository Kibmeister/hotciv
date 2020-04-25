package hotciv.standard;

import hotciv.framework.*;

import hotciv.standard.factories.*;
import org.junit.*;

import java.util.HashSet;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestFractalMaps {

@Test
    public void testGenerator () {
    HashSet<String> tiles = new HashSet<>(); //hasSet, only keeps one entry for a specific sting
    for(int i = 0; i < 25; i ++){
        Game game = new GameImpl(new FractalMapFactory()); // 25 different game instance
        tiles.add(game.getTileAt(new Position(0,0)).getTypeString()); //add tiletype of 0.0 to tiles hasSet for each game instance
    }
    System.out.println(tiles);
    assertTrue(tiles.size() > 1);
}
}
