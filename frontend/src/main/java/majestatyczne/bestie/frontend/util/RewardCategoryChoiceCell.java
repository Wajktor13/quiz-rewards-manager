package majestatyczne.bestie.frontend.util;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;

import java.util.function.BiConsumer;
import majestatyczne.bestie.frontend.model.RewardCategoryView;
import majestatyczne.bestie.frontend.model.RewardView;

public class RewardCategoryChoiceCell extends TableCell<RewardView, RewardCategoryView> {

    private final ComboBox<RewardCategoryView> comboBoxTableCell;

    public RewardCategoryChoiceCell(ObservableList<RewardCategoryView> rewardCategories, BiConsumer<RewardView, RewardCategoryView> onChosenRewardCategory) {
        comboBoxTableCell = new ComboBox<>(rewardCategories);
        comboBoxTableCell.setOnAction(event -> {
            RewardView selectedReward = getTableRow().getItem();
            RewardCategoryView selectedCategory = comboBoxTableCell.getValue();
            onChosenRewardCategory.accept(selectedReward, selectedCategory);
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

