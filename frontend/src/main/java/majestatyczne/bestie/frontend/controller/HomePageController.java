package majestatyczne.bestie.frontend.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import majestatyczne.bestie.frontend.service.FileUploadService;
import majestatyczne.bestie.frontend.service.QuizService;
import majestatyczne.bestie.frontend.model.Quiz;
import majestatyczne.bestie.frontend.model.QuizView;

import java.io.File;
import java.net.URL;
import java.util.*;

public class HomePageController implements Initializable {
    @FXML
    private TableView<QuizView> quizTable;
    @FXML
    private TableColumn<QuizView, String> nameColumn;
    @FXML
    private TableColumn<QuizView, Date> dateColumn;

    private ObservableList<QuizView> quizzes;

    FileUploadService fileUploadService = new FileUploadService();
    FileChooser fileChooser = new FileChooser();

    @FXML
    public void onGetFile() {
        File file = fileChooser.showOpenDialog(new Stage());
        if (file == null) {
            return;
        }
        fileUploadService.makeRequest(file);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDefaultUploadDirectory();
        initializeQuizzes();

        nameColumn.setCellValueFactory(nameValue -> nameValue.getValue().getNameProperty());
        dateColumn.setCellValueFactory(dateValue -> dateValue.getValue().getDateProperty());
        setData();
    }

    private void initializeQuizzes() {
        quizzes = FXCollections.observableArrayList();
        QuizService quizService = new QuizService();
        List<Quiz> quizList = quizService.getQuizzes();
        quizList.forEach(quiz -> quizzes.add(new QuizView(quiz.getName(), quiz.getMaxScore(), quiz.getDate())));
    }

    private void initializeDefaultUploadDirectory() {
        String home = System.getProperty("user.home");
        fileChooser.setInitialDirectory(new File(home + "/Downloads"));
    }

    public void setData() {
        quizTable.setItems(quizzes);
    }
}
