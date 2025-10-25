package vista;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import control.GestorTareas;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import modelo.Estado;
import modelo.Tarea;

public class AppGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Organizador de Tareas :)");
        

        //crear un TableView
        TableView<Tarea> tablaTareas = new TableView<>();

        //columnas
        TableColumn<Tarea, String> tituloTarea = new TableColumn<>("Titulo");
        tituloTarea.setCellValueFactory(new PropertyValueFactory<>("titulo"));

        TableColumn<Tarea, String> descripcionTarea = new TableColumn<>("Descripcion");
        descripcionTarea.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        TableColumn<Tarea, LocalDate> fechaLimiteTarea = new TableColumn<>("Fecha Limite");
        fechaLimiteTarea.setCellValueFactory(new PropertyValueFactory<>("fechaLimite"));

        TableColumn<Tarea, Integer> prioridadTarea = new TableColumn<>("Prioridad");
        prioridadTarea.setCellValueFactory(new PropertyValueFactory<>("prioridad"));

        TableColumn<Tarea, String> etiquetasTarea = new TableColumn<>("Etiquetas");
        etiquetasTarea.setCellValueFactory(new PropertyValueFactory<>("etiquetas"));

        TableColumn<Tarea, Estado> estadoTarea = new TableColumn<>("Estado de la Tarea");
        estadoTarea.setCellValueFactory(new PropertyValueFactory<>("estado"));

        tablaTareas.getColumns().addAll(tituloTarea,descripcionTarea,fechaLimiteTarea
        ,prioridadTarea,etiquetasTarea,estadoTarea);

        GestorTareas gestor = new GestorTareas<>();
        ObservableList<Tarea> datos = FXCollections.observableArrayList(gestor.listarTodas());

        tablaTareas.setItems(datos);

        //botones abajo

        Button btnAgregarTarea = new Button("Agregar Tarea");
        Button btnEliminarTarea = new Button("Eliminar Tarea");
        Button btnEditarTarea = new Button("Editar Tarea");
        Button btnFiltrarTareas = new Button("FIltrar Tareas");
        Button btnMostrarTodas = new Button("Mostrar Todas");


        HBox boxAcciones = new HBox(110, btnAgregarTarea, btnEliminarTarea, btnEditarTarea, btnFiltrarTareas, btnMostrarTodas);
        boxAcciones.setStyle("-fx-padding: 10; -fx-alignment: center;");

        //Organizar interfaz
        VBox root = new VBox(10,tablaTareas,boxAcciones);
        root.setStyle("-fx-padding: auto;");
        VBox.setVgrow(tablaTareas, Priority.ALWAYS);
        tablaTareas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);



        btnAgregarTarea.setOnAction(e ->{
            System.out.println("Agregando HW");
            VentanaAgregarTarea ventana = new VentanaAgregarTarea(datos,gestor);
            ventana.mostrar();;
        });

        btnEliminarTarea.setOnAction(e->{
            System.out.println("Eliminando tarea");
            Tarea tareaSeleccionada = tablaTareas.getSelectionModel().getSelectedItem();
            if(tareaSeleccionada == null){
                mostrarAlerta("No seleccionaste algua Tarea");
                return;
            }
            Alert alerta = new Alert(AlertType.CONFIRMATION);
            alerta.setTitle("Confirmar la eliminacion.!!!!");
            alerta.setHeaderText(null);
            //valida la respuesta de alerta
            alerta.setContentText("Eliminar la tarea: " + tareaSeleccionada.getTitulo());
            Optional<ButtonType> resultado = alerta.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                // Aquí sí se confirma la eliminación
                gestor.eliminarTarea(tareaSeleccionada);
                tablaTareas.getItems().remove(tareaSeleccionada);
                mostrarAlerta("Tarea eliminada correctamente.");
            }

        });

        btnEditarTarea.setOnAction(e->{
            System.out.println("holaaa");
            Tarea tareaSeleccionada = tablaTareas.getSelectionModel().getSelectedItem();
            if(tareaSeleccionada == null){
                mostrarAlerta("No seleccionaste alguna Tarea");
                return;
            }
            VentanaEditar ventana = new VentanaEditar(datos, gestor, tareaSeleccionada);
            ventana.mostrar();
            tablaTareas.refresh(); // para que se reflejen los cambios    
        });

        btnFiltrarTareas.setOnAction(e->{
            VentanaOpcionFiltrar ventana = new VentanaOpcionFiltrar(datos,tablaTareas,gestor);
            ventana.mostrar();
        });

        btnMostrarTodas.setOnAction(e -> {
        ObservableList<Tarea> todas = FXCollections.observableArrayList(gestor.listarTodas());
        tablaTareas.setItems(todas);
        });

        btnEliminarTarea.setId("botonEliminar");
        btnMostrarTodas.setId("botonMostrarTodas");





        Scene scene = new Scene(root, 1150, 400);
        scene.getStylesheets().add(getClass().getResource("styless.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void mostrarAlerta(String mensaje) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Error de validación");
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.showAndWait();
}


    public static void main(String[] args) {
        launch(args); // esto arranca la GUI
    }
}
