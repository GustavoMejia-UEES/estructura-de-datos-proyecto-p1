package org.uees.view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.geometry.Pos;

import org.uees.model.TasksModels.Task;

public class CompletedByDateView extends BorderPane {

    public final ListView<String> fechasView = new ListView<>();
    public final ListView<Task> tasksView = new ListView<>();
    public final Label selectedDateLabel = new Label("Selecciona una fecha");
    public final Label taskCountLabel = new Label("0 tareas");
    public final Button btnExportAll = new Button("Exportar todo");
    public final Button btnClose = new Button("Cerrar");

    public CompletedByDateView() {
        Label title = new Label("Tareas por Fecha de Creación");
        title.getStyleClass().add("app-title");

        HBox headerButtons = new HBox(12, btnExportAll, btnClose);
        headerButtons.setAlignment(Pos.CENTER_RIGHT);

        HBox header = new HBox();
        header.getChildren().addAll(title, headerButtons);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(20);
        header.getStyleClass().add("header-panel");
        header.setPrefHeight(80);

        Label fechasTitle = new Label("Fechas disponibles");
        fechasTitle.getStyleClass().add("panel-title");
        fechasView.getStyleClass().add("main-list");
        fechasView.setPrefHeight(400);

        VBox leftPanel = new VBox(16, fechasTitle, fechasView);
        leftPanel.setPrefWidth(280);
        leftPanel.getStyleClass().add("lists-panel");
        leftPanel.setAlignment(Pos.TOP_CENTER);

        selectedDateLabel.getStyleClass().add("panel-title");
        taskCountLabel.getStyleClass().add("task-count-label");
        tasksView.getStyleClass().add("main-list");
        tasksView.setPrefHeight(400);

        VBox headerBox = new VBox(8, selectedDateLabel, taskCountLabel);
        headerBox.setAlignment(Pos.CENTER_LEFT);

        VBox rightPanel = new VBox(16);
        rightPanel.getChildren().addAll(headerBox, tasksView);
        rightPanel.setPrefWidth(500);
        rightPanel.getStyleClass().add("tasks-panel");

        this.setTop(header);
        this.setLeft(leftPanel);
        this.setCenter(rightPanel);

        btnExportAll.getStyleClass().add("main-button");
        btnClose.getStyleClass().add("main-button");

        this.setPrefSize(900, 600);
    }

    public void updateSelectedDate(String date, int taskCount) {
        selectedDateLabel.setText("Tareas del " + date);
        taskCountLabel.setText(taskCount + " tareas");
    }
}
