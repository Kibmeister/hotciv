package hotciv.standard;

import hotciv.framework.*;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.PostConstruct;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

public class TestBetaCiv {

    private Game game;

    @Before
    public void setUp (){
        this.game = new GameImpl(new BetaWinnerStrategy(), new BetaAgingStrategy ());
    }
    public void roundCounter (int i) {
        for(int j = 0; j < i; j ++){
            game.endOfTurn();
            game.endOfTurn();
        }
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
    public void hillAtTile0dot1() {
        assertThat(game.getTileAt(new Position(0, 1)).getTypeString(), is(GameConstants.HILLS));
    }

    @Test
    public void mountainAt2dot2() {
        assertThat(game.getTileAt(new Position(2, 2)).getTypeString(), is(GameConstants.MOUNTAINS));
    }

    @Test
    public void tilesAreInGeneralOfTypePlain() {
        assertThat(game.getTileAt(new Position(1, 5)).getTypeString(), is(GameConstants.PLAINS));
        assertThat(game.getTileAt(new Position(2, 5)).getTypeString(), is(GameConstants.PLAINS));
        assertThat(game.getTileAt(new Position(3, 5)).getTypeString(), is(GameConstants.PLAINS));
        assertThat(game.getTileAt(new Position(4, 5)).getTypeString(), is(GameConstants.PLAINS));
        assertThat(game.getTileAt(new Position(5, 5)).getTypeString(), is(GameConstants.PLAINS));
    }

    @Test
    public void blueHasACityAt4dot1() {
        assertThat(game.getCityAt(new Position(4, 1)).getOwner(), is(Player.BLUE));
    }

    @Test
    public void gameStartsInYear4000BC() {
        assertThat(game.getAge(), is(-4000));
    }

    @Test
    public void afterRedItIsBluesesTurn() {
        assertThat(game.getPlayerInTurn(), is(Player.RED));
        game.endOfTurn();
        assertThat(game.getPlayerInTurn(), is(Player.BLUE));
    }

    @Test
    public void unitCanNotMoveOverOceanAt1dot0() {
        assertThat(game.getTileAt(new Position(1, 0)).getTypeString(), is(GameConstants.OCEANS));
        assertFalse(game.moveUnit(new Position(2, 0), new Position(1, 0)));
    }

    @Test
    public void moveUnitActuallyMovesTheUnit() {
        game.endOfTurn();
        game.createUnit(new Position(4, 4), new UnitImpl(Player.BLUE, GameConstants.LEGION));
        assertThat(game.getUnitAt(new Position(4, 4)).getTypeString(), is(GameConstants.LEGION));
        game.moveUnit(new Position(4, 4), new Position(4, 5));
        assertThat(game.getUnitAt(new Position(4, 5)).getTypeString(), is(GameConstants.LEGION));
    }

    @Test
    public void moveCountIsDeductedFromUnitAfterMove() {
        assertThat(game.getUnitAt(new Position(4, 3)).getMoveCount(), is(1));
        game.moveUnit(new Position(4, 3), new Position(5, 4));
        assertThat(game.getUnitAt(new Position(5, 4)), is(notNullValue()));
        assertThat(game.getUnitAt(new Position(5, 4)).getMoveCount(), is(0));

    }

    @Test
    public void moveCountHasToBeLargerThan0ForTheUnitToMove() {
        assertThat(game.getUnitAt(new Position(4, 3)).getMoveCount(), is(1));
        game.moveUnit(new Position(4, 3), new Position(4, 2));
        assertThat(game.getUnitAt(new Position(4, 2)).getMoveCount(), is(0));
        assertFalse(game.moveUnit(new Position(4, 2), new Position(5, 3)));

    }

    @Test
    public void unistAreInitializedToMaximalMoveCountAtStartOfEachRound() {
        assertThat(game.getUnitAt(new Position(4, 3)), is(notNullValue()));
        game.moveUnit(new Position(4, 3), new Position(5, 4));
        assertThat(game.getUnitAt(new Position(5, 4)).getMoveCount(), is(0));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(new Position(5, 4)).getMoveCount(), is(1));

    }

