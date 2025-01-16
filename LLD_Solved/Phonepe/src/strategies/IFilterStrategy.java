package strategies;

import enums.Difficulty;
import enums.FilterType;
import model.Problem;

import java.util.List;

public interface IFilterStrategy {

    List<Problem> filter(List<Problem> problems, Difficulty difficulty);
}
