package hotciv.standard;

import hotciv.framework.*;
import hotciv.standard.factories.ThetaCivFactory;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TestThetaCiv {

    private GameImpl game;

    @Before
    public void setUp () {
       game = new GameImpl(new ThetaCivFactory());
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
    @Test
    public void cityAt12dot12CanProduceAB52bomberIfTreasuryIsMoreThan60 (){
        game.createCity(new Position(12,12), new CityImpl(Player.RED));
        game.getCityAt(new Position(12,12)).setProduction(GameConstants.B52);
        assertThat(game.getCityAt(new Position(12,12)).getProduction(), is(GameConstants.B52));
        game.getCityAt(new Position(12,12)).setTreasury(61);
        assertThat(game.getCityAt(new Position(12,12)).getTreasury(), is(61));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(new Position(12,12)).getTypeString(), is(GameConstants.B52));

    }


    @Test
    public void cityAt12dot12CanNOTProduceAB52bomberIfTreasuryIsLessThan60 (){
        game.createCity(new Position(12,12), new CityImpl(Player.RED));
        game.getCityAt(new Position(12,12)).setProduction(GameConstants.B52);
        assertThat(game.getCityAt(new Position(12,12)).getProduction(), is(GameConstants.B52));
        game.getCityAt(new Position(12,12)).setTreasury(53);
        assertThat(game.getCityAt(new Position(12,12)).getTreasury(), is(53));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getCityAt(new Position(12,12)).getTreasury(), is(59));
        assertNull(game.getUnitAt(new Position(12,12)));
    }
    @Test
    public void theB25canFlyADistanceOf2(){
        game.createUnit(new Position(10,10), new UnitImpl(Player.RED, GameConstants.B52));
        assertThat(game.getUnitAt(new Position(10,10)).getTypeString(), is(GameConstants.B52));
        game.moveUnit(new Position(10,10), new Position(10,11));
        assertThat(game.getUnitAt(new Position(10,11)).getTypeString(), is(GameConstants.B52));
        assertTrue(game.moveUnit(new Position(10,11), new Position(11,12)));
    }
    @Test
    public void aB25canOnlyFlyADistanceOf2not3 (){
        game.createUnit(new Position(10,10), new UnitImpl(Player.RED, GameConstants.B52));
        assertThat(game.getUnitAt(new Position(10,10)).getTypeString(), is(GameConstants.B52));
        game.moveUnit(new Position(10,10), new Position(10,11));
        assertThat(game.getUnitAt(new Position(10,11)).getTypeString(), is(GameConstants.B52));
        game.moveUnit(new Position(10,11), new Position(11,12));
        assertThat(game.getUnitAt(new Position(11,12)).getTypeString(), is(GameConstants.B52));
        assertFalse(game.moveUnit(new Position(11,12), new Position(11,13)));
    }
    @Test
    public void theB25bomberCanFlyOverTerrainOfTypeOceansAndMountains (){
        game.createUnit(new Position(10,10), new UnitImpl(Player.RED, GameConstants.B52));
        assertThat(game.getUnitAt(new Position(10,10)).getTypeString(), is(GameConstants.B52));
        game.createTile(new Position(10,11), new TileImpl(GameConstants.MOUNTAINS));
        game.moveUnit(new Position(10,10), new Position(10,11));
        assertThat(game.getUnitAt(new Position(10,11)).getTypeString(), is(GameConstants.B52));
        game.createTile(new Position(11,12), new TileImpl(GameConstants.OCEANS));
        game.moveUnit(new Position(10,11), new Position(11,12));
        assertThat(game.getUnitAt(new Position(11,12)).getTypeString(), is(GameConstants.B52));
    }

    @Test
    public void theB52canOverflyAnEnemyCityWithoutConqueringItIfNoUnitLocatedInTheCity (){
        game.createCity(new Position(14,14), new CityImpl(Player.BLUE));
        assertNull(game.getUnitAt(new Position(14,14)));
        assertThat(game.getCityAt(new Position(14,14)).getOwner(), is(Player.BLUE));
        game.createUnit(new Position(14,13), new UnitImpl(Player.RED, GameConstants.B52));
        game.moveUnit(new Position(14,13), new Position (14,14));
        assertThat(game.getUnitAt(new Position(14,14)).getOwner(), is(Player.RED));
       // assertTrue(game.moveUnit(new Position(14,14), new Position (14,15)));
    }

    @Test
    public void theB52canOverflyAnEnemyCityIfThereIsAUnitPresentItIsConsideredANormalAttack (){
        game.createCity(new Position(14,14), new CityImpl(Player.BLUE));
        game.createUnit(new Position (14,14), new UnitImpl(Player.BLUE, GameConstants.ARCHER));
        assertThat(game.getUnitAt(new Position (14,14)).getTypeString(), is(GameConstants.ARCHER));
        game.createUnit(new Position(14,15), new UnitImpl(Player.RED, GameConstants.B52));
        game.moveUnit(new Position(14,15), new Position(14,14));
        assertThat(game.getUnitAt(new Position(14,14)).getTypeString(), is(GameConstants.B52));
        assertThat(game.getUnitAt(new Position(14,14)).getOwner(), is(Player.RED));
    }
    @Test
    public void b52UnitActionTurnsForrestIntoTypePlain() {
        game.createTile(new Position(10,10), new TileImpl(GameConstants.FOREST));
        assertThat(game.getTileAt(new Position (10,10)).getTypeString(), is(GameConstants.FOREST));
        game.createUnit(new Position(10,10), new UnitImpl(Player.RED, GameConstants.B52));
        game.performUnitActionAt(new Position (10,10));
        assertThat(game.getTileAt(new Position (10,10)).getTypeString(), is(GameConstants.PLAINS));
    }
    @Test
    public void b52UnitActionBombsCityAndDecreasesPopulationBy1 (){
        game.createCity(new Position(12,12), new CityImpl(Player.BLUE));
        assertThat(game.getCityAt(new Position(12,12)).getOwner(), is(Player.BLUE));
        game.createUnit(new Position(12,12), new UnitImpl(Player.RED, GameConstants.B52));
        assertThat(game.getUnitAt(new Position (12,12)).getOwner(), is(Player.RED));
        assertThat(game.getCityAt(new Position(12,12)).getPopulation(), is(1));
        game.getCityAt(new Position(12,12)).incrementPopulation(2);
        game.performUnitActionAt(new Position(12,12));
        assertThat(game.getCityAt(new Position(12,12)).getPopulation(), is(2));

    }
    @Test
    public void b52CannotBombFriendlyCity() {
        game.createCity(new Position(12,12), new CityImpl(Player.RED));
        assertThat(game.getCityAt(new Position(12,12)).getOwner(), is(Player.RED));
        assertThat(game.getCityAt(new Position(12,12)).getPopulation(), is(1));
        game.createUnit(new Position(12,12), new UnitImpl(Player.RED, GameConstants.B52));
        assertThat(game.getUnitAt(new Position (12,12)).getOwner(), is(Player.RED));
        game.performUnitActionAt(new Position(12,12));
        assertThat(game.getCityAt(new Position(12,12)).getPopulation(), is(1));
    }
    @Test
    public void b52UnitActionDoesNoeAffectOtherTerrainTypesThanForrest () {
        game.createTile(new Position(10,10), new TileImpl(GameConstants.OCEANS));
        assertThat(game.getTileAt(new Position (10,10)).getTypeString(), is(GameConstants.OCEANS));
        game.createUnit(new Position(10,10), new UnitImpl(Player.RED, GameConstants.B52));
        game.performUnitActionAt(new Position (10,10));
        assertThat(game.getTileAt(new Position (10,10)).getTypeString(), is(GameConstants.OCEANS));
    }

    @Test
    public void b52UnitHasADefensiveStrengthOg8AndAnAttackStrengthOg1 () {
        game.createUnit(new Position(15,15), new UnitImpl(Player.BLUE, GameConstants.B52));
        assertThat(game.getUnitAt(new Position(15,15)).getAttackingStrength(), is(1));
        assertThat(game.getUnitAt(new Position(15,15)).getDefensiveStrength(), is(8));

    }





}
