module com.example.etlap {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens hu.petrik.etlap to javafx.fxml;
    exports hu.petrik.etlap;
    exports hu.petrik.etlap.controllers;
    opens hu.petrik.etlap.controllers to javafx.fxml;
}