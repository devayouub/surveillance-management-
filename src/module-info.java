module GitHub_surveillance_management {
    requires java.sql;
    requires javafx.fxml;
    requires  javafx.graphics;
    requires javafx.controls;
    requires java.desktop;
    requires javafx.base;
	requires java.base;
	requires jdk.compiler;
    requires javafx.swt;
    opens management to javafx.base;

    opens login;
}
