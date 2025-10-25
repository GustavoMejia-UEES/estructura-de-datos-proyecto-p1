package org.uees.model.emilio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EmilioToDos {
    
    private String listName;
    private EmilioTaskQueue pendingTasks;
    private EmilioTaskStack completedTasks;
    private int nextTaskId;
    
    public EmilioToDos(String listName) {
        this.listName = listName;
        this.pendingTasks = new EmilioTaskQueue();
        this.completedTasks = new EmilioTaskStack();
        this.nextTaskId = 1;
    }
    
    public String getListName() {
        return listName;
    }
    
    public void setListName(String listName) {
        this.listName = listName;
    }
    
    public EmilioTask createTask(String description, LocalDateTime dueDate, EmilioTask.Priority priority) {
        EmilioTask task = new EmilioTask(nextTaskId++, description, dueDate, priority, listName);
        pendingTasks.enqueue(task);
        return task;
    }
    
    public void restoreTask(EmilioTask task) {
        if (task.getStatus() == EmilioTask.TaskStatus.PENDING) {
            pendingTasks.enqueue(task);
        } else {
            completedTasks.push(task);
        }
        if (task.getId() >= nextTaskId) {
            nextTaskId = task.getId() + 1;
        }
    }
    
    public boolean markTaskAsCompleted(int taskId) {
        List<EmilioTask> allPending = pendingTasks.getAllSorted();
        for (EmilioTask task : allPending) {
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
        EmilioTask task = completedTasks.removeAndReturnToPending(taskId);
        if (task != null) {
            pendingTasks.enqueue(task);
            return true;
        }
        return false;
    }
    
    public boolean removeTask(int taskId) {
        List<EmilioTask> allPending = pendingTasks.getAllSorted();
        for (EmilioTask task : allPending) {
            if (task.getId() == taskId) {
                return pendingTasks.remove(task);
            }
        }
        return completedTasks.remove(getCompletedTaskById(taskId));
    }
    
    public boolean editTask(int taskId, String newDescription, LocalDateTime newDueDate, EmilioTask.Priority newPriority) {
        List<EmilioTask> allPending = pendingTasks.getAllSorted();
        for (EmilioTask task : allPending) {
            if (task.getId() == taskId) {
                if (newDescription != null) task.setDescription(newDescription);
                if (newDueDate != null) task.setDueDate(newDueDate);
                if (newPriority != null) task.setPriority(newPriority);
                return true;
            }
        }
        return false;
    }
    
    public List<EmilioTask> getPendingTasksSorted() {
        return pendingTasks.getPendingTasksSorted();
    }
    
    public List<EmilioTask> getFilteredTasks(EmilioTask.Priority priority, String searchText, EmilioTask.TaskStatus status) {
        return pendingTasks.getFilteredSorted(priority, searchText, status);
    }
    
    public List<EmilioTask> getCompletedInLastWeek() {
        return completedTasks.getCompletedInLastWeek();
    }
    
    public List<EmilioTask> getCompletedInLast15Days() {
        return completedTasks.getCompletedInLast15Days();
    }
    
    public List<EmilioTask> getCompletedInLastMonth() {
        return completedTasks.getCompletedInLastMonth();
    }
    
    public List<EmilioTask> getAllTasks() {
        List<EmilioTask> allTasks = new ArrayList<>();
        allTasks.addAll(pendingTasks.getAllSorted());
        allTasks.addAll(completedTasks.getAllCompleted());
        return allTasks;
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
    
    private EmilioTask getCompletedTaskById(int taskId) {
        for (EmilioTask task : completedTasks.getAllCompleted()) {
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
}
