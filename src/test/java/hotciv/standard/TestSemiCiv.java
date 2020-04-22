package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.Utility2;
import hotciv.standard.factories.EpsilonCivFactory;
import hotciv.standard.factories.SemiCivFactory;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;

public class TestSemiCiv  {

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
    public void setUp(){
        game = new GameImpl(new SemiCivFactory());
    }

    public void roundCounter (int i) {
        for(int j = 0; j < i; j ++){
            game.endOfTurn();
            game.endOfTurn();
        }
    }

    @Test
    public void theGameProgressesWith100YearsPerRoundBetween4000BCto1000BC() {
        assertThat(game.getAge(), is (-4000));
        roundCounter(30);

        assertThat(game.getAge(), is(-1000));
    }
    @Test
    public void afterRound39theYearIs100BC(){
        assertThat(game.getAge(), is(-4000));
        roundCounter(39);
        assertThat(game.getAge(), is(-100));
    }
    @Test
    public void afterRound40theYearIs1BC () {
        assertThat(game.getAge(), is(-4000));
        roundCounter(40);
        assertThat(game.getAge(), is(-1));
    }
    @Test
    public void afterRound41theYearIs1AD () {
        assertThat(game.getAge(), is(-4000));
        roundCounter(41);
        assertThat(game.getAge(), is(1));
    }
    @Test
    public void afterRound42theYearIs50AD (){
        assertThat(game.getAge(), is(-4000));
        roundCounter(42);
        assertThat(game.getAge(), is(50));
    }
    @Test
    public void afterRound43theYearIs100AD (){
        assertThat(game.getAge(), is(-4000));
        roundCounter(43);
        assertThat(game.getAge(), is(100));
    }


    @Test
    public void afterRound76theYearIs1750AD () {
        assertThat(game.getAge(), is(-4000));
        roundCounter(76);
        assertThat(game.getAge(), is(1750));
    }

    @Test
    public void afterRound77theYearIs1775() {
        assertThat(game.getAge(), is(-4000));
        roundCounter(77);
        assertThat(game.getAge(), is(1775));
    }
    @Test
    public void afterRound82theYearIs1900AD () {
        assertThat(game.getAge(), is(-4000));
        roundCounter(82);
        assertThat(game.getAge(), is(1900));
    }
    @Test
    public void afterRound83theYearIs1905AD () {
        assertThat(game.getAge(), is(-4000));
        roundCounter(83);
        assertThat(game.getAge(), is(1905));
    }
    @Test
    public void afterRound96theYearIs1970AD () {
        assertThat(game.getAge(), is(-4000));
        roundCounter(96);
        assertThat(game.getAge(), is(1970));
    }
    @Test
    public void afterRound97thYearIs1971AD () {
        assertThat(game.getAge(), is(-4000));
        roundCounter(97);
        assertThat(game.getAge(), is(1971));
    }
    @Test
    public void afterRound98theYearIs1972 () {
        assertThat(game.getAge(), is(-4000));
        roundCounter(98);
        assertThat(game.getAge(), is(1972));
    }

    @Test
    public void settlerAt4dot3IsReplacedWithACityAfterItsUnitsAction (){
        game.createUnit(new Position(4,3), new UnitImpl(Player.RED, GameConstants.SETTLER));
        assertThat(game.getUnitAt(new Position(4,3)), is (notNullValue()));
        game.performUnitActionAt(new Position(4,3));
        assertThat(game.getUnitAt(new Position(4,3)), is(nullValue()));
        assertThat(game.getCityAt(new Position(4,3)), is(notNullValue()));
    }

    @Test
    public void theNewCityThatIsSpawnedHasSizeOfOne () {
        game.createUnit(new Position(4,3), new UnitImpl(Player.RED, GameConstants.SETTLER));
        assertThat(game.getUnitAt(new Position(4,3)), is (notNullValue()));
        game.performUnitActionAt(new Position(4,3));
        assertThat(game.getUnitAt(new Position(4,3)), is(nullValue()));
        assertThat(game.getCityAt(new Position(4,3)).getSize(), is(1));

    }
    @Test
    public void theOwnerOfTheCityIsTheSameAsTheOwnerOfTheSettlerUnit () {
        game.createUnit(new Position(4,3), new UnitImpl(Player.RED, GameConstants.SETTLER));
        assertThat(game.getUnitAt(new Position(4,3)).getOwner(), is (Player.RED));
        game.performUnitActionAt(new Position(4,3));
        assertThat(game.getUnitAt(new Position(4,3)), is(nullValue()));
        assertThat(game.getCityAt(new Position(4,3)).getOwner(), is(Player.RED));
    }
    @Test
    public void archerAt2dot0defensiveStrengthIsDoubledIfUnitActionInitialized () {
        game.createUnit(new Position(2,0), new UnitImpl(Player.RED, GameConstants.ARCHER));
        assertThat(game.getUnitAt(new Position(2,0)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(new Position(2,0 )).getDefensiveStrength(), is(GameConstants.ARCHER_DEFENCE));
        game.performUnitActionAt(new Position(2,0 ));
        assertThat(game.getUnitAt(new Position(2,0 )).getDefensiveStrength(), is(GameConstants.ARCHER_DEFENCE * 2));
    }
    @Test
    public void archerAt2dot0isStationaryAfterItsUnitActionHasBeenPerformed () {
        game.createUnit(new Position(2,0), new UnitImpl(Player.RED, GameConstants.ARCHER));
        assertThat(game.getUnitAt(new Position(2,0 )).getTypeString(), is(GameConstants.ARCHER));
        game.performUnitActionAt(new Position(2,0));
        assertFalse(game.moveUnit(new Position(2,0), new Position(2,1)));
    }
    @Test
    public void invokingUnitActionAtArcher2dot0ThatIsFortifiedRemovesItsFortification () {
        game.createUnit(new Position(2,0), new UnitImpl(Player.RED, GameConstants.ARCHER));
        assertThat(game.getUnitAt(new Position(2,0 )).getTypeString(), is(GameConstants.ARCHER));
        game.performUnitActionAt(new Position(2,0));
        assertFalse(game.moveUnit(new Position(2,0), new Position(2,1)));
        game.performUnitActionAt(new Position(2,0));
        assertThat(game.getUnitAt(new Position(2,0)).getUnitAction(), is(true));

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
        game.createUnit(new Position(1,1), new UnitImpl(Player.RED, GameConstants.ARCHER));
        game.createUnit(new Position(3,0), new UnitImpl(Player.RED, GameConstants.ARCHER));
        game.createUnit(new Position(2,1), new UnitImpl(Player.RED, GameConstants.LEGION));
        assertThat("Red unit at (2,0) should get +3 support",
                Utility2.getFriendlySupport( game, new Position(2,0), Player.RED), is(+3));
    }
    @Test public void shouldGiveSum2ForRedAtP1_1() {
        game.createUnit(new Position(1,2), new UnitImpl(Player.RED, GameConstants.ARCHER));
        game.createUnit(new Position(0,0), new UnitImpl(Player.RED, GameConstants.ARCHER));
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
