package majestatyczne.bestie.rewardsmanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import majestatyczne.bestie.rewardsmanager.model.Reward;

public record RewardDTO (

    int id,

    @JsonProperty("rewardCategory")
    RewardCategoryDTO rewardCategoryDTO,

    String name,

    String description
) {

    public static RewardDTO convertToDTO(Reward reward) {
        if (reward == null) {
            return null;
        }

        return new RewardDTO(reward.getId(), RewardCategoryDTO.convertToDTO(reward.getRewardCategory()),
                reward.getName(), reward.getDescription());
    }
}
