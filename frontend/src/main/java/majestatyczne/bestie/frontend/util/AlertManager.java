package majestatyczne.bestie.frontend.util;

import javafx.scene.control.Alert;
import majestatyczne.bestie.frontend.Constants;

public class AlertManager {

    public static void showWarningAlert(String text) {
        showAlert(Alert.AlertType.WARNING, Constants.ALERT_WARNING_TITLE, text);
    }

    public static void showConfirmationAlert(String text) {
        showAlert(Alert.AlertType.CONFIRMATION, Constants.ALERT_CONFIRMATION_TITLE, text);
    }

    public static void showErrorAlert(int statusCode, String text) {
        showAlert(Alert.AlertType.ERROR, Constants.ALERT_ERROR_TITLE, text, Constants.ALERT_ERROR_CODE + statusCode);
    }

    private static void showAlert(Alert.AlertType alertType, String title, String headerText) {
        showAlert(alertType, title, headerText, null);
    }

    private static void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
