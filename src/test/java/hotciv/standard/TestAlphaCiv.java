package hotciv.standard;

import hotciv.framework.*;

import hotciv.standard.factories.AlphaCivFactory;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Skeleton class for AlphaCiv test cases
 * <p>
 * Updated Oct 2015 for using Hamcrest matchers
 * <p>
 * This source code is from the book
 * "Flexible, Reliable Software:
 * Using Patterns and Agile Development"
 * published 2010 by CRC Press.
 * Author:
 * Henrik B Christensen
 * Department of Computer Science
 * Aarhus University
 * <p>
 * Please visit http://www.baerbak.com/ for further information.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class TestAlphaCiv {
    private Game game;

    /**
     * Fixture for alphaciv testing.
     */
    @Before
    public void setUp() {
        game = new GameImpl(new AlphaCivFactory());
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
        for (int i = 0; i < 40; i++) {
            game.endOfTurn();
        }
        assertThat(game.getAge(), is(-2000));
    }

    @Test
    public void redHasAnArcherAt2dot0() {
        assertThat(game.getUnitAt(new Position(2, 0)).getOwner(), is(Player.RED));
        assertThat(game.getUnitAt(new Position(2, 0)).getTypeString(), is(GameConstants.ARCHER));

    }

    @Test
    public void blueHasALegionAt3dot2() {
        assertThat(game.getUnitAt(new Position(3, 2)).getOwner(), is(Player.BLUE));
        assertThat(game.getUnitAt(new Position(3, 2)).getTypeString(), is(GameConstants.LEGION));
    }

    @Test
    public void redHasASettlerAt4dot3() {
        assertThat(game.getUnitAt(new Position(4, 3)).getOwner(), is(Player.RED));
        assertThat(game.getUnitAt(new Position(4, 3)).getTypeString(), is(GameConstants.SETTLER));
    }

    @Test
    public void unitsCannotMoveOverMountains() {
        assertNotNull(game.getUnitAt(new Position(3, 2)));
        assertThat(game.getTileAt(new Position(2, 2)).getTypeString(), is(GameConstants.MOUNTAINS));
        assertFalse(game.moveUnit(new Position(3, 2), new Position(2, 2)));
    }

    @Test
    public void redCannotMoveBluesUnit() {
        assertThat(game.getPlayerInTurn(), is(Player.RED));
        game.moveUnit(new Position(3, 2), new Position(4, 2));
        assertThat(game.getUnitAt(new Position(4, 2)), is(nullValue()));
    }

    @Test
    public void onlyPlayerInTurnCanMoveUnit() {
        assertThat(game.getPlayerInTurn(), is(Player.RED));
        assertThat(game.moveUnit(new Position(3, 2), new Position(3, 3)), is(false));
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
    public void afterRedItIsBluesesTurn() {
        assertThat(game.getPlayerInTurn(), is(Player.RED));
        game.endOfTurn();
        assertThat(game.getPlayerInTurn(), is(Player.BLUE));
    }

    @Test
    public void blueUnitAt3dot2AttacksAndDefeatsRedUnitAt4dot3() {
        game.endOfTurn();
        assertThat(game.getUnitAt(new Position(3, 2)).getOwner(), is(Player.BLUE));
        assertThat(game.getUnitAt(new Position(4, 3)).getOwner(), is(Player.RED));
        game.moveUnit(new Position(3, 2), new Position(4, 3));
        assertThat(game.getUnitAt(new Position(4, 3)).getOwner(), is(Player.BLUE));
    }

    @Test
    public void redUnitAt4dot3AttacksAndDefeatsBluesUnitAt3dot2() {
        assertThat(game.getUnitAt(new Position(3, 2)).getOwner(), is(Player.BLUE));
        assertThat(game.getUnitAt(new Position(4, 3)).getOwner(), is(Player.RED));
        game.moveUnit(new Position(4, 3), new Position(3, 2));
        assertThat(game.getUnitAt(new Position(3, 2)).getOwner(), is(Player.RED));

    }

    @Test
    public void anArcherCanOnlyMoveOneDistanceBetweenTwoTiles() {
        assertThat(game.getUnitAt(new Position(2, 0)).getTypeString(), is(GameConstants.ARCHER));
        assertFalse(game.moveUnit(new Position(2, 0), new Position(4, 0)));
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
        assertThat(game.getUnitAt(new Position(4, 3)).getMoveCount(), is(1));
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
    public void redCanProduceArcher() {
        assertThat(game.getCityAt(new Position(1, 1)).getOwner(), is(Player.RED));
        game.getCityAt(new Position(1, 1)).setProduction(GameConstants.ARCHER);
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(new Position(0, 1)).getTypeString(), is(GameConstants.ARCHER));
    }

    @Test
    public void redCanProduceLegion() {
        assertThat(game.getCityAt(new Position(1, 1)).getOwner(), is(Player.RED));
        game.getCityAt(new Position(1, 1)).setProduction(GameConstants.LEGION);
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(new Position(0, 1)).getTypeString(), is(GameConstants.LEGION));
    }

    @Test
    public void redCanProduceSettler() {
        assertThat(game.getCityAt(new Position(1, 1)).getOwner(), is(Player.RED));
        game.getCityAt(new Position(1, 1)).setProduction(GameConstants.SETTLER);
        for (int i = 0; i < 10; i++) {
            game.endOfTurn();
        }
        assertThat(game.getUnitAt(new Position(0, 1)).getTypeString(), is(GameConstants.SETTLER));
    }

    @Test
    public void blueCanProduceArcher() {
        assertThat(game.getCityAt(new Position(4, 1)).getOwner(), is(Player.BLUE));
        game.getCityAt(new Position(4, 1)).setProduction(GameConstants.ARCHER);
        for (int i = 0; i < 4; i++) {
            game.endOfTurn();
        }
        assertThat(game.getUnitAt(new Position(4, 1)).getTypeString(), is(GameConstants.ARCHER));

    }

    @Test
    public void blueCanProduceLegion() {
        assertThat(game.getCityAt(new Position(4, 1)).getOwner(), is(Player.BLUE));
        game.getCityAt(new Position(4, 1)).setProduction(GameConstants.LEGION);
        for (int i = 0; i < 6; i++) {
            game.endOfTurn();
        }
        assertThat(game.getUnitAt(new Position(4, 1)).getTypeString(), is(GameConstants.LEGION));
    }

    @Test
    public void blueCanProduceSettler() {
        assertThat(game.getCityAt(new Position(4, 1)).getOwner(), is(Player.BLUE));
        game.getCityAt(new Position(4, 1)).setProduction(GameConstants.SETTLER);
        for (int i = 0; i < 10; i++) {
            game.endOfTurn();
        }
        assertThat(game.getUnitAt(new Position(4, 1)).getTypeString(), is(GameConstants.SETTLER));
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

}
