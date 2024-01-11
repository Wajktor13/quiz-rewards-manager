package majestatyczne.bestie.rewardsmanager.reward_selection_strategy;

import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.model.*;

import java.util.List;

@AllArgsConstructor
public class PercentageStrategy implements RewardSelectionStrategy {
    @Override
    public List<Result> insertRewards(Quiz quiz, RewardStrategy rewardStrategy, List<Preference> preferences) {
        var results = quiz.getResults();
        var rewardParameters =  rewardStrategy.getParameters();

        int resultIndex = 0;
        for (RewardStrategyParameter parameter: rewardParameters) {
            var quantity = parameter.getParameterValue() * results.size() / 100;
            var reward = parameter.getRewardCategory().getRewards().stream()
                    .filter(r -> r.getRewardCategory().equals(parameter.getRewardCategory()))
                    .findFirst().orElseThrow();
            for (int i = 0; i < quantity; i++) {
                results.get(resultIndex).setReward(reward);
                resultIndex++;
            }
        }
        while (resultIndex < results.size()) {
            results.get(resultIndex).setReward(rewardParameters.get(rewardParameters.size() - 1)
                    .getRewardCategory().getRewards().get(0));
            resultIndex++;
        }

        return results;
    }
}
