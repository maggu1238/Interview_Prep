package strategies;

import enums.Difficulty;
import enums.FilterType;
import model.Problem;

import java.util.ArrayList;
import java.util.List;

public class FilterStrategyByDifficulty implements IFilterStrategy{

    @Override
    public List<Problem> filter(List<Problem> problems, Difficulty difficulty) {
        ArrayList<Problem> fetctedProblems = new ArrayList<Problem>();

        for (Problem problem : problems) {
            if (problem.getDifficulty() == difficulty) {
                fetctedProblems.add(problem);
            }
        }

        return fetctedProblems;
    }
}
