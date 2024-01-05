package majestatyczne.bestie.frontend.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import majestatyczne.bestie.frontend.Constants;
import majestatyczne.bestie.frontend.HomePageApplication;
import majestatyczne.bestie.frontend.model.*;
import majestatyczne.bestie.frontend.service.QuizService;
import majestatyczne.bestie.frontend.service.RewardCategoryService;
import majestatyczne.bestie.frontend.service.RewardStrategyService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.util.Callback;

public class QuizSettingsPageController implements Initializable {

    private QuizView quizView;

    private RewardStrategy strategy;

    private ObservableList<RewardStrategyParameterView> parameters;

    private ObservableList<RewardCategoryView> rewardCategories;

    private List<RewardCategory> rewardCategoryList;

    @FXML
    private ImageView backIcon;

    @FXML
    private Label quizNameLabel;

    @FXML
    private Label quizScoreLabel;

    @FXML
    private ComboBox<RewardStrategyType> rewardStrategyTypeBox;

    @FXML
    private Button saveButton;

    @FXML
    private VBox percentageVBox;

    @FXML
    private VBox scoreVBox;

    @FXML
    private Label percentageStrategyInfo;

    @FXML
    private Label scoreStrategyInfo;

    @FXML
    private TableView<RewardStrategyParameterView> parametersTable;

    @FXML
    private TableColumn<RewardStrategyParameterView, Integer> priorityColumn;

    @FXML
    private TableColumn<RewardStrategyParameterView, Integer> parameterValueColumn;

    @FXML
    private TableColumn<RewardStrategyParameterView, RewardCategory> rewardCategoryColumn;

    @FXML
    private TableColumn<RewardStrategyParameterView, RewardCategoryView> rewardCategoryChoiceColumn;




    public void setQuizView(QuizView quizView) {
        this.quizView = quizView;
        setData();
    }

    private void setData() {
        quizNameLabel.setText(quizView.getName());
        quizScoreLabel.setText(Constants.QUIZ_MAX_SCORE_INFO + quizView.getMaxScore());
        initializeStrategy();
        parametersTable.setItems(parameters);
        parametersTable.setVisible(false);
        parametersTable.setManaged(false);
        initializeRewardCategories();
    }

    private void initializeRewardCategories() {
        RewardCategoryService rewardCategoryService = new RewardCategoryService();
        rewardCategories = FXCollections.observableArrayList();
        rewardCategoryList = rewardCategoryService.getRewardCategories();
        rewardCategoryList.forEach(rewardCategory -> rewardCategories.add(new RewardCategoryView(rewardCategory.getId(), rewardCategory.getName())));

    }

    private void initializeStrategy() {
        parameters = FXCollections.observableArrayList();
        strategy = new RewardStrategy();
        QuizService quizService = new QuizService();
        strategy.setQuiz(quizService.getQuizById(quizView.getId()).orElseThrow(() -> new IllegalArgumentException("Quiz not found")));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backIcon.setImage(new Image(String.valueOf(HomePageApplication.class.getResource(Constants.BACK_ICON_RESOURCE))));
        rewardStrategyTypeBox.getItems().addAll(RewardStrategyType.PERCENTAGE, RewardStrategyType.SCORE);
        initializeParametersTable();
    }

