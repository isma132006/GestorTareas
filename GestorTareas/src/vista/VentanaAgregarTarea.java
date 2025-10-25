package vista;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import control.GestorTareas;
import javafx.collections.ObservableList;
import modelo.Estado;
import modelo.Tarea;

public class VentanaAgregarTarea extends Stage {

    public VentanaAgregarTarea(ObservableList<Tarea> listaTareas,GestorTareas<Tarea> gestor) {
        setTitle("Agregar Nueva Tarea");
        // Aquí van tus controles: TextFields, DatePicker, ComboBox, etc.
        // Ejemplo:
        Label lblTitulo = new Label("Título:");
        TextField campoTitulo = new TextField();
        campoTitulo.setPromptText("Titulo");

        Label lblDescripcion = new Label("Descripcion: ");
        TextField campoDescripcion = new TextField();
        campoDescripcion.setPromptText("Descripcion");

        Label lblFechaLimte = new Label("Fecha Limite:");
        TextField campoFechaLimite = new TextField();
        campoFechaLimite.setPromptText("AAAA-MM-DD");

        Label lblPrioridad = new Label("Prioridad: ");
        TextField campoPrioridad = new TextField();
        campoPrioridad.setPromptText("1-10");

        Label lblEtiqueta = new Label("Etiquetas: ");
        TextField campoEtiquetas = new TextField();
        campoEtiquetas.setPromptText("etiqueta1, etiqueta2");

        Label lblEstado = new Label("Estado: :");
        TextField campoEstado = new TextField();
        campoEstado.setPromptText("PENDIENTE/EN_PROCESO/COMPLETADA");



        Button btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(e -> {
            if (!ValidarCampos.validarText(campoTitulo)) {
                mostrarAlerta("Título inválido");
                return;
            }
            if (!ValidarCampos.validarDescrip(campoDescripcion)) {
                mostrarAlerta("Descripcion inválido");
                return;
            }
            if (!ValidarCampos.validarFechaLim(campoFechaLimite)) {
                mostrarAlerta("Fecha LImite inválida(AAAA-MM-DD) o es anterior");
                return;
            }
            if (!ValidarCampos.validarPriori(campoPrioridad)) {
                mostrarAlerta("Prioridad inválida (1-10)");
                return;
            }
            if (!ValidarCampos.validarEtiquetas(campoEtiquetas)) {
                mostrarAlerta("Etiquetas inválidas (etiqueta1,etiqueta2)");
                return;
            }
            if (!ValidarCampos.validarEstado(campoEstado)) {
                mostrarAlerta("Estado inválido(PENDIENTE/EN_PROCESO/TERMINADA)");
                return;
            }
            String titulo = campoTitulo.getText().trim();
            String descripcion = campoDescripcion.getText().trim();
            LocalDate fechaLimite = LocalDate.parse(campoFechaLimite.getText().trim());
            int prioridad = Integer.parseInt(campoPrioridad.getText().trim());
            Estado estado = Estado.valueOf(campoEstado.getText().trim().toUpperCase());

            String entradaEtiquetas = campoEtiquetas.getText().trim();
            List<String> etiquetas = new ArrayList<>();

            for (String et : entradaEtiquetas.split("[,+/\\-~]+")) {
                if (!et.trim().isEmpty()) {
                    etiquetas.add(et.trim());
                }
            }
            //tareanueva
            Tarea nueva = new Tarea(titulo, descripcion, fechaLimite, prioridad, estado, etiquetas);

            listaTareas.add(nueva);
            gestor.agregarTarea(nueva);

            close(); // cierra la ventana
        });

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 15;");
        layout.getChildren().addAll(lblTitulo, campoTitulo, lblDescripcion, campoDescripcion, lblFechaLimte, campoFechaLimite, lblPrioridad, campoPrioridad, lblEtiqueta,campoEtiquetas, lblEstado, campoEstado, btnGuardar);

        Scene escena = new Scene(layout, 420, 480);
        escena.getStylesheets().add(getClass().getResource("styless.css").toExternalForm());
        setScene(escena);

        // Para que se bloquee la ventana principal hasta cerrar esta
        initModality(Modality.APPLICATION_MODAL);
    }

    public void mostrar() {
        showAndWait();
    }

    private static void mostrarAlerta(String mensaje) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Error de validación");
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.showAndWait();
}

}
