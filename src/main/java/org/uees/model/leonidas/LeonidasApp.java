package org.uees.model.leonidas;

import org.uees.model.api.App;
import org.uees.model.api.Task;
import org.uees.model.api.ToDos;

import java.io.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.uees.model.api.Task.Priority.*;

public class LeonidasApp implements App{
    private LinkedList<LeonidasToDos> todosList;
    private LinkedList<LeonidasTask> pendingTasks;
    private LinkedList<LeonidasTask> completedTasks;

    public LeonidasApp() {
        this.todosList = new LinkedList<>();
        this.pendingTasks = new LinkedList<>();
        this.completedTasks = new LinkedList<>();
    }

    public void addToDos(ToDos todos) {
        todosList.add((LeonidasToDos) todos);
        for (Task task : todos.getTasks()) {
            if (task.isCompleted()) {
                completedTasks.add((LeonidasTask) task);
            } else {
                pendingTasks.add((LeonidasTask) task);
            }}}

    public void removeToDos(String listName) {
        for (ToDos t : todosList) {
            if (t.getListName() == listName) {
                todosList.remove(t);
                break;
            }
        }
    }

    public void editToDos(String oldListName, ToDos newListData) {
        for (int i = 0; i < todosList.size(); i++) {
            ToDos t = todosList.get(i);
            if (t.getListName() == (oldListName)) {
                todosList.set(i, (LeonidasToDos) newListData);
                break;
            }
        }
    }

    public List<ToDos> getAllToDos() {
        return new LinkedList<>(todosList);
    }

    public void moveTaskBetweenLists(String fromList, String toList, int taskId) {
        ToDos origen = null, destino = null;
        for (ToDos t : todosList) {
            if (t.getListName() == fromList) origen = t;
            if (t.getListName() == toList) destino = t;
        }
        if (origen != null && destino != null) {
            for (Task task : origen.getTasks()) {
                if (task.getId() == taskId) {
                    origen.removeTask(taskId);
                    destino.addTask(task);
                    break;
                }
            }
        }
    }

    public void addTaskToList(String listName, Task task) {
        for (ToDos t : todosList) {
            if (t.getListName() == (listName)) {
                t.addTask(task);
                break;
            }
        }
    }

    public void removeTaskFromList(String listName, int taskId) {
        for (ToDos t : todosList) {
            if (t.getListName().equalsIgnoreCase(listName)) {
                t.removeTask(taskId);
                break;
            }
        }
    }

    public void editTaskInList(String listName, int taskId, Task newData) {
        for (ToDos t : todosList) {
            if (t.getListName().equalsIgnoreCase(listName)) {
                t.editTask(taskId, newData);
                break;
            }
        }
    }

    public List<Task> getTasksByPriority(Task.Priority priority) {
        LinkedList<Task> listaPrioritaria = new LinkedList<>();
        for (ToDos t : todosList) {
            listaPrioritaria.addAll(t.getTasksByPriority(priority));
        }
        return listaPrioritaria;
    }
    public List<Task> getAllTasksOrderedByPriority() {
        LinkedList<Task> orderedList = new LinkedList<>();
        orderedList.addAll(getTasksByPriority(Task.Priority.HIGH));
        orderedList.addAll(getTasksByPriority(Task.Priority.MEDIUM));
        orderedList.addAll(getTasksByPriority(Task.Priority.LOW));
        return orderedList;
    }

    public List<Task> getTasksByStatus(boolean completed) {
        LeonidasTaskComparator comparator = new LeonidasTaskComparator();
        List<Task> allTasks = new LinkedList<>();
        for (ToDos t : todosList) {
            allTasks.addAll(t.getTasks());
        }
        return comparator.findTasksByStatus(allTasks, completed);}

    public List<Task> searchTasksByDescription(String descriptionPart) { // Instancia del comparador de tareas (el que contiene la lógica de búsqueda)
        LeonidasTaskComparator comparator = new LeonidasTaskComparator();
        List<Task> allTasks = new LinkedList<>();
        for (ToDos t : todosList) {
            allTasks.addAll(t.getTasks());}
        return comparator.findTasksByDescription(allTasks, descriptionPart);}

