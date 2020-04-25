package hotciv.standard;

import hotciv.framework.*;
import hotciv.standard.factories.AlphaCivFactory;
import org.hamcrest.Factory;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;

public class TestGameTranscription {
    public Game game ; // type game
    public Game decoratee; // type game




    @Before
    public void setUp (){
        game = new GameImpl(new AlphaCivFactory());
        decoratee = new GameImpl(new AlphaCivFactory());
    }

    public void turnOnOffTranscript(){
        if(game == decoratee){
            decoratee = game;
            game = new GameDecorator(game);
        } else {
            game = decoratee;
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
    public void gameAdvances100YearsPerRound() {
        turnOnOffTranscript();
        for (int i = 0; i < 40; i++) {
            game.endOfTurn();
        }
        assertThat(game.getAge(), is(-2000));
    }
    @Test
    public void redUnitAt4dot3AttacksAndDefeatsBluesUnitAt3dot2() {
        turnOnOffTranscript();
        assertThat(game.getUnitAt(new Position(3, 2)).getOwner(), is(Player.BLUE));
        assertThat(game.getUnitAt(new Position(4, 3)).getOwner(), is(Player.RED));
        game.moveUnit(new Position(4, 3), new Position(3, 2));
        assertThat(game.getUnitAt(new Position(3, 2)).getOwner(), is(Player.RED));

    }
    @Test
    public void changeProductionInCityAt4dot1toSettler(){
        game.changeProductionInCityAt(new Position(4,1), GameConstants.SETTLER);
        assertThat(game.getCityAt(new Position(4,1)).getProduction(), is(GameConstants.SETTLER));

    }
    @Test
    public void turnOnAndOffTranscript(){
        turnOnOffTranscript();
        game.changeProductionInCityAt(new Position(4,1), GameConstants.SETTLER);
        assertThat(game.getCityAt(new Position(4,1)).getProduction(), is(GameConstants.SETTLER));
        turnOnOffTranscript();
        game.changeProductionInCityAt(new Position(4,1), GameConstants.SETTLER);
        assertThat(game.getCityAt(new Position(4,1)).getProduction(), is(GameConstants.SETTLER));

    }





}
