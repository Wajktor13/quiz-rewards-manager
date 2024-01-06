package majestatyczne.bestie.rewardsmanager.reward_selection_strategy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import majestatyczne.bestie.rewardsmanager.model.RewardStrategy;

@Getter
@AllArgsConstructor
public enum RewardStrategyType {

    PERCENTAGE("X% of the best (fully correct and fastest) - rewardDTO of category 1, the rest - rewardDTO of" +
            " category 2"),
    SCORE("X correct answers - rewardDTO of category 1, Y correct answers - rewardDTO of category 2 etc.");

    private final String description;

    public static RewardSelectionStrategy getRewardSelectionStrategy(RewardStrategy rewardSelection) {
        RewardStrategyType rewardSelectionStrategyType = rewardSelection.getRewardStrategyType();

        return switch (rewardSelectionStrategyType) {
            case PERCENTAGE -> new PercentageStrategy();
            case SCORE -> new ScoreStrategy();
        };
    }

}
