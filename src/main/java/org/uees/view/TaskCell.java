package org.uees.view;

import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

import org.uees.controller.TaskController;
import org.uees.model.TasksModels.Task;

public class TaskCell extends ListCell<Task> {

    private final HBox root;
    private final VBox leftBox;
    private final Label descriptionLabel;
    private final Label dueDateLabel;
    private final Label priorityLabel;
    private final Button estadoBtn;
    private final Button editBtn;
    private final Button deleteBtn;
    private final TaskController principal;

    public TaskCell(TaskController principal, boolean esCompletada) {
        this.principal = principal;
        root = new HBox(16);
        root.setAlignment(Pos.CENTER_LEFT);
        root.setPadding(new Insets(12, 18, 12, 18));
        root.getStyleClass().add("task-card");

        leftBox = new VBox(4);
        leftBox.setAlignment(Pos.CENTER_LEFT);

        descriptionLabel = new Label();
        descriptionLabel.getStyleClass().add("task-desc");
        dueDateLabel = new Label();
        dueDateLabel.getStyleClass().add("task-date");
        priorityLabel = new Label();
        priorityLabel.getStyleClass().add("task-priority");

        leftBox.getChildren().addAll(descriptionLabel, dueDateLabel, priorityLabel);

        estadoBtn = new Button();
        estadoBtn.getStyleClass().add("task-btn");
        editBtn = new Button();
        editBtn.getStyleClass().add("task-btn");
        deleteBtn = new Button();
        deleteBtn.getStyleClass().add("task-btn");

        estadoBtn.setMinWidth(120);
        editBtn.setMinWidth(80);
        deleteBtn.setMinWidth(80);

        HBox btnBox = new HBox(12, estadoBtn, editBtn, deleteBtn);
        btnBox.setAlignment(Pos.CENTER_RIGHT);

        HBox.setHgrow(leftBox, Priority.ALWAYS);
        root.getChildren().addAll(leftBox, btnBox);
    }

    @Override
    protected void updateItem(Task tarea, boolean empty) {
        super.updateItem(tarea, empty);
        if (empty || tarea == null) {
            setGraphic(null);
        } else {
            descriptionLabel.setText(tarea.getDescription());
            dueDateLabel.setText("Vence: " + (tarea.getDueDate() != null ? tarea.getDueDate().toLocalDate().toString() : "Sin fecha"));
            priorityLabel.setText("Prioridad: " + tarea.getPriority().getDisplayName());
            priorityLabel.setStyle("-fx-text-fill: " + (tarea.getPriority() == Task.Priority.HIGH ? "#FF5A5A"
                    : tarea.getPriority() == Task.Priority.MEDIUM ? "#FFA726" : "#8BC34A")
                    + "; -fx-font-weight: bold;");

            if (tarea.getStatus() == Task.TaskStatus.COMPLETED) {
                estadoBtn.setText("✔ Completada");
                estadoBtn.setStyle("-fx-background-color: #4787ED; -fx-text-fill: #fff;");
            } else {
                estadoBtn.setText("⏰ Completar");
                estadoBtn.setStyle("-fx-background-color: #4787ED; -fx-text-fill: #fff;");
            }
            estadoBtn.setOnAction(e -> principal.cambiarEstadoTarea(tarea, tarea.getStatus() != Task.TaskStatus.COMPLETED));

            editBtn.setText("Editar");
            editBtn.setStyle("-fx-background-color: #4787ED; -fx-text-fill: #fff;");
            editBtn.setOnAction(e -> principal.editarTarea(tarea));

            deleteBtn.setText("Eliminar");
            deleteBtn.setStyle("-fx-background-color: #4787ED; -fx-text-fill: #fff;");
            deleteBtn.setOnAction(e -> principal.eliminarTarea(tarea));

            setGraphic(root);
        }
    }
}
