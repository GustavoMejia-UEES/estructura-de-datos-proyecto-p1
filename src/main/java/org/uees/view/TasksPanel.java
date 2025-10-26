package org.uees.view;

import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;

import org.uees.model.TasksModels.Task;

import javafx.geometry.Insets;

public class TasksPanel extends VBox {

    public final TextField searchField = new TextField();
    public final ComboBox<String> comboPrioridad = new ComboBox<>();
    public final ComboBox<String> comboRangoPendientes = new ComboBox<>();
    public final Button btnBuscar = new Button("Buscar");
    public final Button btnCrearTarea = new Button("Crear Tarea");
    public final ListView<Task> listTareasIncompletas = new ListView<>();

    public TasksPanel() {
        super(18);
        setPrefHeight(400);
        getStyleClass().add("tasks-panel");

        HBox filtroBox = new HBox(18,
                searchField,
                comboPrioridad,
                comboRangoPendientes,
                btnBuscar
        );
        filtroBox.setAlignment(Pos.CENTER_LEFT);
        filtroBox.setPadding(new Insets(0, 0, 0, 0));

        searchField.setPromptText("Buscar tareas...");
        searchField.getStyleClass().add("main-input-small");

        comboPrioridad.getStyleClass().add("main-input-small");
        comboPrioridad.setPromptText("Prioridad");
        comboPrioridad.getItems().addAll("Todas", "Alta", "Media", "Baja");
        comboPrioridad.getSelectionModel().select("Todas");

        comboRangoPendientes.getStyleClass().add("main-input-small");
        comboRangoPendientes.setPromptText("Rango");
        comboRangoPendientes.getItems().addAll("Todas", "Semana", "15 días", "Mes");
        comboRangoPendientes.getSelectionModel().select("Todas");

        btnBuscar.getStyleClass().add("main-button-small");
        btnCrearTarea.getStyleClass().add("main-button-small");

        listTareasIncompletas.getStyleClass().add("main-list");
        listTareasIncompletas.setPrefHeight(260);

        Label tituloTareas = new Label("Tareas incompletas");
        tituloTareas.getStyleClass().add("panel-title");
        HBox tituloBox = new HBox(18, tituloTareas, btnCrearTarea);
        tituloBox.setAlignment(Pos.CENTER_LEFT);

        this.getChildren().addAll(filtroBox, tituloBox, listTareasIncompletas);
    }
}
