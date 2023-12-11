module majestatyczne.bestie.frontend {
    requires javafx.controls;
    requires javafx.fxml;


    opens majestatyczne.bestie.frontend to javafx.fxml;
    exports majestatyczne.bestie.frontend;
}