package majestatyczne.bestie.frontend.model;

import javafx.beans.property.*;

import java.util.Date;

public class ResultView {
    private StringProperty personName;
    private ObjectProperty<Date> endDate;
    private ObjectProperty<Integer> score;
    private StringProperty reward;

    public ResultView(String personName, Date endDate, int score) {
        this.personName = new SimpleStringProperty(personName);
        this.endDate = new SimpleObjectProperty<>(endDate);
        this.score = new SimpleObjectProperty<>(score);
        this.reward = new SimpleStringProperty("placeholder");
    }

    public String getPersonName() {
        return personName.get();
    }

    public StringProperty getPersonNameProperty() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName.set(personName);
    }

    public Date getEndDate() {
        return endDate.get();
    }

    public ObjectProperty<Date> getEndDateProperty() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate.set(endDate);
    }

    public int getScore() {
        return score.get();
    }

    public ObjectProperty<Integer> getScoreProperty() {
        return score;
    }

    public void setScore(int score) {
        this.score.set(score);
    }

    public String getReward() {
        return reward.get();
    }

    public StringProperty getRewardProperty() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward.set(reward);
    }
}