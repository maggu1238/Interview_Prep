package services;

import model.Cluster;
import model.Job;

import java.util.ArrayList;
import java.util.List;

public class ClusterManager {
    List<Cluster> clusterList;
    public ClusterManager(){
        this.clusterList = new ArrayList<>();
    }

    public void addCluster(String id, int cpu, int ram){
        Cluster cluster = new Cluster(id, cpu, ram);
        clusterList.add(cluster);
    }

    public Cluster checkClusterAvailable(Job job){
        for(Cluster cluster : clusterList){
            if(cluster.IsAvailable(job.getCpuCores(), job.getRam())){
                return cluster;
            }
        }
        return null;
    }

    public Cluster getAvailableCluster(Job job){
        for(Cluster cluster : clusterList){
            if(cluster.allocateResources(job.getCpuCores(),job.getRam())){
                return cluster;
            }
        }
        return null;
    }

    public void releaseResources(Cluster cluster, int cpu, int ram){
        cluster.releaseResources(cpu, ram);
        return;
    }
}
