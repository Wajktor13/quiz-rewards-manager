package majestatyczne.bestie.rewardsmanager.reward_selection_strategy;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RewardStrategyType {

    STRATEGY1("X% of the best (fully correct and fastest) - reward of category 1, the rest - reward of" +
            " category 2"),
    STRATEGY2("X correct answers - reward of category 1, Y correct answers - reward of category 2 etc.");

    private final String description;

    public RewardSelectionStrategy getRewardSelectionStrategy(majestatyczne.bestie.rewardsmanager.model.RewardStrategy rewardSelection) {
        RewardStrategyType rewardSelectionStrategyType = rewardSelection.getRewardStrategyType();

        switch (rewardSelectionStrategyType) {
            case STRATEGY1:
                // get parameters for strategy 1...
                return new PercentageStrategy();
            case STRATEGY2:
                // get parameters for strategy 2...
                return new ScoreStrategy();
            default:
                throw new IllegalArgumentException("Unsupported strategy type: " + rewardSelectionStrategyType);
        }
    }
}
