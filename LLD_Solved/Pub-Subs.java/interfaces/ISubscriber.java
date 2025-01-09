package interfaces;

import model.Message;

public interface ISubscriber {
    String getId();
    void consumeMessage(Message message) throws InterruptedException;
}
