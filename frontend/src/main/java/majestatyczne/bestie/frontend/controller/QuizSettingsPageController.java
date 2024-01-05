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
        getStrategy();
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

    private void getStrategy() {
        RewardStrategyService rewardStrategyService = new RewardStrategyService();
        parameters = FXCollections.observableArrayList();
        strategy = rewardStrategyService.getRewardStrategyById(quizView.getId()).orElse(null);
        if (strategy == null ) {
            strategy = new RewardStrategy();
            QuizService quizService = new QuizService();
            strategy.setQuiz(quizService.getQuizById(quizView.getId()).orElseThrow(() -> new IllegalArgumentException("Quiz not found")));
            strategy.setRewardStrategyType(RewardStrategyType.PERCENTAGE);
        } else{

        }
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

//        rewardCategoryChoiceColumn = new TableColumn<>("Zmień kategorię");

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
        parametersTable.getColumns().add(rewardCategoryChoiceColumn);

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
            default -> {
//                alert ioio
            }
        }

    }

    private void generateParameters(int numberOfRaws) {

    }

    private void showPercentageStrategyDetails() {
        parameters.clear();
        percentageStrategyInfo.setText(Constants.PERCENTAGE_REWARD_STRATEGY_DESCRIPTION);
        percentageVBox.setVisible(true);
        percentageVBox.setManaged(true);
        scoreVBox.setVisible(false);
        scoreVBox.setManaged(false);
        System.out.println(strategy);
        if (strategy.getParameters() == null) {
            parameters.addAll(new RewardStrategyParameterView(1, 1, 0, null),
                    new RewardStrategyParameterView(2, 2, 0, null));

        } else {
            strategy.getParameters().forEach(parameter -> parameters.add(new RewardStrategyParameterView(parameter.getId(),
                    parameter.getPriority(), parameter.getParameterValue(), parameter.getRewardCategory())));

        }
    }

    private void showScoreStrategyDetails() {
        parameters.clear();
        scoreStrategyInfo.setText(Constants.SCORE_REWARD_STRATEGY_DESCRIPTION);
        scoreVBox.setVisible(true);
        scoreVBox.setManaged(true);
        percentageVBox.setVisible(false);
        percentageVBox.setManaged(false);
    }

    @FXML
    public void onSaveButtonClicked(ActionEvent actionEvent) {
        var rewardStrategyService = new RewardStrategyService();
        strategy.setParameters(parameters.stream().map(RewardStrategyParameterView::toRewardStrategyParameter).toList());
        System.out.println(strategy);
        System.out.println(rewardStrategyService.addRewardStrategy(strategy));
        onGoBackClicked();
    }
}
