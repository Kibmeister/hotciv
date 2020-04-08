package hotciv.standard;

import hotciv.framework.*;
import org.junit.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class TestEpsilonCiv {

    private Game game;

    @Before
    public void setUp(){
        this.game = new GameImpl(
                new EpsilonWinnerStrategy(),
                new AlphaAgingStrategy(),
                new AlphaUnitStrategy(),
                new AlphaWorldLayoutStrategy(),
                new EpsilonAttackStrategy (new FixedBattleProbability()));
    }

    @Test
    public void shouldBeRedAsStartingPlayer() {
        assertThat(game, is(notNullValue()));
        assertThat(game.getPlayerInTurn(), is(Player.RED));
    }

    @Test
    public void redHasACityAt1dot1() {
        assertThat(game.getCityAt(new Position(1, 1)).getOwner(), is(Player.RED));
    }

    @Test
    public void oceanAt1dot0() {
        assertThat(game.getTileAt(new Position(1, 0)).getTypeString(), is(GameConstants.OCEANS));
    }

    @Test
    public void tilePlainShouldBeTilePlain() {
        Tile t = new TileImpl(GameConstants.PLAINS);
        assertThat(t.getTypeString(), is(GameConstants.PLAINS));
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
    public void anArcherCanOnlyMoveOneDistanceBetweenTwoTiles() {
        assertThat(game.getUnitAt(new Position(2, 0)).getTypeString(), is(GameConstants.ARCHER));
        assertFalse(game.moveUnit(new Position(2, 0), new Position(4, 0)));
    }
    @Test
    public void getUnitCostOfArcherIs10() {
        assertThat(game.getUnitAt(new Position(2, 0)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(GameConstants.getUnitCost(GameConstants.ARCHER), is(10));
    }
    @Test
    public void citiesStayAtPopulation1() {
        assertThat(game.getCityAt(new Position(1, 1)).getSize(), is(1));
        game.getCityAt(new Position(1, 1)).getWorkforceFocus();
        for (int i = 1; i < 4; i++) {
            game.endOfTurn();
        }
        assertThat(game.getCityAt(new Position(1, 1)).getSize(), is(1));
    }
    @Test public void shouldGiveCorrectTerrainFactors() {
        // plains have multiplier 1
        assertThat(Utility2.getTerrainFactor(game, new Position(0,0)), is(1));
        // hills have multiplier 2
        assertThat(Utility2.getTerrainFactor(game, new Position(0,1)), is(2));
        // forest have multiplier 2
        assertThat(Utility2.getTerrainFactor(game, new Position(10,10)), is(2));
        // cities have multiplier 3
        assertThat(Utility2.getTerrainFactor(game, new Position(1,1)), is(3));

    }

    @Test public void shouldGiveSum0ForBlueAtP3_2() {
        assertThat("Blue unit at (3,2) should get +0 support",
                Utility2.getFriendlySupport( game, new Position(3,2), Player.BLUE), is(+0));
    }
    @Test public void shouldGiveSum1ForBlueAtP2_4() {
        game.createUnit(new Position(3,5), new UnitImpl(Player.BLUE, GameConstants.LEGION));
        assertThat("Blue unit at (2,4) should get +1 support",
                Utility2.getFriendlySupport( game, new Position(2,4), Player.BLUE), is(+1));
    }
    @Test public void shouldGiveSum3ForRedAtP2_0() {
        game.createUnit(new Position(3,0), new UnitImpl(Player.RED, GameConstants.ARCHER));
        game.createUnit(new Position(2,1), new UnitImpl(Player.RED, GameConstants.LEGION));
        assertThat("Red unit at (2,0) should get +3 support",
                Utility2.getFriendlySupport( game, new Position(2,0), Player.RED), is(+3));
    }
    @Test public void shouldGiveSum2ForRedAtP1_1() {
        game.createUnit(new Position(1,2), new UnitImpl(Player.RED, GameConstants.ARCHER));
        assertThat("Red unit at (1,1) should get +2 support",
                Utility2.getFriendlySupport( game, new Position(1,1), Player.RED), is(+2));
    }
    @Test
    public void theStrongestPlayerWinsTheBattle(){
        GameImpl game = new GameImpl(
                new EpsilonWinnerStrategy(),
                new AlphaAgingStrategy(),
                new AlphaUnitStrategy(),
                new AlphaWorldLayoutStrategy(),
                new EpsilonAttackStrategy (new FixedBattleProbability(5,1)));
        game.createUnit(new Position(11,11), new UnitImpl(Player.RED, GameConstants.LEGION)); // 4+ attack strength
        game.createUnit(new Position(12,12), new UnitImpl(Player.BLUE, GameConstants.LEGION)); // +2 defensive strength
        assertThat(Utility2.getFriendlySupport(game ,new Position (11,11), game.getUnitAt(new Position (11,11)).getOwner()), is(0)); // +0 attack strength
        assertThat(Utility2.getFriendlySupport(game ,new Position (12,12), game.getUnitAt(new Position (12,12)).getOwner()), is(0)); // +0 defensive strength
        assertThat(Utility2.getTerrainFactor(game, new Position(11,11)), is(1));
        assertThat(Utility2.getTerrainFactor(game, new Position(12,12)), is(1));
        game.moveUnit(new Position(11,11), new Position(12,12));
        assertThat(game.getUnitAt(new Position(12,12)).getOwner(), is(Player.RED));
    }
    @Test
    public void thereIsADefeatIfTheDefendingPlayerIsStrongest () {
        GameImpl game = new GameImpl(
                new EpsilonWinnerStrategy(),
                new AlphaAgingStrategy(),
                new AlphaUnitStrategy(),
                new AlphaWorldLayoutStrategy(),
                new EpsilonAttackStrategy (new FixedBattleProbability(1,10)));
        game.createUnit(new Position(11,11), new UnitImpl(Player.RED, GameConstants.LEGION)); // 4+ attack strength
        game.createUnit(new Position(12,12), new UnitImpl(Player.BLUE, GameConstants.LEGION)); // +2 defensive strength
        assertThat(Utility2.getFriendlySupport(game ,new Position (11,11), game.getUnitAt(new Position (11,11)).getOwner()), is(0)); // +0 attack strength
        assertThat(Utility2.getFriendlySupport(game ,new Position (12,12), game.getUnitAt(new Position (12,12)).getOwner()), is(0)); // +0 defensive strength
        assertThat(Utility2.getTerrainFactor(game, new Position(11,11)), is(1));
        assertThat(Utility2.getTerrainFactor(game, new Position(12,12)), is(1));
        game.moveUnit(new Position(11,11), new Position(12,12));
        assertThat(game.getUnitAt(new Position(12,12)).getOwner(), is(Player.BLUE));
        assertThat(game.getUnitAt(new Position(11,11)), is(nullValue()));
    }

    @Test
    public void friendlySupportIsAddedAsStrengthIfPresent () {

    }




}
