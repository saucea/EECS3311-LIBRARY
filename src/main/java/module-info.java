module com.libraryapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.postgresql.jdbc;
    requires java.sql;
    requires java.naming;

    opens com.libraryapplication to javafx.fxml;
    exports com.libraryapplication;
}
