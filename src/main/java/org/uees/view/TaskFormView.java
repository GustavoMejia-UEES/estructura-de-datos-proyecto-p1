package org.uees.view;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.stage.Stage;

import org.uees.controller.TaskController;
import org.uees.model.TasksModels.Task;

public class TaskFormView extends HBox {

    private final TextField descriptionField = new TextField();
    private final DatePicker dueDatePicker = new DatePicker();
    private final ComboBox<Task.Priority> priorityCombo = new ComboBox<>();
    private final Button createBtn = new Button("Crear");
    private final Button cancelBtn = new Button("Cancelar");
    private final TaskController principal;
    private Task tareaAEditar = null;
    private boolean esModoEdicion = false;

    public TaskFormView(TaskController principal) {
        this(principal, null);
    }

    public TaskFormView(TaskController principal, Task tareaAEditar) {
        this.principal = principal;
        this.tareaAEditar = tareaAEditar;
        this.esModoEdicion = (tareaAEditar != null);

        this.getStyleClass().add("form-root");
        this.getStylesheets().add(getClass().getResource("/css/taskform.css").toExternalForm());
        setAlignment(Pos.CENTER);
        setSpacing(0);
        setPrefWidth(720);
        setPrefHeight(520);

        ImageView taskImg = new ImageView(new Image(getClass().getResource("/images/taskimage.png").toExternalForm()));
        taskImg.setFitWidth(260);
        taskImg.setFitHeight(480);
        taskImg.setPreserveRatio(true);
        VBox imgBox = new VBox(taskImg);
        imgBox.setAlignment(Pos.CENTER);
        imgBox.setStyle("-fx-background-color: #fff; -fx-pref-width: 320; -fx-min-width: 320; -fx-max-width: 320; -fx-pref-height: 520;");

        Label title = new Label(esModoEdicion ? "Editar Tarea" : "Nueva Tarea");
        title.getStyleClass().add("form-title");

        descriptionField.setPromptText("Descripción de la tarea");
        descriptionField.getStyleClass().add("form-input-large");

        dueDatePicker.setPromptText("Fecha límite");
        dueDatePicker.getStyleClass().add("form-input-small");
        priorityCombo.getItems().addAll(Task.Priority.HIGH, Task.Priority.MEDIUM, Task.Priority.LOW);
        priorityCombo.getSelectionModel().select(Task.Priority.MEDIUM);
        priorityCombo.getStyleClass().add("form-input-small");

        // Si estamos en modo edición, llenar los campos con los datos existentes
        if (esModoEdicion && tareaAEditar != null) {
            descriptionField.setText(tareaAEditar.getDescription());
            if (tareaAEditar.getDueDate() != null) {
                dueDatePicker.setValue(tareaAEditar.getDueDate().toLocalDate());
            }
            priorityCombo.getSelectionModel().select(tareaAEditar.getPriority());
        }

        HBox fechaPrioridadRow = new HBox(18, dueDatePicker, priorityCombo);
        fechaPrioridadRow.setAlignment(Pos.CENTER_LEFT);
        fechaPrioridadRow.setPadding(new Insets(0, 0, 10, 0));
        HBox.setHgrow(dueDatePicker, Priority.ALWAYS);
        HBox.setHgrow(priorityCombo, Priority.ALWAYS);

        createBtn.setText(esModoEdicion ? "Guardar" : "Crear");
        createBtn.getStyleClass().add("form-button-small");
        cancelBtn.getStyleClass().add("form-button-small");
        createBtn.setMinWidth(110);
        createBtn.setMaxWidth(140);
        cancelBtn.setMinWidth(110);
        cancelBtn.setMaxWidth(140);

        HBox btnRow = new HBox(18, createBtn, cancelBtn);
        btnRow.setAlignment(Pos.CENTER);

        VBox formBox = new VBox(24, title, descriptionField, fechaPrioridadRow, btnRow);
        formBox.setAlignment(Pos.CENTER);
        formBox.setStyle("-fx-padding: 0 0 0 44;");

        this.getChildren().addAll(imgBox, formBox);

        createBtn.setOnAction(e -> handleCreate());
        cancelBtn.setOnAction(e -> handleCancel());
    }

    private void handleCreate() {
        String desc = descriptionField.getText();
        var date = dueDatePicker.getValue();
        var priority = priorityCombo.getValue();
        if (desc == null || desc.trim().isEmpty() || date == null || priority == null) {
            // Puedes mostrar un mensaje de error aquí
            return;
        }

        if (esModoEdicion && tareaAEditar != null) {
            // Modo edición: actualizar tarea existente
            tareaAEditar.setDescription(desc);
            tareaAEditar.setDueDate(date.atStartOfDay());
            tareaAEditar.setPriority(priority);
            principal.actualizarTarea(tareaAEditar);
        } else {
            // Modo creación: crear nueva tarea
            Task nuevaTarea = new Task(
                    (int) (System.currentTimeMillis() % Integer.MAX_VALUE), desc, date.atStartOfDay(), priority
            );
            principal.agregarTarea(nuevaTarea);
        }
        cerrarVentana();
    }

    private void handleCancel() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) createBtn.getScene().getWindow();
        stage.close();
    }
}