    @Test
    public void citiesProduce6ProductionAfterEachRound() {
        assertThat(game.getCityAt(new Position(1, 1)), is(notNullValue()));
        assertThat(game.getCityAt(new Position(1, 1)).getTreasury(), is(0));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getCityAt(new Position(1, 1)).getTreasury(), is(6));
    }

    @Test
    public void citiesPopulationSizeIsAlways1() {
        assertThat(game.getCityAt(new Position(1, 1)).getSize(), is(1));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getCityAt(new Position(1, 1)).getSize(), is(1));
    }

    @Test
    public void shouldSetProductionFocusForACity() {
        assertThat(game.getCityAt(new Position(1, 1)), is(notNullValue()));
        game.getCityAt(new Position(1, 1)).setProduction(GameConstants.ARCHER);
        assertThat(game.getCityAt(new Position(1, 1)).getProduction(), is(GameConstants.ARCHER));
    }

    @Test
    public void citiesAccumulateProductionOverMoreRounds() {
        assertThat(game.getCityAt(new Position(4, 1)), is(notNullValue())); // there is a city at 4,3
        game.getCityAt(new Position(4, 1)).setProduction("No unit type");
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getCityAt(new Position(4, 1)).getTreasury(), is(6));

    }

    @Test
    public void getUnitCostOfArcherIs10() {
        assertThat(game.getUnitAt(new Position(2, 0)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(GameConstants.getUnitCost(GameConstants.ARCHER), is(10));
    }

    @Test
    public void aCityPlacesTheUnitSetForProductionInTheCityIfNoOtherUnitPresent() {
        assertThat(game.getCityAt(new Position(4, 1)), is(notNullValue()));
        assertThat(game.getUnitAt(new Position(4, 1)), is(nullValue()));
        game.getCityAt(new Position(4, 1)).setProduction(GameConstants.ARCHER);
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(new Position(4, 1)), is(notNullValue()));

    }

    @Test
    public void aCityPlacesTheUnitSetForProductionAroundTheCityWhenUnitIsAlreadyPresent() {
        assertThat(game.getCityAt(new Position(1, 1)), is(notNullValue()));
        assertThat(game.getUnitAt(new Position(1, 1)), is(notNullValue()));
        game.getCityAt(new Position(1, 1)).setProduction(GameConstants.ARCHER);
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(new Position(0, 1)), is(notNullValue()));

    }

    @Test
    public void whenAcityHasAccumulatedEnoughProductionItProducesUnitSetForProduction() {
        assertThat(game.getCityAt(new Position(4, 1)), is(notNullValue()));
        assertThat(game.getUnitAt(new Position(4, 1)), is(nullValue()));
        game.getCityAt(new Position(4, 1)).setProduction(GameConstants.LEGION);
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(new Position(4, 1)), is(nullValue()));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(new Position(4, 1)).getTypeString(), is(GameConstants.LEGION));
    }

    @Test
    public void theUnitCostIsDeductedFromTheCitiesTreasury() {
        assertThat(game.getCityAt(new Position(4, 1)), is(notNullValue()));
        assertThat(game.getUnitAt(new Position(4, 1)), is(nullValue()));
        game.getCityAt(new Position(4, 1)).setProduction(GameConstants.LEGION);
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getCityAt(new Position(4, 1)).getTreasury(), is(6));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getCityAt(new Position(4, 1)).getTreasury(), is(12));
        assertThat(game.getUnitAt(new Position(4, 1)), is(nullValue()));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(new Position(4, 1)).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getCityAt(new Position(4, 1)).getTreasury(), is(3));
    }

    @Test
    public void shouldOnlyProduceOneUnitOnOneAdjacentTileToCityAt10dot10() {
        game.createCity(new Position(10, 10), new CityImpl(Player.RED));
        game.createUnit(new Position(10, 10), new UnitImpl(Player.RED, GameConstants.ARCHER));
        game.endOfTurn();
        game.endOfTurn();// 6
        game.endOfTurn();
        game.endOfTurn(); // 12
        assertThat(game.getUnitAt(new Position(9, 10)).getTypeString(), is(GameConstants.ARCHER)); // 2
        assertThat(game.getUnitAt(new Position(9, 11)), is(nullValue()));
        assertThat(game.getUnitAt(new Position(8, 11)), is(nullValue()));
    }

    @Test
    public void theUnitLocatedInACityBecomesTheOwnerOfTheCity () {
        assertThat(game.getCityAt(new Position(4,1)).getOwner(), is(Player.BLUE));
        game.createUnit(new Position(3,0), new UnitImpl(Player.RED, GameConstants.ARCHER));
        assertThat(game.getUnitAt(new Position(3,0)).getOwner(), is(Player.RED));
        game.moveUnit(new Position(3,0), new Position(4,1));
        assertThat(game.getUnitAt(new Position(4,1)).getOwner(), is(Player.RED));
        assertThat(game.getCityAt(new Position(4,1)).getOwner(), is(Player.RED));

    }


    @Test
    public void theWinnerIsTheFirstPlayerToConcurAllTheCitiesInTheWorld (){
        assertThat(game.getCityAt(new Position(1,1)).getOwner(), is(Player.RED));
        assertThat(game.getCityAt(new Position(4,1)).getOwner(), is(Player.BLUE));
        game.endOfTurn();
        game.createUnit(new Position(1,2), new UnitImpl(Player.BLUE, GameConstants.ARCHER));
        game.moveUnit(new Position(1,2), new Position(1,1));
        assertThat(game.getUnitAt(new Position(1,1)).getOwner() , is(Player.BLUE));
        assertThat(game.getWinner(), is(Player.BLUE));
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
}
