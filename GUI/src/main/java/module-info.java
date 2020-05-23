module jedrekszor.dataintegration.project {
    requires javafx.controls;
    requires javafx.fxml;

    opens jedrekszor.dataintegration.project to javafx.fxml;
    exports jedrekszor.dataintegration.project;
}