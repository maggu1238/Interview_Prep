package model;

public class Job {
    private int cpuCores;
    private int ram;
    private int executionTimeInSeconds;
    private int priority;
    private String id;

    public Job(int cpuCores, int ram, int executionTimeInSeconds, int priority, String id){
        this.cpuCores = cpuCores;
        this.ram = ram;
        this.executionTimeInSeconds = executionTimeInSeconds;
        this.priority = priority;
        this.id = id;
    }

    public int getCpuCores() {
        return cpuCores;
    }

    public int getRam() {
        return ram;
    }

    public int getExecutionTime() {
        return executionTimeInSeconds;
    }

    public int getPriority() {
        return priority;
    }

    public String getId() {
        return id;
    }
}
