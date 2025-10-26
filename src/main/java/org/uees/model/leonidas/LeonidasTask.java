package org.uees.model.leonidas;

import org.uees.model.api.Task;
import java.time.LocalDate;

public class LeonidasTask implements Task {
    private int id;
    private String name;
    private String description;
    private Boolean state;
    private Priority priority;
    private LocalDate dueDate;
    private LocalDate creationDate;
    private LocalDate completionDate;

    public LeonidasTask(int id,String name, String description, Task.Priority priority, LocalDate dueDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.state = false;
        this.priority = priority;
        this.dueDate = dueDate;
        this.creationDate = LocalDate.now();
    }

    public int getId() {return id;}
    public String getName() {return name;}
    public String getDescription() {return description;}
    public LocalDate getDueDate() {return dueDate;}
    public Task.Priority getPriority() {return priority;}
    public LocalDate getCreationDate(){return creationDate;}
    public LocalDate getCompletionDate(){return completionDate;}
    public boolean isCompleted() {return state;}
    public void markCompleted() {this.state = true;this.completionDate=LocalDate.now();}
    public void markPending() {this.state = false;this.completionDate=LocalDate.now();}
    public void editName(String newName) {this.name=newName;}
    public void editDescription(String newDescription) {this.description = newDescription;}
    public void editDueDate(LocalDate newDate) {this.dueDate = newDate;}
    public void editPriority(Task.Priority newPriority) {this.priority = newPriority;}

    public String toString() {
        if (state == true) {
            String s = "\n" + id + "- Tarea completada: " + name + " - Prioridad: " + priority + "\n" + "Fecha de Creación: " + creationDate + " - Fecha Límite: " + dueDate +"\n" + description;
            return s;
        } else {
            String s = "\n" + id + "- Tarea pendiente: " + name + " - Prioridad: " + priority + "\n" + "Fecha de Creación: " + creationDate + " - Fecha Límite: " + dueDate +"\n" + description;
            return s;
        }
    }
    public String toFileString() {
        return id + ";" + name + ";" + description + ";" + dueDate + ";" + state + ";" + priority + ";" +
                (completionDate != null ? completionDate : "");
    }
    public static LeonidasTask fromFileString(String data) {
        try {
            String[] parts = data.split(";");
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            String description = parts[2];
            LocalDate dueDate = LocalDate.parse(parts[3]);
            boolean completed = Boolean.parseBoolean(parts[4]);
            Priority priority = Priority.valueOf(parts[5]);
            LocalDate completionDate = parts.length > 6 && !parts[6].isEmpty() ? LocalDate.parse(parts[6]) : null;

            LeonidasTask task = new LeonidasTask(id, name, description, priority, dueDate);
            if (completed) task.markCompleted();
            if (completionDate != null) task.setCompletionDate();
            return task;

        } catch (Exception e) {
            System.out.println("Error al leer tarea: " + e.getMessage());
            return null;
        }
    }
    private void setCompletionDate() {this.completionDate=LocalDate.now();}
}
