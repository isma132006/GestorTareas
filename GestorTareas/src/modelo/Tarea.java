package modelo;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;


import javax.print.DocFlavor.STRING;
/**
 * Clase para representar una Tarea con atributos como titulo,descripcion, fecha limite, 
 * prioridad, estado, etiquetas
 * 
 */

public class Tarea implements Serializable {

    private static final long serialVersionUID = 1L;  // versión para serialización

    /* Frase para resumir la Tarea*//* */
    private String titulo;
    /* Descripcion de la Tarea */
    private String descripcion;
    /* Fecha limite de la Tarea */
    private LocalDate fechaLimite;
    /*Prioridad de la Tarea */
    private int prioridad;
    /* Estado Actual de la Tarea */
    private Estado estado;
    /* Etiquetas para agrupar tareas */
    private List<String> etiquetas;

    /**
     * COnstructor para inicializar los atributos de Tarea
     * @param titulo el titulo de la Tarea 
     * @param descripcion descripcion de la tarea 
     * @param fechaLimite fech alimiet de la tarea
     * @param prioridad prioridad de la tarea 
     * @param estado estado de la tarea
     * @param etiquetas etiqueta/categoria de la tarea 
     * 
     */
    public Tarea(String titulo, String descripcion, LocalDate fechaLimite, int prioridad, Estado estado, List<String> etiquetas){
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaLimite = fechaLimite;
        this.prioridad = prioridad;
        this.estado = estado;
        this.etiquetas = new ArrayList<>(etiquetas);
    }

    //Metodos getter y setter, deben ser publicos para que puedan ser moficiados desde fuera.
    
    /**
     * Regresa el titulo de la Tarea 
     * @return el nombre la tarea 
     */
    public String getTitulo(){
        return titulo;
    }
    /**
     * Define el nombre de la Tarea 
     *@param tirulo el tirulo de la tarea 
     */
    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    /**
     * Regresa la descripcion
     * @return el nombre la tarea 
     */
    public String getDescripcion(){
        return descripcion;
    }
    /**
     * Define la descripcion  
     *@param descripcion la descripcion de la tarea 
     */
    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    /**
     * Regresa la fecha limite
     * @return la fecha limite 
     */
    public LocalDate getFechaLimite(){
        return fechaLimite;
    }
    /**
     * Define la fecha limite  
     *@param fechaLimite la fecha limite  
     */
    public void setFechaLimite(LocalDate fechaLimite){
        this.fechaLimite = fechaLimite;
    }

    /**
     * Regresa la prioridad
     * @return la prioridad 
     */
    public int getPrioridad(){
        return prioridad;
    }
    /**
     * Define la prioridad
     *@param prioridad la prioridad   
     */
    public void setPrioridad(int prioridad){
        if(prioridad <0 || prioridad >10){
            throw new IllegalArgumentException("La prioridad debe estar entre 0-10");
        }else{
            this.prioridad = prioridad;
        }
    }
    /**
     * Regresa el estado
     * @returnel estado/categoria
     */
    public Estado getEstado(){
        return estado;
    }
    /**
     * Define el estado 
     *@param estado el estado   
     */
    public void setEstado(Estado estado){
        this.estado = estado;
    }
    /**
     * Regresa las etiquetas
     * @return las etiquetas 
     */
    public List<String> getEtiquetas(){
        return etiquetas;
    }
    /**
     * Define la etiqueta
     * @param etiquetas las etiquetas 
     */
    public void setEtiquetas (List<String> etiquetas){
        /*Esta línea crea una nueva lista propia e interna 
        dentro del objeto Tarea, copiando todos los 
        elementos que venían en la lista etiquetas recibida 
        como parámetro. */
        this.etiquetas = new ArrayList<>(etiquetas);
    }

    /**
     * Regresa una representacion en cadena de la Tarea
     * @return uuna representacion en cadena de la Tarea 
     */
    @Override
    public String toString(){
        return String.format(
                            "%-13s: %s\n" +  // "Titulo", getTitulo()
                            "%-13s: %s\n" +  // "Descripcion", getDescripcion()
                            "%-13s: %s\n" +  // "Fecha Limite", getFechaLimite()
                            "%-13s: %d\n" +  // "Prioridad", getPrioridad()
                            "%-13s: %s\n" +  // "Estado", getEstado()
                            "%-13s: %s\n",   // "Etiqueta", getEtiquetas()
                            "Titulo", getTitulo(),
                            "Descripcion", getDescripcion(),
                            "Fecha Limite", getFechaLimite(),
                            "Prioridad", getPrioridad(),
                            "Estado", getEstado(),
                            "Etiqueta", getEtiquetas());
    }

    /**
     * Compara dos Tareas
     * @param objeto el objeto con el se compara la tarea 
     * @return <code>true</code> si el objeto recibido es una Tarea con las
     *         mismas propiedades que el objeto que manda llamar al método.
     *         <code>false</code> en otro caso.
     */
    @Override
    public boolean equals(Object objecto){
        if(!(objecto instanceof Tarea)) return false;
        Tarea tareaDos = (Tarea)objecto;
        if( (tareaDos.getTitulo().equals(getTitulo()))
        && (tareaDos.getDescripcion().equals(getDescripcion()))
        && (tareaDos.getFechaLimite().equals(getFechaLimite()))
        && (tareaDos.getPrioridad() == (getPrioridad()))
        && (tareaDos.getEstado().equals(getEstado()))
        && (tareaDos.getEtiquetas().equals(getEtiquetas()))){
            return true;
        }
    return false; 
    }

    /**
     * Pasa la Tarea a hashcode, para buscarlo directamnete.
     * el metodo.hashcode devuelve un numero realiza por java.
     * @return el hashcode correspondiete a la tarea.
     */
    @Override
    public int hashCode(){
        int resultado = 1;
        resultado = 31* resultado + (titulo != null ? titulo.hashCode() : 0);
        resultado = 31* resultado +(descripcion != null ? descripcion.hashCode() : 0);
        resultado = 31* resultado +(fechaLimite != null ? fechaLimite.hashCode() : 0);
        resultado = 31* resultado + prioridad;
        resultado = 31* resultado +(estado != null ? estado.hashCode() : 0 );
        resultado = 31* resultado + (etiquetas != null ? etiquetas.hashCode() : 0);
        return resultado;
    }
}
