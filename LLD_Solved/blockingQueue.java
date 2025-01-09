import java.util.LinkedList;
import java.util.Queue;

class Demonstration {
    public static void main(String args[]) throws Exception {
        final BlockingQueue q = new BlockingQueue(5);

        // Creating producer thread
        Producer producer = new Producer(q);
        Thread t1 = new Thread(producer);

        // Creating consumer threads
        Consumer consumer1 = new Consumer(q);
        Thread t2 = new Thread(consumer1);

        Consumer consumer2 = new Consumer(q);
        Thread t3 = new Thread(consumer2);

        // Starting threads
        t1.start();
        Thread.sleep(4000); // Wait for a while before consumers start
        t2.start();
        t3.start();

        // Waiting for threads to finish
        t1.join();
        t2.join();
        t3.join();
    }
}

// Producer Class
class Producer implements Runnable {
    private final BlockingQueue queue;

    public Producer(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 50; i++) {
                queue.enqueue("Message " + i);  // Producing data and enqueueing it
                System.out.println("Produced: Message " + i);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// Consumer Class
class Consumer implements Runnable {
    private final BlockingQueue queue;

    public Consumer(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 25; i++) {
                String item = queue.dequeue();  // Consuming data from the queue
                System.out.println(Thread.currentThread().getName() + " Consumed: " + item);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// The BlockingQueue class with the synchronized enqueue and dequeue logic
class BlockingQueue {
    private Queue<String> queue;
    private int capacity;

    public BlockingQueue(int capacity) {
        this.queue = new LinkedList<>();
        this.capacity = capacity;
    }

    // Enqueue method (producer functionality)
    public synchronized void enqueue(String message) throws InterruptedException {
        while (queue.size() == capacity) {
            wait(); // Wait if the queue is full
        }
        queue.offer(message);
        notifyAll(); // Notify waiting consumers that there's a new item
    }

    // Dequeue method (consumer functionality)
    public synchronized String dequeue() throws InterruptedException {
        while (queue.isEmpty()) {
            wait(); // Wait if the queue is empty
        }
        String message = queue.poll();
        notifyAll(); // Notify waiting producers that space has been freed
        return message;
    }
}
