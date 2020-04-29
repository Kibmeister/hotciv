package hotciv.tools;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.framework.Unit;
import hotciv.view.GfxConstants;
import minidraw.framework.DrawingEditor;
import minidraw.standard.NullTool;
import minidraw.standard.SelectionTool;


import java.awt.event.MouseEvent;

public class ActionTool extends NullTool {
    private Game game;
    private DrawingEditor editor;


    public ActionTool(DrawingEditor editor, Game game) {
        this.editor = editor;
        this.game = game;
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        Position positionClicked = GfxConstants.getPositionFromXY(x, y);
        Unit unitClicked = game.getUnitAt(positionClicked);
        if (unitClicked != null) {
            if (e.isShiftDown()) {
                game.performUnitActionAt(positionClicked);
                System.out.println("ActionTool: unit action invoked");
            }
        }

        super.mouseDown(e, x, y);
    }
}
