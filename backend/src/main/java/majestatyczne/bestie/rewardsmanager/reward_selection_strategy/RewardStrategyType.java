package majestatyczne.bestie.rewardsmanager.reward_selection_strategy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import majestatyczne.bestie.rewardsmanager.model.RewardStrategy;

@Getter
@AllArgsConstructor
public enum RewardStrategyType {

    PERCENTAGE("X% of the best (fully correct and fastest) - reward of category 1, the rest - reward of" +
            " category 2"),
    SCORE("X correct answers - reward of category 1, Y correct answers - reward of category 2 etc.");

    private final String description;

    public static RewardSelectionStrategy getRewardSelectionStrategy(RewardStrategy rewardSelection) {
        RewardStrategyType rewardSelectionStrategyType = rewardSelection.getRewardStrategyType();

        switch (rewardSelectionStrategyType) {
            case PERCENTAGE:
                // get parameters for strategy 1...
                return new PercentageStrategy();
            case SCORE:
                // get parameters for strategy 2...
                return new ScoreStrategy();
            default:
                throw new IllegalArgumentException("Unsupported strategy type: " + rewardSelectionStrategyType);
        }
    }

}
