package hotciv.tools;

import hotciv.framework.Game;
import hotciv.view.GfxConstants;
import minidraw.framework.DrawingEditor;
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
    public void mouseMove(MouseEvent e, int x, int y) {
        game.setTileFocus(GfxConstants.getPositionFromXY(x,y));
    }
}

