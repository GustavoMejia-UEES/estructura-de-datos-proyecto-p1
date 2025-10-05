package model;

import java.util.ArrayList;
import java.util.List;

public class ToDos {

    private String name;
    private String creationDate; // simple string
    private List<Tarea> tareas;

    public ToDos(String name) {
        this.name = name;
        this.creationDate = "";
        this.tareas = new ArrayList<>();
    }

    public ToDos(String name, String creationDate) {
        this.name = name;
        this.creationDate = creationDate;
        this.tareas = new ArrayList<>();
    }

    // getters y setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    public void addTarea(Tarea t) {
        tareas.add(t);
    }

    public void markAllCompleted() {
        for (int i = 0; i < tareas.size(); i++) {
            tareas.get(i).markCompleted();
        }
    }

    public List<Tarea> viewPending() {
        List<Tarea> result = new ArrayList<>();
        for (int i = 0; i < tareas.size(); i++) {
            Tarea t = tareas.get(i);
            if (!t.isCompleted()) {
                result.add(t);
            }
        }
        return result;
    }

    public void removeCompleted() {
        for (int i = tareas.size() - 1; i >= 0; i--) {
            if (tareas.get(i).isCompleted()) {
                tareas.remove(i);
            }
        }
    }

    public List<Tarea> getByPriority(Priority p) {
        List<Tarea> result = new ArrayList<>();
        for (int i = 0; i < tareas.size(); i++) {
            Tarea t = tareas.get(i);
            if (p.equals(t.getPriority())) {
                result.add(t);
            }
        }
        return result;
    }

    public List<Tarea> getCompleted() {
        List<Tarea> result = new ArrayList<>();
        for (int i = 0; i < tareas.size(); i++) {
            Tarea t = tareas.get(i);
            if (t.isCompleted()) {
                result.add(t);
            }
        }
        return result;
    }

    public String toString() {
        String s = "ToDos: " + name + " (creación: " + creationDate + ")\n";
        for (int i = 0; i < tareas.size(); i++) {
            s = s + "- " + tareas.get(i).getTitle() + " [id:" + tareas.get(i).getId() + ", prioridad:" + (tareas.get(i).getPriority() == null ? "" : tareas.get(i).getPriority().toString()) + ", fecha:" + tareas.get(i).getDueDate() + ", hecha:" + (tareas.get(i).isCompleted() ? "sí" : "no") + "]\n";
        }
        return s;
    }
}
