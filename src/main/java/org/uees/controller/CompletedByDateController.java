package org.uees.controller;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import org.uees.view.CompletedByDateView;
import org.uees.model.TasksModels.App;
import org.uees.model.TasksModels.Task;
import org.uees.model.TasksModels.ToDos;

public class CompletedByDateController {

    private final CompletedByDateView view;
    private final Stage stage;
    private final App appBackend;
    private final ObservableList<String> fechasDisponibles;
    private final ObservableList<Task> tareasEnFechaSeleccionada;

    public CompletedByDateController(App appBackend) {
        this.appBackend = appBackend;
        this.view = new CompletedByDateView();
        this.stage = new Stage();
        this.fechasDisponibles = FXCollections.observableArrayList();
        this.tareasEnFechaSeleccionada = FXCollections.observableArrayList();

        setupStage();
        setupEventHandlers();
        loadAvailableDates();
    }

    private void setupStage() {
        Scene scene = new Scene(view, 900, 600);
        scene.getStylesheets().add(getClass().getResource("/css/primary.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Tareas por Fecha de Creación");
        stage.setResizable(true);

        try {
            stage.getIcons().add(new javafx.scene.image.Image(getClass().getResource("/images/PendoSplashLogo.png").toExternalForm()));
        } catch (Exception e) {
        }
    }

    private void setupEventHandlers() {
        view.fechasView.setItems(fechasDisponibles);
        view.fechasView.setCellFactory(listView -> {
            return new javafx.scene.control.ListCell<String>() {
                @Override
                protected void updateItem(String fecha, boolean empty) {
                    super.updateItem(fecha, empty);
                    if (empty || fecha == null) {
                        setText(null);
                    } else {
                        // Obtener conteo de tareas para esta fecha
                        int count = getTotalTasksCountOnDate(fecha);
                        setText("📅 " + fecha + " (" + count + " tareas)");
                    }
                }
            };
        });

        view.tasksView.setItems(tareasEnFechaSeleccionada);
        view.tasksView.setCellFactory(listView -> {
            return new javafx.scene.control.ListCell<Task>() {
                @Override
                protected void updateItem(Task task, boolean empty) {
                    super.updateItem(task, empty);
                    if (empty || task == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        // Formato: "Tarea 1 (Alta) - Creada: 15:30"
                        String timeCreated = task.getCreatedAt().toLocalTime().toString().substring(0, 5);
                        String text = String.format("%s (%s) - Creada: %s",
                                task.getDescription(),
                                task.getPriority().getDisplayName(),
                                timeCreated
                        );
                        setText(text);
                        setGraphic(null);
                    }
                }
            };
        });

        view.fechasView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadTasksForDate(newSelection);
            }
        });

        view.btnExportAll.setOnAction(e -> exportAllGroupedByDate());

        // Evento: Botón cerrar
        view.btnClose.setOnAction(e -> stage.close());
    }

    private void loadAvailableDates() {
        fechasDisponibles.clear();

        for (String listName : appBackend.getAllListNames()) {
            ToDos todoList = (ToDos) appBackend.getTodoList(listName);
            if (todoList != null) {
                List<String> allDates = todoList.getAvailableCreationDates();
                for (String date : allDates) {
                    List<Task> completedOnDate = todoList.getTasksCreatedOnDate(date);
                    if (!completedOnDate.isEmpty() && !fechasDisponibles.contains(date)) {
                        fechasDisponibles.add(date);
                    }
                }
            }
        }

        // Ordenar fechas (más reciente primero)
        fechasDisponibles.sort((d1, d2) -> d2.compareTo(d1));
    }

    private void loadTasksForDate(String date) {
        tareasEnFechaSeleccionada.clear();

        // Obtener tareas de todas las listas para esta fecha
        for (String listName : appBackend.getAllListNames()) {
            ToDos todoList = (ToDos) appBackend.getTodoList(listName);
            if (todoList != null) {
                List<Task> tasksOnDate = todoList.getTasksCreatedOnDate(date);
                tareasEnFechaSeleccionada.addAll(tasksOnDate);
            }
        }

        // Actualizar labels
        view.updateSelectedDate(date, tareasEnFechaSeleccionada.size());
    }

    private int getTotalTasksCountOnDate(String date) {
        int totalCount = 0;

        for (String listName : appBackend.getAllListNames()) {
            ToDos todoList = (ToDos) appBackend.getTodoList(listName);
            if (todoList != null) {
                totalCount += todoList.getTasksCountOnDate(date);
            }
        }

        return totalCount;
    }

    private void exportAllGroupedByDate() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exportar Tareas por Fecha de Creación");
        fileChooser.setInitialFileName("tareas_por_fecha.txt");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Archivos de texto", "*.txt")
        );

        java.io.File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                ToDos tempTodos = new ToDos("temp");
                tempTodos.exportCompletedTasksByDate(file, fechasDisponibles, date -> {
                    List<Task> tasksForDate = new ArrayList<>();
                    for (String listName : appBackend.getAllListNames()) {
                        ToDos todoList = (ToDos) appBackend.getTodoList(listName);
                        if (todoList != null) {
                            tasksForDate.addAll(todoList.getTasksCreatedOnDate(date));
                        }
                    }
                    return tasksForDate;
                });
            } catch (IOException e) {
                // Silencioso - no mostrar alertas
            }
        }
    }

    public void show() {
        stage.show();
    }

    public Stage getStage() {
        return stage;
    }
}
