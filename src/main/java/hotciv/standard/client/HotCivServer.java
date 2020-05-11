package hotciv.standard.client;

import frds.broker.Invoker;
import frds.broker.ServerRequestHandler;
import frds.broker.ipc.socket.SocketServerRequestHandler;
import hotciv.framework.Game;
import hotciv.standard.GameImpl;
import hotciv.standard.factories.AlphaCivFactory;
import hotciv.standard.factories.SemiCivFactory;
import hotciv.standard.server.GameJSONInvoker;
import hotciv.standard.server.GameRootInvoker;
import hotciv.stub.StubGame3;

import javax.rmi.CORBA.Stub;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class HotCivServer {

    public static void main(String[] args) throws UnknownHostException {
        new HotCivServer();
    }

    public HotCivServer() {
        int port = 61934;

        Game gameServant = new GameImpl(new SemiCivFactory());
        Invoker invoker = new GameRootInvoker(gameServant);
        // a server requestHandler that takes a port and an invoker as parameters

        SocketServerRequestHandler socketServerRequestHandler = new SocketServerRequestHandler();
        socketServerRequestHandler.setPortAndInvoker(port, invoker);

        System.out.println("Hotciv server hosted on port: "
                + port + ".");
        System.out.println(" Use ctrl-c to terminate!");
        socketServerRequestHandler.start();

    }


}
