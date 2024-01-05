package majestatyczne.bestie.frontend.model;

import lombok.Data;

import java.util.List;

@Data
public class RewardStrategy {

    private int id;

    private RewardStrategyType rewardStrategyType;

    private Quiz quiz;

    private List<RewardStrategyParameter> parameters;
}
