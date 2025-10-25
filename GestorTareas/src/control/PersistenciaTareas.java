package control;

import modelo.Tarea;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class PersistenciaTareas {

    // Guarda la lista de tareas en un archivo
    public static void guardar(List<Tarea> lista, String nombreArchivo) {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            salida.writeObject(lista);  // serializa la lista completa
        } catch (IOException e) {
            System.out.println("Error al guardar tareas: " + e.getMessage());
        }
    }

    // Carga la lista de tareas desde un archivo
    public static List<Tarea> cargar(String nombreArchivo) {
        List<Tarea> lista = new ArrayList<>();
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            lista = (List<Tarea>) entrada.readObject(); // deserializa la lista
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado, se creará uno nuevo.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar tareas: " + e.getMessage());
        }
        return lista;
    }
}
