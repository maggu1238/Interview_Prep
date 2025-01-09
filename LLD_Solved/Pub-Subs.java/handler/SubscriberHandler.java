package handler;

import data.SleepingSubscriber;
import model.Topic;

import java.util.HashMap;

public class SubscriberHandler {


    public void resetOffset(Topic topic, SleepingSubscriber subscriber, Integer newOffset) {
        for (SleepingSubscriber topicSubscriber : topic.getSubscribers()) {
            if (topicSubscriber.equals(subscriber)) {
                topicSubscriber.getOffset().set(newOffset);
                System.out.println(topicSubscriber.getId() + " offset reset to: " + newOffset);
                new Thread(() -> topicProcessors.get(topic.getTopicId()).startSubsriberWorker(topicSubscriber)).start();
                break;
            }
        }
    }

}
