package majestatyczne.bestie.frontend.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import majestatyczne.bestie.frontend.model.QuizView;
import majestatyczne.bestie.frontend.model.ResultView;
import majestatyczne.bestie.frontend.service.QuizResultsService;

public class QuizPageController {
    private QuizView quizView;
    private ObservableList<ResultView> results;

    public void setQuizView(QuizView quizView) {
        this.quizView = quizView;
        getData();
        setData();
    }

    private void getData() {
        // TODO implement request for data
    }

    private void setData() {
        var quizResultsService = new QuizResultsService();
        results = FXCollections.observableArrayList();
        var resultList = quizResultsService.getResults(quizView.getId());
        resultList.forEach(result -> results.add(new ResultView(
                result.getPerson().getName(), result.getEndDate(), result.getScore()
        )));
    }
}
