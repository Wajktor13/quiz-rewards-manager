package majestatyczne.bestie.rewardsmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RewardCategoryDTO {

    private int id;

    private String name;

    private int priority;
}
