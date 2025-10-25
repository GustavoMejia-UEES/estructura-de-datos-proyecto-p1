package org.uees.model.emilio;

import java.util.ArrayList;
import java.util.List;

public class EmilioTaskStack {
    
    private List<EmilioTask> stack;
    
    public EmilioTaskStack() {
        this.stack = new ArrayList<>();
    }
    
    public void push(EmilioTask task) {
        if (task != null && task.getStatus() == EmilioTask.TaskStatus.COMPLETED) {
            stack.add(task);
        }
    }
    
    public EmilioTask pop() {
        if (isEmpty()) return null;
        return stack.remove(stack.size() - 1);
    }
    
    public EmilioTask peek() {
        if (isEmpty()) return null;
        return stack.get(stack.size() - 1);
    }
    
    public boolean isEmpty() {
        return stack.isEmpty();
    }
    
    public int size() {
        return stack.size();
    }
    
    public List<EmilioTask> getCompletedInLastWeek() {
        return getCompletedInLastDays(7);
    }
    
    public List<EmilioTask> getCompletedInLast15Days() {
        return getCompletedInLastDays(15);
    }
    
    public List<EmilioTask> getCompletedInLastMonth() {
        return getCompletedInLastDays(30);
    }
    
    public List<EmilioTask> getCompletedInLastDays(int days) {
        List<EmilioTask> result = new ArrayList<>();
        for (EmilioTask task : stack) {
            if (task.isCompletedInLastDays(days)) {
                result.add(task);
            }
        }
        return result;
    }
    
    public List<EmilioTask> getAllCompleted() {
        return new ArrayList<>(stack);
    }
    
    public boolean contains(EmilioTask task) {
        return stack.contains(task);
    }
    
    public boolean remove(EmilioTask task) {
        return stack.remove(task);
    }
    
    public EmilioTask removeAndReturnToPending(int taskId) {
        for (int i = stack.size() - 1; i >= 0; i--) {
            EmilioTask task = stack.get(i);
            if (task.getId() == taskId) {
                stack.remove(i);
                task.markAsPending();
                return task;
            }
        }
        return null;
    }
    
    public void clear() {
        stack.clear();
    }
    
    public List<EmilioTask> searchCompleted(String searchText) {
        List<EmilioTask> result = new ArrayList<>();
        for (EmilioTask task : stack) {
            if (task.matchesSearchText(searchText)) {
                result.add(task);
            }
        }
        return result;
    }
}
