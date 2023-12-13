module majestatyczne.bestie.frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpmime;


    opens majestatyczne.bestie.frontend to javafx.fxml;
    exports majestatyczne.bestie.frontend;
}