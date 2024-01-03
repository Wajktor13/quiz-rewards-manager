package majestatyczne.bestie.frontend.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

import java.util.Date;

public class RewardCategoryView {

    @Getter
    private int id;

    private StringProperty name;

    public RewardCategoryView(int id, String name) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
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
}
