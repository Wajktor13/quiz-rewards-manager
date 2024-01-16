package majestatyczne.bestie.frontend.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import majestatyczne.bestie.frontend.model.FileFormat;

public class ExportPopupController {

    @FXML
    private ChoiceBox<FileFormat> formatChoiceBox;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
        setData();
    }

    private void setData() {
        ObservableList<FileFormat> fileFormats = FXCollections.observableArrayList();
        fileFormats.addAll(FileFormat.values());
        formatChoiceBox.setItems(fileFormats);
    }

    @FXML
    private void onConfirmClicked() {
        FileFormat selectedFormat = formatChoiceBox.getValue();
        // TODO send request to server
        stage.close();
    }
}
