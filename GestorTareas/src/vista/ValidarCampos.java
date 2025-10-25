package vista;

import java.time.LocalDate;

import javafx.scene.control.TextField;
import modelo.Estado;

public class ValidarCampos{

    public static boolean validarText(TextField texto){
        String dato = texto.getText();
        return !dato.isEmpty();
    }

    public static boolean validarDescrip(TextField texto){
        String dato = texto.getText();
        return !dato.isEmpty();
    }

    public static boolean validarFechaLim(TextField texto){
        try {
            LocalDate fecha = LocalDate.parse(texto.getText().trim());
            return !fecha.isBefore(LocalDate.now());
        } catch (Exception e) {
            return false;
        }
        
    }

    public static boolean validarPriori(TextField texto){
        try {
            int numero = Integer.parseInt(texto.getText().trim());
            if(numero <0 || numero>10) return false;
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validarEtiquetas(TextField texto){
        String entrada = texto.getText().trim();
        if(entrada.isEmpty()) return false;

        String[] arregloEtiquetas = entrada.split("[,+/\\-~]+");
        for(String et: arregloEtiquetas){
            if (et.trim().isEmpty()) {
                return false;
            }
        }
        return true;   
    }

    public static boolean validarEstado(TextField texto){
        try {
            String pruebaestado = texto.getText().toUpperCase();
            Estado estado = Estado.valueOf(pruebaestado);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
