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
        Player playerInTurn = this.gameproxy.getPlayerInTurn();
        System.out.println("The player in turn is: " + playerInTurn);
        System.out.println("====================");
        System.out.println("The winner is: " + this.gameproxy.getWinner());
        System.out.println("====================");
        System.out.println("Checking if Red player can move his unit: " + gameproxy.moveUnit(new Position(3,3), new Position(4,3)));
    }
}
