package majestatyczne.bestie.rewardsmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import majestatyczne.bestie.rewardsmanager.model.RewardCategory;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RewardDTO {

    private int id;

    private RewardCategoryDTO rewardCategory;

    private String name;

    private String description;
}
