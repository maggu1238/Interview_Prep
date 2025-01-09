package data;

import handler.TopicHandler;
import interfaces.IPublisher;
import model.*;

public class Publisher implements IPublisher {

    private String Id;

    public Publisher(String Id) {
        this.Id = Id;
    }

    @Override
    public String getId(){
        return Id;
    }

    @Override
    public void publishMessage(TopicHandler topicHandler, Message message) {
        topicHandler.getTopic().addMessage(message);
        System.out.println(message.getMsg() + " published to topic: " + topicHandler.getTopic().getTopicName());
        new Thread(() ->  topicHandler.publish()).start();
    }
}
