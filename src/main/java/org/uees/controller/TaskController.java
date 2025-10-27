package org.uees.controller;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.scene.control.TextInputDialog;
import java.io.FileWriter;
import java.io.IOException;

import org.uees.view.TaskOrganizer;
import org.uees.model.TasksModels.App;
import org.uees.model.TasksModels.Task;
import org.uees.model.TasksModels.ToDos;
import org.uees.view.TaskCell;
import org.uees.view.TaskFormView;

public class TaskController {

    public final TaskOrganizer organizer = new TaskOrganizer();
    public final ObservableList<String> listas = FXCollections.observableArrayList();
    public final ObservableList<Task> tareasPendientes = FXCollections.observableArrayList();
    public final ObservableList<Task> tareasCompletadas = FXCollections.observableArrayList();

    private final App appBackend = new App();
    private String listaActual = "Default";

    public TaskController(Stage stage) {
        Scene scene = new Scene(organizer, 1280, 832);
        scene.getStylesheets().add(getClass().getResource("/css/primary.css").toExternalForm());

        try {
            appBackend.loadAllFromFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (appBackend.getTodoList(listaActual) == null) {
            appBackend.createTodoList(listaActual);
            guardar();
        }

        listas.setAll(appBackend.getAllListNames());
        organizer.listsPanel.setListas(listas);

        organizer.listsPanel.listasView.getSelectionModel().selectedItemProperty().addListener((_, _, newVal) -> {
            if (newVal != null) {
                listaActual = newVal;
                actualizarListas();
            }
        });

        organizer.listsPanel.btnNuevaLista.setOnAction(_ -> crearLista());
        organizer.listsPanel.btnEliminarLista.setOnAction(_ -> eliminarLista());

        organizer.tasksPanel.btnCrearTarea.setOnAction(_ -> abrirFormularioNuevaTarea());
        organizer.tasksPanel.btnBuscar.setOnAction(_ -> buscarTareas());
        organizer.completedPanel.btnLimpiarCompletadas.setOnAction(_ -> limpiarCompletadas());
        organizer.completedPanel.btnExportarHistorial.setOnAction(_ -> exportarHistorial());
        organizer.completedPanel.btnVerPorFechas.setOnAction(_ -> abrirVentanaPorFechas());

        organizer.tasksPanel.listTareasIncompletas.setItems(tareasPendientes);
        organizer.tasksPanel.listTareasIncompletas.setCellFactory(listView -> new TaskCell(this, false));
        organizer.completedPanel.listTareasCompletadas.setItems(tareasCompletadas);
        organizer.completedPanel.listTareasCompletadas.setCellFactory(listView -> new TaskCell(this, true));

        // Filtro rango pendientes
        organizer.tasksPanel.comboRangoPendientes.getItems().clear();
        organizer.tasksPanel.comboRangoPendientes.getItems().addAll("Todas", "Semana", "15 días", "Mes");
        organizer.tasksPanel.comboRangoPendientes.getSelectionModel().select("Todas");
        organizer.tasksPanel.comboRangoPendientes.setOnAction(_ -> buscarTareas());

        // Cargar tareas completadas
        filtrarCompletadasPorPeriodo("Todas");

        actualizarListas();

        stage.setScene(scene);
        stage.setTitle("PENDO - Task Organizer");

        // Agregar icono a la aplicación
        try {
            stage.getIcons().add(new javafx.scene.image.Image(getClass().getResource("/images/PendoSplashLogo.png").toExternalForm()));
        } catch (Exception e) {
            System.out.println("No se pudo cargar el icono de la aplicación");
        }

        stage.show();
    }

    // MÉTODOS DE LISTAS
    public void crearLista() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Crear nueva lista");
        dialog.setHeaderText("Ingrese el nombre de la nueva lista:");
        dialog.setContentText("Nombre:");

        dialog.showAndWait().ifPresent(nombreLista -> {
            if (!nombreLista.trim().isEmpty() && !listas.contains(nombreLista.trim())) {
                appBackend.createTodoList(nombreLista.trim());
                guardar();
                listas.setAll(appBackend.getAllListNames());
                organizer.listsPanel.listasView.getSelectionModel().select(nombreLista.trim());
            }
        });
    }

    public void eliminarLista() {
        String listaSeleccionada = organizer.listsPanel.listasView.getSelectionModel().getSelectedItem();
        if (listaSeleccionada != null && !listaSeleccionada.equals("Default")) {
            appBackend.removeTodoList(listaSeleccionada);
            guardar();
            listas.setAll(appBackend.getAllListNames());
            listaActual = listas.isEmpty() ? "Default" : listas.get(0);
            organizer.listsPanel.listasView.getSelectionModel().select(listaActual);
        }
    }

    // MÉTODOS DE TAREAS
    public void agregarTarea(Task tarea) {
        ToDos lista = (ToDos) appBackend.getTodoList(listaActual);
        lista.restoreTask(tarea);
        guardar();
        actualizarListas();
    }

    public void cambiarEstadoTarea(Task tarea, boolean aCompletada) {
        ToDos lista = (ToDos) appBackend.getTodoList(listaActual);
        if (aCompletada) {
            lista.markTaskAsCompleted(tarea.getId());
        } else {
            lista.returnTaskToPending(tarea.getId());
        }
        guardar();
        actualizarListas();
    }

