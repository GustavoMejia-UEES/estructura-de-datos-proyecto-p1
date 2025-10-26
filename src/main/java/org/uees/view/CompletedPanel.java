package org.uees.view;

import javafx.scene.layout.VBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;

import org.uees.model.TasksModels.Task;

import javafx.collections.ObservableList;

public class CompletedPanel extends VBox {

    public final ListView<Task> listTareasCompletadas = new ListView<>();
    public final Button btnLimpiarCompletadas = new Button("Limpiar completadas");
    public final Button btnExportarHistorial = new Button("Exportar historial");

    public CompletedPanel() {
        super(16);
        setPrefHeight(320);
        getStyleClass().add("completed-tasks-panel");

        Label title = new Label("Tareas completadas");
        title.getStyleClass().add("panel-title");

        listTareasCompletadas.getStyleClass().add("main-list");
        listTareasCompletadas.setPrefHeight(240);

        btnLimpiarCompletadas.getStyleClass().add("main-button-expanded");
        btnExportarHistorial.getStyleClass().add("main-button-expanded");

        HBox btnBox = new HBox(24, btnExportarHistorial, btnLimpiarCompletadas);
        btnBox.setAlignment(Pos.CENTER_LEFT);

        this.getChildren().addAll(title, listTareasCompletadas, btnBox);
    }

    public void setTareasCompletadas(ObservableList<Task> tareas) {
        listTareasCompletadas.setItems(tareas);
    }
}
