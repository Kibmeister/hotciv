package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestGammaCiv {

    private Game game;

    @Before
    public void setUp(){
        game = new GameImpl(new AlphaWinnerStrategy(),
                new AlphaAgingStrategy(),
                new GammaUnitStrategy(),
                new AlphaWorldLayoutStrategy(),
                new AlphaAttackStrategy());
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
    public void gameAdvances100YearsPerRound() {
        for (int i = 0; i < 40; i++) {
            game.endOfTurn();
        }
        assertThat(game.getAge(), is(-2000));
    }
    @Test
    public void redWinsIn3000BC() {
        for (int i = 0; i < 20; i++) {
            game.endOfTurn();
        }
        assertThat(game.getAge(), is(-3000));
        assertThat(game.getWinner(), is(Player.RED));
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
    public void settlerAt4dot3IsReplacedWithACityAfterItsUnitsAction (){
        assertThat(game.getUnitAt(new Position(4,3)), is (notNullValue()));
        game.performUnitActionAt(new Position(4,3));
        assertThat(game.getUnitAt(new Position(4,3)), is(nullValue()));
        assertThat(game.getCityAt(new Position(4,3)), is(notNullValue()));
    }

    @Test
    public void theNewCityThatIsSpawnedHasSizeOfOne () {
        assertThat(game.getUnitAt(new Position(4,3)), is (notNullValue()));
        game.performUnitActionAt(new Position(4,3));
        assertThat(game.getUnitAt(new Position(4,3)), is(nullValue()));
        assertThat(game.getCityAt(new Position(4,3)).getSize(), is(1));

    }
    @Test
    public void theOwnerOfTheCityIsTheSameAsTheOwnerOfTheSettlerUnit () {
        assertThat(game.getUnitAt(new Position(4,3)).getOwner(), is (Player.RED));
        game.performUnitActionAt(new Position(4,3));
        assertThat(game.getUnitAt(new Position(4,3)), is(nullValue()));
        assertThat(game.getCityAt(new Position(4,3)).getOwner(), is(Player.RED));
    }
    @Test
    public void archerAt2dot0defensiveStrengthIsDoubledIfUnitActionInitialized () {
        assertThat(game.getUnitAt(new Position(2,0)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(new Position(2,0 )).getDefensiveStrength(), is(GameConstants.ARCHER_DEFENCE));
        game.performUnitActionAt(new Position(2,0 ));
        assertThat(game.getUnitAt(new Position(2,0 )).getDefensiveStrength(), is(GameConstants.ARCHER_DEFENCE * 2));
    }
    @Test
    public void archerAt2dot0isStationaryAfterItsUnitActionHasBeenPerformed () {
        assertThat(game.getUnitAt(new Position(2,0 )).getTypeString(), is(GameConstants.ARCHER));
        game.performUnitActionAt(new Position(2,0));
        assertFalse(game.moveUnit(new Position(2,0), new Position(2,1)));
    }
    @Test
    public void invokingUnitActionAtArcher2dot0ThatIsFortifiedRemovesItsFortification () {
        assertThat(game.getUnitAt(new Position(2,0 )).getTypeString(), is(GameConstants.ARCHER));
        game.performUnitActionAt(new Position(2,0));
        assertFalse(game.moveUnit(new Position(2,0), new Position(2,1)));
        game.performUnitActionAt(new Position(2,0));
        assertThat(game.getUnitAt(new Position(2,0)).getUnitAction(), is(true));

    }



}
