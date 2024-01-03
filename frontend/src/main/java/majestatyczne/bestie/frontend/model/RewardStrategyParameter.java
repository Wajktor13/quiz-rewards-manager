package majestatyczne.bestie.frontend.model;

import lombok.Data;

@Data
public class RewardStrategyParameter {
    private int id;

    private int priority;

    private int parameterValue;

    private RewardCategory rewardCategory;
}
