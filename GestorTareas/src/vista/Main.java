package vista;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import control.GestorTareas;
import modelo.Estado;
import modelo.Tarea;
/**
 * Aqui se podra ejecutar el programa por consola.
 */

public class Main {
    /**
     * EL metodo main se encarga de ejecutar el programa, 
     * aqui se interactua conel usuario.
     * @param args argumentos-parametros cuando se corre main.java
     */
    public static void main(String[] args) {
        GestorTareas gestor = new GestorTareas<>();

        Scanner entrada = new Scanner(System.in);

        System.out.println("Escribe el numero a ejecutar: ");   

        while (true) {
            System.out.println("1. Agregar tarea");
            System.out.println("2. Eliminar tarea");
            System.out.println("3. Listar tareas");
            System.out.println("4. Buscar por título");
            System.out.println("5. Filtrar por etiqueta");
            System.out.println("6. Ordenar por prioridad");
            System.out.println("7. Salir");

            int opcion = Integer.parseInt(entrada.nextLine());
            

            switch (opcion) {
                case 1:
                System.out.println("=======  Agregando Tarea  =======");

                String titulo = "a";
                while (true) {
                    try {
                System.out.println("Titulo: ");
                titulo = entrada.nextLine().trim();
                if(titulo != null && !(titulo.isEmpty())){
                    break;
                }else{
                    System.out.println("Titulo Invalido");
                }
                    } catch (Exception e) {
                        System.out.println("Titulo invalido");
                    }
                }

                String descripcion;
                while (true) {
                    try {
                System.out.println("Descripcion: ");
                descripcion = entrada.nextLine().trim();
                if(descripcion != null && !(descripcion.isEmpty())){
                    break;
                }else{
                    System.out.println("Descripcion Invalida");
                }
                    } catch (Exception e) {
                        System.out.println("Descripcion Invalida");
                    }
                }

                LocalDate fechaLimite;
                while (true) {
                    try {
                        System.out.println("Fecha limite (AAAA-MM-DD): ");
                        String entradaFecha = entrada.nextLine();
                        fechaLimite = LocalDate.parse(entradaFecha);

                        if (fechaLimite.isBefore(LocalDate.now())) {
                            System.out.println("La fecha no puede ser anterior a hoy. Intenta otra vez.");
                        } else {
                            break; // Fecha válida y futura
                        }
                    } catch (Exception e) {
                        System.out.println("Formato de fecha inválido. Usa el formato AAAA-MM-DD.");
                    }
                    
                }

                int prioridad = -1;
                while (true) {
                    try {
                        System.out.println("Prioridad (1-10): ");
                        prioridad = Integer.parseInt(entrada.nextLine());
                    
                        if (prioridad < 0 || prioridad > 10) {
                            System.out.println("La prioridad debe estar entre 0 y 10.");
                        } else {
                            break; //  solo salimos si es válido
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Debes escribir un número válido.");
                    }
                }
                
                Estado estado = null;
                while (true) {
                    try{
                System.out.println("Estado (PENDIENTE, EN_PROCESO, COMPLETADA): ");
                String pruebaEstado = (entrada.nextLine().toUpperCase());
                estado = Estado.valueOf(pruebaEstado);
                break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("EL Estado debe ser  (PENDIENTE, EN_PROCESO, COMPLETADA)");
                    }
                }

                
                List<String> etiquetas = new ArrayList<>();

                String et = null;
                while (true) {
                    try {
                System.out.print("Escribe una etiqueta (o escribe 'fin' o 'salir' o 'X' o presiona Enter para terminar): ");    
                et = entrada.nextLine().trim();
                if (et.equalsIgnoreCase("fin") || 
                et.equalsIgnoreCase("salir") || 
                et.equalsIgnoreCase("X") || 
                et.isEmpty()) {
                    break;
                }
                etiquetas.add(et);
                    } catch (Exception e) {
                        System.out.println("Titulo invalido. Reintenta");
                    }
                }

                
                Tarea tarea = new Tarea(titulo, descripcion, fechaLimite, prioridad, estado, etiquetas);

                gestor.agregarTarea(tarea);
                System.out.println("Tarea agregada Correctamente :)");
                    break;
                

                case 2:
                List<Tarea> listaPorTitulo = gestor.listarTodas();
                if(listaPorTitulo.isEmpty()){
                    System.out.println("NO HAY TAREAS");
                    break;
                } 
                System.out.println("=== Eliminando Tarea ===");
                System.out.println("Tareas registradas:");
                for(Tarea tareaActual : listaPorTitulo){
                System.out.println(tareaActual.getTitulo());
                }

                System.out.println("Escribe el titulo de la tarea: ");
                String tituloTarea = entrada.nextLine().trim();

                Tarea aBuscar = gestor.buscarPorTitulo(tituloTarea);
                if(aBuscar != null){
                    Boolean  relsultado= gestor.eliminarTarea(aBuscar);
                    if(relsultado == true){
                        System.out.println("La tarea se elimino correctamente :)");
                    } else {
                        System.out.println("La tarea no se pudo eliminar. :()");
                    }
                }else{
                    System.out.println("No se encontro la tarea con ese tirulo, verifica de nuevo.");
                }
                    break;

                case 3:
                System.out.println("==== Listado de Tareas ===");
                List<Tarea> lista = gestor.listarTodas();
                if(lista.isEmpty()){
                    System.out.println("no hay tareas registradas ");
                } else{
                    for(Tarea tareaActual : lista){
                        System.out.println(tareaActual.toString());
                    }
                }
                break;

                case 4:
                List<Tarea> buscandoPorTitulo = gestor.listarTodas();
                if(buscandoPorTitulo.isEmpty()){
                    System.out.println("NO HAY TAREAS");
                    break;
                } 
                System.out.println("=== Buscando por Titulo ===");
                System.out.println("Titulo de la tarea: ");
                String entradaTitulo = entrada.nextLine();
                Tarea rel = gestor.buscarPorTitulo(entradaTitulo);
                if ( rel == null){
                    System.out.println("No hay coincidencias");
                    break;
                }
                System.out.println("Coincidencia:");
                System.out.println(rel);
                    break;

                case 5:
                System.out.println("=== Filtrando por etiqueta ===");
                System.out.println("Escribe la etiqueta: ");
                String etiqueta = entrada.nextLine();
                List<Tarea> filtrandoPorEtiqueta = gestor.buscarPorEtiqueta(etiqueta);
                if(filtrandoPorEtiqueta.isEmpty ()){
                    System.out.println("NO HAY TAREAS CON ESA ETIQUETA");
                    break;
                }
                for(Tarea tr : filtrandoPorEtiqueta){
                    System.out.println(tr);
                }
                    break;

                case 6:
                System.out.println("=== Ordenando por prioridad ===");
                List<Tarea> ordenandoPorPrioridad = gestor.ordenarPrioridad();
                if(ordenandoPorPrioridad.isEmpty ()){
                    System.out.println("NO HAY TAREAS ");
                    break;
                }
                for(Tarea tr : ordenandoPorPrioridad){
                    System.out.println(tr);
                }
                    break;

                case 7:
                System.out.println("HASTA LUEGO!!! :)");
                    return;
            
                default:
                    break;
            }
        }
    }
    
}
