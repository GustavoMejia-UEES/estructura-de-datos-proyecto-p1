package org.uees.model.leonidas;

import org.uees.model.api.Task;
import org.uees.model.api.TaskQueue;

import java.util.*;

public class LeonidasTaskQueue implements TaskQueue {
    private PriorityQueue<Task> taskQueue;

    public LeonidasTaskQueue() {
        taskQueue = new PriorityQueue<>(Comparator.comparing(Task::getPriority));
    }

    public void enqueueTask(Task task){taskQueue.add(task);}
    public Task dequeueHighestPriorityTask(){return taskQueue.poll();}
    public Task peekHighestPriorityTask(){return taskQueue.peek();}

    public List<Task> getTasksOrderedByPriority(){
        return new LeonidasTaskComparator().sortTasksByPriority(new ArrayList<>(taskQueue));
        }

    public List<Task> getTasksByPriority(Task.Priority priority) {
        LinkedList<Task> result = new LinkedList<>();
        for (Task t : taskQueue) {
            if (t.getPriority() == priority) {
                result.add(t);
            }}return result;}

    public void removeTask(int taskId) {
        Iterator<Task> it = taskQueue.iterator();
        while (it.hasNext()) {
            Task t = it.next();
            if (t.getId() == taskId) {
                it.remove();
                break;}}}

    public boolean isEmpty(){return taskQueue.isEmpty();}
    public int size(){return taskQueue.size();}
}
