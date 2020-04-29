package hotciv.tools;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.view.GfxConstants;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Figure;
import minidraw.standard.SelectionTool;

import java.awt.event.MouseEvent;

public class EndOfTurnTool extends SelectionTool {
    private Game game;

    public EndOfTurnTool(DrawingEditor editor, Game game) {
        super(editor);
        this.game = game;
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        Figure clickedElement = editor.drawing().findFigure(x, y);
        if (clickedElement != null) {
            if (clickedElement.displayBox().contains(GfxConstants.TURN_SHIELD_X, GfxConstants.TURN_SHIELD_Y)) {
                System.out.println("End of turn pressed ");
                game.endOfTurn();
            }
        }
        super.mouseDown(e, x, y);
    }

}
