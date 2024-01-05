package majestatyczne.bestie.frontend.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class RewardStrategyParameterView {
    private int id;

    private ObjectProperty<Integer> priority;

    private ObjectProperty<Integer> parameterValue;

    private ObjectProperty<RewardCategory> rewardCategory;

    public RewardStrategyParameterView(int id, int priority, int parameterValue, RewardCategory rewardCategory) {
        this.id = id;
        this.priority = new SimpleObjectProperty<>(priority);
        this.parameterValue = new SimpleObjectProperty<>(parameterValue);
        this.rewardCategory = new SimpleObjectProperty<>(rewardCategory);
    }

    public int getPriority() {
        return priority.get();
    }

    public int getParameterValue() {
        return parameterValue.get();
    }

    public RewardCategory getRewardCategory() {
        return rewardCategory.get();
    }

    public ObjectProperty<RewardCategory> getRewardCategoryProperty() {
        return rewardCategory;
    }

    public ObjectProperty<Integer> getPriorityProperty() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority.set(priority);
    }

    public ObjectProperty<Integer> getParameterValueProperty() {
        return parameterValue;
    }

    public void setParameterValue(Integer parameterValue) {
        this.parameterValue.set(parameterValue);
    }

    public void setRewardCategory(RewardCategory rewardCategory) {
        this.rewardCategory.set(rewardCategory);
    }

    public RewardStrategyParameter toRewardStrategyParameter() {
        return new RewardStrategyParameter(id, priority.get(), parameterValue.get(), rewardCategory.get());
    }
}
