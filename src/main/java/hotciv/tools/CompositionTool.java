package hotciv.tools;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.framework.Unit;
import hotciv.view.GfxConstants;
import minidraw.framework.DrawingEditor;
import minidraw.framework.RubberBandSelectionStrategy;
import minidraw.framework.Tool;
import minidraw.standard.NullTool;
import minidraw.standard.SelectionTool;
import minidraw.standard.handlers.StandardRubberBandSelectionStrategy;

import java.awt.event.MouseEvent;

public class CompositionTool extends UnitMoveTool {

    private Game game;
    private Tool dragTool, unitMoveTool,setFocusTool, endOfTurnTool, actionTool;

   RubberBandSelectionStrategy selectionStrategy;

    public CompositionTool(DrawingEditor editor, Game game) {
        super(editor, game);
        this.game = game;
        this.unitMoveTool = new UnitMoveTool(editor, game);
        this.setFocusTool = new SetFocusTool(editor, game);
        this.endOfTurnTool = new EndOfTurnTool(editor, game);
        this.actionTool = new ActionTool(editor, game);
        this.selectionStrategy = new StandardRubberBandSelectionStrategy();
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        boolean isPressingShift = e.isShiftDown();
        boolean isEndingTurn = false;
        if (editor.drawing().findFigure(x, y) != null) {
            isEndingTurn = (
                    x < GfxConstants.TURN_SHIELD_X + 27 &&
                            x > GfxConstants.TURN_SHIELD_X &&
                            y < GfxConstants.TURN_SHIELD_Y + 39 &&
                            y > GfxConstants.TURN_SHIELD_Y);
        }
        boolean isRefreshing = (
                x < GfxConstants.REFRESH_BUTTON_X + 46 &&
                        x > GfxConstants.REFRESH_BUTTON_X &&
                        y < GfxConstants.REFRESH_BUTTON_Y + 14 &&
                        y > GfxConstants.REFRESH_BUTTON_Y);

        if (isPressingShift) {
            game.performUnitActionAt(GfxConstants.getPositionFromXY(x,y));
        }
        else if (isEndingTurn) {
            game.endOfTurn();
        }
        else if (isRefreshing) {
            editor.drawing().requestUpdate();
        }
        else {
            setFocusTool.mouseDown(e, x, y);
            super.mouseDown(e, x, y);
        }
    }
}
