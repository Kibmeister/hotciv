package hotciv.standard.client;

import frds.broker.ClientRequestHandler;
import frds.broker.Requestor;
import frds.broker.ipc.socket.SocketClientRequestHandler;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.Game;
import hotciv.standard.client.proxies.GameProxy;
import hotciv.tools.CompositionTool;
import hotciv.stub.StubGame3;

import hotciv.visual.HotCivFactory4;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;

public class HotCivClient {
    public static void main(String[] args) {
        new HotCivClient("localhost");
    }

    Game gameProxy;

    public HotCivClient(String hostname) {
        int portNumber = 61934;

        if (hostname == null) {
            hostname = "localhost";
        }

        ClientRequestHandler clientRequestHandler
                = new SocketClientRequestHandler(hostname, portNumber);

        Requestor requestor = new StandardJSONRequestor(clientRequestHandler);
        gameProxy = new GameProxy(requestor);

        System.out.println();
        System.out.println(
                "Connecting to server on: "
                        + hostname
                        + ":"
                        + portNumber
                        + "."
        );
        System.out.println();

        DrawingEditor editor = new MiniDrawApplication("Semi civ implementation",
                new HotCivFactory4(gameProxy));
        editor.showStatus("Move units to see Game's moveUnit method being called.");

        editor.open();
        editor.setTool(new CompositionTool(editor, gameProxy));
    }
}
