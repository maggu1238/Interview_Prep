package Strategies;

import model.Cluster;
import model.Job;
import services.ClusterManager;

import java.util.Objects;
import java.util.concurrent.PriorityBlockingQueue;

public class RealTimeSchedulingStrategy implements SchedulingStrategy{

    private  ClusterManager clusterManager;

    public RealTimeSchedulingStrategy(ClusterManager clusterManager){
        this.clusterManager = clusterManager;
    }

    @Override
    public void schedule(PriorityBlockingQueue<Job> jobQueue) {
        while(true){
            try {
                Job job = jobQueue.take();
                Cluster cluster = clusterManager.getAvailableCluster(job);
                if(Objects.isNull(cluster)){
                    System.out.println("No cluster available for the job " + job.getId());
                    jobQueue.offer(job);
                    continue;
                }
                new Thread(() -> startJob(cluster, job)).start();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void startJob(Cluster cluster, Job job) {
        long currentTime = System.currentTimeMillis();
        System.out.println("Job " + job.getId() + " started on cluster "+ cluster.getId() + " at " + currentTime);
        try {
            Thread.sleep(job.getExecutionTime() * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            clusterManager.releaseResources(cluster, job.getCpuCores(), job.getRam());
        }
        System.out.println("Job " + job.getId() + " completed on cluster "+cluster.getId() + " at " + System.currentTimeMillis());
        System.out.println("Total time taken for job "+job.getId()+" is "+(System.currentTimeMillis()-currentTime)/1000+"s");
    }
}
