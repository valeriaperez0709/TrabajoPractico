package Interfaz;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Clases.Agencia;
import Clases.Fotografo;
import Clases.DatoInvalido;
import Clases.Duplicado;
import Clases.CapacidadMaxima;
import Clases.ValorInexistente;

public class VentanaFotografos {
    private Agencia agencia;
    private TextArea areaFotografos;

    public VentanaFotografos(Agencia agencia) {
        this.agencia = agencia;
    }

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Gestión de Fotógrafos");
        stage.setWidth(800);
        stage.setHeight(600);

        VBox root = new VBox(15);
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: #f0f0f0;");

        // ==================== TÍTULO ====================
        Label titulo = new Label("Gestión de Fotógrafos");
        titulo.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        // ==================== FORMULARIO ====================
        VBox formulario = crearFormulario();

        // ==================== BOTONES DE ACCIÓN ====================
        HBox botonesAccion = new HBox(10);
        botonesAccion.setAlignment(Pos.CENTER);
        Button btnAgregar = new Button("Agregar Fotógrafo");
        Button btnEliminar = new Button("Eliminar Fotógrafo");
        Button btnLimpiar = new Button("Limpiar");

        btnAgregar.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        btnEliminar.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        btnLimpiar.setStyle("-fx-font-size: 12; -fx-padding: 8;");

        botonesAccion.getChildren().addAll(btnAgregar, btnEliminar, btnLimpiar);

