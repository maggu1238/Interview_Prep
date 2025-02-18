package model;
import java.util.concurrent.atomic.AtomicLong;

public class Tweet {
    private static final AtomicLong idCounter = new AtomicLong(0);
    private final long id;
    private final long userId;
    private final String content;
    private final long timestamp;

    public Tweet(long userId, String content) {
        this.id = idCounter.incrementAndGet();
        this.userId = userId;
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }

    public long getId() { return id; }
    public long getUserId() { return userId; }
    public String getContent() { return content; }
    public long getTimestamp() { return timestamp; }
}
