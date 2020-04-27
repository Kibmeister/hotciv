package hotciv.standard;

import hotciv.framework.*;
import hotciv.standard.factories.AlphaCivFactory;
import hotciv.stub.StubGameObserver;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class TestGameObserver {

    private StubGameObserver observer = new StubGameObserver();
    private Game game = new GameImpl(new AlphaCivFactory());

    @Before
    public void setUp (){
        game.addObserver(observer);
    }
    @Test
    public void testWorldChangeAtPosition() {
        List<String> listWorldChangedAtPosition = new ArrayList<>();
        game.createUnit(new Position(10,10), new UnitImpl(Player.RED,GameConstants.ARCHER));
        listWorldChangedAtPosition.add("[10,10]");
        game.createUnit(new Position(11,11), new UnitImpl(Player.BLUE,GameConstants.ARCHER));
        listWorldChangedAtPosition.add("[11,11]");
        game.createUnit(new Position(14,14), new UnitImpl(Player.BLUE,GameConstants.SETTLER));
        listWorldChangedAtPosition.add("[14,14]");
        assertEquals(listWorldChangedAtPosition, observer.getWorldChangedAtPositions());
    }

    @Test
    public void testTurnEndValues () {
            game.endOfTurn();
            Player firstPlayerInTurn = game.getPlayerInTurn();
            int gameAge1 = game.getAge();
            assertEquals(firstPlayerInTurn.toString(), observer.getTurnEndValues()[0]);
            assertEquals(String.valueOf(gameAge1), observer.getTurnEndValues()[1]);
            game.endOfTurn();

            Player secondPlayerInTurn = game.getPlayerInTurn();
            int gameAge2 = game.getAge();
            assertEquals(secondPlayerInTurn.toString(), observer.getTurnEndValues()[0]);
            assertEquals(String.valueOf(gameAge2), observer.getTurnEndValues()[1]);
        }

    @Test
    public void testTileFocus() {
        game.setTileFocus(new Position(14,14));
        assertEquals(new Position(14,14), observer.getTileFocusedChangedPosition());
    }







}