    public List<Task> getTasksByDueDateRange(LocalDate from, LocalDate to) {
        LinkedList<Task> result = new LinkedList<>();
        for (ToDos t : todosList) {
            for (Task task : t.getTasks()) {
                if (!task.getDueDate().isBefore(from) && !task.getDueDate().isAfter(to)) {
                    result.add(task);
                }
            }
        }
        return result;
    }
    public List<Task> getCompletedTasksSince(LocalDate fromDate) {
        LinkedList<Task> result = new LinkedList<>();
        LocalDate today = LocalDate.now();
        for (ToDos t : todosList) {
            for (Task task : t.getTasks()) {
                if (task.isCompleted() && task instanceof LeonidasTask) {
                    LocalDate completion = ((LeonidasTask) task).getCompletionDate();
                    if (completion != null && !completion.isBefore(fromDate) && !completion.isAfter(today)) {
                        result.add(task);
                    }}}}return result;}

    public List<Task> getCompletedTasksHistory(String period) {
        LinkedList<Task> result = new LinkedList<>();
        for (ToDos t : todosList) {
            result.addAll(t.getCompletedTasks());
        }
        return result;
    }

    public void markAllTasksCompleted(String listName) {
        for (ToDos t : todosList) {
        if (t.getListName().equalsIgnoreCase(listName)) {
            t.markAllCompleted();
            break;}}}

    public void removeCompletedTasks(String listName) {
        for (ToDos t : todosList) {
            if (t.getListName() == (listName)) {
                t.removeCompletedTasks();
                break;
            }
        }
    }
    public void revertLastCompletedTask() {
        Task reciente = null;
        LeonidasToDos toDos = null;
        LocalDate latestDate = LocalDate.MIN;
        for (LeonidasToDos t : todosList) {
            List<Task> completed = t.getCompletedTasks();
            for (Task task : completed) {
                if (task instanceof LeonidasTask) {
                    LocalDate completion = ((LeonidasTask) task).getCompletionDate();
                    if (completion != null && completion.isAfter(latestDate)) {
                        latestDate = completion;
                        reciente = task;
                        toDos = t;}}}}
        if (reciente != null && toDos != null) {
            toDos.returnCompletedTask(reciente.getId());}}

    public void loadData() {
        try {
            File dataDir = new File("data/leonidas/");
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }

            File file = new File(dataDir, "tasks.txt");
            if (!file.exists()) {
                System.out.println("No hay datos guardados previamente.");
                return;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("PENDING:")) {
                        String taskData = line.substring(8).trim();
                        LeonidasTask task = LeonidasTask.fromFileString(taskData);
                        if (task != null) pendingTasks.add(task);
                    } else if (line.startsWith("COMPLETED:")) {
                        String taskData = line.substring(10).trim();
                        LeonidasTask task = LeonidasTask.fromFileString(taskData);
                        if (task != null) completedTasks.add(task);
                    }
                }
            }

            System.out.println("Datos cargados correctamente desde: " + file.getPath());

        } catch (Exception e) {
            System.out.println("Error al cargar datos: " + e.getMessage());
        }
    }


    public void saveData() {
        try {
            File dataDir = new File("data/leonidas/");
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }
            File file = new File(dataDir, "tasks.txt");
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println("PENDING_TASKS:");
                for (LeonidasTask task : pendingTasks) {
                    writer.println("PENDING:" + task.toFileString());
                }
                writer.println("COMPLETED_TASKS:");
                for (LeonidasTask task : completedTasks) {
                    writer.println("COMPLETED:" + task.toFileString());
                }
            }
            System.out.println("Datos guardados exitosamente en: " + file.getPath());
        } catch (Exception e) {
            System.out.println("Error al guardar datos: " + e.getMessage());
        }}
}