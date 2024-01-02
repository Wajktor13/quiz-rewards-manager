package majestatyczne.bestie.rewardsmanager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RewardSelectionStrategyType {

    STRATEGY1("X% of the best (fully correct and fastest) - reward of category 1, the rest - reward of" +
            " category 2"),
    STRATEGY2("X correct answers - reward of category 1, Y correct answers - reward of category 2 etc.");

    private final String description;
}
