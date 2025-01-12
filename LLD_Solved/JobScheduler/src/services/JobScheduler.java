package services;

import Strategies.SchedulingStrategy;
import model.Cluster;
import model.Job;

import java.util.Objects;
import java.util.concurrent.PriorityBlockingQueue;

public class JobScheduler {
    private ClusterManager clusterManager;
    private SchedulingStrategy strategy;
    public JobScheduler(ClusterManager clusterManager, SchedulingStrategy strategy){
        this.clusterManager = clusterManager;
        this.strategy = strategy;
    }


    public void schedule(PriorityBlockingQueue<Job> jobQueue) {
        strategy.schedule(jobQueue);
        return;
    }
}
