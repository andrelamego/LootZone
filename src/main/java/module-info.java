module lamego.lootzone {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires jdk.compiler;
    requires java.sql;
    requires jtds;

    opens lamego.lootzone to javafx.fxml;
    exports lamego.lootzone;
    exports lamego.lootzone.app.controller;
    opens lamego.lootzone.app.controller to javafx.fxml;
    exports lamego.lootzone.frameworks.ui.javafx.controllers;
    opens lamego.lootzone.frameworks.ui.javafx.controllers to javafx.fxml;
}