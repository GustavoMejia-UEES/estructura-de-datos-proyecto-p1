package model;

public class Priority {

    public static  Priority LOW = new Priority("LOW");
    public static  Priority MID = new Priority("MID");
    public static  Priority HIGH = new Priority("HIGH");

    private  String name;

    private Priority(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Priority)) return false;
        Priority other = (Priority) o;
        return name.equals(other.name);
    }

    public int hashCode() {
        return name.hashCode();
    }
}
