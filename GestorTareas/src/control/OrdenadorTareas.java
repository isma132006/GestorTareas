package control;

import modelo.Tarea;
import java.util.List;
import java.util.ArrayList;

public class OrdenadorTareas {

    // Método genérico para ordenar por prioridad
    public static <T extends Tarea> List<T> ordenarPorPrioridad(List<T> tareas) {
        List<T> copia = new ArrayList<>(tareas);
        // Puedes usar quickSort o Collections.sort con Comparator
        // Por simplicidad, aquí te pongo Collections.sort con Comparator inverso (prioridad descendente)
        copia.sort((t1, t2) -> Integer.compare(t2.getPrioridad(), t1.getPrioridad()));
        return copia;
    }

    // Método genérico para ordenar por fecha límite
    public static <T extends Tarea> List<T> ordenarPorFechaLimite(List<T> tareas) {
        List<T> copia = new ArrayList<>(tareas);
        copia.sort((t1, t2) -> t1.getFechaLimite().compareTo(t2.getFechaLimite()));
        return copia;
    }

    // Si quieres puedes agregar quickSort genérico también, pero con sort es suficiente
}
