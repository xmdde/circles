module com.example.circles {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.circles to javafx.fxml;
    exports com.example.circles;
}