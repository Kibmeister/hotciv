package hotciv.standard.server;

import frds.broker.Invoker;
import frds.broker.ReplyObject;
import sun.awt.shell.ShellFolder;

import java.util.HashMap;
import java.util.concurrent.Callable;

public class GameRootInvoker implements Invoker {

    public HashMap<String, Invoker> subInvoker;

    @Override
    public ReplyObject handleRequest(String objectId, String operationName, String payload) {
        return null;
    }
}
