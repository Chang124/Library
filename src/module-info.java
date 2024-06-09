module Library {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;
    requires java.desktop;

    opens application to javafx.fxml, javafx.base;
    exports application;
}

