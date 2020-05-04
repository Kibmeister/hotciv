package hotciv.standard.client;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;

public class LocalMethodCallClientRequestHandler implements ClientRequestHandler {

    private Invoker invoker;

    public LocalMethodCallClientRequestHandler(Invoker invoker){
        this.invoker = invoker;
    }
    @Override
    public ReplyObject sendToServer(RequestObject requestObject) {
        System.out.println("This is the requestobject -> :" + requestObject);
        ReplyObject replyObject = invoker.handleRequest(
                requestObject.getObjectId(),
                requestObject.getOperationName(),
                requestObject.getObjectId());
        System.out.println("This is the reply object <- :" + replyObject);
        return replyObject;
    }

    @Override
    public void setServer(String hostname, int port) {

    }

    @Override
    public void close() {

    }
}
