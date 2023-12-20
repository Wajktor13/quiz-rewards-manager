package majestatyczne.bestie.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;


import java.io.IOException;

public class HomePageApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Constants.FXML_HOME_PAGE_RESOURCE));
        Scene scene = new Scene(fxmlLoader.load(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
        setStageProperties(stage);
        stage.setScene(scene);
        stage.show();
    }


    private void setStageProperties(Stage stage) {
        stage.setTitle(Constants.STAGE_TITLE);
        stage.setResizable(false);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource(Constants.APPLICATION_ICON_RESOURCE))));
        if (Taskbar.isTaskbarSupported()) {
            setIconOnTaskbar();
        }
    }

    private void setIconOnTaskbar() {
        var taskbar = Taskbar.getTaskbar();
        if (taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
            final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
            var dockIcon = defaultToolkit.getImage(getClass().getResource(Constants.APPLICATION_ICON_RESOURCE));
            taskbar.setIconImage(dockIcon);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}