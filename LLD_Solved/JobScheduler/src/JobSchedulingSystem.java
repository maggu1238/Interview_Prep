import enums.StrategyType;
import factories.StrategyFactory;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.PriorityBlockingQueue;
import model.Job;
import services.ClusterManager;
import services.JobScheduler;

public class JobSchedulingSystem {
    private ClusterManager clusterManager;
    private JobScheduler jobScheduler;
    private PriorityBlockingQueue<Job> jobQueue;
    private StrategyFactory strategyFactory;

    private JobSchedulingSystem(StrategyType strategyType) {

        this.clusterManager = new ClusterManager();
        this.strategyFactory = new StrategyFactory();

        this.jobScheduler  = new JobScheduler(this.clusterManager, strategyFactory.createStrategy(this.clusterManager, strategyType));
    }

    public static JobSchedulingSystem createJobSchedulingSystem(StrategyType strategyType) {
        return new JobSchedulingSystem(strategyType);
    }

    public Job createJob(int cpu, int ram, int executionTime, int priority, String id){
        return new Job(cpu, ram, executionTime, priority, id);
    }

    public void addCluster(String id, int cpu, int ram){
        clusterManager.addCluster(id, cpu, ram);
    }

    public void submitJobs(List<Job> jobs){
        for( Job job : jobs){
            if(isValid(job)){
                jobQueue.offer(job);
            }
        }
        jobScheduler.schedule(jobQueue);
    }

    private boolean isValid(Job job){
        if(Objects.isNull(clusterManager.checkClusterAvailable(job))){
            return true;
        }
        return false;
    }

}
