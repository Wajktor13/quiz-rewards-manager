package majestatyczne.bestie.rewardsmanager.dto;

import majestatyczne.bestie.rewardsmanager.model.Quiz;
import majestatyczne.bestie.rewardsmanager.reward_selection_strategy.RewardStrategyType;

import java.util.List;

public class RewardStrategyDTO {

    private int id;

    private RewardStrategyType rewardStrategyType;

    private Quiz quiz;

    private List<RewardStrategyParameterDTO> parameters;
}
