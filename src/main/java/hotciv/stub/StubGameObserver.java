package hotciv.stub;

import hotciv.framework.GameObserver;
import hotciv.framework.Player;
import hotciv.framework.Position;

import java.util.ArrayList;
import java.util.List;

public class StubGameObserver implements GameObserver {

    private List<String> worldChangedAtPositions = new ArrayList<>();
    private String[] turnEndValues = new String[2];
    private Position tileFocusedChangedPosition;


    @Override
    public void worldChangedAt(Position pos) {
       this.worldChangedAtPositions.add(pos.toString());
    }

    @Override
    public void turnEnds(Player nextPlayer, int age) {
        turnEndValues[0] = nextPlayer.toString();
        turnEndValues[1] = String.valueOf(age);
    }

    @Override
    public void tileFocusChangedAt(Position position) {
        this.tileFocusedChangedPosition = position;

    }

    public List<String> getWorldChangedAtPositions () {
        return worldChangedAtPositions;
    }
    public String[] getTurnEndValues (){
        return turnEndValues;
    }
    public Position getTileFocusedChangedPosition () {
        return tileFocusedChangedPosition;
    }
}
