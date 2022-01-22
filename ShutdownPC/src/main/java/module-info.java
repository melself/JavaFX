module com.eumll.shutdownpc {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.eumll.shutdownpc to javafx.fxml;
    exports com.eumll.shutdownpc;
}