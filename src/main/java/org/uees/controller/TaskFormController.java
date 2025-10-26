package org.uees.controller;

import org.uees.model.TasksModels.Task;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TaskFormController {

    @FXML
    private TextField descriptionField;
    @FXML
    private DatePicker dueDatePicker;
    @FXML
    private ComboBox<Task.Priority> priorityCombo;
    @FXML
    private Button createBtn;
    @FXML
    private Button cancelBtn;

    private TaskController principal;

    public void setPrincipal(TaskController principal) {
        this.principal = principal;
    }

    @FXML
    private void handleCreate() {
        String desc = descriptionField.getText();
        var date = dueDatePicker.getValue();
        var priority = priorityCombo.getValue();
        if (desc == null || desc.trim().isEmpty() || date == null || priority == null) {
            // Puedes mostrar un mensaje de error aquí
            return;
        }
        Task nuevaTarea = new Task(
                (int) (System.currentTimeMillis() % Integer.MAX_VALUE), desc, date.atStartOfDay(), priority
        );
        principal.agregarTarea(nuevaTarea);
        cerrarVentana();
    }

    @FXML
    private void handleCancel() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) createBtn.getScene().getWindow();
        stage.close();
    }
}
