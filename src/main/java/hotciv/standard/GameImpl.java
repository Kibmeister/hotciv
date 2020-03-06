package hotciv.standard;

import hotciv.framework.*;
import javafx.geometry.Pos;

import javax.swing.plaf.synth.SynthEditorPaneUI;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Skeleton implementation of HotCiv.
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

public class GameImpl implements Game {

    private HashMap<Position, TileImpl> world = new HashMap<Position, TileImpl>();
    private HashMap<Position, CityImpl> cities = new HashMap<Position, CityImpl>();
    private HashMap<Position, UnitImpl> units = new HashMap<Position, UnitImpl>();
    private int gameAge;
    private Player playerInTurn;


    public GameImpl() {
        playerInTurn = Player.RED;
        gameAge = -4000;
        for (int i = 0; i < GameConstants.WORLDSIZE; i++) {
            for (int j = 0; j < GameConstants.WORLDSIZE; j++) {
                createTile(new Position(i, j), new TileImpl(GameConstants.PLAINS));
            }
        }

        createTile(new Position(1, 0), new TileImpl(GameConstants.OCEANS));
        createTile(new Position(0, 1), new TileImpl(GameConstants.HILLS));
        createTile(new Position(2, 2), new TileImpl(GameConstants.MOUNTAINS));

        createCity(new Position(1, 1), new CityImpl(Player.RED));
        createCity(new Position(4, 1), new CityImpl(Player.BLUE));


        createUnit(new Position(3, 2), new UnitImpl(Player.BLUE, GameConstants.LEGION));
        createUnit(new Position(4, 3), new UnitImpl(Player.RED, GameConstants.SETTLER));
        createUnit(new Position(2, 0), new UnitImpl(Player.RED, GameConstants.ARCHER));
        createUnit(new Position(1, 1), new UnitImpl(Player.RED, GameConstants.SETTLER));

    }


    public TileImpl getTileAt(Position p) {
        return world.get(p);
    }

    public UnitImpl getUnitAt(Position p) {
        return units.get(p);
    }

    public CityImpl getCityAt(Position p) {
        return cities.get(p);
    }

    public Player getPlayerInTurn() {
        return playerInTurn;
    }

    public Player getWinner() {
        if (gameAge == -3000) {
            return Player.RED;
        }
        return null;
    }

    public int getAge() {
        return gameAge;
    }

    public boolean moveUnit(Position from, Position to) {
        if (!world.get(to).getTypeString().equals(GameConstants.MOUNTAINS) // there is no mountain at to tile
                && units.get(from).getOwner() == getPlayerInTurn()          // acting player is player in turn
                && world.containsKey(to)                                    // to position is within the scope of the world
                && tileAdjacent(from, to)                                   // checks if to tile is within the "1" distance range
                && !world.get(to).getTypeString().equals(GameConstants.OCEANS)) {

            createUnit(to, units.get(from));                                // create the new unit

            units.get(to).setMoveCount(units.get(to).getMoveCount() - 1);                                  // deduct the moveCount
            units.remove(from);                                               // removes the unit at from position as it moves

            endOfTurn();
            return true;
        } else {
            return false;
        }
    }

    public boolean tileAdjacent(Position from, Position to) {
        for (Position p : hotciv.framework.Utility.get8neighborhoodOf(from)) {
            if (p.equals(to)) {
                return true;
            }
        }
        return false;
    }

    public void endOfTurn() {
        if (playerInTurn == Player.RED) {
            playerInTurn = Player.BLUE;
        } else {
            playerInTurn = Player.RED;
            for (UnitImpl u : units.values()) {
                u.setMoveCount(1);
            }
            produceUnits();
        }
        gameAge += 100;
    }

    public void produceUnits() {

        for (Position p : cities.keySet()) {
            CityImpl c = getCityAt(p);

            c.setTreasury(6);
          if (c.getTreasury() >= GameConstants.getUnitCost(c.getProduction()) && !c.getProduction().equals("No unit type")) {               //hvis nok penger
                c.deductTreasury(c.getTreasury() - GameConstants.getUnitCost(c.getProduction()));  //trekk prisen fra
                if (!units.containsKey(p)) {                                                    //hvis ingen unit
                   createUnit(p, new UnitImpl(c.getOwner(), c.getProduction()));                //lag en by
                }
                else {
                    int counter = 0;
                    for (Position u : Utility.get8neighborhoodOf(p)) {
                        if (!units.containsKey(u) && counter == 0) {
                             createUnit(u, new UnitImpl(c.getOwner(), c.getProduction()));      //lag units på alle de flisene
                            counter ++;
                        }
                    }
                }

            }
       }
    }


    public void changeWorkForceFocusInCityAt(Position p, String balance) {
    }

    public void changeProductionInCityAt(Position p, String unitType) {
    }

    public void performUnitActionAt(Position p) {
    }

    public void createTile(Position p, TileImpl t) {
        world.put(p, t);
    }

    public void createUnit(Position p, UnitImpl u) {
        units.put(p, u);
    }

    public void createCity(Position p, CityImpl c) {
        cities.put(p, c);
    }

}
