package hotciv.standard;

import hotciv.framework.*;

import hotciv.standard.factories.DeltaCivFactory;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestDeltaCiv  {

    private GameImpl game;
    private String[] hills = new String[] {
                "hhhhhhhhhhhhhhhh",
                "hhhhhhhhhhhhhhhh",
                "hhhhhhhhhhhhhhhh",
                "hhhhhhhhhhhhhhhh",
                "hhhhhhhhhhhhhhhh",
                "hhhhhhhhhhhhhhhh",
                "hhhhhhhhhhhhhhhh",
                "hhhhhhhhhhhhhhhh",
                "hhhhhhhhhhhhhhhh",
                "hhhhhhhhhhhhhhhh",
                "hhhhhhhhhhhhhhhh",
                "hhhhhhhhhhhhhhhh",
                "hhhhhhhhhhhhhhhh",
                "hhhhhhhhhhhhhhhh",
                "hhhhhhhhhhhhhhhh",
                "hhhhhhhhhhhhhhhh"};


    @Before
    public void setUp () {
        game = new GameImpl(new DeltaCivFactory());
    }
    @Test
    public void shouldBeRedAsStartingPlayer() {
        assertThat(game, is(notNullValue()));
        assertThat(game.getPlayerInTurn(), is(Player.RED));
    }
    @Test
    public void mapShouldBeenCreated() {
        for (int i = 0; i < GameConstants.WORLDSIZE; i++) {
            for (int j = 0; j < GameConstants.WORLDSIZE; j++) {
                assertNotNull(game.getTileAt(new Position(i, j)));
            }
        }
        assertNull(game.getTileAt(new Position(16, 16)));
    }

    @Test
    public void gameStartsInYear4000BC() {
        assertThat(game.getAge(), is(-4000));
    }
    @Test
    public void gameAdvances100YearsPerRound() {
        for (int i = 0; i < 40; i++) {
            game.endOfTurn();
        }
        assertThat(game.getAge(), is(-2000));
    }
    @Test
    public void shouldBePlainAt0dot3 () {
        assertThat(game.getTileAt(new Position(0,3)).getTypeString(), is(GameConstants.PLAINS));

    }
    @Test
    public void shouldBeMountainAt0dot5 () {
        assertThat(game.getTileAt(new Position(0,5)).getTypeString(), is(GameConstants.MOUNTAINS));
    }
    @Test
    public void shouldBeMountainAt7dot13 () {
        assertThat(game.getTileAt(new Position(7,13)).getTypeString(), is(GameConstants.MOUNTAINS));
    }
    @Test
    public void shouldBeHillsAt1dot3 () {
        assertThat(game.getTileAt(new Position(1,3)).getTypeString(), is(GameConstants.HILLS));
    }
    @Test
    public void hillsAtAllTiles (){


        game.changeGameWorldLayout(new DynamicWorldLayoutStrategy(hills));

        assertThat(game.getTileAt(new Position(14,14)).getTypeString(), is(GameConstants.HILLS));

        for(int i = 0 ; i < GameConstants.WORLDSIZE; i ++){
            for(int j = 0; j < GameConstants.WORLDSIZE; j++){
               assertThat(game.getTileAt(new Position(i,j)).getTypeString(), is(GameConstants.HILLS));
            }
        }
    }
    @Test
    public void thereIsAForrestAt4dot4 () {
        assertThat(game.getTileAt(new Position(4,4 )).getTypeString(), is(GameConstants.FOREST));
    }
    @Test
    public void redHasACityAt8dot12 (){
        assertThat(game.getCityAt(new Position(8,12)).getOwner(), is(Player.RED));
    }
    @Test
    public void blueHasACityAt4dot5 () {
        assertThat(game.getCityAt(new Position(4,5)).getOwner(), is(Player.BLUE));
    }

}
