package majestatyczne.bestie.frontend.util;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;

import java.util.function.BiConsumer;

import majestatyczne.bestie.frontend.model.RewardCategoryView;

public class RewardCategoryChoiceCell<T> extends TableCell<T, RewardCategoryView> {

    private final ComboBox<RewardCategoryView> comboBoxTableCell;

    public RewardCategoryChoiceCell(ObservableList<RewardCategoryView> rewardCategories, BiConsumer<T, RewardCategoryView> onChosenRewardCategory) {
        comboBoxTableCell = new ComboBox<>(rewardCategories);
        comboBoxTableCell.setOnAction(event -> {
            T item = getTableRow().getItem();
            RewardCategoryView selectedCategory = comboBoxTableCell.getValue();
            onChosenRewardCategory.accept(item, selectedCategory);
        });
    }

    @Override
    public void updateItem(RewardCategoryView item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(comboBoxTableCell);
            comboBoxTableCell.setValue(item);
        }
    }

}

