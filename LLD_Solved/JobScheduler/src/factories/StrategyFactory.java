package factories;

import Strategies.BatchSchedulingStrategy;
import Strategies.RealTimeSchedulingStrategy;
import Strategies.SchedulingStrategy;
import enums.StrategyType;
import services.ClusterManager;

public class StrategyFactory {
    public SchedulingStrategy createStrategy(ClusterManager clusterManager, StrategyType strategyType){
        switch (strategyType){
            case Batch:
                return new BatchSchedulingStrategy(clusterManager);
            case RealTime:
                return new RealTimeSchedulingStrategy(clusterManager);
        }

        return null;
    }
}
