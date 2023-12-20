package majestatyczne.bestie.frontend.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import majestatyczne.bestie.frontend.Constants;
import majestatyczne.bestie.frontend.HomePageApplication;
import majestatyczne.bestie.frontend.service.FileUploadService;
import majestatyczne.bestie.frontend.service.QuizService;
import majestatyczne.bestie.frontend.model.Quiz;
import majestatyczne.bestie.frontend.model.QuizView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class HomePageController implements Initializable {
    @FXML
    private TableView<QuizView> quizTable;
    @FXML
    private TableColumn<QuizView, String> nameColumn;
    @FXML
    private TableColumn<QuizView, Date> dateColumn;
    @FXML
    private ImageView settingsIcon;
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
        setData();
    }

    @FXML
    public void onQuizSelected() {
        QuizView selectedQuiz = quizTable.getSelectionModel().getSelectedItem();
        moveToQuizPage(selectedQuiz);
    }

    public void moveToQuizPage(QuizView quizView) {
        Stage stage = (Stage) quizTable.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HomePageApplication.class.getResource(Constants.FXML_QUIZ_PAGE_RESOURCE));
        try {
            Scene scene = new Scene(fxmlLoader.load(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
            QuizPageController quizPageController = fxmlLoader.getController();
            quizPageController.setQuizView(quizView);
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDefaultUploadDirectory();
        nameColumn.setCellValueFactory(nameValue -> nameValue.getValue().getNameProperty());
        dateColumn.setCellValueFactory(dateValue -> dateValue.getValue().getDateProperty());
        setData();
        settingsIcon.setImage(new Image(String.valueOf(HomePageApplication.class.getResource(Constants.SETTINGS_ICON_RESOURCE))));
    }

    private void initializeQuizzes() {
        QuizService quizService = new QuizService();
        quizzes = FXCollections.observableArrayList();
        List<Quiz> quizList = quizService.getQuizzes();
        quizList.forEach(quiz -> quizzes.add(new QuizView(quiz.getId(), quiz.getName(), quiz.getMaxScore(), quiz.getDate())));
    }

    private void initializeDefaultUploadDirectory() {
        String home = System.getProperty("user.home");
        fileChooser.setInitialDirectory(new File(home + Constants.DEFAULT_UPLOAD_DIRECTORY));
    }

    public void setData() {
        initializeQuizzes();
        quizTable.setItems(quizzes);
    }
}
