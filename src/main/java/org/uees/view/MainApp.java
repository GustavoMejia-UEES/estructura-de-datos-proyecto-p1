package org.uees.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/uees/splash.fxml"));
        Scene splashScene = new Scene(loader.load());
        primaryStage.setScene(splashScene);
        primaryStage.setTitle("PENDO - Task Organizer");
        primaryStage.show();

        org.uees.controller.SplashController splashController = loader.getController();
        splashController.setMainStage(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
