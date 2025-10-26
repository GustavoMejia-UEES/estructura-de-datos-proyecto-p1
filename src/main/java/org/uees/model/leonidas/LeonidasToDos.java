package org.uees.model.leonidas;

import org.uees.model.api.Task;
import org.uees.model.api.ToDos;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class LeonidasToDos implements ToDos{
    private int id;
    private LinkedList<Task> taskList;
    private LeonidasTaskQueue priorityTasks;
    private LeonidasTaskStack completedTasks;
    private String name;
    private LocalDate creationDate;
    private ListIterator<Task> li;

    public LeonidasToDos (int id, String name){
        this.priorityTasks = new LeonidasTaskQueue();
        this.completedTasks = new LeonidasTaskStack();
        this.id=id;
        this.taskList = new LinkedList<>();
        this.name = name;
        this.creationDate = LocalDate.now();
    }

    public String getListName(){return name;}
    public LocalDate getCreatedDate(){return creationDate;}
    public List<Task> getTasks(){return taskList;}
    public void addTask(Task task){
        taskList.addLast(task);
        priorityTasks.enqueueTask(task);
        if (task.isCompleted()){removeTask(task.getId());completedTasks.pushCompletedTask(task);}
    }

    public void removeTask(int taskId) {
        li=taskList.listIterator();
        while (li.hasNext()){
            Task current = li.next();
            if (current.getId() == taskId) {
                li.remove();
                priorityTasks.removeTask(taskId);
                break;}}}

    public void editTask(int taskId, Task newTask) {
        ListIterator<Task> iterator = taskList.listIterator();
        while (iterator.hasNext()) {
            Task current = iterator.next();
            if (current.getId() == taskId) {
                iterator.set(newTask);
                priorityTasks.removeTask(newTask.getId());
                priorityTasks.enqueueTask(newTask);
                break;}
        }
    }

    public void markTaskCompleted(int taskId){
        ListIterator<Task> it = taskList.listIterator();
        while(it.hasNext()){
            Task t = it.next();
            if(t.getId() == taskId && !t.isCompleted()){
                t.markCompleted();
                removeTask(t.getId());
                completedTasks.pushCompletedTask(t);
                break;}}}

    public void markAllCompleted(){
        li=taskList.listIterator();
        while (li.hasNext()){
            {Task current = li.next();
                if (!current.isCompleted()){
            current.markCompleted();
            removeTask(current.getId());
                completedTasks.pushCompletedTask(current);}}
        }
    }

    public List<Task> getPendingTasks(){
        return taskList;
    }

    public List<Task> getCompletedTasks(){return completedTasks.getCompletedTasksHistory();}

    public List<Task> getTasksByPriority(Task.Priority priority){
        return priorityTasks.getTasksByPriority(priority);}

    public void removeCompletedTasks(){LinkedList<Task> result = new LinkedList<>();
        li=taskList.listIterator();
        while (li.hasNext()){
            Task current = li.next();
            if (current.isCompleted()) {
                result.remove(current);
             completedTasks.removeTaskById(current.getId());
            }}}

    public void moveTaskTo(Task task, ToDos targetList){
        removeTask(task.getId());
        targetList.addTask(task);
    }

    public String toString() {
        String list = "Lista de Tareas("+id+"): " + name + " - Fecha de Creación: " + creationDate;
        li=taskList.listIterator();
        while (li.hasNext()){
            list += li.next().toString();
        }
        return list;
    }
    public LeonidasTaskQueue getPriorityQueue(){return priorityTasks;}

    public void returnCompletedTask(int taskId){
        Task task = completedTasks.removeTaskById(taskId);
        if (task != null) {
            if (task.isCompleted()) {
                if (task instanceof LeonidasTask) {
                    ((LeonidasTask) task).markPending();
                } else {
                    return;}}
            taskList.addLast(task);
            priorityTasks.enqueueTask(task);}}
}
