package org.uees.model.api;

import java.time.LocalDate;
import java.util.List;

public interface ToDos {

    String getListName();

    LocalDate getCreatedDate();

    List<Task> getTasks();

    void addTask(Task task);

    void removeTask(int taskId);

    void editTask(int taskId, Task newTask);

    void markAllCompleted();

    List<Task> getPendingTasks();

    List<Task> getCompletedTasks();

    List<Task> getTasksByPriority(Task.Priority priority);

    void removeCompletedTasks();

    void moveTaskTo(Task task, ToDos targetList);
}
