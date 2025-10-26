package org.uees.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class SplashController {

    @FXML
    private ImageView logoImage;

    private Stage mainStage;

    public void setMainStage(Stage stage) {
        this.mainStage = stage;

        // Agregar icono al splash
        try {
            stage.getIcons().add(new javafx.scene.image.Image(getClass().getResource("/images/PendoSplashLogo.png").toExternalForm()));
        } catch (Exception e) {
            System.out.println("No se pudo cargar el icono del splash");
        }
    }

    @FXML
    public void initialize() {
        logoImage.setImage(new Image(getClass().getResource("/images/PendoSplashLogo.png").toExternalForm()));

        // Auto-cargar después de 2.5 segundos
        PauseTransition pause = new PauseTransition(Duration.seconds(2.5));
        pause.setOnFinished(_ -> cargarAplicacion());
        pause.play();
    }

    private void cargarAplicacion() {
        // Animación de fade antes de cargar la aplicación principal
        FadeTransition fade = new FadeTransition(Duration.seconds(0.6), logoImage.getScene().getRoot());
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setOnFinished(_ -> new TaskController(mainStage));
        fade.play();
    }
}
