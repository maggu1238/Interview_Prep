package handler;
import data.*;
import java.util.*;
import model.*;

public class TopicHandler {
    private final Topic topic;
    private final Map<String, SubscriberWorker> subscriberWorkers;

    public TopicHandler(Topic topic) {
        this.topic = topic;
        subscriberWorkers = new HashMap<>();
    }

    public void publish() {
        for (SleepingSubscriber sleepingSubscriber:topic.getSubscribers()) {
            startSubsriberWorker(sleepingSubscriber);
        }
    }

    public Topic getTopic(){
        return topic;
    }

    public void startSubsriberWorker(SleepingSubscriber sleepingSubscriber) {
        final String subscriberId = sleepingSubscriber.getId();
        if (!subscriberWorkers.containsKey(subscriberId)) {
            final SubscriberWorker subscriberWorker = new SubscriberWorker(topic, sleepingSubscriber);
            subscriberWorkers.put(subscriberId, subscriberWorker);
            new Thread(subscriberWorker).start();
        }
        final SubscriberWorker subscriberWorker = subscriberWorkers.get(subscriberId);
        subscriberWorker.wakeUpIfNeeded();
    }
}