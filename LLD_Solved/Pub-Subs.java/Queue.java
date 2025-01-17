package model;

import handler.*;
import java.util.*;
import java.util.UUID;

public class Queue {

    private static Queue instance;

    private final Map<String, TopicHandler> topicProcessors;

    public Queue() {
        this.topicProcessors = new HashMap<>();
    }

    public static Queue getInstance() {
        if (instance == null) {
            synchronized (Queue.class) {
                if (instance == null) {
                    instance = new Queue();
                }
            }
        }
        return instance;
    }

    public TopicHandler getTopicHandler(Topic topic) {
        return topicProcessors.get(topic.getTopicId());
    }

    public Topic createTopic( String topicName) {
        final Topic topic = new Topic(topicName, UUID.randomUUID().toString());
        TopicHandler topicHandler = new TopicHandler(topic);
        topicProcessors.put(topic.getTopicId(), topicHandler);
        System.out.println("Created topic: " + topic.getTopicName());
        return topic;
    }

}
