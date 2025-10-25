package org.uees.model.emilio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmilioTask {
    
    public enum Priority {
        HIGH(3, "Alta"),
        MEDIUM(2, "Media"),
        LOW(1, "Baja");
        
        private final int value;
        private final String displayName;
        
        Priority(int value, String displayName) {
            this.value = value;
            this.displayName = displayName;
        }
        
        public int getValue() { return value; }
        public String getDisplayName() { return displayName; }
        
        public static Priority fromString(String str) {
            if (str == null) return MEDIUM;
            switch (str.toUpperCase()) {
                case "ALTA": case "HIGH": case "3": return HIGH;
                case "MEDIA": case "MEDIUM": case "2": return MEDIUM;
                case "BAJA": case "LOW": case "1": return LOW;
                default: return MEDIUM;
            }
        }
    }
    
    public enum TaskStatus {
        PENDING("Pendiente"),
        COMPLETED("Completada");
        
        private final String displayName;
        
        TaskStatus(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() { return displayName; }
        
        public static TaskStatus fromString(String str) {
            if (str == null) return PENDING;
            switch (str.toUpperCase()) {
                case "COMPLETADA": case "COMPLETED": return COMPLETED;
                case "PENDIENTE": case "PENDING": default: return PENDING;
            }
        }
    }
    
    private final int id;
    private String description;
    private LocalDateTime dueDate;
    private Priority priority;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private String listName;
    
    public EmilioTask(int id, String description, LocalDateTime dueDate, Priority priority) {
        this.id = id;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = TaskStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.completedAt = null;
        this.listName = "Default";
    }
    
    public EmilioTask(int id, String description, LocalDateTime dueDate, Priority priority, String listName) {
        this(id, description, dueDate, priority);
        this.listName = listName;
    }
    
    public int getId() { return id; }
    public String getDescription() { return description; }
    public LocalDateTime getDueDate() { return dueDate; }
    public Priority getPriority() { return priority; }
    public TaskStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public String getListName() { return listName; }
    
    public void setDescription(String description) { this.description = description; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }
    public void setPriority(Priority priority) { this.priority = priority; }
    
    public void markAsCompleted() {
        this.status = TaskStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
    }
    
    public void markAsPending() {
        this.status = TaskStatus.PENDING;
        this.completedAt = null;
    }
    
    public boolean isOverdue() {
        return status == TaskStatus.PENDING && LocalDateTime.now().isAfter(dueDate);
    }
    
    public boolean isCompletedInLastDays(int days) {
        if (status != TaskStatus.COMPLETED || completedAt == null) return false;
        return completedAt.isAfter(LocalDateTime.now().minusDays(days));
    }
    
    public boolean matchesSearchText(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) return true;
        return description.toLowerCase().contains(searchText.toLowerCase());
    }
    
    public boolean matchesPriority(Priority searchPriority) {
        return searchPriority == null || this.priority == searchPriority;
    }
    
    public boolean matchesStatus(TaskStatus searchStatus) {
        return searchStatus == null || this.status == searchStatus;
    }
    
    public String toFileString() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return String.join("|",
            String.valueOf(id),
            description,
            dueDate.format(formatter),
            priority.name(),
            status.name(),
            createdAt.format(formatter),
            completedAt != null ? completedAt.format(formatter) : "",
            listName
        );
    }
    
    public static EmilioTask fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 7) return null;
        
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        EmilioTask task = new EmilioTask(
            Integer.parseInt(parts[0]),
            parts[1],
            LocalDateTime.parse(parts[2], formatter),
            Priority.valueOf(parts[3]),
            parts.length > 7 ? parts[7] : "Default"
        );
        
        task.status = TaskStatus.valueOf(parts[4]);
        task.createdAt = LocalDateTime.parse(parts[5], formatter);
        if (!parts[6].isEmpty()) {
            task.completedAt = LocalDateTime.parse(parts[6], formatter);
        }
        
        return task;
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("[%s] %s - %s (Vence: %s)%s",
            priority.getDisplayName(),
            description,
            status.getDisplayName(),
            dueDate.format(formatter),
            isOverdue() ? " ¡VENCIDA!" : ""
        );
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EmilioTask task = (EmilioTask) obj;
        return id == task.id;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
