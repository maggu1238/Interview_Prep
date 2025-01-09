import data.*;
import model.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final Queue queue = new Queue();
        final Topic topic1 = queue.createTopic("t1");
        final Topic topic2 = queue.createTopic("t2");
        final SleepingSubscriber sub1 = new SleepingSubscriber("sub1", 10000);
        final SleepingSubscriber sub2 = new SleepingSubscriber("sub2", 10000);

        topic1.addSubscriber(sub1);
        topic1.addSubscriber(sub2);

        final SleepingSubscriber sub3 = new SleepingSubscriber("sub3", 5000);
        final Publisher pub1 = new Publisher("pub1");

        topic2.addSubscriber(sub2);

        pub1.publishMessage(queue.getTopicHandler(topic1), new Message("m1"));
        pub1.publishMessage(queue.getTopicHandler(topic1), new Message("m2"));
        pub1.publishMessage(queue.getTopicHandler(topic1), new Message("m3"));
        Thread.sleep(15000);

        pub1.publishMessage(queue.getTopicHandler(topic1), new Message("m4"));
        pub1.publishMessage(queue.getTopicHandler(topic1), new Message("m5"));

        queue.resetOffset(topic1, sub1, 0);
    }
}