package majestatyczne.bestie.frontend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RewardStrategyParameter {
    private int id;

    private int priority;

    private int parameterValue;

    private RewardCategory rewardCategory;
}
