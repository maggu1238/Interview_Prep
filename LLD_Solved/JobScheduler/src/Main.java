import enums.StrategyType;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        JobSchedulingSystem jobSchedulingSystem = JobSchedulingSystem.createJobSchedulingSystem(StrategyType.Batch);

        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.

    }
}




import java.util.*;

class Job implements Comparable<Job> {
    String id;
    int executionTime; // Time required to execute the job
    long deadline; // Deadline for real-time jobs (in milliseconds)
    boolean isRealTime;

    public Job(String id, int executionTime, long deadline, boolean isRealTime) {
        this.id = id;
        this.executionTime = executionTime;
        this.deadline = deadline;
        this.isRealTime = isRealTime;
    }

    @Override
    public int compareTo(Job other) {
        if (this.isRealTime && other.isRealTime) {
            return Long.compare(this.deadline, other.deadline); // EDF: Earlier deadlines first
        }
        return 0; // Batch jobs don't need ordering here
    }

    @Override
    public String toString() {
        return "Job{id='" + id + "', executionTime=" + executionTime +
               ", deadline=" + deadline + ", isRealTime=" + isRealTime + '}';
    }
}

class HybridJobScheduler {
    private final PriorityQueue<Job> realTimeQueue;
    private final Queue<Job> batchQueue;

    public HybridJobScheduler() {
        realTimeQueue = new PriorityQueue<>(); // For real-time jobs, sorted by deadline
        batchQueue = new LinkedList<>(); // For batch jobs, FIFO
    }

    public void addJob(Job job) {
        if (job.isRealTime) {
            realTimeQueue.offer(job);
        } else {
            batchQueue.offer(job);
        }
    }

    public void scheduleJobs() {
        System.out.println("Starting job scheduling...");
        while (!realTimeQueue.isEmpty() || !batchQueue.isEmpty()) {
            // Execute real-time jobs first
            if (!realTimeQueue.isEmpty()) {
                Job realTimeJob = realTimeQueue.poll();
                System.out.println("Executing real-time job: " + realTimeJob);
                executeJob(realTimeJob);
            } else if (!batchQueue.isEmpty()) {
                Job batchJob = batchQueue.poll();
                System.out.println("Executing batch job: " + batchJob);
                executeJob(batchJob);
            }
        }
        System.out.println("All jobs completed.");
    }

    private void executeJob(Job job) {
        try {
            Thread.sleep(job.executionTime * 1000L); // Simulate job execution
        } catch (InterruptedException e) {
            System.err.println("Job execution interrupted: " + job);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        HybridJobScheduler scheduler = new HybridJobScheduler();

        // Adding jobs
        scheduler.addJob(new Job("Batch1", 3, 0, false)); // Batch job
        scheduler.addJob(new Job("RealTime1", 2, System.currentTimeMillis() + 5000, true)); // Real-time job
        scheduler.addJob(new Job("Batch2", 4, 0, false)); // Batch job
        scheduler.addJob(new Job("RealTime2", 1, System.currentTimeMillis() + 3000, true)); // Real-time job

        // Schedule and execute jobs
        scheduler.scheduleJobs();
    }
}
