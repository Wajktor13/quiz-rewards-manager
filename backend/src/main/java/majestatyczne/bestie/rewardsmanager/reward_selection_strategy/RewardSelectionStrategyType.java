package majestatyczne.bestie.rewardsmanager.reward_selection_strategy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import majestatyczne.bestie.rewardsmanager.model.RewardSelection;

@Getter
@AllArgsConstructor
public enum RewardSelectionStrategyType {

    STRATEGY1("X% of the best (fully correct and fastest) - reward of category 1, the rest - reward of" +
            " category 2"),
    STRATEGY2("X correct answers - reward of category 1, Y correct answers - reward of category 2 etc.");

    private final String description;

    public RewardSelectionStrategy getRewardSelectionStrategy(RewardSelection rewardSelection) {
        RewardSelectionStrategyType rewardSelectionStrategyType = rewardSelection.getRewardSelectionStrategyType();

        switch (rewardSelectionStrategyType) {
            case STRATEGY1:
                // get parameters for strategy 1...
                return new RewardSelectionStrategy1();
            case STRATEGY2:
                // get parameters for strategy 2...
                return new RewardSelectionStrategy2();
            default:
                throw new IllegalArgumentException("Unsupported strategy type: " + rewardSelectionStrategyType);
        }
    }
}
