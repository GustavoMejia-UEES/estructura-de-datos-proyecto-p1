package org.uees.model;

import java.util.List;
import java.util.Set;
import java.time.LocalDateTime;
import org.uees.model.TasksModels.Task;

public interface TaskManager {

    void loadAll() throws Exception;

    void saveAll() throws Exception;

    Set<String> getAllListNames();

    List<Task> getAllTasks(String listName);

    Task createTask(String listName, String description, LocalDateTime dueDate, Task.Priority priority);

    boolean editTask(String listName, int taskId, String newDescription, LocalDateTime newDueDate, Task.Priority newPriority);

    boolean removeTask(String listName, int taskId);

    boolean markTaskAsCompleted(String listName, int taskId);

    boolean returnTaskToPending(String listName, int taskId);

    boolean removeTodoList(String listName);

    boolean moveTaskBetweenLists(String fromList, String toList, int taskId);

    List<Task> getFilteredTasks(String listName, Task.Priority priority, String searchText, Task.TaskStatus status);

    List<Task> getCompletedInLastWeek(String listName);

    List<Task> getCompletedInLast15Days(String listName);

    List<Task> getCompletedInLastMonth(String listName);
}
