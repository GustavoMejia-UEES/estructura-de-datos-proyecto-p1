package org.uees.model.TasksModels;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ToDos {

    private String listName;
    private TaskQueue pendingTasks;
    private TaskStack completedTasks;
    private int nextTaskId;

    public ToDos(String listName) {
        this.listName = listName;
        this.pendingTasks = new TaskQueue(); // Usa comparator por prioridad descendente por defecto
        this.completedTasks = new TaskStack();
        this.nextTaskId = 1;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    // Crear tarea
    public Task createTask(String description, LocalDateTime dueDate, Task.Priority priority) {
        Task task = new Task(nextTaskId++, description, dueDate, priority, listName);
        pendingTasks.enqueue(task);
        return task;
    }

    // Restaurar tarea (al cargar desde archivo)
    public void restoreTask(Task task) {
        if (task.getStatus() == Task.TaskStatus.PENDING) {
            pendingTasks.enqueue(task);
        } else {
            completedTasks.push(task);
        }
        if (task.getId() >= nextTaskId) {
            nextTaskId = task.getId() + 1;
        }
    }

    // Marcar tarea como completada
    public boolean markTaskAsCompleted(int taskId) {
        List<Task> allPending = pendingTasks.getAllSorted();
        for (Task task : allPending) {
            if (task.getId() == taskId) {
                pendingTasks.remove(task);
                task.markAsCompleted();
                completedTasks.push(task);
                return true;
            }
        }
        return false;
    }

    // Regresar tarea completada a pendientes
    public boolean returnTaskToPending(int taskId) {
        Task task = completedTasks.removeAndReturnToPending(taskId);
        if (task != null) {
            pendingTasks.enqueue(task);
            return true;
        }
        return false;
    }

    // Remover tarea (de pendientes o completadas)
    public boolean removeTask(int taskId) {
        List<Task> allPending = pendingTasks.getAllSorted();
        for (Task task : allPending) {
            if (task.getId() == taskId) {
                return pendingTasks.remove(task);
            }
        }
        return completedTasks.remove(getCompletedTaskById(taskId));
    }

    // Editar tarea pendiente
    public boolean editTask(int taskId, String newDescription, LocalDateTime newDueDate, Task.Priority newPriority) {
        List<Task> allPending = pendingTasks.getAllSorted();
        for (Task task : allPending) {
            if (task.getId() == taskId) {
                if (newDescription != null) {
                    task.setDescription(newDescription);
                }
                if (newDueDate != null) {
                    task.setDueDate(newDueDate);
                }
                if (newPriority != null) {
                    task.setPriority(newPriority);
                }
                return true;
            }
        }
        return false;
    }

    // Listar pendientes ordenadas por prioridad (Comparator lo hace)
    public List<Task> getPendingTasksSorted() {
        return pendingTasks.getPendingTasksSorted();
    }

    // Filtrar pendientes por prioridad, texto y status (usa Comparator)
    public List<Task> getFilteredTasks(Task.Priority priority, String searchText, Task.TaskStatus status) {
        return pendingTasks.getFilteredSorted(priority, searchText, status);
    }

    // Filtrar pendientes por rango de fecha de entrega (además de prioridad/texto)
    public List<Task> getPendingTasksInLastWeek(Task.Priority priority, String searchText) {
        return filterPendingTasksByDateRange(priority, searchText, 7);
    }

    public List<Task> getPendingTasksInLast15Days(Task.Priority priority, String searchText) {
        return filterPendingTasksByDateRange(priority, searchText, 15);
    }

    public List<Task> getPendingTasksInLastMonth(Task.Priority priority, String searchText) {
        return filterPendingTasksByDateRange(priority, searchText, 31);
    }

    private List<Task> filterPendingTasksByDateRange(Task.Priority priority, String searchText, int days) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minDate = now.minusDays(days);

        // Filtra primero por prioridad, texto y status pendiente usando el Comparator
        List<Task> filtered = pendingTasks.getFilteredSorted(priority, searchText, Task.TaskStatus.PENDING);

        // Luego filtra por rango de fecha de entrega
        return filtered.stream()
                .filter(task -> task.getDueDate() != null
                && !task.getDueDate().isBefore(minDate)
                && !task.getDueDate().isAfter(now))
                .collect(Collectors.toList());
    }

    // Listar completadas por rango de fechas
    public List<Task> getCompletedInLastWeek() {
        return completedTasks.getCompletedInLastWeek();
    }

    public List<Task> getCompletedInLast15Days() {
        return completedTasks.getCompletedInLast15Days();
    }

    public List<Task> getCompletedInLastMonth() {
        return completedTasks.getCompletedInLastMonth();
    }

    // Listar todas las tareas (pendientes + completadas)
    public List<Task> getAllTasks() {
        List<Task> allTasks = new ArrayList<>();
        allTasks.addAll(pendingTasks.getAllSorted());
        allTasks.addAll(completedTasks.getAllCompleted());
        return allTasks;
    }

    public List<Task> getCompletedTasksSorted() {
        return completedTasks.getAllCompleted(); // O ordénalas si quieres
    }

    // Contadores
    public int getPendingCount() {
        return pendingTasks.size();
    }

    public int getCompletedCount() {
        return completedTasks.size();
    }

    public int getTotalCount() {
        return getPendingCount() + getCompletedCount();
    }

    // Buscar tarea completada por ID
    private Task getCompletedTaskById(int taskId) {
        for (Task task : completedTasks.getAllCompleted()) {
            if (task.getId() == taskId) {
                return task;
            }
        }
        return null;
    }

    // Limpiar lista
    public void clear() {
        pendingTasks.clear();
        completedTasks.clear();
        nextTaskId = 1;
    }
}
