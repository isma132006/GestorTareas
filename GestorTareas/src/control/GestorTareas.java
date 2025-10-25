package control;

import modelo.Estado;
import modelo.Tarea;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Clase para metodos que haran las Tareas 
 */
public class GestorTareas<T extends Tarea> {
    /*Lista de tareas a manejar */
    private List<T> tareas;
    private final String archivoDatos = "tareas.dat";
    
    /*COnstructor para inicializar la list a */
    public GestorTareas() {
    List<Tarea> cargadas = PersistenciaTareas.cargar(archivoDatos);
    if (cargadas != null) {
        tareas = new ArrayList<>();
        for (Tarea tarea : cargadas) {
            tareas.add((T) tarea); // casteo seguro si sabes que cargaste solo Ts
        }
    } else {
        tareas = new ArrayList<>();
    }
    }
    //Metodos que usaran las Tareas 

    /**
     * Metodo para agregar una Tarea a la lista
     * @param tarea
     */
    public void agregarTarea(T tarea) {
        if(tarea == null){
            throw new IllegalArgumentException("La tarea no puede ser nula");
        }else{
            tareas.add(tarea);
            List<Tarea> copia = new ArrayList<>();
            for (T t : tareas) {
                copia.add(t); // seguro porque T extends Tarea
            }
            PersistenciaTareas.guardar(copia, archivoDatos);

        }
    }
    /**
     * Metodoo para  Eliminar una Tarea d ela lista 
     * @param tarea
     * @return
     */
    public boolean eliminarTarea(T tarea) {
    if (tarea == null || tareas == null) {
        throw new IllegalArgumentException("Tarea nula o no hay tareas");
    } else {
        if (tareas.contains(tarea)) {
            tareas.remove(tarea);
            // Convertimos la lista a List<Tarea>
            List<Tarea> copia = new ArrayList<>();
            for (T t : tareas) {
                copia.add(t);
            }
            PersistenciaTareas.guardar(copia, archivoDatos);
            return true;
        }
        return false;
    }
}

    /**
     * Metodo Listar las Tareas 
     * @return lista de las Tareas 
     */
    public List<T> listarTodas() {
        return copiarTareas();
    }

    /**
     * Metodo para devolver una copia de la lista de las Tareas, 
     * si no devolvieramos una copia, la podrian modificar directamente y eso no siempre es lo
     *  correcto.
     * @return una coopia de la lista de tareas
     */
    public List<T> copiarTareas(){
        return new ArrayList<>(tareas);
    }

    /**
     * Buscar la Tarea por titulo
     * @param titulo
     * @return Tarea con el tirulo
     */
    public Tarea buscarPorTitulo(String titulo) {
        if(titulo == null || tareas == null){
            throw new IllegalArgumentException("Titulo nulo o no hay tareas");
        }
        Iterator<T> itTareas = tareas.iterator();
        while (itTareas.hasNext()) {

            T elemento = itTareas.next();
            Tarea tareaActual = (Tarea) elemento;
            if(tareaActual.getTitulo().equals(titulo)){
                return tareaActual;
            }
        }
        return null;
    }

    /**
     * 
     * @param tarea
     * @return <code> true </code> si la tarea esta contenida en la lista,
     * <code> false </code> en caso contrario 
     */
    public boolean contieneTarea(T tarea) {
        Tarea tareaParaBuscar = (Tarea) tarea;
        if(tarea == null || tareas == null){
            throw new IllegalArgumentException("Tarea nula o no hay tareas");
        }
        Iterator<T>itTareas = tareas.iterator();
        while (itTareas.hasNext()) {
            T elemento = itTareas.next();
            Tarea TareaActual = (Tarea) elemento;
            if(TareaActual.equals(tareaParaBuscar)){
                return true;
            }
        }
            return false; 
        }
    

    /**
     * metodo para modificar una tarea existente
     * @param tarea
     * @return <code> True </code> si se actualizo correctamente <code> false </code> 
     * en otro caso
     */
    public Boolean actualizarTarea(T tarea){
        if(tarea == null || tareas == null){
            throw new IllegalArgumentException("Tarea nula o no hay tareas");
        }
        for(int i = 0; i<tareas.size(); i++){
            if(tarea.equals(tareas.get(i))){
                tareas.set(i, tarea);

                List<Tarea> copia = new ArrayList<>();
                for (T t : tareas) {
                    copia.add(t);
                }
                PersistenciaTareas.guardar(copia, archivoDatos);

                return true;
            }
        }
        return false;
    }

    /**
     * Metodo para devolver las tareas que coincidan con la etiqueta
     * @param etiqueta
     * @return Una lsta de las tareas con las etiquetas
     */
    public List<T> buscarPorEtiqueta(String etiqueta){
        return FiltroTareas.buscarPorEtiqueta(tareas, etiqueta);
    }

    /**
     * Metodo para Listar tareas por estado
     * @param estado
     * @return Una lista de las tareas filtraras por estado
     */
    public List<T> listarPorEstado(Estado estado){
        return FiltroTareas.listarPorEstado(tareas, estado);
    }

    public List<T> ordenarPrioridad() {
        return OrdenadorTareas.ordenarPorPrioridad(tareas);
    }

    public List<T> ordenarPorFechaLimite() {
        return OrdenadorTareas.ordenarPorFechaLimite(tareas);
    }
    }