        // ==================== ÁREA DE VISUALIZACIÓN ====================
        areaFotografos = new TextArea();
        areaFotografos.setEditable(false);
        areaFotografos.setWrapText(true);
        areaFotografos.setPrefRowCount(15);

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
                new Label("Fotógrafos registrados:"),
                areaFotografos,
                btnCerrar
        );

        // ==================== ACCIONES DE BOTONES ====================
        btnAgregar.setOnAction(e -> agregarFotografo());
        btnEliminar.setOnAction(e -> eliminarFotografo());
        btnLimpiar.setOnAction(e -> limpiarFormulario());

        // Cargar fotógrafos al abrir
        cargarFotografos();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private VBox crearFormulario() {
        VBox form = new VBox(10);
        form.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-padding: 15; -fx-background-color: white;");

        // Nombre
        HBox hboxNombre = new HBox(10);
        Label lblNombre = new Label("Nombre:");
        lblNombre.setPrefWidth(100);
        TextField txtNombre = new TextField();
        txtNombre.setId("txtNombre");
        hboxNombre.getChildren().addAll(lblNombre, txtNombre);

        // Identificación
        HBox hboxIdentificacion = new HBox(10);
        Label lblIdentificacion = new Label("Identificación:");
        lblIdentificacion.setPrefWidth(100);
        TextField txtIdentificacion = new TextField();
        txtIdentificacion.setId("txtIdentificacion");
        hboxIdentificacion.getChildren().addAll(lblIdentificacion, txtIdentificacion);

        // Número de contacto
        HBox hboxNumero = new HBox(10);
        Label lblNumero = new Label("Contacto:");
        lblNumero.setPrefWidth(100);
        TextField txtNumero = new TextField();
        txtNumero.setId("txtNumero");
        hboxNumero.getChildren().addAll(lblNumero, txtNumero);

        // Código de fotógrafo
        HBox hboxCodigoFotografo = new HBox(10);
        Label lblCodigoFotografo = new Label("Código Fotógrafo:");
        lblCodigoFotografo.setPrefWidth(100);
        TextField txtCodigoFotografo = new TextField();
        txtCodigoFotografo.setId("txtCodigoFotografo");
        hboxCodigoFotografo.getChildren().addAll(lblCodigoFotografo, txtCodigoFotografo);

        // Especialidad
        HBox hboxEspecialidad = new HBox(10);
        Label lblEspecialidad = new Label("Especialidad:");
        lblEspecialidad.setPrefWidth(100);
        ComboBox<String> cmbEspecialidad = new ComboBox<>();
        cmbEspecialidad.getItems().addAll("Retrato", "Eventos", "Moda", "Producto", "Naturaleza", "Deportes");
        cmbEspecialidad.setId("cmbEspecialidad");
        hboxEspecialidad.getChildren().addAll(lblEspecialidad, cmbEspecialidad);

        // Años de experiencia
        HBox hboxExperiencia = new HBox(10);
        Label lblExperiencia = new Label("Años Experiencia:");
        lblExperiencia.setPrefWidth(100);
        TextField txtExperiencia = new TextField();
        txtExperiencia.setId("txtExperiencia");
        hboxExperiencia.getChildren().addAll(lblExperiencia, txtExperiencia);

        // Tarifa por evento
        HBox hboxTarifa = new HBox(10);
        Label lblTarifa = new Label("Tarifa por Evento:");
        lblTarifa.setPrefWidth(100);
        TextField txtTarifa = new TextField();
        txtTarifa.setId("txtTarifa");
        hboxTarifa.getChildren().addAll(lblTarifa, txtTarifa);

        form.getChildren().addAll(
                hboxNombre,
                hboxIdentificacion,
                hboxNumero,
                hboxCodigoFotografo,
                hboxEspecialidad,
                hboxExperiencia,
                hboxTarifa
        );

        return form;
    }

    private void agregarFotografo() {
        try {
            TextField txtNombre = (TextField) areaFotografos.getScene().getRoot().lookup("#txtNombre");
            TextField txtIdentificacion = (TextField) areaFotografos.getScene().getRoot().lookup("#txtIdentificacion");
            TextField txtNumero = (TextField) areaFotografos.getScene().getRoot().lookup("#txtNumero");
            TextField txtCodigoFotografo = (TextField) areaFotografos.getScene().getRoot().lookup("#txtCodigoFotografo");
            ComboBox<String> cmbEspecialidad = (ComboBox<String>) areaFotografos.getScene().getRoot().lookup("#cmbEspecialidad");
            TextField txtExperiencia = (TextField) areaFotografos.getScene().getRoot().lookup("#txtExperiencia");
            TextField txtTarifa = (TextField) areaFotografos.getScene().getRoot().lookup("#txtTarifa");

            String nombre = txtNombre.getText();
            int identificacion = Integer.parseInt(txtIdentificacion.getText());
            int numero = Integer.parseInt(txtNumero.getText());
            int codigoFotografo = Integer.parseInt(txtCodigoFotografo.getText());
            String especialidad = cmbEspecialidad.getValue();
            float experiencia = Float.parseFloat(txtExperiencia.getText());
            float tarifa = Float.parseFloat(txtTarifa.getText());

            if (nombre.isEmpty() || especialidad == null) {
                mostrarAlerta("Error", "Por favor completa todos los campos");
                return;
            }

            Fotografo fotografo = new Fotografo(nombre, identificacion, numero, codigoFotografo, especialidad, experiencia, tarifa);
            agencia.agregarFotografo(fotografo);
            agencia.guardar();

            mostrarAlerta("Éxito", "Fotógrafo agregado correctamente");
            limpiarFormulario();
            cargarFotografos();
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Verifica que los números sean válidos");
        } catch (DatoInvalido | Duplicado | CapacidadMaxima e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    private void eliminarFotografo() {
        try {
            TextField txtCodigoFotografo = (TextField) areaFotografos.getScene().getRoot().lookup("#txtCodigoFotografo");
            int codigo = Integer.parseInt(txtCodigoFotografo.getText());

            Fotografo fotografo = agencia.buscarFotografoPorCodigo(codigo);
            if (fotografo == null) {
                mostrarAlerta("Error", "Fotógrafo no encontrado");
                return;
            }

            agencia.eliminarFotografo(fotografo);
            agencia.guardar();

            mostrarAlerta("Éxito", "Fotógrafo eliminado correctamente");
            limpiarFormulario();
            cargarFotografos();
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Ingresa un código válido");
        } catch (ValorInexistente | DatoInvalido e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    private void limpiarFormulario() {
        TextField txtNombre = (TextField) areaFotografos.getScene().getRoot().lookup("#txtNombre");
        TextField txtIdentificacion = (TextField) areaFotografos.getScene().getRoot().lookup("#txtIdentificacion");
        TextField txtNumero = (TextField) areaFotografos.getScene().getRoot().lookup("#txtNumero");
        TextField txtCodigoFotografo = (TextField) areaFotografos.getScene().getRoot().lookup("#txtCodigoFotografo");
        ComboBox<String> cmbEspecialidad = (ComboBox<String>) areaFotografos.getScene().getRoot().lookup("#cmbEspecialidad");
        TextField txtExperiencia = (TextField) areaFotografos.getScene().getRoot().lookup("#txtExperiencia");
        TextField txtTarifa = (TextField) areaFotografos.getScene().getRoot().lookup("#txtTarifa");

        txtNombre.clear();
        txtIdentificacion.clear();
        txtNumero.clear();
        txtCodigoFotografo.clear();
        cmbEspecialidad.setValue(null);
        txtExperiencia.clear();
        txtTarifa.clear();
    }

    private void cargarFotografos() {
        areaFotografos.clear();
        Fotografo[] fotografos = agencia.getFotografos();
        int numFotografos = agencia.getNumFotografos();

        if (numFotografos == 0) {
            areaFotografos.setText("No hay fotógrafos registrados.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numFotografos; i++) {
            if (fotografos[i] != null) {
                sb.append(fotografos[i].toString()).append("\n\n");
            }
        }
        areaFotografos.setText(sb.toString());
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}