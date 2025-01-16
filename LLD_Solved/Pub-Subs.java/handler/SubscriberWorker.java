package handler;

import data.SleepingSubscriber;
import model.*;

public class SubscriberWorker implements Runnable {

    private final Topic topic;
    private final SleepingSubscriber sleepingSubscriber;

    public SubscriberWorker(Topic topic, SleepingSubscriber sleepingSubscriber) {
        this.topic = topic;
        this.sleepingSubscriber = sleepingSubscriber;
    }

    @Override
    public void run() {
        synchronized (sleepingSubscriber) {
            do {
                int curOffset = sleepingSubscriber.getOffset().get();
                while (curOffset >= topic.getMessages().size()) {
                    try {
                        sleepingSubscriber.wait();
                    } catch (InterruptedException ex) {
                    }
                }
                Message message = topic.getMessages().get(curOffset);
                try {
                    sleepingSubscriber.consumeMessage(message);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // We cannot just increment here since subscriber offset can be reset while it is consuming. So, after
                // consuming we need to increase only if it was previous one.
                sleepingSubscriber.getOffset().compareAndSet(curOffset, curOffset + 1);
            } while (true);
        }
    }

    synchronized public void wakeUpIfNeeded() {
        synchronized (sleepingSubscriber) {
            sleepingSubscriber.notify();
        }
    }
}