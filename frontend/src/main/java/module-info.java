module majestatyczne.bestie.frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpmime;
    requires java.desktop;


    opens majestatyczne.bestie.frontend to javafx.fxml;
    exports majestatyczne.bestie.frontend;
    exports majestatyczne.bestie.frontend.controller;
    opens majestatyczne.bestie.frontend.controller to javafx.fxml;
}