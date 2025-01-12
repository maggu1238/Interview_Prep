package Strategies;

import model.Cluster;
import model.Job;
import services.ClusterManager;

import java.util.Objects;
import java.util.concurrent.PriorityBlockingQueue;

public class BatchSchedulingStrategy implements SchedulingStrategy{

    private ClusterManager clusterManager;

    public BatchSchedulingStrategy(ClusterManager clusterManager){
        this.clusterManager = clusterManager;
    }

    @Override
    public void schedule(PriorityBlockingQueue<Job> jobQueue) {
        while(!jobQueue.isEmpty()){
            Job job = jobQueue.poll();
            Cluster cluster = clusterManager.getAvailableCluster(job);
            if(Objects.isNull(cluster)){
                jobQueue.offer(job);
                continue;
            }
            new Thread(() -> execute(cluster, job)).start();
        }
    }

    private void execute(Cluster cluster, Job job){
        long currentTime = System.currentTimeMillis();
        try{
            Thread.sleep(job.getExecutionTime() * 1000L);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            clusterManager.releaseResources(cluster, job.getCpuCores(), job.getRam());
        }
    }
}
