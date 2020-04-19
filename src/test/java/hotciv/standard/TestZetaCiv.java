package hotciv.standard;

import hotciv.framework.*;

import hotciv.framework.factories.ZetaCivFactory;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestZetaCiv {
    private Game game;

    @Before
    public void setUp() {
        game = new GameImpl(new ZetaCivFactory());
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
    public void preRound20theWinnerIsTheFirstPlayerToConcurAllTheCitiesInTheWorld() {
        assertThat(game.getCityAt(new Position(1, 1)).getOwner(), is(Player.RED));
        assertThat(game.getCityAt(new Position(4, 1)).getOwner(), is(Player.BLUE));
        game.endOfTurn();
        game.createUnit(new Position(1, 2), new UnitImpl(Player.BLUE, GameConstants.ARCHER));
        game.moveUnit(new Position(1, 2), new Position(1, 1));
        assertThat(game.getUnitAt(new Position(1, 1)).getOwner(), is(Player.BLUE));
        assertThat(game.getCityAt(new Position(1, 1)).getOwner(), is(Player.BLUE));
        assertThat(game.getWinner(), is(Player.BLUE));
    }


    @Test
    public void after20RoundsTheWinnerIsTheFirstToWinThreeSuccessfulAttacks() {

        for (int i = 0; i < 40; i++) {
            game.endOfTurn();
        }

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

        game.createUnit(new Position(14, 10), new UnitImpl(Player.RED, GameConstants.ARCHER));   // red wins
        game.createUnit(new Position(13, 9), new UnitImpl(Player.BLUE, GameConstants.LEGION));
        game.moveUnit(new Position(14, 10), new Position(13, 9));
        assertThat(game.getUnitAt(new Position(13, 9)).getOwner(), is(Player.RED));

        assertThat(game.getWinner(), is(Player.RED));
    }

    @Test
    public void playerToExecuteThreeSuccessfulAttacksIsNotTheWinnerBeforeTheEndOfRound20() {

        for (int i = 0; i < 40; i++) {
            game.endOfTurn();
        }
        assertThat(game.getCityAt(new Position(1, 1)).getOwner(), is(Player.RED));
        assertThat(game.getCityAt(new Position(4, 1)).getOwner(), is(Player.BLUE));
        game.endOfTurn();
        game.createUnit(new Position(1, 2), new UnitImpl(Player.BLUE, GameConstants.ARCHER));
        game.moveUnit(new Position(1, 2), new Position(1, 1));
        assertThat(game.getUnitAt(new Position(1, 1)).getOwner(), is(Player.BLUE));
        assertThat(game.getCityAt(new Position(1, 1)).getOwner(), is(Player.BLUE));
        assertNull(game.getWinner());

    }

    @Test
    public void untilRound20HasEndedTheCountingOfAttacksWonIsNotCounted() {

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

        game.createUnit(new Position(14, 10), new UnitImpl(Player.RED, GameConstants.ARCHER));   // red wins
        game.createUnit(new Position(13, 9), new UnitImpl(Player.BLUE, GameConstants.LEGION));
        game.moveUnit(new Position(14, 10), new Position(13, 9));
        assertThat(game.getUnitAt(new Position(13, 9)).getOwner(), is(Player.RED));

        for (int i = 0; i < 40; i++) {
            game.endOfTurn();
        }

        assertNull(game.getWinner());

    }


}
