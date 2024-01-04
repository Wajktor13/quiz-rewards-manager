package majestatyczne.bestie.rewardsmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import majestatyczne.bestie.rewardsmanager.model.RewardCategory;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RewardCategoryDTO {

    private int id;

    private String name;
    public static RewardCategoryDTO toDTO(RewardCategory rewardCategory) {
        if(rewardCategory == null) {
            return null;
        }
        return new RewardCategoryDTO(rewardCategory.getId(), rewardCategory.getName());
    }
}
