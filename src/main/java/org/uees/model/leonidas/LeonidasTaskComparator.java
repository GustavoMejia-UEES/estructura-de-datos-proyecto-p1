package org.uees.model.leonidas;

import org.uees.model.api.Task;
import org.uees.model.api.TaskComparator;

import java.util.LinkedList;
import java.util.List;

public class LeonidasTaskComparator implements TaskComparator {

    public int compare(Task t1, Task t2) {
        return t1.getPriority().compareTo(t2.getPriority());}

    public List<Task> sortTasksByPriority(List<Task> tasks) {
        LinkedList<Task> priorityList = new LinkedList<>();
        for (Task t : tasks) {
            boolean added = false;
            for (int i = 0; i < priorityList.size(); i++) {
                if (compare(t, priorityList.get(i)) < 0) {
                    priorityList.add(i, t);
                    added = true;
                    break;}}
            if (!added) {
                priorityList.add(t);
            }}
        return priorityList;}

    public List<Task> findTasksByDescription(List<Task> tasks, String description) {
        LinkedList<Task> result = new LinkedList<>();
        for (Task t : tasks) {
            if (t.getDescription().contains(description)) {
                result.add(t);
            }
        }
        return result;
    }

    public List<Task> findTasksByStatus(List<Task> tasks, boolean state) {
        LinkedList<Task> result = new LinkedList<>();
        for (Task t : tasks) {
            if (t.isCompleted() == state) {
                result.add(t);
            }
        }
        return result;
    }}
