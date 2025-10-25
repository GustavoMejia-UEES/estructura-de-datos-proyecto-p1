package org.uees.model.api;

import java.util.List;

public interface TaskComparator {

    int compare(Task t1, Task t2);

    List<Task> sortTasksByPriority(List<Task> tasks);

    List<Task> findTasksByDescription(List<Task> tasks, String descriptionPattern);

    List<Task> findTasksByStatus(List<Task> tasks, boolean completed);
}
