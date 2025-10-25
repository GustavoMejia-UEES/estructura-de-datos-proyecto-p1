package org.uees.model.emilio;

import java.io.*;
import java.util.*;

public class EmilioApp {
    
    private Map<String, EmilioToDos> todoLists;
    private String dataDirectory;
    
    public EmilioApp() {
        this.todoLists = new HashMap<>();
        this.dataDirectory = "data/emilio/";
        createDataDirectory();
    }
    
    public EmilioApp(String dataDirectory) {
        this.todoLists = new HashMap<>();
        this.dataDirectory = dataDirectory;
        createDataDirectory();
    }
    
    public EmilioToDos createTodoList(String listName) {
        if (todoLists.containsKey(listName)) {
            return null;
        }
        EmilioToDos newList = new EmilioToDos(listName);
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
    
    public EmilioToDos getTodoList(String listName) {
        return todoLists.get(listName);
    }
    
    public Set<String> getAllListNames() {
        return new HashSet<>(todoLists.keySet());
    }
    
    public List<EmilioToDos> getAllTodoLists() {
        return new ArrayList<>(todoLists.values());
    }
    
    public boolean moveTaskBetweenLists(String fromListName, String toListName, int taskId) {
        EmilioToDos fromList = todoLists.get(fromListName);
        EmilioToDos toList = todoLists.get(toListName);
        
        if (fromList == null || toList == null) return false;
        
        List<EmilioTask> allTasks = fromList.getAllTasks();
        for (EmilioTask task : allTasks) {
            if (task.getId() == taskId) {
                if (fromList.removeTask(taskId)) {
                    EmilioTask newTask = toList.createTask(
                        task.getDescription(),
                        task.getDueDate(),
                        task.getPriority()
                    );
                    if (task.getStatus() == EmilioTask.TaskStatus.COMPLETED) {
                        toList.markTaskAsCompleted(newTask.getId());
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    public List<EmilioTask> searchTasksAcrossAllLists(String searchText) {
        List<EmilioTask> results = new ArrayList<>();
        for (EmilioToDos todoList : todoLists.values()) {
            results.addAll(todoList.getFilteredTasks(null, searchText, null));
        }
        return results;
    }
    
    public List<EmilioTask> getTasksByPriorityAcrossAllLists(EmilioTask.Priority priority) {
        List<EmilioTask> results = new ArrayList<>();
        for (EmilioToDos todoList : todoLists.values()) {
            results.addAll(todoList.getFilteredTasks(priority, null, null));
        }
        return results;
    }
    
    public List<EmilioTask> getTasksByStatusAcrossAllLists(EmilioTask.TaskStatus status) {
        List<EmilioTask> results = new ArrayList<>();
        for (EmilioToDos todoList : todoLists.values()) {
            results.addAll(todoList.getFilteredTasks(null, null, status));
        }
        return results;
    }
    
    public void saveAllToFiles() throws IOException {
        for (EmilioToDos todoList : todoLists.values()) {
            saveListToFile(todoList);
        }
    }
    
    public void loadAllFromFiles() throws IOException {
        File dataDir = new File(dataDirectory);
        if (!dataDir.exists()) return;
        
        File[] files = dataDir.listFiles((d, name) -> name.endsWith(".txt"));
        if (files == null) return;
        
        for (File file : files) {
            String listName = file.getName().replace(".txt", "");
            EmilioToDos todoList = loadListFromFile(listName);
            if (todoList != null) {
                todoLists.put(listName, todoList);
            }
        }
    }
    
    private void saveListToFile(EmilioToDos todoList) throws IOException {
        String fileName = dataDirectory + todoList.getListName() + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("LIST_NAME:" + todoList.getListName());
            writer.println("PENDING_TASKS:");
            
            for (EmilioTask task : todoList.getPendingTasksSorted()) {
                writer.println("PENDING:" + task.toFileString());
            }
            
            writer.println("COMPLETED_TASKS:");
            for (EmilioTask task : todoList.getCompletedInLastMonth()) {
                writer.println("COMPLETED:" + task.toFileString());
            }
        }
    }
    
    private EmilioToDos loadListFromFile(String listName) throws IOException {
        String fileName = dataDirectory + listName + ".txt";
        File file = new File(fileName);
        if (!file.exists()) return null;
        
        EmilioToDos todoList = new EmilioToDos(listName);
        
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("PENDING:")) {
                    String taskData = line.substring(8);
                    EmilioTask task = EmilioTask.fromFileString(taskData);
                    if (task != null) {
                        todoList.restoreTask(task);
                    }
                } else if (line.startsWith("COMPLETED:")) {
                    String taskData = line.substring(10);
                    EmilioTask task = EmilioTask.fromFileString(taskData);
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
        return todoLists.values().stream().mapToInt(EmilioToDos::getTotalCount).sum();
    }
    
    public int getTotalPendingTasksCount() {
        return todoLists.values().stream().mapToInt(EmilioToDos::getPendingCount).sum();
    }
    
    public int getTotalCompletedTasksCount() {
        return todoLists.values().stream().mapToInt(EmilioToDos::getCompletedCount).sum();
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
