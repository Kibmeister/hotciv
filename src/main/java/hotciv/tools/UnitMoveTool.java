package hotciv.tools;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.framework.Unit;
import hotciv.view.GfxConstants;
import minidraw.framework.Drawing;
import minidraw.framework.DrawingEditor;
import minidraw.standard.SelectionTool;

import java.awt.event.MouseEvent;

public class UnitMoveTool extends SelectionTool {
    private Game game;
    private Position dragStartPosition;

    public UnitMoveTool(DrawingEditor editor, Game game) {
        super(editor);
        this.game = game;
    }
    public void mouseDown(MouseEvent e, int x, int y) {
        Unit unitsAtPosition = game.getUnitAt(GfxConstants.getPositionFromXY(x,y));
        if(unitsAtPosition != null && unitsAtPosition.getOwner().equals(game.getPlayerInTurn())){
            dragStartPosition = GfxConstants.getPositionFromXY(x,y);
            super.mouseDown(e,x,y);
        }


    }

    public void mouseUp(MouseEvent e, int x, int y) {
        if(draggedFigure != null){
            game.moveUnit(dragStartPosition, GfxConstants.getPositionFromXY(x,y));
        }
        super.mouseUp(e,x,y);
    }



}
