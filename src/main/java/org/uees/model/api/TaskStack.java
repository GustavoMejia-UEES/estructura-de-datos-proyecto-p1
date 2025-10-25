package org.uees.model.api;

import java.util.List;

public interface TaskStack {

    void pushCompletedTask(Task task);

    Task popLastCompletedTask();

    Task peekLastCompletedTask();

    List<Task> getCompletedTasksHistory();

    boolean isEmpty();

    int size();
}
