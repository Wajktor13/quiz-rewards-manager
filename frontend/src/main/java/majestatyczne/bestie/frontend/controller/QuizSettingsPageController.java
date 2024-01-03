package majestatyczne.bestie.frontend.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import majestatyczne.bestie.frontend.Constants;
import majestatyczne.bestie.frontend.HomePageApplication;
import majestatyczne.bestie.frontend.model.QuizView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QuizSettingsPageController implements Initializable {

    private QuizView quizView;

    @FXML
    private ImageView backIcon;

    @FXML
    private Label quizNameLabel;

    @FXML
    private Label quizScoreLabel;

    public void setQuizView(QuizView quizView) {
        this.quizView = quizView;
        setData();
    }

    private void setData() {
        quizNameLabel.setText(quizView.getName());
        quizScoreLabel.setText(Constants.QUIZ_MAX_SCORE_INFO + quizView.getMaxScore());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backIcon.setImage(new Image(String.valueOf(HomePageApplication.class.getResource(Constants.BACK_ICON_RESOURCE))));
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
