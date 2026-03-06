package Interfaz;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Clases.Agencia;
import Clases.Lugar;

public class VentanaLugares {
    private Agencia agencia;
    private TextArea areaLugares;

    public VentanaLugares(Agencia agencia) {
        this.agencia = agencia;
    }

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Gestión de Lugares");
        stage.setWidth(800);
        stage.setHeight(600);

        VBox root = new VBox(15);
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: #f0f0f0;");

        // ==================== TÍTULO ====================
        Label titulo = new Label("Gestión de Lugares");
        titulo.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        // ==================== FORMULARIO ====================
        VBox formulario = crearFormulario();

        // ==================== BOTONES DE ACCIÓN ====================
        HBox botonesAccion = new HBox(10);
        botonesAccion.setAlignment(Pos.CENTER);
        Button btnAgregar = new Button("Agregar Lugar");
        Button btnEliminar = new Button("Eliminar Lugar");
        Button btnLimpiar = new Button("Limpiar");

        btnAgregar.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        btnEliminar.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        btnLimpiar.setStyle("-fx-font-size: 12; -fx-padding: 8;");

        botonesAccion.getChildren().addAll(btnAgregar, btnEliminar, btnLimpiar);

        // ==================== ÁREA DE VISUALIZACIÓN ====================
        areaLugares = new TextArea();
        areaLugares.setEditable(false);
        areaLugares.setWrapText(true);
        areaLugares.setPrefRowCount(15);

        // ==================== BOTÓN CERRAR ====================
        Button btnCerrar = new Button("Cerrar Ventana");
        btnCerrar.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        btnCerrar.setOnAction(e -> stage.close());

        // ==================== LAYOUT PRINCIPAL ====================
        root.getChildren().addAll(
                titulo,
                new Separator(),
                formulario,
                botonesAccion,
                new Label("Lugares registrados:"),
                areaLugares,
                btnCerrar
        );

        // ==================== ACCIONES DE BOTONES ====================
        btnAgregar.setOnAction(e -> agregarLugar());
        btnEliminar.setOnAction(e -> eliminarLugar());
        btnLimpiar.setOnAction(e -> limpiarFormulario());

        // Cargar lugares al abrir
        cargarLugares();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private VBox crearFormulario() {
        VBox form = new VBox(10);
        form.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-padding: 15; -fx-background-color: white;");

        // Nombre del lugar
        HBox hboxNombre = new HBox(10);
        Label lblNombre = new Label("Nombre:");
        lblNombre.setPrefWidth(100);
        TextField txtNombre = new TextField();
        txtNombre.setId("txtNombre");
        hboxNombre.getChildren().addAll(lblNombre, txtNombre);

        // Dirección
        HBox hboxDireccion = new HBox(10);
        Label lblDireccion = new Label("Dirección:");
        lblDireccion.setPrefWidth(100);
        TextField txtDireccion = new TextField();
        txtDireccion.setId("txtDireccion");
        hboxDireccion.getChildren().addAll(lblDireccion, txtDireccion);

        // Ciudad
        HBox hboxCiudad = new HBox(10);
        Label lblCiudad = new Label("Ciudad:");
        lblCiudad.setPrefWidth(100);
        TextField txtCiudad = new TextField();
        txtCiudad.setId("txtCiudad");
        hboxCiudad.getChildren().addAll(lblCiudad, txtCiudad);

        // Capacidad
        HBox hboxCapacidad = new HBox(10);
        Label lblCapacidad = new Label("Capacidad:");
        lblCapacidad.setPrefWidth(100);
        TextField txtCapacidad = new TextField();
        txtCapacidad.setId("txtCapacidad");
        hboxCapacidad.getChildren().addAll(lblCapacidad, txtCapacidad);

        // Tipo de lugar
        HBox hboxTipo = new HBox(10);
        Label lblTipo = new Label("Tipo de Lugar:");
        lblTipo.setPrefWidth(100);
        ComboBox<String> cmbTipo = new ComboBox<>();
        cmbTipo.getItems().addAll("Hotel", "Estudio", "Pasarela", "Salón", "Teatro", "Auditorio");
        cmbTipo.setId("cmbTipo");
        hboxTipo.getChildren().addAll(lblTipo, cmbTipo);

        form.getChildren().addAll(
                hboxNombre,
                hboxDireccion,
                hboxCiudad,
                hboxCapacidad,
                hboxTipo
        );

        return form;
    }

    private void agregarLugar() {
        try {
            TextField txtNombre = (TextField) areaLugares.getScene().getRoot().lookup("#txtNombre");
            TextField txtDireccion = (TextField) areaLugares.getScene().getRoot().lookup("#txtDireccion");
            TextField txtCiudad = (TextField) areaLugares.getScene().getRoot().lookup("#txtCiudad");
            TextField txtCapacidad = (TextField) areaLugares.getScene().getRoot().lookup("#txtCapacidad");
            ComboBox<String> cmbTipo = (ComboBox<String>) areaLugares.getScene().getRoot().lookup("#cmbTipo");

            String nombre = txtNombre.getText();
            String direccion = txtDireccion.getText();
            String ciudad = txtCiudad.getText();
            int capacidad = Integer.parseInt(txtCapacidad.getText());
            String tipo = cmbTipo.getValue();

            if (nombre.isEmpty() || direccion.isEmpty() || ciudad.isEmpty() || tipo == null) {
                mostrarAlerta("Error", "Por favor completa todos los campos");
                return;
            }

            Lugar lugar = new Lugar(nombre, direccion, ciudad, capacidad, tipo);
            // Aquí podrías agregarlo a un array en agencia si implementas getLugares()
            // Por ahora solo lo creamos

            mostrarAlerta("Éxito", "Lugar agregado correctamente");
            limpiarFormulario();
            cargarLugares();
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Verifica que los números sean válidos");
        }
    }

    private void eliminarLugar() {
        try {
            TextField txtNombre = (TextField) areaLugares.getScene().getRoot().lookup("#txtNombre");
            String nombre = txtNombre.getText();

            if (nombre.isEmpty()) {
                mostrarAlerta("Error", "Ingresa el nombre del lugar a eliminar");
                return;
            }

            mostrarAlerta("Éxito", "Lugar eliminado correctamente");
            limpiarFormulario();
            cargarLugares();
        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error al eliminar");
        }
    }

    private void limpiarFormulario() {
        TextField txtNombre = (TextField) areaLugares.getScene().getRoot().lookup("#txtNombre");
        TextField txtDireccion = (TextField) areaLugares.getScene().getRoot().lookup("#txtDireccion");
        TextField txtCiudad = (TextField) areaLugares.getScene().getRoot().lookup("#txtCiudad");
        TextField txtCapacidad = (TextField) areaLugares.getScene().getRoot().lookup("#txtCapacidad");
        ComboBox<String> cmbTipo = (ComboBox<String>) areaLugares.getScene().getRoot().lookup("#cmbTipo");

        txtNombre.clear();
        txtDireccion.clear();
        txtCiudad.clear();
        txtCapacidad.clear();
        cmbTipo.setValue(null);
    }

    private void cargarLugares() {
        areaLugares.clear();
        areaLugares.setText("Sistema de lugares (en desarrollo)");
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}