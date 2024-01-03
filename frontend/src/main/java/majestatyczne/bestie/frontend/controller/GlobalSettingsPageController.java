package majestatyczne.bestie.frontend.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import majestatyczne.bestie.frontend.Constants;
import majestatyczne.bestie.frontend.HomePageApplication;
import majestatyczne.bestie.frontend.model.QuizView;
import majestatyczne.bestie.frontend.model.RewardCategory;
import majestatyczne.bestie.frontend.model.RewardCategoryView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GlobalSettingsPageController implements Initializable {

    @FXML
    private ImageView backIcon;

    private ObservableList<RewardCategoryView> rewardCategories;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setData();
        backIcon.setImage(new Image(String.valueOf(HomePageApplication.class.getResource(Constants.BACK_ICON_RESOURCE))));
    }

    private void setData() {
        initializeRewardCategories();
    }

    private void initializeRewardCategories() {
    }

    @FXML
    public void onGoBackClicked(MouseEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HomePageApplication.class.getResource(Constants.FXML_HOME_PAGE_RESOURCE));
        try {
            Scene scene = new Scene(fxmlLoader.load(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
