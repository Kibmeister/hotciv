package hotciv.standard;

import hotciv.framework.*;
import hotciv.standard.factories.EpsilonCivFactory;
import org.junit.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class TestEpsilonCiv {

    private Game game;

    @Before
    public void setUp(){
        this.game = new GameImpl(new EpsilonCivFactory());
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
        GameImpl game = new GameImpl(new EpsilonCivFactory( new FixedBattleProbability(10,1)));

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
        GameImpl game = new GameImpl(new EpsilonCivFactory(new FixedBattleProbability(1,10)));
        game.createUnit(new Position(11,11), new UnitImpl(Player.RED, GameConstants.LEGION)); // 4+ attack strength
        game.createUnit(new Position(12,12), new UnitImpl(Player.BLUE, GameConstants.LEGION)); // +2 defensive strength
        assertThat(Utility2.getFriendlySupport(game ,new Position (11,11), game.getUnitAt(new Position (11,11)).getOwner()), is(0)); // +0 attack strength
        assertThat(Utility2.getFriendlySupport(game ,new Position (12,12), game.getUnitAt(new Position (12,12)).getOwner()), is(0)); // +0 defensive strength
        assertThat(Utility2.getTerrainFactor(game, new Position(11,11)), is(1)); // *1 terrain factor
        assertThat(Utility2.getTerrainFactor(game, new Position(12,12)), is(1)); // *1 terrain factor
        game.moveUnit(new Position(11,11), new Position(12,12));
        assertThat(game.getUnitAt(new Position(12,12)).getOwner(), is(Player.BLUE));
        assertThat(game.getUnitAt(new Position(11,11)), is(nullValue()));
    }

    @Test
    public void defendingPlayerWinsWhenWinningTheBattleWithFriendlySupport () {
        GameImpl game = new GameImpl(new EpsilonCivFactory(new FixedBattleProbability(1,1)));
        game.createUnit(new Position(11,11), new UnitImpl(Player.RED, GameConstants.LEGION)); // 4+ attack strength
        game.createUnit(new Position(12,12), new UnitImpl(Player.BLUE, GameConstants.LEGION)); // +2 defensive strength
        game.createUnit(new Position(12,13), new UnitImpl(Player.BLUE, GameConstants.ARCHER)); // +1 defensive support strength
        game.createUnit(new Position(13,12), new UnitImpl(Player.BLUE, GameConstants.SETTLER)); // +1 defensive support strength
        assertThat(Utility2.getFriendlySupport(game ,new Position (11,11), game.getUnitAt(new Position (11,11)).getOwner()), is(0)); // +0 attack strength
        assertThat(Utility2.getFriendlySupport(game ,new Position (12,12), game.getUnitAt(new Position (12,12)).getOwner()), is(2)); // +2 defensive strength
        assertThat(Utility2.getTerrainFactor(game, new Position(11,11)), is(1)); // *1 terrain factor
        assertThat(Utility2.getTerrainFactor(game, new Position(12,12)), is(1)); // *1 terrain factor
        game.moveUnit(new Position(11,11), new Position(12,12)); // attack vs defend = (4+0)*1*1 vs (2+1+1)*1*1 = defender wins
        assertThat(game.getUnitAt(new Position(12,12)).getOwner(), is(Player.BLUE));
        assertThat(game.getUnitAt(new Position(11,11)), is(nullValue()));

    }
    @Test
    public void attackingPlayerWinsWhenGettingSupportFromFriendlyUnits () {
        GameImpl game = new GameImpl(new EpsilonCivFactory(new FixedBattleProbability(1,1)));
        game.createUnit(new Position(11,11), new UnitImpl(Player.RED, GameConstants.ARCHER)); // 2+ attack strength
        game.createUnit(new Position(11,10), new UnitImpl(Player.RED, GameConstants.SETTLER)); // +1 attack supportive strength

        game.createUnit(new Position(12,12), new UnitImpl(Player.BLUE, GameConstants.LEGION)); // +2 defensive strength
        assertThat(Utility2.getFriendlySupport(game ,new Position (11,11), game.getUnitAt(new Position (11,11)).getOwner()), is(1)); // +0 attack strength
        assertThat(Utility2.getFriendlySupport(game ,new Position (12,12), game.getUnitAt(new Position (12,12)).getOwner()), is(0)); // +0 defensive strength
        assertThat(Utility2.getTerrainFactor(game, new Position(11,11)), is(1)); // *1 terrain factor
        assertThat(Utility2.getTerrainFactor(game, new Position(12,12)), is(1)); // *1 terrain factor
        game.moveUnit(new Position(11,11), new Position(12,12)); // attack vs defend = (4+0)*1*1 vs (2+1+1)*1*1 = defender wins
        assertThat(game.getUnitAt(new Position(12,12)).getOwner(), is(Player.RED));
        assertThat(game.getUnitAt(new Position(12,12)).getTypeString(), is(GameConstants.ARCHER));
    }
    @Test
    public void theWinnerIsTheFirstToWinThreeSuccessfulAttacks () {
        GameImpl game = new GameImpl(new EpsilonCivFactory(new FixedBattleProbability(10,1)));
        assertNull(game.getWinner());
        game.createUnit(new Position(12, 12), new UnitImpl(Player.RED, GameConstants.ARCHER));    // red wins
        game.createUnit(new Position(12, 13), new UnitImpl(Player.BLUE, GameConstants.LEGION));
        game.moveUnit(new Position(12, 12), new Position(12, 13));
        assertThat(game.getUnitAt(new Position(12, 13)).getOwner(), is(Player.RED));

        game.endOfTurn();
        game.createUnit(new Position(9, 9), new UnitImpl(Player.RED, GameConstants.ARCHER));    // red wins
        game.createUnit(new Position(8, 9), new UnitImpl(Player.BLUE, GameConstants.LEGION));
        game.moveUnit(new Position(9, 9), new Position(8, 9));
        assertThat(game.getUnitAt(new Position(8, 9)).getOwner(), is(Player.RED));

        game.createUnit(new Position(14, 14), new UnitImpl(Player.BLUE, GameConstants.ARCHER));   // blue wins
        game.createUnit(new Position(13, 14), new UnitImpl(Player.RED, GameConstants.LEGION));
        game.moveUnit(new Position(14, 14), new Position(13, 14));
        assertThat(game.getUnitAt(new Position(13, 14)).getOwner(), is(Player.BLUE));

       game.createUnit(new Position(14,10 ), new UnitImpl(Player.RED, GameConstants.ARCHER));   // red wins
        game.createUnit(new Position(13, 9), new UnitImpl(Player.BLUE, GameConstants.LEGION));
        game.moveUnit(new Position(14, 10), new Position(13, 9));
        assertThat(game.getUnitAt(new Position(13, 9)).getOwner(), is(Player.RED));

       assertThat(game.getWinner(), is(Player.RED));
    }

    @Test
    public void threeSuccessfulDefeatsWontMakeYouAWinner (){
         GameImpl game = new GameImpl(new EpsilonCivFactory( new FixedBattleProbability(1,10)));
         assertNull(game.getWinner());
         game.createUnit(new Position(12, 12), new UnitImpl(Player.RED, GameConstants.ARCHER));    // BLUE wins
         game.createUnit(new Position(12, 13), new UnitImpl(Player.BLUE, GameConstants.LEGION));
         game.moveUnit(new Position(12, 12), new Position(12, 13));
         assertThat(game.getUnitAt(new Position(12, 13)).getOwner(), is(Player.BLUE));

         game.endOfTurn();
         game.createUnit(new Position(9, 9), new UnitImpl(Player.RED, GameConstants.ARCHER));    // BLUE wins
         game.createUnit(new Position(8, 9), new UnitImpl(Player.BLUE, GameConstants.LEGION));
         game.moveUnit(new Position(9, 9), new Position(8, 9));
         assertThat(game.getUnitAt(new Position(8, 9)).getOwner(), is(Player.BLUE));

         game.createUnit(new Position(14, 14), new UnitImpl(Player.BLUE, GameConstants.ARCHER));   // RED wins
         game.createUnit(new Position(13, 14), new UnitImpl(Player.RED, GameConstants.LEGION));
         game.moveUnit(new Position(14, 14), new Position(13, 14));
         assertThat(game.getUnitAt(new Position(13, 14)).getOwner(), is(Player.RED));

        game.createUnit(new Position(14,10 ), new UnitImpl(Player.RED, GameConstants.ARCHER));   // BLUE wins
         game.createUnit(new Position(13, 9), new UnitImpl(Player.BLUE, GameConstants.LEGION));
         game.moveUnit(new Position(14, 10), new Position(13, 9));
         assertThat(game.getUnitAt(new Position(13, 9)).getOwner(), is(Player.BLUE));

         assertThat(game.getWinner(), is(nullValue()));
    }
}
