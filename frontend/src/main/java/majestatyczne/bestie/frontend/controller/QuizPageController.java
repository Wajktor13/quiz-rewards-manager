package majestatyczne.bestie.frontend.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import majestatyczne.bestie.frontend.Constants;
import majestatyczne.bestie.frontend.HomePageApplication;
import majestatyczne.bestie.frontend.model.QuizView;
import majestatyczne.bestie.frontend.model.ResultView;
import majestatyczne.bestie.frontend.service.QuizResultsService;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.Date;
import java.util.ResourceBundle;

public class QuizPageController implements Initializable {

    private QuizView quizView;

    private ObservableList<ResultView> results;

    @FXML
    private TableView<ResultView> resultTable;

    @FXML
    private TableColumn<ResultView, String> nameColumn;

    @FXML
    private TableColumn<ResultView, Integer> scoreColumn;

    @FXML
    private TableColumn<ResultView, Date> endDateColumn;

    @FXML
    private TableColumn<ResultView, String> rewardColumn;

    @FXML
    private Label quizNameLabel;

    @FXML
    private Label quizDateLabel;

    @FXML
    private ImageView settingsIcon;

    @FXML
    private ImageView backIcon;

    public void setQuizView(QuizView quizView) {
        this.quizView = quizView;
        setData();
    }

    private void initializeResults() {
        results = FXCollections.observableArrayList();
        var quizResultsService = new QuizResultsService();
        var resultList = quizResultsService.getResults(quizView.getId());
        resultList.forEach(result -> results.add(new ResultView(
                result.getPerson().getName(), result.getEndDate(), result.getScore(), result.getReward()
        )));
        results.sort(
                Comparator.comparing(ResultView::getScore, Comparator.reverseOrder())
                        .thenComparing(ResultView::getEndDate)
        );

    }

    private void setData() {
        initializeResults();
        quizNameLabel.setText(quizView.getName());
        quizDateLabel.setText(quizView.getDate().toString());
        resultTable.setItems(results);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameColumn.setCellValueFactory(nameValue -> nameValue.getValue().getPersonNameProperty());
        scoreColumn.setCellValueFactory(scoreValue -> scoreValue.getValue().getScoreProperty());
        endDateColumn.setCellValueFactory(dateValue -> dateValue.getValue().getEndDateProperty());
        rewardColumn.setCellValueFactory(rewardValue -> rewardValue.getValue().getRewardProperty());
        settingsIcon.setImage(new Image(String.valueOf(HomePageApplication.class.getResource(Constants.SETTINGS_ICON_RESOURCE))));
        backIcon.setImage(new Image(String.valueOf(HomePageApplication.class.getResource(Constants.BACK_ICON_RESOURCE))));
    }

    public void onGoBackClicked() {
        FXMLLoader fxmlLoader = new FXMLLoader(HomePageApplication.class.getResource(Constants.FXML_HOME_PAGE_RESOURCE));
        Stage stage = (Stage) resultTable.getScene().getWindow();
        try {
            Scene scene = new Scene(fxmlLoader.load(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onSettingsClicked() {
        FXMLLoader fxmlLoader = new FXMLLoader(HomePageApplication.class.getResource(Constants.FXML_QUIZ_SETTINGS_RESOURCE));
        Stage stage = (Stage) resultTable.getScene().getWindow();
        try {
            Scene scene = new Scene(fxmlLoader.load(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
            QuizSettingsPageController quizSettingsPageController = fxmlLoader.getController();
            quizSettingsPageController.setQuizView(quizView);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
