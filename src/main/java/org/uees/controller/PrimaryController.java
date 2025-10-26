package org.uees.controller;

import javafx.stage.Stage;

public class PrimaryController {

    public PrimaryController(Stage stage) {
        // Ir directamente a la aplicación principal
        new TaskController(stage);
    }
}
