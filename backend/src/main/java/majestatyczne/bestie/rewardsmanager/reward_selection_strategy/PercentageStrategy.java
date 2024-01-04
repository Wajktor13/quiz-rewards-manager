package majestatyczne.bestie.rewardsmanager.reward_selection_strategy;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import majestatyczne.bestie.rewardsmanager.model.*;
import majestatyczne.bestie.rewardsmanager.service.RewardCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

@AllArgsConstructor
public class PercentageStrategy implements RewardSelectionStrategy {
    @Override
    public List<Result> insertRewards(Quiz quiz, List<Preference> preferences) {
        var results = quiz.getResults();
        var rewardStrategy = quiz.getRewardStrategy();
        var rewardParameters =  rewardStrategy.getParameters();
        rewardParameters.sort(Comparator.comparingInt(RewardStrategyParameter::getPriority));

        int j = 0;
        for (RewardStrategyParameter parameter: rewardParameters) {
            var quantity = parameter.getParameterValue() / 100 * results.size();
            for (int i = 0; i < quantity; i++) {
                var reward = parameter.getRewardCategory().getRewards().stream()
                        .filter(r -> r.getRewardCategory().equals(parameter.getRewardCategory()))
                        .findFirst().orElseThrow();
                results.get(j).setReward(reward);
                j++;
            }
        }
        if (j < results.size()) {
            results.get(j).setReward(rewardParameters.get(rewardParameters.size() - 1)
                    .getRewardCategory().getRewards().get(0));
        }

        return results;
    }
}
