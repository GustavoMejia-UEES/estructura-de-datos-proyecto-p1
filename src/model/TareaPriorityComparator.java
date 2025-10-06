package model;

import java.util.Comparator;

/**
 * Comparator para Tarea que compara por prioridad (HIGH > MID > LOW).
 */
public class TareaPriorityComparator implements Comparator<Tarea> {

    private int rank(Priority p) {
        if (p == null) {
            return 0;
        }
        if (p.equals(Priority.HIGH)) {
            return 3;
        }
        if (p.equals(Priority.MID)) {
            return 2;
        }
        if (p.equals(Priority.LOW)) {
            return 1;
        }
        return 0;
    }

    @Override
    public int compare(Tarea a, Tarea b) {
        if (a == null && b == null) {
            return 0;
        }
        if (a == null) {
            return -1;
        }
        if (b == null) {
            return 1;
        }
        int ra = rank(a.getPriority());
        int rb = rank(b.getPriority());
        return Integer.compare(ra, rb);
    }
}
