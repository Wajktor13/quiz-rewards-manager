package majestatyczne.bestie.rewardsmanager.reward_selection_strategy;

import majestatyczne.bestie.rewardsmanager.model.*;
import majestatyczne.bestie.rewardsmanager.service.RewardCategoryService;

import java.util.Comparator;
import java.util.List;

public class ScoreStrategy implements RewardSelectionStrategy {
    @Override
    public List<Result> insertRewards(Quiz quiz, RewardStrategy rewardStrategy, List<Preference> preferences) {
        var results = quiz.getResults();
        var rewardParameters =  rewardStrategy.getParameters();
        rewardParameters.sort(Comparator.comparingInt(RewardStrategyParameter::getPriority));
        for (Result result : results) {
            var score = result.getScore();
            var reward = rewardParameters.stream()
                    .filter(parameter -> parameter.getParameterValue() <= score)
                    .max(Comparator.comparingInt(RewardStrategyParameter::getParameterValue))
                    .orElseThrow()
                    .getRewardCategory()
                    .getRewards()
                    .get(0);
            result.setReward(reward);
        }
        return results;
    }
}
