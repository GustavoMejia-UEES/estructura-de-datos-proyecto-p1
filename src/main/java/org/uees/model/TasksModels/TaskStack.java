package org.uees.model.TasksModels;

import java.util.ArrayList;
import java.util.List;

public class TaskStack {

    private List<Task> stack;

    public TaskStack() {
        this.stack = new ArrayList<>();
    }

    public void push(Task task) {
        if (task != null && task.getStatus() == Task.TaskStatus.COMPLETED) {
            stack.add(task);
        }
    }

    public Task pop() {
        if (isEmpty()) {
            return null;
        }
        return stack.remove(stack.size() - 1);
    }

    public Task peek() {
        if (isEmpty()) {
            return null;
        }
        return stack.get(stack.size() - 1);
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }

    public List<Task> getCompletedInLastWeek() {
        return getCompletedInLastDays(7);
    }

    public List<Task> getCompletedInLast15Days() {
        return getCompletedInLastDays(15);
    }

    public List<Task> getCompletedInLastMonth() {
        return getCompletedInLastDays(30);
    }

    public List<Task> getCompletedInLastDays(int days) {
        List<Task> result = new ArrayList<>();
        for (Task task : stack) {
            if (task.isCompletedInLastDays(days)) {
                result.add(task);
            }
        }
        return result;
    }

    public List<Task> getAllCompleted() {
        return new ArrayList<>(stack);
    }

    public boolean contains(Task task) {
        return stack.contains(task);
    }

    public boolean remove(Task task) {
        return stack.remove(task);
    }

    public Task removeAndReturnToPending(int taskId) {
        for (int i = stack.size() - 1; i >= 0; i--) {
            Task task = stack.get(i);
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

    public List<Task> searchCompleted(String searchText) {
        List<Task> result = new ArrayList<>();
        for (Task task : stack) {
            if (task.matchesSearchText(searchText)) {
                result.add(task);
            }
        }
        return result;
    }
}
