package org.uees.model.api;

import java.util.List;

public interface TaskQueue {

    void enqueueTask(Task task);

    Task dequeueHighestPriorityTask();

    Task peekHighestPriorityTask();

    List<Task> getTasksOrderedByPriority();

    boolean isEmpty();

    int size();
}
