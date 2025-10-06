
import model.App;
import model.ToDos;
import model.Tarea;
import model.Priority;
import model.TareaPriorityComparator;

public class Main {

    public static void main(String[] args) {
        App app = new App();

        // Quick programmatic demo
        ToDos l1 = new ToDos("Personal");
        l1.addTarea(new Tarea(1, "Comprar leche", "Ir al supermercado", Priority.LOW, "2025-10-05"));
        l1.addTarea(new Tarea(2, "Entregar tarea", "Estructura de Datos", Priority.HIGH, "2025-10-06"));

        ToDos l2 = new ToDos("Trabajo");
        l2.addTarea(new Tarea(3, "Reunión", "Revisión de proyecto", Priority.MID, "2025-10-04"));
        l2.addTarea(new Tarea(4, "Enviar reporte", "Enviar por email", Priority.HIGH, "2025-10-07"));

        app.addLista(l1);
        app.addLista(l2);

        System.out.println("--- Todas las listas ---");
        for (ToDos x : app.getListas()) {
            System.out.println(x);
        }

        System.out.println("--- Tareas de prioridad HIGH en todas las listas (usando Comparator/find) ---");
        Tarea sample = new Tarea(0, "", "", Priority.HIGH, "");
        TareaPriorityComparator comp = new TareaPriorityComparator();
        for (ToDos list : app.getListas()) {
            java.util.List<Tarea> found = list.find(comp, sample);
            for (Tarea t : found) {
                System.out.println(t);
            }
        }

        // Marcar algunas como completadas
        l1.getTareas().get(1).markCompleted();
        l2.getTareas().get(1).markCompleted();

        System.out.println("--- Todas las tareas hechas ---");
        for (Tarea t : app.tareasHechas()) {
            System.out.println(t);
        }
    }
}
