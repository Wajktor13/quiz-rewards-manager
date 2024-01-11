package majestatyczne.bestie.frontend.model;

import lombok.Data;

@Data
public class Reward {
    private int id;

    private RewardCategory rewardCategory;

    private String name;

    private String description;

    public Reward(String name, String description) {
        this.rewardCategory = null;
        this.name = name;
        this.description = description;
    }
}
