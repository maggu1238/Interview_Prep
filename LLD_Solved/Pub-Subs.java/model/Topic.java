package model;
import java.util.*;
import data.*;

public class Topic {
    private final String topicName;
    private final String topicId;
    private final List<Message> messages; // TODO: Change getter this to send only immutable list outside.
    private final List<SleepingSubscriber> subscribers; // TODO: Change getter this to send only immutable list outside.

    public Topic(String topicName, String topicId) {
        this.topicName = topicName;
        this.topicId = topicId;
        this.messages = new ArrayList<>();
        this.subscribers = new ArrayList<>();
    }

    public synchronized void addMessage( Message message) {
        messages.add(message);
    }

    public void addSubscriber(SleepingSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    public List<SleepingSubscriber> getSubscribers(){
        return subscribers;
    }

    public List<Message> getMessages(){
        return messages;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getTopicId() {
        return topicId;
    }
}