package majestatyczne.bestie.rewardsmanager.dto;

import majestatyczne.bestie.rewardsmanager.model.RewardCategory;

public record RewardCategoryDTO (

    int id,

    String name
) {

    public static RewardCategoryDTO convertToDTO(RewardCategory rewardCategory) {
        if (rewardCategory == null) {
            return null;
        }

        return new RewardCategoryDTO(rewardCategory.getId(), rewardCategory.getName());
    }
}
