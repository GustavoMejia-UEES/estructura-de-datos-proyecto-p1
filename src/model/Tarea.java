package model;

public class Tarea {

    private int id;
    private String title;
    private String description;
    private Priority priority;
    private String dueDate; // fecha como String
    private boolean completed;

    public Tarea(int id, String title, String description, Priority priority, String dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.completed = false;
    }

    // getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void markCompleted() {
        this.completed = true;
    }

    public String toString() {
        String s = "Tarea: " + title + " (id: " + id + ")\n";
        s = s + "  Descripción: " + description + "\n";
        s = s + "  Prioridad: " + (priority == null ? "" : priority.toString()) + "\n";
        s = s + "  Fecha: " + dueDate + "\n";
        s = s + "  Completada: " + (completed ? "sí" : "no") + "\n";
        return s;
    }
}
