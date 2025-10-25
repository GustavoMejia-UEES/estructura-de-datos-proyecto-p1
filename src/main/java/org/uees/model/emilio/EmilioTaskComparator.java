package org.uees.model.emilio;

import java.util.Comparator;

public class EmilioTaskComparator implements Comparator<EmilioTask> {
    
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
    
    public EmilioTaskComparator(SortType sortType) {
        this.sortType = sortType;
    }
    
    @Override
    public int compare(EmilioTask t1, EmilioTask t2) {
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
    
    public static EmilioTaskComparator byPriorityDesc() {
        return new EmilioTaskComparator(SortType.PRIORITY_DESC);
    }
    
    public static EmilioTaskComparator byPriorityAsc() {
        return new EmilioTaskComparator(SortType.PRIORITY_ASC);
    }
    
    public static EmilioTaskComparator byDueDateAsc() {
        return new EmilioTaskComparator(SortType.DUE_DATE_ASC);
    }
    
    public static EmilioTaskComparator byDueDateDesc() {
        return new EmilioTaskComparator(SortType.DUE_DATE_DESC);
    }
    
    public static EmilioTaskComparator byCreatedDateAsc() {
        return new EmilioTaskComparator(SortType.CREATED_DATE_ASC);
    }
    
    public static EmilioTaskComparator byCreatedDateDesc() {
        return new EmilioTaskComparator(SortType.CREATED_DATE_DESC);
    }
    
    public static EmilioTaskComparator byDescriptionAsc() {
        return new EmilioTaskComparator(SortType.DESCRIPTION_ASC);
    }
    
    public static EmilioTaskComparator byDescriptionDesc() {
        return new EmilioTaskComparator(SortType.DESCRIPTION_DESC);
    }
}