    public void editarTarea(Task tarea) {
        Stage stage = new Stage();
        TaskFormView formView = new TaskFormView(this, tarea);
        Scene scene = new Scene(formView, 720, 520);
        stage.setScene(scene);
        stage.setTitle("Editar Tarea");
        stage.show();
    }

    public void actualizarTarea(Task tarea) {
        ToDos lista = (ToDos) appBackend.getTodoList(listaActual);
        if (lista != null) {
            lista.editTask(tarea.getId(), tarea.getDescription(), tarea.getDueDate(), tarea.getPriority());
            guardar();
            actualizarListas();
        }
    }

    public void eliminarTarea(Task tarea) {
        ToDos lista = (ToDos) appBackend.getTodoList(listaActual);
        lista.removeTask(tarea.getId());
        guardar();
        actualizarListas();
    }

    public void moverTarea(Task tarea, String listaDestino) {
        if (!listaDestino.equals(listaActual)) {
            appBackend.moveTaskBetweenLists(listaActual, listaDestino, tarea.getId());
            guardar();
            actualizarListas();
        }
    }

    // FILTRADO Y HISTÓRICO
    private void actualizarListas() {
        ToDos lista = (ToDos) appBackend.getTodoList(listaActual);
        if (lista != null) {
            buscarTareas();
            filtrarCompletadasPorPeriodo("Todas");
        }
    }

    private void buscarTareas() {
        ToDos lista = (ToDos) appBackend.getTodoList(listaActual);
        String searchTxt = organizer.tasksPanel.searchField.getText();
        String prioridadStr = organizer.tasksPanel.comboPrioridad.getValue();
        String rango = organizer.tasksPanel.comboRangoPendientes.getValue();

        // Si es "Todas" o null, usar null para mostrar todas las prioridades
        Task.Priority prioridad = (prioridadStr == null || prioridadStr.equals("Todas"))
                ? null
                : Task.Priority.fromString(prioridadStr);

        // Si el texto de búsqueda está vacío, usar null
        String searchText = (searchTxt == null || searchTxt.trim().isEmpty()) ? null : searchTxt.trim();

        if (lista != null && rango != null) {
            switch (rango) {
                case "Todas":
                    tareasPendientes.setAll(lista.getFilteredTasks(prioridad, searchText, Task.TaskStatus.PENDING));
                    break;
                case "Semana":
                    tareasPendientes.setAll(lista.getPendingTasksInLastWeek(prioridad, searchText));
                    break;
                case "15 días":
                    tareasPendientes.setAll(lista.getPendingTasksInLast15Days(prioridad, searchText));
                    break;
                case "Mes":
                    tareasPendientes.setAll(lista.getPendingTasksInLastMonth(prioridad, searchText));
                    break;
                default:
                    tareasPendientes.setAll(lista.getFilteredTasks(prioridad, searchText, Task.TaskStatus.PENDING));
            }
        } else if (lista != null) {
            tareasPendientes.setAll(lista.getFilteredTasks(prioridad, searchText, Task.TaskStatus.PENDING));
        }
    }

    public void filtrarCompletadasPorPeriodo(String periodo) {
        ToDos lista = (ToDos) appBackend.getTodoList(listaActual);
        if (lista != null) {
            switch (periodo) {
                case "Semana":
                    tareasCompletadas.setAll(lista.getCompletedInLastWeek());
                    break;
                case "15 días":
                case "15 Días":
                    tareasCompletadas.setAll(lista.getCompletedInLast15Days());
                    break;
                case "Mes":
                    tareasCompletadas.setAll(lista.getCompletedInLastMonth());
                    break;
                case "Todas":
                default:
                    tareasCompletadas.setAll(lista.getCompletedTasksSorted());
            }
        }
    }

    private void limpiarCompletadas() {
        ToDos lista = (ToDos) appBackend.getTodoList(listaActual);
        for (Task t : tareasCompletadas) {
            lista.removeTask(t.getId());
        }
        guardar();
        actualizarListas();
    }

    public void exportarHistorial() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exportar historial");
        fileChooser.setInitialFileName("historial_" + listaActual + ".txt");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo de texto (*.txt)", "*.txt"));
        Stage stage = (Stage) organizer.getScene().getWindow();
        java.io.File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                for (Task t : tareasCompletadas) {
                    writer.write("Tarea: " + t.getDescription() + "\n");
                    writer.write("Vence: " + t.getDueDate() + "\n");
                    writer.write("Prioridad: " + t.getPriority().getDisplayName() + "\n");
                    writer.write("Completada: " + t.getCompletedAt() + "\n");
                    writer.write("------\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void abrirFormularioNuevaTarea() {
        Stage stage = new Stage();
        TaskFormView formView = new TaskFormView(this);
        Scene scene = new Scene(formView, 720, 520);
        scene.getStylesheets().add(getClass().getResource("/css/taskform.css").toExternalForm());
        stage.setScene(scene);
        stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        stage.setTitle("Nueva Tarea");
        stage.showAndWait();
    }

    private void guardar() {
        try {
            appBackend.saveAllToFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirVentanaPorFechas() {
        CompletedByDateController controller = new CompletedByDateController(appBackend);
        controller.show();
    }
}
