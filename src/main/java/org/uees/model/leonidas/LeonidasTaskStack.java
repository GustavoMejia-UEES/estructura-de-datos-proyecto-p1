package org.uees.model.leonidas;

import org.uees.model.api.Task;
import org.uees.model.api.TaskStack;

import java.util.List;
import java.util.Stack;

public class LeonidasTaskStack implements TaskStack {
    private Stack<Task> completedTasks;
    private Stack<Task> completedTasksHistory;

    public LeonidasTaskStack() {
        completedTasks = new Stack<>();
        completedTasksHistory = new Stack<>();
    }

    public void pushCompletedTask(Task task){
        completedTasks.push(task);
        completedTasksHistory.push(task);
    }

    public Task popLastCompletedTask(){
        if (!completedTasks.isEmpty()) {
            return completedTasks.pop();}
        return null;}

    public Task peekLastCompletedTask(){
        if (!completedTasks.isEmpty()) {
        return completedTasks.peek();}
        return null;}

    public List<Task> getCompletedTasks(){return completedTasks;}
    public List<Task> getCompletedTasksHistory(){return completedTasksHistory;}

    public boolean isEmpty(){return completedTasks.isEmpty();}

    public int size(){return completedTasks.size();}

    public Task removeTaskById(int taskId) {
        Stack<Task> Stack2 = new Stack<>();
        Task removedTask = null;
        while (!completedTasks.isEmpty()) {
            Task t = completedTasks.pop();
            if (t.getId() == taskId) {
                removedTask = t;
                break;
            } else {Stack2.push(t);}}
        while (!Stack2.isEmpty()) {
            completedTasks.push(Stack2.pop());}
        return removedTask;}

}

