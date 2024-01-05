package majestatyczne.bestie.frontend.model;

import lombok.Data;

@Data
public class RewardCategory {

    private int id;

    private String name;

    public RewardCategory(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
