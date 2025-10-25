package vista;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import control.GestorTareas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import modelo.Estado;
import modelo.Tarea;

public class VentanaOpcionFiltrar extends Stage {

    private ObservableList<Tarea> todasLasTareas;
    private TableView<Tarea> tabla;

    public VentanaOpcionFiltrar(ObservableList<Tarea> todasLasTareas, TableView<Tarea> tabla, GestorTareas<Tarea> gestor) {
        this.todasLasTareas = todasLasTareas;
        this.tabla = tabla;

        setTitle("Opciones para filtrar");

        Button btnFilTitu = new Button("Filtrar por titulo.");
        Button btnFilDescrip = new Button("Filtrar por Descripcion.");
        Button btnFilFechaLim = new Button("Filtrar por proximas a vencer.");
        Button btnFilPriorid = new Button("Filtrar por proridad.");
        Button btnFilEtiqueta = new Button("Filtrar por etiqueta.");
        Button btnFilEstado = new Button("Filtrar por Estado.");

        // FILTRAR POR TÍTULO
        btnFilTitu.setOnAction(e -> {
            TextInputDialog dialogo = new TextInputDialog();
            dialogo.setTitle("Filtrar por Título");
            dialogo.setHeaderText(null);
            dialogo.setContentText("Escribe el título a buscar:");

            dialogo.showAndWait().ifPresent(tituloBuscado -> {
                if (tituloBuscado.trim().isEmpty()) {
                    mostrarAlerta("Debes escribir un título.");
                    return;
                }

                ObservableList<Tarea> filtradas = FXCollections.observableArrayList();
                for (Tarea tarea : todasLasTareas) {
                    if (tarea.getTitulo().toLowerCase().contains(tituloBuscado.toLowerCase())) {
                        filtradas.add(tarea);
                    }
                }

                if (filtradas.isEmpty()) {
                    mostrarAlerta("No se encontraron tareas con ese título.");
                } else {
                    tabla.setItems(filtradas);
                    close(); // cerrar la ventana de opciones
                }
            });
        });

        // FILTRAR POR DESCRIPCIÓN
        btnFilDescrip.setOnAction(e -> {
            TextInputDialog descrip = new TextInputDialog();
            descrip.setTitle("Filtrar por descripción");
            descrip.setHeaderText(null);
            descrip.setContentText("Escribe algo de la descripción:");

            descrip.showAndWait().ifPresent(descripcionBuscada -> {
                if (descripcionBuscada.trim().isEmpty()) {
                    mostrarAlerta("Debes escribir una descripción.");
                    return;
                }

                ObservableList<Tarea> filtradas = FXCollections.observableArrayList();
                for (Tarea tarea : todasLasTareas) {
                    if (tarea.getDescripcion().toLowerCase().contains(descripcionBuscada.toLowerCase())) {
                        filtradas.add(tarea);
                    }
                }

                if (filtradas.isEmpty()) {
                    mostrarAlerta("No se encontraron tareas con esa descripción.");
                } else {
                    tabla.setItems(filtradas);
                    close(); // cerrar la ventana de opciones
                }
            });
        });

        // FILTRAR POR FECHA LÍMITE PRÓXIMA
        btnFilFechaLim.setOnAction(e -> {
            List<Tarea> ordenadas = gestor.ordenarPorFechaLimite();
            ObservableList<Tarea> observableOrdenadas = FXCollections.observableArrayList(ordenadas);
            tabla.setItems(observableOrdenadas);
            close();
        });

        btnFilPriorid.setOnAction(e->{
            List<Tarea> ordenadasPriori = gestor.ordenarPrioridad();
            ObservableList<Tarea> observableOrdenadas = FXCollections.observableArrayList(ordenadasPriori);
            tabla.setItems(observableOrdenadas);
            close();
        });

        btnFilEtiqueta.setOnAction(e -> {
        TextInputDialog dialogo = new TextInputDialog();
        dialogo.setTitle("Filtrar por etiqueta");
        dialogo.setHeaderText(null);
        dialogo.setContentText("Escribe la etiqueta:");

        dialogo.showAndWait().ifPresent(etiquetaBuscada -> {
            if (etiquetaBuscada.trim().isEmpty()) {
                mostrarAlerta("Debes escribir una etiqueta.");
                return;
            }

            ObservableList<Tarea> filtradas = FXCollections.observableArrayList();
            for (Tarea tarea : todasLasTareas) {
                for (String etiqueta : tarea.getEtiquetas()) {
                    if (etiqueta.equalsIgnoreCase(etiquetaBuscada.trim())) {
                        filtradas.add(tarea);
                        break; // ya coincidió, no hace falta seguir con esa tarea
                    }
                }
            }

            if (filtradas.isEmpty()) {
                mostrarAlerta("No se encontraron tareas con esa etiqueta.");
            } else {
                tabla.setItems(filtradas);
                close(); // cerrar la ventana de opciones
            }
        });
    });

        btnFilEstado.setOnAction(e -> {
            TextInputDialog descrip = new TextInputDialog();
            descrip.setTitle("Filtrar por estado");
            descrip.setHeaderText(null);
            descrip.setContentText("Escribe el estado:");
        
            descrip.showAndWait().ifPresent(estadoBuscadoStr -> {
                if (estadoBuscadoStr.trim().isEmpty()) {
                    mostrarAlerta("Debes escribir un estado.");
                    return;
                }
            
                try {
                    Estado estado = Estado.valueOf(estadoBuscadoStr.trim().toUpperCase());
                
                    ObservableList<Tarea> filtradas = FXCollections.observableArrayList();
                    for (Tarea tarea : todasLasTareas) {
                        if (tarea.getEstado().equals(estado)) {
                            filtradas.add(tarea);
                        }
                    }
                
                    if (filtradas.isEmpty()) {
                        mostrarAlerta("No se encontraron tareas con ese estado.");
                    } else {
                        tabla.setItems(filtradas);
                        close(); // cerrar la ventana de opciones
                    }
                
                } catch (IllegalArgumentException ex) {
                    mostrarAlerta("Estado inválido. Usa PENDIENTE, EN_PROCESO o COMPLETADA.");
                }
            });
        });

        // LAYOUT
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 28;");
        layout.getChildren().addAll(
            btnFilTitu, btnFilDescrip, btnFilFechaLim,
            btnFilPriorid, btnFilEtiqueta, btnFilEstado
        );

        Scene escena = new Scene(layout, 220, 330);
        escena.getStylesheets().add(getClass().getResource("styless.css").toExternalForm());
        setScene(escena);

        // Para que se bloquee la ventana principal hasta cerrar esta
        initModality(Modality.APPLICATION_MODAL);
    }

    public void mostrar() {
        showAndWait();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Filtrado");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
