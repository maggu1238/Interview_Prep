package factories;

import enums.SearchStrategyType;
import strategies.searchStrategies.SearchStrategy;
import strategies.searchStrategies.NameSearchStrategy;
import strategies.searchStrategies.TitleSearchStrategy;

public class SearchStrategyFactory {
    public SearchStrategy createStrategy(SearchStrategyType searchStrategyType){
        switch (searchStrategyType) {
            case SearchStrategyType.Title:
                return new TitleSearchStrategy();
            case SearchStrategyType.Name:
                return new NameSearchStrategy();

        }
        return null;
    }
}
