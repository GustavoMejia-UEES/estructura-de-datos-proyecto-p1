package model;

import java.util.ArrayList;
import java.util.List;

public class App {

    private List<ToDos> listas;

    public App() {
        listas = new ArrayList<>();
    }

    public void addLista(ToDos l) {
        listas.add(l);
    }

    public List<ToDos> getListas() {
        return listas;
    }

    public List<Tarea> tareasDePrioridad(Priority p) {
        List<Tarea> result = new ArrayList<>();
        for (int i = 0; i < listas.size(); i++) {
            ToDos l = listas.get(i);
            List<Tarea> parcial = l.getByPriority(p);
            for (int j = 0; j < parcial.size(); j++) {
                result.add(parcial.get(j));
            }
        }
        return result;
    }

    public List<Tarea> tareasHechas() {
        List<Tarea> result = new ArrayList<>();
        for (int i = 0; i < listas.size(); i++) {
            ToDos l = listas.get(i);
            List<Tarea> hechas = l.getCompleted();
            for (int j = 0; j < hechas.size(); j++) {
                result.add(hechas.get(j));
            }
        }
        return result;
    }
}