    private void initializeParametersTable() {
        priorityColumn.setCellValueFactory(value -> value.getValue().getPriorityProperty());
        parameterValueColumn.setCellValueFactory(value -> value.getValue().getParameterValueProperty());
        rewardCategoryColumn.setCellValueFactory(value -> value.getValue().getRewardCategoryProperty());

        priorityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        parameterValueColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        priorityColumn.setOnEditCommit(this::onPriorityEdit);
        parameterValueColumn.setOnEditCommit(this::onParameterValueEdit);

        Callback<TableColumn<RewardStrategyParameterView, RewardCategoryView>, TableCell<RewardStrategyParameterView, RewardCategoryView>> cellFactory = new Callback<>() {
            @Override
            public TableCell<RewardStrategyParameterView, RewardCategoryView> call(TableColumn<RewardStrategyParameterView, RewardCategoryView> tableColumn) {
                return new TableCell<>() {
                    private final ComboBox<RewardCategoryView> comboBoxTableCell = new ComboBox<>(rewardCategories);

                    {
                        comboBoxTableCell.setOnAction(event -> {
                            if (getTableRow() != null && getTableRow().getItem() != null) {
                                RewardStrategyParameterView selectedParameterView = getTableRow().getItem();
                                RewardCategoryView selectedCategory = comboBoxTableCell.getValue();
                                if (selectedParameterView != null && selectedCategory != null) {
                                    RewardCategory category = rewardCategoryList.stream()
                                            .filter(x -> x.getId() == selectedCategory.getId())
                                            .findFirst()
                                            .orElse(null);
                                    selectedParameterView.setRewardCategory(category);
                                }
                            }
                        });
                    }

                    @Override
                    public void updateItem(RewardCategoryView item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(comboBoxTableCell);
                            comboBoxTableCell.setValue(item);
                        }
                    }
                };
            }
        };
        rewardCategoryChoiceColumn.setCellFactory(cellFactory);
    }

    private void onPriorityEdit(TableColumn.CellEditEvent<RewardStrategyParameterView, Integer> event) {
        event.getRowValue().setPriority(event.getNewValue());
    }

    private void onParameterValueEdit(TableColumn.CellEditEvent<RewardStrategyParameterView, Integer> event) {
        event.getRowValue().setParameterValue(event.getNewValue());
    }

    @FXML
    public void onGoBackClicked() {
        Stage stage = (Stage) rewardStrategyTypeBox.getScene().getWindow();
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

    @FXML
    public void onRewardStrategyTypeBoxClicked(ActionEvent actionEvent) {
        saveButton.setDisable(false);
        RewardStrategyType selectedType = rewardStrategyTypeBox.getValue();
        parametersTable.setVisible(true);
        parametersTable.setManaged(true);
        switch (selectedType) {
            case PERCENTAGE -> showPercentageStrategyDetails();
            case SCORE -> showScoreStrategyDetails();
        }

    }

    private void generateParameters(int numberOfRows) {
        for (int i = 0; i < numberOfRows; i++) {
            parameters.add(new RewardStrategyParameterView(i + 1, i + 1, 0, null));
        }
    }

    private void showPercentageStrategyDetails() {
        parameters.clear();
        percentageStrategyInfo.setText(Constants.PERCENTAGE_REWARD_STRATEGY_DESCRIPTION);
        percentageVBox.setVisible(true);
        percentageVBox.setManaged(true);
        scoreVBox.setVisible(false);
        scoreVBox.setManaged(false);
        strategy.setRewardStrategyType(RewardStrategyType.PERCENTAGE);
        System.out.println(strategy);
        generateParameters(2);
    }

    private void showScoreStrategyDetails() {
        parameters.clear();
        scoreStrategyInfo.setText(Constants.SCORE_REWARD_STRATEGY_DESCRIPTION);
        scoreVBox.setVisible(true);
        scoreVBox.setManaged(true);
        percentageVBox.setVisible(false);
        percentageVBox.setManaged(false);
        strategy.setRewardStrategyType(RewardStrategyType.SCORE);
        System.out.println(strategy);
        generateParameters(quizView.getMaxScore() + 1);
    }

    @FXML
    public void onSaveButtonClicked(ActionEvent actionEvent) {
        var rewardStrategyService = new RewardStrategyService();
        strategy.setParameters(parameters.stream().map(RewardStrategyParameterView::toRewardStrategyParameter).toList());
        RewardStrategy existingStrategy = rewardStrategyService.getRewardStrategyByQuizId(quizView.getId()).orElse(null);
        System.out.println("existing strategy " + existingStrategy);
        if (existingStrategy == null) {
            System.out.println("add " + rewardStrategyService.addRewardStrategy(strategy));
        } else {
            strategy.setId(existingStrategy.getId());
            System.out.println("update " + rewardStrategyService.updateRewardStrategy(strategy));
        }
        onGoBackClicked();
    }
}
