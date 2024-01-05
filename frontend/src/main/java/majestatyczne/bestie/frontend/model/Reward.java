package majestatyczne.bestie.frontend.model;

import lombok.Data;

@Data
public class Reward {
    private int id;

    private RewardCategory rewardCategory;

    private String name;

    private String description;
}
