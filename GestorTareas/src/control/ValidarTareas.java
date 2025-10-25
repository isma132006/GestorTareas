package control;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.List;

import modelo.Estado;
import modelo.Etiqueta;

public class ValidarTareas {

    /**
     * Valida que el tirulo sea correcto
     * @param titulo
     * @return <code> true </code> si es correcto.<br> </br>
     * <code> false </code> en otro caso.
     */
    public static boolean validarTitulo(String titulo){
        return titulo != null && !(titulo.isEmpty()); 
    }
    /**
     * valida si la descripcion es valida 
     * @param descripcion
     * @return <code> true </code> si es correcto.<br> </br>
     * <code> false </code> en otro caso.
     */
    public static Boolean validarDescripcion(String descripcion){
        return descripcion != null;
    }

    /**
     * Valida de la fecha limite es valida 
     * @param fechaLimite
     * @return <code> true </code> si es correcto.<br> </br>
     * <code> false </code> en otro caso.
     */
    public static Boolean validarFechaLimite(LocalDate fechaLimite){
        if(fechaLimite == null) return false;
        if(fechaLimite.isBefore(LocalDate.now())){
            return false;
        }
        return true;
    }

    /**
     * Valida si la prioridad de la tarea es valida 
     * @param prioridad
     * @return <code> true </code> si es correcto.<br> </br>
     * <code> false </code> en otro caso.
     */
    public static Boolean validarPrioridad(int prioridad){
        return  prioridad>=0 && prioridad <11;
    
    }

    /**
     * Valida de las etiquetas son corectas
     * @param etiquetas
     * @return <code> true </code> si es correcto.<br> </br>
     * <code> false </code> en otro caso.
     */
    public static Boolean validarEtiquetas(List<String> etiquetas){
        if(etiquetas == null) return false;
        for(String etiqueta: etiquetas){
            if(etiqueta ==null || etiqueta.trim().isEmpty()) return false;
        }
        return true;
    }

    /**
     * valida si el estado es correcto
     * @param estado
     * @return <code> true </code> si es correcto.<br> </br>
     * <code> false </code> en otro caso.
     */
    public static Boolean validarEstado(Estado estado){
        return estado != null && EnumSet.allOf(Estado.class).contains(estado);
    }
}
