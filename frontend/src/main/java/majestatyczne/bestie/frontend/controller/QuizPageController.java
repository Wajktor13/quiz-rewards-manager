package majestatyczne.bestie.frontend.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import majestatyczne.bestie.frontend.model.QuizView;
import majestatyczne.bestie.frontend.model.ResultView;
import majestatyczne.bestie.frontend.service.QuizResultsService;

import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class QuizPageController implements Initializable {
    private QuizView quizView;
    private ObservableList<ResultView> results;
    @FXML
    private TableView<ResultView> resultTable;
    @FXML
    private TableColumn<ResultView,String> nameColumn;
    @FXML
    private TableColumn<ResultView,Integer> scoreColumn;
    @FXML
    private TableColumn<ResultView, Date> endDateColumn;
    @FXML
    private TableColumn<ResultView,String> rewardColumn;
    @FXML
    private Label quizNameLabel;
    @FXML
    private Label quizDateLabel;

    public void setQuizView(QuizView quizView) {
        this.quizView = quizView;
        setData();
    }

    private void initializeResults() {
        results = FXCollections.observableArrayList();
        var quizResultsService = new QuizResultsService();
        var resultList = quizResultsService.getResults(quizView.getId());
        resultList.forEach(result -> results.add(new ResultView(
                result.getPerson().getName(), result.getEndDate(), result.getScore()
        )));
        results.sort(Comparator.comparing(ResultView::getScore).reversed());
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
    }
}
