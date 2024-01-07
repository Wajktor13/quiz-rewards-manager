package majestatyczne.bestie.frontend.util;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

import java.util.function.Consumer;

public class DeleteButtonCell<T> extends TableCell<T, Void> {
    private final Button button;

    public DeleteButtonCell(Consumer<T> onDeleteButtonClicked) {
        this.button = new Button("Delete");
        this.button.getStyleClass().add("secondary-button");
        button.setOnAction(event -> {
            T item = getTableView().getItems().get(getIndex());
            onDeleteButtonClicked.accept(item);
        });
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(button);
        }
    }
}

