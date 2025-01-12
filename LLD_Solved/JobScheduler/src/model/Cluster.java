package model;

public class Cluster {
    private String id;
    private int cpuCores;
    private int ram;

    public Cluster(String id, int cpuCores, int ram){
        this.id = id;
        this.cpuCores = cpuCores;
        this.ram = ram;
    }

    public int getCpuCores() {
        return cpuCores;
    }

    public int getRam() {
        return ram;
    }

    public boolean IsAvailable(int cpuRequested, int ramRequested){
        if(cpuRequested <= cpuCores && ramRequested <= ram){
            return true;
        }
        return false;
    }

    public synchronized boolean allocateResources(int cpuRequested, int ramRequested){
        if(IsAvailable(cpuRequested,ramRequested)){
            System.out.println("Resources available");
            cpuCores = cpuCores - cpuRequested;
            ram = ram - ramRequested;
            return true;
        }
        System.out.println("Resources not available at cluster");
        return false;
    }

    public synchronized void releaseResources(int cpuRelease, int ramReleased){
        cpuCores += cpuRelease;
        ram += ramReleased;

        return;
    }

    public String getId() {
        return id;
    }
}
