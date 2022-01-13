module com.eumll.animeshutdownguifix {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.eumll.animeshutdownguifix to javafx.fxml;
    exports com.eumll.animeshutdownguifix;
}