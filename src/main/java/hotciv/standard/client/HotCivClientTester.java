package hotciv.standard.client;

import frds.broker.ClientRequestHandler;
import frds.broker.Requestor;
import frds.broker.ipc.socket.SocketClientRequestHandler;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.standard.client.proxies.GameProxy;


public class HotCivClientTester {

    private GameProxy gameproxy;

    public static void main (String[] args){
        String hostname = args[0];
        new HotCivClientTester(hostname);}


    public HotCivClientTester (String hostname) {

        if(hostname == null){
            hostname = "localhost";
        }
        int portNumber = 61934;

        ClientRequestHandler clientRequestHandler = new SocketClientRequestHandler(hostname, portNumber);
        Requestor requestor = new StandardJSONRequestor(clientRequestHandler);

        this.gameproxy = new GameProxy(requestor);
        runTest();
    }

    public void runTest() {
        System.out.println("Running getPlayerInTurn()");
        System.out.println("====================");
        System.out.println("The tile at 10 dot 10 is : " + this.gameproxy.getTileAt(new Position(10,10)).getTypeString());
        System.out.println("====================");
        System.out.println("The player in turn is: " + this.gameproxy.getPlayerInTurn());
        System.out.println("====================");
        System.out.println("The winner is: " + this.gameproxy.getWinner());
        System.out.println("====================");
        System.out.println("The owner of city at 9 dot 9 is : " + this.gameproxy.getCityAt(new Position(4,5)).getOwner());
        System.out.println("====================");
        System.out.println("Checking if Red player can move his unit: " + gameproxy.moveUnit(new Position(3,3), new Position(4,3)));
    }
}
