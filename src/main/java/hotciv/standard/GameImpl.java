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
    private AttackStrategy attackStrategy;


    public GameImpl(WinnerStrategy winnerStrategy,
                    AgingStrategy agingStrategy,
                    UnitStrategy unitStrategy,
                    WorldLayoutStrategy worldLayoutStrategy,
                    AttackStrategy attackStrategy) {
        this.winnerStrategy = winnerStrategy;
        this.agingStrategy = agingStrategy;
        this.unitStrategy = unitStrategy;
        this.worldLayoutStrategy = worldLayoutStrategy;
        this.attackStrategy = attackStrategy;
        main();
    }

    public void main() {
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

        if (!validMove(from, to)) { return false ; }

            createUnit(to, units.get(from));

            deductMoveCount(to);
            removeUnit(from);
            setUnitOwner(to);
            endOfTurn();

            return true;
    }

    private void setUnitOwner(Position to) {
        if (cities.containsKey(to)) {
            cities.get(to).setOwner(units.get(to).getOwner());                  //the unit occupying the city becomes its owner
        }
    }

    public void deductMoveCount(Position to) {
        units.get(to).setMoveCount(units.get(to).getMoveCount() - 1);
    }

    public boolean validMove(Position from, Position to) {

        boolean typeMountain = world.get(to).getTypeString().equals(GameConstants.MOUNTAINS);
        boolean typeOcean = world.get(to).getTypeString().equals(GameConstants.OCEANS);
        if (typeMountain || typeOcean) {
            return false;
        }
        boolean playerInTurnUnit = units.get(from).getOwner() == getPlayerInTurn();
        if (!playerInTurnUnit) {
            return false;
        }boolean unitHasMoved = units.get(from).getMoveCount() < 1;
        if(unitHasMoved){
            return false;
        }

        boolean validToPosition = world.containsKey(to);
        if (!validToPosition) {
            return false;
        }

        boolean validDistance = tileAdjacent(from, to);
        if (!validDistance) {
            return false;
        }

        boolean unitAction = units.get(from).getUnitAction();
        if (!unitAction) {
            return false;
        }

        return true;
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
            boolean enoughTreasury = c.getTreasury() >= GameConstants.getUnitCost(c.getProduction());
            boolean unitAtPosition = units.containsKey(p);

            if (enoughTreasury) {
                updateTreasury(c);
                if (!unitAtPosition) {
                    placeUnits(p, c);
                } else {
                    placeUnitsOutsideCity(p, c);
                }
            }
        }
    }

    public void placeUnitsOutsideCity(Position p, CityImpl c) {
        int tilesRotated = 0;
        for (Position u : Utility.get8neighborhoodOf(p)) {
            if (!unitAtThisPosition(u) && tilesRotated == 0) {

                createUnit(u, new UnitImpl(c.getOwner(), c.getProduction()));
                tilesRotated++;
            }
        }

    }

    public void placeUnits(Position p, CityImpl c) {
        if (validProduction(c)) {
            createUnit(p, new UnitImpl(c.getOwner(), c.getProduction()));
        }

    }

    private boolean validProduction(CityImpl c) {
        if (c.getProduction().equals(GameConstants.ARCHER) ||
                c.getProduction().equals(GameConstants.SETTLER) ||
                c.getProduction().equals(GameConstants.LEGION)) {
            return true;
        } else {
            return false;
        }
    }

    public void updateTreasury(CityImpl c) {
        c.deductTreasury(c.getTreasury() - GameConstants.getUnitCost(c.getProduction()));
    }

    private boolean unitAtThisPosition(Position p) {
        if (units.containsKey(p)) {
            return true;
        }
        return false;
    }


    public void changeWorkForceFocusInCityAt(Position p, String balance) {
    }

    public void changeProductionInCityAt(Position p, String unitType) {
    }

    public void performUnitActionAt(Position p) {
        if (units.containsKey(p)) {
            unitStrategy.unitAction(p, this);
        }
    }

    public HashMap<Position, CityImpl> getCities() {
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
