package org.uees.model.TasksModels;

import java.util.Comparator;

public class TaskComparator implements Comparator<Task> {

    public enum SortType {
        PRIORITY_DESC,
        PRIORITY_ASC,
        DUE_DATE_ASC,
        DUE_DATE_DESC,
        CREATED_DATE_ASC,
        CREATED_DATE_DESC,
        DESCRIPTION_ASC,
        DESCRIPTION_DESC
    }

    private final SortType sortType;

    public TaskComparator(SortType sortType) {
        this.sortType = sortType;
    }

    @Override
    public int compare(Task t1, Task t2) {
        switch (sortType) {
            case PRIORITY_DESC:
                return Integer.compare(t2.getPriority().getValue(), t1.getPriority().getValue());
            case PRIORITY_ASC:
                return Integer.compare(t1.getPriority().getValue(), t2.getPriority().getValue());
            case DUE_DATE_ASC:
                return t1.getDueDate().compareTo(t2.getDueDate());
            case DUE_DATE_DESC:
                return t2.getDueDate().compareTo(t1.getDueDate());
            case CREATED_DATE_ASC:
                return t1.getCreatedAt().compareTo(t2.getCreatedAt());
            case CREATED_DATE_DESC:
                return t2.getCreatedAt().compareTo(t1.getCreatedAt());
            case DESCRIPTION_ASC:
                return t1.getDescription().compareToIgnoreCase(t2.getDescription());
            case DESCRIPTION_DESC:
                return t2.getDescription().compareToIgnoreCase(t1.getDescription());
            default:
                return Integer.compare(t2.getPriority().getValue(), t1.getPriority().getValue());
        }
    }

    public static TaskComparator byPriorityDesc() {
        return new TaskComparator(SortType.PRIORITY_DESC);
    }

    public static TaskComparator byPriorityAsc() {
        return new TaskComparator(SortType.PRIORITY_ASC);
    }

    public static TaskComparator byDueDateAsc() {
        return new TaskComparator(SortType.DUE_DATE_ASC);
    }

    public static TaskComparator byDueDateDesc() {
        return new TaskComparator(SortType.DUE_DATE_DESC);
    }

    public static TaskComparator byCreatedDateAsc() {
        return new TaskComparator(SortType.CREATED_DATE_ASC);
    }

    public static TaskComparator byCreatedDateDesc() {
        return new TaskComparator(SortType.CREATED_DATE_DESC);
    }

    public static TaskComparator byDescriptionAsc() {
        return new TaskComparator(SortType.DESCRIPTION_ASC);
    }

    public static TaskComparator byDescriptionDesc() {
        return new TaskComparator(SortType.DESCRIPTION_DESC);
    }
}
