package hotciv.tools;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.view.GfxConstants;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Figure;
import minidraw.framework.Tool;
import minidraw.standard.SelectionTool;

import java.awt.event.MouseEvent;

public class SetFocusTool extends SelectionTool {
    private Game game;

    public SetFocusTool(DrawingEditor editor, Game game) {
        super(editor);
        this.game = game;
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        // Check if the tile is part of the world
        Position p = GfxConstants.getPositionFromXY(x, y);
        if (x > GameConstants.WORLDSIZE && y > GameConstants.WORLDSIZE) {
            return;
        }
        Figure figure = editor.drawing().findFigure(x, y);
        if (figure != null) {
            if (x > 20 && x < 493 && y > 20 && y < 493) {
                if (editor.drawing().findFigure(x, y).displayBox().height < 50) {
                    game.setTileFocus(GfxConstants.getPositionFromXY(x, y));
                }
            }
        }
    }
}

