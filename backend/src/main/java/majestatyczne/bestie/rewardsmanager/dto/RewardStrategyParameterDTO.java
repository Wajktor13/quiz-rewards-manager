package majestatyczne.bestie.rewardsmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RewardStrategyParameterDTO {

    private int id;

    private int priority;

    private int parameterValue;

    private RewardCategoryDTO rewardCategory;
}
