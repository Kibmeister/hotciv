package hotciv.tools;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.framework.Unit;
import hotciv.view.GfxConstants;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Tool;
import minidraw.standard.NullTool;
import minidraw.standard.SelectionTool;

import java.awt.event.MouseEvent;

public class CompositionTool extends NullTool {

    private Game game;
    private Tool dragTool, unitMoveTool,setFocusTool, endOfTurnTool, actionTool;

    private DrawingEditor editor;

    private Position dragStartPosition;

    public CompositionTool(DrawingEditor editor, Game game) {
        this.editor = editor;
        this.game = game;
        this.dragTool = new NullTool();
        this.unitMoveTool = new UnitMoveTool(editor, game);
        this.setFocusTool = new SetFocusTool(editor, game);
        this.endOfTurnTool = new EndOfTurnTool(editor, game);
        this.actionTool = new ActionTool(editor, game);
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        boolean endOfTurnTool = x > GfxConstants.TURN_SHIELD_X - 50 && x < GfxConstants.TURN_SHIELD_X + 50 && y > GfxConstants.TURN_SHIELD_Y - 50 && y < GfxConstants.TURN_SHIELD_Y + 50;
        Position positionClicked = GfxConstants.getPositionFromXY(x, y);
        boolean unitsAtPosition = game.getUnitAt(positionClicked) != null;

        if (endOfTurnTool) {
            this.endOfTurnTool.mouseDown(e, x, y);
        } else if (e.isShiftDown()) {
            this.actionTool.mouseDown(e, x, y);
        } else {
            dragStartPosition = positionClicked;
            if (unitsAtPosition) {
                dragTool = unitMoveTool;
                unitMoveTool.mouseDown(e, x, y);
            }
        }
    }


    @Override
    public void mouseDrag(MouseEvent e, int x, int y) {
        this.dragTool.mouseDrag(e, x, y);
    }

    @Override
    public void mouseUp(MouseEvent e, int x, int y) {
        Position p = GfxConstants.getPositionFromXY(x, y);
        if (this.dragStartPosition.equals(p) && dragStartPosition.getColumn() <= 15) {
            setFocusTool.mouseMove(e, x, y);
        } else {
            unitMoveTool.mouseUp(e, x, y);
        }
        dragTool = new NullTool();
    }
}
