package vista;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import control.GestorTareas;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Estado;
import modelo.Tarea;

public class VentanaEditar extends Stage {

    private final TextField campoTitulo = new TextField();
    private final TextField campoDescripcion = new TextField();
    private final TextField campoFechaLimite = new TextField();
    private final TextField campoPrioridad = new TextField();
    private final TextField campoEtiquetas = new TextField();
    private final TextField campoEstado = new TextField();

    public VentanaEditar(ObservableList<Tarea> listaTareas, GestorTareas<Tarea> gestor, Tarea tareaOriginal) {
        setTitle("Editar Tarea");

        // Rellenar campos con datos actuales de la tarea
        campoTitulo.setText(tareaOriginal.getTitulo());
        campoDescripcion.setText(tareaOriginal.getDescripcion());
        campoFechaLimite.setText(tareaOriginal.getFechaLimite().toString());
        campoPrioridad.setText(String.valueOf(tareaOriginal.getPrioridad()));
        campoEtiquetas.setText(String.join(",", tareaOriginal.getEtiquetas()));
        campoEstado.setText(tareaOriginal.getEstado().toString());

        campoTitulo.setPromptText("Título");
        campoDescripcion.setPromptText("Descripción");
        campoFechaLimite.setPromptText("AAAA-MM-DD");
        campoPrioridad.setPromptText("1-10");
        campoEtiquetas.setPromptText("etiqueta1, etiqueta2");
        campoEstado.setPromptText("PENDIENTE / EN_PROCESO / COMPLETADA");

        Button btnGuardarCambios = new Button("Guardar Cambios");

        btnGuardarCambios.setOnAction(e -> {
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
            tareaOriginal.setTitulo(titulo);
            tareaOriginal.setDescripcion(descripcion);
            tareaOriginal.setFechaLimite(fechaLimite);
            tareaOriginal.setPrioridad(prioridad);
            tareaOriginal.setEtiquetas(etiquetas);
            tareaOriginal.setEstado(estado);

            close(); // cierra la ventana
        });

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 15;");
        layout.getChildren().addAll(
            new Label("Título:"), campoTitulo,
            new Label("Descripción:"), campoDescripcion,
            new Label("Fecha límite:"), campoFechaLimite,
            new Label("Prioridad:"), campoPrioridad,
            new Label("Etiquetas:"), campoEtiquetas,
            new Label("Estado:"), campoEstado,
            btnGuardarCambios
        );

        Scene scene = new Scene(layout, 420, 490);
        scene.getStylesheets().add(getClass().getResource("styless.css").toExternalForm());
        setScene(scene);
        initModality(Modality.APPLICATION_MODAL); // Bloquea ventana principal hasta que esta cierre
    }

    public void mostrar() {
        showAndWait();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Error de validación");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
