package majestatyczne.bestie.rewardsmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import majestatyczne.bestie.rewardsmanager.model.RewardStrategyParameter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RewardStrategyParameterDTO {

    private int id;

    private int priority;

    private int parameterValue;

    private RewardCategoryDTO rewardCategory;

    public static RewardStrategyParameterDTO fromRewardStrategyParameter(RewardStrategyParameter rewardStrategyParameter) {
        return new RewardStrategyParameterDTO(rewardStrategyParameter.getId(), rewardStrategyParameter.getPriority(),
                rewardStrategyParameter.getParameterValue(), RewardCategoryDTO.fromRewardCategory(rewardStrategyParameter.getRewardCategory()));
    }

    public static List<RewardStrategyParameterDTO> fromRewardStrategyParameters(List<RewardStrategyParameter> rewardStrategyParameters) {
        return rewardStrategyParameters.stream().map(RewardStrategyParameterDTO::fromRewardStrategyParameter).toList();
    }
}
