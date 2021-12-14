module com.vsu.cgcourse {
    requires javafx.controls;
    requires javafx.fxml;
    requires vecmath;
    requires java.desktop;


    opens com.vsu.cgcourse to javafx.fxml;
    exports com.vsu.cgcourse;
    exports com.vsu.cgcourse.model;
    opens com.vsu.cgcourse.model to javafx.fxml;

}