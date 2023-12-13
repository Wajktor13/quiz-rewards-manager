package majestatyczne.bestie.frontend.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import majestatyczne.bestie.frontend.FileUploadClient;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    FileUploadClient fileUploadClient = new FileUploadClient();
    FileChooser fileChooser = new FileChooser();

    @FXML
    public void onGetFile() {
        File file = fileChooser.showOpenDialog(new Stage());
        fileUploadClient.makeRequest(file);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String home = System.getProperty("user.home");
        fileChooser.setInitialDirectory(new File(home + "/Downloads"));
    }
}
