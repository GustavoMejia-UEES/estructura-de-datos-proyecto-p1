package org.uees.model.api;

import java.time.LocalDate;

public interface Task {

    int getId();

    String getDescription();

    LocalDate getDueDate();

    Priority getPriority();

    boolean isCompleted();

    void markCompleted();

    void editDescription(String newDescription);

    void editDueDate(LocalDate newDate);

    void editPriority(Priority newPriority);

    String toString();

    enum Priority {
        HIGH, MEDIUM, LOW
    }
}
