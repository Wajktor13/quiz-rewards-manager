package majestatyczne.bestie.frontend.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import majestatyczne.bestie.frontend.Constants;
import majestatyczne.bestie.frontend.HomePageApplication;
import majestatyczne.bestie.frontend.model.*;
import majestatyczne.bestie.frontend.service.RewardCategoryService;
import majestatyczne.bestie.frontend.service.RewardService;
import javafx.util.Callback;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GlobalSettingsPageController implements Initializable {

    @FXML
    private ImageView backIcon;

    private ObservableList<RewardView> rewards;

    private List<Reward> rewardList;

    private ObservableList<RewardCategoryView> rewardCategories;

    private List<RewardCategory> rewardCategoryList;

    @FXML
    private TableView<RewardView> rewardTable;

    @FXML
    private TableColumn<RewardView, String> rewardNameColumn;

    @FXML
    private TableColumn<RewardView, RewardCategory> rewardCategoryColumn;

    @FXML
    private TableColumn<RewardView, String> rewardDescriptionColumn;

    @FXML
    private TableColumn<RewardView, RewardCategoryView> rewardCategoryChoiceColumn;

    @FXML
    private TableView<RewardCategoryView> categoryTable;

    @FXML
    private TableColumn<RewardCategoryView, String> categoryNameColumn;

    @FXML
    private TextField newCategoryTextField;

    @FXML
    private Button addCategoryButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeRewardTable();
        initializeRewardCategoriesTable();
        setData();
        backIcon.setImage(new Image(String.valueOf(HomePageApplication.class.getResource(Constants.BACK_ICON_RESOURCE))));
        newCategoryTextField.setPromptText(Constants.NEW_REWARD_CATEGORY_PROMPT);
        addCategoryButton.setOnAction(this::onAddCategoryClicked);
    }

    private void initializeRewardCategoriesTable() {
        categoryNameColumn.setCellValueFactory(value -> value.getValue().getNameProperty());
        categoryNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        categoryNameColumn.setOnEditCommit(this::onCategoryEdit);
    }

    private void onCategoryEdit(TableColumn.CellEditEvent<RewardCategoryView, String> event) {
        event.getRowValue().setName(event.getNewValue());
    }

    private void initializeRewardTable() {
        rewardNameColumn.setCellValueFactory(value -> value.getValue().getNameProperty());
        rewardCategoryColumn.setCellValueFactory(value -> value.getValue().getRewardCategoryProperty());
        rewardDescriptionColumn.setCellValueFactory(value -> value.getValue().getDescriptionProperty());

        rewardNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        rewardDescriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        rewardNameColumn.setOnEditCommit(this::onRewardNameEdit);
        rewardDescriptionColumn.setOnEditCommit(this::onRewardDescriptionEdit);
        rewardCategoryChoiceColumn = new TableColumn<>("Zmien kategorie");
        Callback<TableColumn<RewardView, RewardCategoryView>, TableCell<RewardView, RewardCategoryView>> cellFactory = new Callback<>() {
            @Override
            public TableCell<RewardView, RewardCategoryView> call(TableColumn<RewardView, RewardCategoryView> rewardViewRewardCategoryViewTableColumn) {
                return new TableCell<>() {
                    private final ComboBox<RewardCategoryView> comboBoxTableCell = new ComboBox<>(rewardCategories);

                    {
                        comboBoxTableCell.setOnAction(event -> {
                            if (getTableRow() != null && getTableRow().getItem() != null) {
                                RewardView selectedReward = getTableRow().getItem();
                                RewardCategoryView selectedCategory = comboBoxTableCell.getValue();
                                if (selectedReward != null && selectedCategory != null) {
                                    RewardCategory category = rewardCategoryList.stream()
                                            .filter(x -> x.getId() == selectedCategory.getId())
                                            .findFirst()
                                            .orElse(null);
                                    selectedReward.setRewardCategory(category);
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
        rewardTable.getColumns().add(rewardCategoryChoiceColumn);
    }

    private void onRewardDescriptionEdit(TableColumn.CellEditEvent<RewardView, String> event) {
        event.getRowValue().setDescription(event.getNewValue());
    }

    private void onRewardNameEdit(TableColumn.CellEditEvent<RewardView, String> event) {
        event.getRowValue().setName(event.getNewValue());
    }

    private void setData() {
        initializeRewardCategories();
        initializeRewards();
        rewardTable.setItems(rewards);
        categoryTable.setItems(rewardCategories);
    }

    private void initializeRewardCategories() {
        RewardCategoryService rewardCategoryService = new RewardCategoryService();
        rewardCategories = FXCollections.observableArrayList();
        rewardCategoryList = rewardCategoryService.getRewardCategories();
        rewardCategoryList.forEach(rewardCategory -> rewardCategories.add(new RewardCategoryView(rewardCategory.getId(), rewardCategory.getName())));
    }

    private void initializeRewards() {
        RewardService rewardService = new RewardService();
        rewards = FXCollections.observableArrayList();
        rewardList = rewardService.getRewards();
        rewardList.forEach(reward -> rewards.add(new RewardView(reward.getId(), reward.getRewardCategory(), reward.getName(), reward.getDescription())));
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

    @FXML
    public void onAddCategoryClicked(ActionEvent actionEvent) {
        String categoryName = newCategoryTextField.getText();
        if (categoryName.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Wpisz nazwę kategorii");
            return;
        }
        RewardCategory rewardCategory = new RewardCategory(categoryName);
        RewardCategoryService rewardCategoryService = new RewardCategoryService();
        int responseCode = rewardCategoryService.addRewardCategory(rewardCategory);
        switch (responseCode) {
            case HttpStatus.SC_OK, HttpStatus.SC_CREATED -> {
                newCategoryTextField.clear();
                onRequestAccepted(Constants.ADD_REWARD_CATEGORY_INFO);
            }
            default -> onRequestFailed(responseCode, Constants.ADD_REWARD_CATEGORY_ERROR_TITLE);
        }
    }

    private void showAlert(Alert.AlertType alertType, String title) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(title);
        alert.showAndWait();
    }

    public void onSaveChangesClicked() {
        saveRewards();
        saveRewardCategories();
    }

    private void saveRewardCategories() {
        RewardCategoryService rewardCategoryService = new RewardCategoryService();
        for (RewardCategoryView rewardCategoryView : rewardCategories) {
            System.out.println(rewardCategoryView.getName());
            if (rewardCategoryView.getName().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, Constants.ADD_REWARD_CATEGORY_EMPTY_WARNING);
                return;
            }
        }
        rewardCategories.forEach(rewardCategoryView -> rewardCategoryList.forEach(rewardCategory -> {
            if (rewardCategory.getId() == rewardCategoryView.getId()) {
                rewardCategory.setName(rewardCategoryView.getName());
            }
        }));
        int responseCode = rewardCategoryService.updateRewardCategories(rewardCategoryList);
        switch (responseCode) {
            case HttpStatus.SC_OK, HttpStatus.SC_ACCEPTED -> onRequestAccepted(Constants.UPDATE_REWARD_CATEGORIES_INFO);
            default -> onRequestFailed(responseCode, Constants.UPDATE_REWARD_CATEGORIES_ERROR_TITLE);
        }
    }

    private void saveRewards() {
        RewardService rewardService = new RewardService();
        rewards.forEach(rewardView -> rewardList.forEach(reward -> {
            if (reward.getId() == rewardView.getId()) {
                reward.setName(rewardView.getName());
                reward.setRewardCategory(rewardView.getRewardCategory());
                reward.setDescription(rewardView.getDescription());
            }
        }));
        int responseCode = rewardService.updateRewards(rewardList);
        switch (responseCode) {
            case HttpStatus.SC_OK, HttpStatus.SC_ACCEPTED -> onRequestAccepted(Constants.UPDATE_REWARDS_INFO);
            default -> onRequestFailed(responseCode, Constants.UPDATE_REWARDS_ERROR_TITLE);
        }
    }

    @FXML
    private void onRequestAccepted(String alertTitle) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(alertTitle);
        alert.setHeaderText(alertTitle);
        alert.showAndWait();
    }

    @FXML
    private void onRequestFailed(int statusCode, String alertTitle) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(alertTitle);
        alert.setContentText(Constants.UPDATE_REWARDS_ERROR + statusCode);
        alert.showAndWait();
    }
}
