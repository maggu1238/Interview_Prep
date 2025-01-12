package Strategies;

import model.Job;

import java.util.concurrent.PriorityBlockingQueue;

public interface SchedulingStrategy {
    void schedule(PriorityBlockingQueue<Job> jobQueue);
}
