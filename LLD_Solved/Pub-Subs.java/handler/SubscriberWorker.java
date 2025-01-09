package handler;

import data.SleepingSubscriber;
import model.*;

public class SubscriberWorker implements Runnable {

    private final Topic topic;
    private final SleepingSubscriber topicSubscriber;

    public SubscriberWorker(Topic topic, SleepingSubscriber topicSubscriber) {
        this.topic = topic;
        this.topicSubscriber = topicSubscriber;
    }

    @Override
    public void run() {
        synchronized (topicSubscriber) {
            do {
                int curOffset = topicSubscriber.getOffset().get();
                while (curOffset >= topic.getMessages().size()) {
                    topicSubscriber.wait();
                }
                Message message = topic.getMessages().get(curOffset);
                try {
                    topicSubscriber.consumeMessage(message);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // We cannot just increment here since subscriber offset can be reset while it is consuming. So, after
                // consuming we need to increase only if it was previous one.
                topicSubscriber.getOffset().compareAndSet(curOffset, curOffset + 1);
            } while (true);
        }
    }

    synchronized public void wakeUpIfNeeded() {
        synchronized (topicSubscriber) {
            topicSubscriber.notify();
        }
    }


}