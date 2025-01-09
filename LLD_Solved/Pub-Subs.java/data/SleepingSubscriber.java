package data;

import java.util.concurrent.atomic.AtomicInteger;
import interfaces.ISubscriber;
import model.Message;

public class SleepingSubscriber implements ISubscriber {
    private final String id;
    private final int sleepTimeInMillis;
    private final AtomicInteger offset;

    public SleepingSubscriber(String id, int sleepTimeInMillis) {
        this.id = id;
        this.sleepTimeInMillis = sleepTimeInMillis;
        this.offset = new AtomicInteger(0);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void consumeMessage(Message message) throws InterruptedException {
        System.out.println("Subscriber: " + id + " started consuming: " + message.getMsg());
        Thread.sleep(sleepTimeInMillis);
        System.out.println("Subscriber: " + id + " done consuming: " + message.getMsg());
    }

    public AtomicInteger getOffset(){
        return offset;
    }


}