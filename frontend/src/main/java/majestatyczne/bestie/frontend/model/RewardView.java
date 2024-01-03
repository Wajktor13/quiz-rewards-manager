package majestatyczne.bestie.frontend.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

public class RewardView {

    @Getter
    private int id;

    private StringProperty rewardCategory;

    private StringProperty name;

    private StringProperty description;

    public RewardView(int id, RewardCategory rewardCategory, String name, String description) {
        this.id = id;
        if (rewardCategory == null) {
            this.rewardCategory = new SimpleStringProperty("brak kategorii");
        } else {
            this.rewardCategory = new SimpleStringProperty(rewardCategory.getName());
        }
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
    }

    public String getRewardCategory() {
        return rewardCategory.get();
    }

    public StringProperty getRewardCategoryProperty() {
        return rewardCategory;
    }

    public void setRewardCategory(String rewardCategory) {
        this.rewardCategory.set(rewardCategory);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty getDescriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }
}
