package org.uees.model.api;

import java.time.LocalDate;
import java.util.List;

public interface App {

    void addToDos(ToDos todos);

    void removeToDos(String listName);

    void editToDos(String oldListName, ToDos newListData);

    List<ToDos> getAllToDos();

    void moveTaskBetweenLists(String fromList, String toList, int taskId);

    void addTaskToList(String listName, Task task);

    void removeTaskFromList(String listName, int taskId);

    void editTaskInList(String listName, int taskId, Task newData);

    List<Task> getTasksByPriority(Task.Priority priority);

    List<Task> getTasksByStatus(boolean completed);

    List<Task> searchTasksByDescription(String descriptionPart);

    List<Task> getTasksByDueDateRange(LocalDate from, LocalDate to);

    List<Task> getCompletedTasksHistory(String period);

    void revertLastCompletedTask();

    void loadData();

    void saveData();

    void markAllTasksCompleted(String listName);

    void removeCompletedTasks(String listName);
}
