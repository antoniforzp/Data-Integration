module Logic {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdom2;
    requires java.logging;
    requires java.xml;
    requires saxon9.s9api;
    requires org.apache.commons.lang3;
    requires java.desktop;

    opens gui.tabs to javafx.fxml;
    opens gui to javafx.fxml;

    exports gui;
    exports gui.tabs;
}