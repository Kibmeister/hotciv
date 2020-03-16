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
    private WinnerStrategy winnerStrategy;
    private AgingStrategy agingStrategy;
    private UnitStrategy unitStrategy;
    private WorldLayoutStrategy worldLayoutStrategy;


    public GameImpl(WinnerStrategy winnerStrategy,
                    AgingStrategy agingStrategy,
                    UnitStrategy unitStrategy,
                    WorldLayoutStrategy worldLayoutStrategy) {
        this.winnerStrategy = winnerStrategy;
        this.agingStrategy = agingStrategy;
        this.unitStrategy = unitStrategy;
        this.worldLayoutStrategy = worldLayoutStrategy;
        main();
        }
    public void main (){
        playerInTurn = Player.RED;
        gameAge = -4000;
        setGameLayoutStrategy();
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
      return winnerStrategy.getWinner(this);
    }

    public int getAge() {
        return gameAge;
    }

    public boolean moveUnit(Position from, Position to) {
        if (!world.get(to).getTypeString().equals(GameConstants.MOUNTAINS) // there is no mountain at to tile
                && units.get(from).getOwner() == getPlayerInTurn()          // acting player is player in turn
                && world.containsKey(to)                                    // to position is within the scope of the world
                && tileAdjacent(from, to)                                   // checks if to tile is within the "1" distance range
                && !world.get(to).getTypeString().equals(GameConstants.OCEANS)
                && getUnitAt(from).getUnitAction()) {

            createUnit(to, units.get(from));                                // create the new unit

            units.get(to).setMoveCount(units.get(to).getMoveCount() - 1);      // deduct the moveCount
            units.remove(from);                                               // removes the unit at from position as it moves
            if(cities.containsKey(to)){
                cities.get(to).setOwner(units.get(to).getOwner());                  //the unit occupying the city becomes its owner
            }
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
            gameAge += agingStrategy.worldAges();
        }

    }

    public void produceUnits() {

        for (Position p : cities.keySet()) {
            CityImpl c = getCityAt(p);

            c.setTreasury(6);
            if (c.getTreasury() >= GameConstants.getUnitCost(c.getProduction()) && !c.getProduction().equals("No unit type")) {               //hvis nok penger
                c.deductTreasury(c.getTreasury() - GameConstants.getUnitCost(c.getProduction()));  //trekk prisen fra
                if (!units.containsKey(p)) {                                                    //hvis ingen unit
                    createUnit(p, new UnitImpl(c.getOwner(), c.getProduction()));                //lag en by
                } else {
                    int counter = 0;
                    for (Position u : Utility.get8neighborhoodOf(p)) {
                        if (!units.containsKey(u) && counter == 0) {
                            createUnit(u, new UnitImpl(c.getOwner(), c.getProduction()));      //lag units på alle de flisene
                            counter++;
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
        if(units.containsKey(p)){
            unitStrategy.unitAction(p, this);
        }
    }
    public HashMap<Position, CityImpl>  getCities(){
        return cities;
    }
    private void setGameLayoutStrategy() {
        world = worldLayoutStrategy.getWorldLayout();
        units = worldLayoutStrategy.getUnitsLayout();
        cities = worldLayoutStrategy.getCitiesLayout();
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

    public void removeUnit(Position p) {
        units.remove(p);
    }
}
