package majestatyczne.bestie.frontend.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import majestatyczne.bestie.frontend.Constants;
import majestatyczne.bestie.frontend.HomePageApplication;
import majestatyczne.bestie.frontend.model.Answer;
import majestatyczne.bestie.frontend.model.view.QuestionView;
import majestatyczne.bestie.frontend.model.view.QuizView;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AnswersPageController implements Initializable {

    @FXML
    private ImageView backIcon;

    @FXML
    private Label questionContentLabel;

    private QuizView quizView;

    private QuestionView questionView;

    private List<Answer> answerList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backIcon.setImage(new Image(String.valueOf(HomePageApplication.class.getResource(Constants.BACK_ICON_RESOURCE))));
    }

    public void setData(QuestionView selectedQuestion, List<Answer> answerList, QuizView quizView) {
        this.quizView = quizView;
        this.questionView = selectedQuestion;
        this.answerList = answerList;
        questionContentLabel.setText(questionView.getContent());
    }

    @FXML
    private void onGoBackClicked() {
        FXMLLoader fxmlLoader = new FXMLLoader(HomePageApplication.class.getResource(Constants.FXML_STATS_PAGE_RESOURCE));
        Stage stage = (Stage) backIcon.getScene().getWindow();
        try {
            Scene scene = new Scene(fxmlLoader.load(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
            StatsPageController statsPageController = fxmlLoader.getController();
            statsPageController.setQuizView(quizView);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
