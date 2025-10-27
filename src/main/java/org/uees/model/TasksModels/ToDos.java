package org.uees.model.TasksModels;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ToDos {

    private String listName;
    private TaskQueue pendingTasks;
    private TaskStack completedTasks;
    private int nextTaskId;

    public ToDos(String listName) {
        this.listName = listName;
        this.pendingTasks = new TaskQueue();
        this.completedTasks = new TaskStack();
        this.nextTaskId = 1;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public Task createTask(String description, LocalDateTime dueDate, Task.Priority priority) {
        Task task = new Task(nextTaskId++, description, dueDate, priority, listName);
        pendingTasks.enqueue(task);
        return task;
    }

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

    public boolean returnTaskToPending(int taskId) {
        Task task = completedTasks.removeAndReturnToPending(taskId);
        if (task != null) {
            pendingTasks.enqueue(task);
            return true;
        }
        return false;
    }

    public boolean removeTask(int taskId) {
        List<Task> allPending = pendingTasks.getAllSorted();
        for (Task task : allPending) {
            if (task.getId() == taskId) {
                return pendingTasks.remove(task);
            }
        }
        return completedTasks.remove(getCompletedTaskById(taskId));
    }

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

    public List<Task> getPendingTasksSorted() {
        return pendingTasks.getPendingTasksSorted();
    }

    public List<Task> getFilteredTasks(Task.Priority priority, String searchText, Task.TaskStatus status) {
        return pendingTasks.getFilteredSorted(priority, searchText, status);
    }

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

        List<Task> filtered = pendingTasks.getFilteredSorted(priority, searchText, Task.TaskStatus.PENDING);

        return filtered.stream()
                .filter(task -> task.getDueDate() != null
                && !task.getDueDate().isBefore(minDate)
                && !task.getDueDate().isAfter(now))
                .collect(Collectors.toList());
    }

    public List<Task> getCompletedInLastWeek() {
        return completedTasks.getCompletedInLastWeek();
    }

    public List<Task> getCompletedInLast15Days() {
        return completedTasks.getCompletedInLast15Days();
    }

    public List<Task> getCompletedInLastMonth() {
        return completedTasks.getCompletedInLastMonth();
    }

    public List<Task> getAllTasks() {
        List<Task> allTasks = new ArrayList<>();
        allTasks.addAll(pendingTasks.getAllSorted());
        allTasks.addAll(completedTasks.getAllCompleted());
        return allTasks;
    }

    public List<Task> getCompletedTasksSorted() {
        return completedTasks.getAllCompleted();
    }

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

    public void clear() {
        pendingTasks.clear();
        completedTasks.clear();
        nextTaskId = 1;
    }

    public List<String> getAvailableCreationDates() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<String> dates = new ArrayList<>();

        List<Task> allTasks = getAllTasks();

        for (Task task : allTasks) {
            if (task.getCreatedAt() != null) {
                String dateKey = task.getCreatedAt().format(dateFormatter);
                if (!dates.contains(dateKey)) {
                    dates.add(dateKey);
                }
            }
        }

        dates.sort((d1, d2) -> d2.compareTo(d1));
        return dates;
    }

    public List<Task> getTasksCreatedOnDate(String dateString) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<Task> result = new ArrayList<>();

        List<Task> completedTasksList = completedTasks.getAllCompleted();

        for (Task task : completedTasksList) {
            if (task.getCreatedAt() != null && task.getStatus() == Task.TaskStatus.COMPLETED) {
                String taskDateKey = task.getCreatedAt().format(dateFormatter);
                if (taskDateKey.equals(dateString)) {
                    result.add(task);
                }
            }
        }

        return result;
    }

    public int getTasksCountOnDate(String dateString) {
        return getTasksCreatedOnDate(dateString).size();
    }

    public Map<String, List<Task>> getAllTasksGroupedByCreationDate() {
        Map<String, List<Task>> groupedTasks = new LinkedHashMap<>();
        List<String> availableDates = getAvailableCreationDates();

        for (String date : availableDates) {
            List<Task> completedTasksOnDate = getTasksCreatedOnDate(date);
            if (!completedTasksOnDate.isEmpty()) {
                groupedTasks.put(date, completedTasksOnDate);
            }
        }

        return groupedTasks;
    }

    public void exportCompletedTasksByDate(java.io.File file, java.util.List<String> availableDates, java.util.function.Function<String, java.util.List<Task>> getTasksForDateFunc) throws java.io.IOException {
        try (java.io.FileWriter writer = new java.io.FileWriter(file)) {
            writer.write("=".repeat(60) + "\n");
            writer.write("       TAREAS COMPLETADAS POR FECHA DE CREACION\n");
            writer.write("=".repeat(60) + "\n\n");

            for (String date : availableDates) {
                writer.write("FECHA: " + date + "\n");
                writer.write("-".repeat(40) + "\n");

                java.util.List<Task> completedTasksOnDate = getTasksForDateFunc.apply(date);

                if (!completedTasksOnDate.isEmpty()) {
                    for (Task task : completedTasksOnDate) {
                        writer.write("- " + task.getDescription() + "\n");
                    }
                } else {
                    writer.write("  (Sin tareas completadas en esta fecha)\n");
                }

                writer.write("\n");
            }

            writer.write("=".repeat(60) + "\n");
            writer.write("Exportado el: " + LocalDateTime.now().toString() + "\n");
        }
    }
}
