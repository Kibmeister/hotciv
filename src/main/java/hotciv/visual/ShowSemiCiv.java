package hotciv.visual;

import hotciv.framework.Game;
import hotciv.standard.GameImpl;
import hotciv.standard.factories.SemiCivFactory;
import hotciv.stub.StubGame2;
import hotciv.tools.CompositionTool;
import hotciv.tools.UnitMoveTool;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;

public class ShowSemiCiv {

    public static void main(String[] args) {
        Game game = new GameImpl( new SemiCivFactory());

        DrawingEditor editor =
                new MiniDrawApplication( "Move any unit using the mouse",
                        new HotCivFactory4(game) );
        editor.open();
        editor.showStatus("Move units to see Game's moveUnit method being called.");

        // TODO: Replace the setting of the tool with your UnitMoveTool implementation.
        editor.setTool( new CompositionTool(editor, game) );
    }
}
