package control;

import java.util.ArrayList;
import java.util.List;

import modelo.Estado;
import modelo.Tarea;

public class FiltroTareas {
    /**
     * Metodo para devolver las tareas que coincidan con la etiqueta
     * @param etiqueta
     * @return Una lsta de las tareas con las etiquetas
     */
    public static <T extends Tarea> List<T> buscarPorEtiqueta(List<T> tareas, String etiqueta){
        if(etiqueta == null || tareas == null){
            throw new IllegalArgumentException("etiqueta nula o no hay tareas");
        }
        ArrayList<T> listaPorEtiqueta = new ArrayList<>(); 

        for(int i = 0; i<tareas.size(); i++){
            if (tareas.get(i).getEtiquetas().contains(etiqueta)) {
                listaPorEtiqueta.add(tareas.get(i));
            }
        }
        return listaPorEtiqueta;
    }

    /**
     * Metodo para Listar tareas por estado
     * @param estado
     * @return Una lista de las tareas filtraras por estado
     */
    public static <T extends Tarea> List<T> listarPorEstado(List<T> tareas, Estado estado){
        if(estado == null || tareas == null){
            throw new IllegalArgumentException("estado nulo o no hay tareas");
        }
        ArrayList<T> listaPorEstado = new ArrayList<>(); 

        for(int i = 0; i<tareas.size(); i++){
            if (tareas.get(i).getEstado().equals(estado)) {
                listaPorEstado.add(tareas.get(i));
            }
        }
        return listaPorEstado;
    }

}
