package org.uees.model.TasksModels;

import java.io.*;
import java.util.*;

public class App {

    private Map<String, ToDos> todoLists;
    private String dataDirectory;

    public App() {
        this.todoLists = new HashMap<>();
        this.dataDirectory = "data/";
        createDataDirectory();
    }

    public App(String dataDirectory) {
        this.todoLists = new HashMap<>();
        this.dataDirectory = dataDirectory;
        createDataDirectory();
    }

    public ToDos createTodoList(String listName) {
        if (todoLists.containsKey(listName)) {
            return null;
        }
        ToDos newList = new ToDos(listName);
        todoLists.put(listName, newList);
        return newList;
    }

    public boolean removeTodoList(String listName) {
        if (todoLists.remove(listName) != null) {
            deleteListFile(listName);
            return true;
        }
        return false;
    }

    public ToDos getTodoList(String listName) {
        return todoLists.get(listName);
    }

    public Set<String> getAllListNames() {
        return new HashSet<>(todoLists.keySet());
    }

    public List<ToDos> getAllTodoLists() {
        return new ArrayList<>(todoLists.values());
    }

    public boolean moveTaskBetweenLists(String fromListName, String toListName, int taskId) {
        ToDos fromList = todoLists.get(fromListName);
        ToDos toList = todoLists.get(toListName);

        if (fromList == null || toList == null) {
            return false;
        }

        List<Task> allTasks = fromList.getAllTasks();
        for (Task task : allTasks) {
            if (task.getId() == taskId) {
                if (fromList.removeTask(taskId)) {
                    Task newTask = toList.createTask(
                            task.getDescription(),
                            task.getDueDate(),
                            task.getPriority()
                    );
                    if (task.getStatus() == Task.TaskStatus.COMPLETED) {
                        toList.markTaskAsCompleted(newTask.getId());
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public List<Task> searchTasksAcrossAllLists(String searchText) {
        List<Task> results = new ArrayList<>();
        for (ToDos todoList : todoLists.values()) {
            results.addAll(todoList.getFilteredTasks(null, searchText, null));
        }
        return results;
    }

    public List<Task> getTasksByPriorityAcrossAllLists(Task.Priority priority) {
        List<Task> results = new ArrayList<>();
        for (ToDos todoList : todoLists.values()) {
            results.addAll(todoList.getFilteredTasks(priority, null, null));
        }
        return results;
    }

    public List<Task> getTasksByStatusAcrossAllLists(Task.TaskStatus status) {
        List<Task> results = new ArrayList<>();
        for (ToDos todoList : todoLists.values()) {
            results.addAll(todoList.getFilteredTasks(null, null, status));
        }
        return results;
    }

    public void saveAllToFiles() throws IOException {
        for (ToDos todoList : todoLists.values()) {
            saveListToFile(todoList);
        }
    }

    public void loadAllFromFiles() throws IOException {
        File dataDir = new File(dataDirectory);
        if (!dataDir.exists()) {
            return;
        }

        File[] files = dataDir.listFiles((d, name) -> name.endsWith(".txt"));
        if (files == null) {
            return;
        }

        for (File file : files) {
            String listName = file.getName().replace(".txt", "");
            ToDos todoList = loadListFromFile(listName);
            if (todoList != null) {
                todoLists.put(listName, todoList);
            }
        }
    }

    private void saveListToFile(ToDos todoList) throws IOException {
        String fileName = dataDirectory + todoList.getListName() + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("LIST_NAME:" + todoList.getListName());
            writer.println("PENDING_TASKS:");

            for (Task task : todoList.getPendingTasksSorted()) {
                writer.println("PENDING:" + task.toFileString());
            }

            writer.println("COMPLETED_TASKS:");
            for (Task task : todoList.getCompletedInLastMonth()) {
                writer.println("COMPLETED:" + task.toFileString());
            }
        }
    }

    private ToDos loadListFromFile(String listName) throws IOException {
        String fileName = dataDirectory + listName + ".txt";
        File file = new File(fileName);
        if (!file.exists()) {
            return null;
        }

        ToDos todoList = new ToDos(listName);

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("PENDING:")) {
                    String taskData = line.substring(8);
                    Task task = Task.fromFileString(taskData);
                    if (task != null) {
                        todoList.restoreTask(task);
                    }
                } else if (line.startsWith("COMPLETED:")) {
                    String taskData = line.substring(10);
                    Task task = Task.fromFileString(taskData);
                    if (task != null) {
                        todoList.restoreTask(task);
                    }
                }
            }
        }

        return todoList;
    }

    private void deleteListFile(String listName) {
        String fileName = dataDirectory + listName + ".txt";
        File file = new File(fileName);
        file.delete();
    }

    private void createDataDirectory() {
        File dir = new File(dataDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public int getTotalTasksCount() {
        return todoLists.values().stream().mapToInt(ToDos::getTotalCount).sum();
    }

    public int getTotalPendingTasksCount() {
        return todoLists.values().stream().mapToInt(ToDos::getPendingCount).sum();
    }

    public int getTotalCompletedTasksCount() {
        return todoLists.values().stream().mapToInt(ToDos::getCompletedCount).sum();
    }

    public void clearAllData() {
        todoLists.clear();
        File dataDir = new File(dataDirectory);
        if (dataDir.exists()) {
            File[] files = dataDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
    }
}
