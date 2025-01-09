package interfaces;

import handler.TopicHandler;
import model.Message;
import model.Topic;

public interface IPublisher {
    String getId();
    void publishMessage(TopicHandler topicHandler, Message message) ;
}
