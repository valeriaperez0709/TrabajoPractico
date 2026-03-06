package Interfaz;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Clases.Agencia;
import Clases.Evento;
import Clases.EventoPublico;
import Clases.EventoPrivado;
import Clases.Lugar;
import Clases.DatoInvalido;
import Clases.Duplicado;
import Clases.CapacidadMaxima;
import Clases.ValorInexistente;
import java.time.LocalDate;
import java.util.Date;
import java.util.Calendar;

public class VentanaEventos {
    private Agencia agencia;
    private TextArea areaEventos;

    public VentanaEventos(Agencia agencia) {
        this.agencia = agencia;
    }

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Gestión de Eventos");
        stage.setWidth(900);
        stage.setHeight(650);

        VBox root = new VBox(15);
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: #f0f0f0;");

        // ==================== TÍTULO ====================
        Label titulo = new Label("Gestión de Eventos");
        titulo.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        // ==================== TIPO DE EVENTO ====================
        HBox tipoEventoBox = new HBox(10);
        Label lblTipoEvento = new Label("Tipo de Evento:");
        ComboBox<String> cmbTipoEvento = new ComboBox<>();
        cmbTipoEvento.getItems().addAll("Público", "Privado");
        cmbTipoEvento.setValue("Público");
        cmbTipoEvento.setId("cmbTipoEvento");
        tipoEventoBox.getChildren().addAll(lblTipoEvento, cmbTipoEvento);

        // ==================== FORMULARIO ====================
        VBox formulario = crearFormulario();

        // ==================== BOTONES DE ACCIÓN ====================
        HBox botonesAccion = new HBox(10);
        botonesAccion.setAlignment(Pos.CENTER);
        Button btnAgregar = new Button("Agregar Evento");
        Button btnEliminar = new Button("Eliminar Evento");
        Button btnLimpiar = new Button("Limpiar");

        btnAgregar.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        btnEliminar.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        btnLimpiar.setStyle("-fx-font-size: 12; -fx-padding: 8;");

        botonesAccion.getChildren().addAll(btnAgregar, btnEliminar, btnLimpiar);

        // ==================== ÁREA DE VISUALIZACIÓN ====================
        areaEventos = new TextArea();
        areaEventos.setEditable(false);
        areaEventos.setWrapText(true);
        areaEventos.setPrefRowCount(12);

        // ==================== BOTÓN CERRAR ====================
        Button btnCerrar = new Button("Cerrar Ventana");
        btnCerrar.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        btnCerrar.setOnAction(e -> stage.close());

        // ==================== LAYOUT PRINCIPAL ====================
        root.getChildren().addAll(
                titulo,
                new Separator(),
                tipoEventoBox,
                formulario,
                botonesAccion,
                new Label("Eventos registrados:"),
                areaEventos,
                btnCerrar
        );

        // ==================== ACCIONES DE BOTONES ====================
        btnAgregar.setOnAction(e -> agregarEvento());
        btnEliminar.setOnAction(e -> eliminarEvento());
        btnLimpiar.setOnAction(e -> limpiarFormulario());

        // Cargar eventos al abrir
        cargarEventos();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private VBox crearFormulario() {
        VBox form = new VBox(10);
        form.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-padding: 15; -fx-background-color: white;");

        // Nombre del evento
        HBox hboxNombre = new HBox(10);
        Label lblNombre = new Label("Nombre Evento:");
        lblNombre.setPrefWidth(120);
        TextField txtNombre = new TextField();
        txtNombre.setId("txtNombre");
        hboxNombre.getChildren().addAll(lblNombre, txtNombre);

        // Fecha
        HBox hboxFecha = new HBox(10);
        Label lblFecha = new Label("Fecha:");
        lblFecha.setPrefWidth(120);
        DatePicker dpFecha = new DatePicker();
        dpFecha.setId("dpFecha");
        hboxFecha.getChildren().addAll(lblFecha, dpFecha);

        // Lugar
        HBox hboxLugar = new HBox(10);
        Label lblLugar = new Label("Lugar:");
        lblLugar.setPrefWidth(120);
        ComboBox<String> cmbLugar = new ComboBox<>();
        cmbLugar.setId("cmbLugar");
        hboxLugar.getChildren().addAll(lblLugar, cmbLugar);

        // Máximo de modelos
        HBox hboxMaxModelos = new HBox(10);
        Label lblMaxModelos = new Label("Máx Modelos:");
        lblMaxModelos.setPrefWidth(120);
        TextField txtMaxModelos = new TextField("5");
        txtMaxModelos.setId("txtMaxModelos");
        hboxMaxModelos.getChildren().addAll(lblMaxModelos, txtMaxModelos);

        // Campos específicos para evento público
        HBox hboxCapacidad = new HBox(10);
        Label lblCapacidad = new Label("Capacidad:");
        lblCapacidad.setPrefWidth(120);
        TextField txtCapacidad = new TextField();
        txtCapacidad.setId("txtCapacidad");
        hboxCapacidad.getChildren().addAll(lblCapacidad, txtCapacidad);

        HBox hboxPrecioEntrada = new HBox(10);
        Label lblPrecioEntrada = new Label("Precio Entrada:");
        lblPrecioEntrada.setPrefWidth(120);
        TextField txtPrecioEntrada = new TextField();
        txtPrecioEntrada.setId("txtPrecioEntrada");
        hboxPrecioEntrada.getChildren().addAll(lblPrecioEntrada, txtPrecioEntrada);

        // Campos específicos para evento privado
        HBox hboxCliente = new HBox(10);
        Label lblCliente = new Label("Cliente:");
        lblCliente.setPrefWidth(120);
        TextField txtCliente = new TextField();
        txtCliente.setId("txtCliente");
        hboxCliente.getChildren().addAll(lblCliente, txtCliente);

        HBox hboxConfidencialidad = new HBox(10);
        Label lblConfidencialidad = new Label("Nivel Confidencialidad:");
        lblConfidencialidad.setPrefWidth(120);
        ComboBox<Integer> cmbConfidencialidad = new ComboBox<>();
        cmbConfidencialidad.getItems().addAll(1, 2, 3, 4, 5);
        cmbConfidencialidad.setId("cmbConfidencialidad");
        hboxConfidencialidad.getChildren().addAll(lblConfidencialidad, cmbConfidencialidad);

        HBox hboxPresupuesto = new HBox(10);
        Label lblPresupuesto = new Label("Presupuesto:");
        lblPresupuesto.setPrefWidth(120);
        TextField txtPresupuesto = new TextField();
        txtPresupuesto.setId("txtPresupuesto");
        hboxPresupuesto.getChildren().addAll(lblPresupuesto, txtPresupuesto);

        form.getChildren().addAll(
                hboxNombre,
                hboxFecha,
                hboxLugar,
                hboxMaxModelos,
                hboxCapacidad,
                hboxPrecioEntrada,
                hboxCliente,
                hboxConfidencialidad,
                hboxPresupuesto
        );

        // Cargar lugares en el combobox
        cargarLugaresEnCombo(cmbLugar);

        return form;
    }

    private void cargarLugaresEnCombo(ComboBox<String> cmb) {
        cmb.getItems().clear();
        Lugar[] lugares = agencia.getLugares();
        if (lugares == null || lugares.length == 0) {
            cmb.getItems().add("No hay lugares disponibles");
            return;
        }
        for (int i = 0; i < lugares.length && lugares[i] != null; i++) {
            cmb.getItems().add(lugares[i].getNombreDelLugar());
        }
    }

    private void agregarEvento() {
        try {
            TextField txtNombre = (TextField) areaEventos.getScene().getRoot().lookup("#txtNombre");
            DatePicker dpFecha = (DatePicker) areaEventos.getScene().getRoot().lookup("#dpFecha");
            ComboBox<String> cmbLugar = (ComboBox<String>) areaEventos.getScene().getRoot().lookup("#cmbLugar");
            TextField txtMaxModelos = (TextField) areaEventos.getScene().getRoot().lookup("#txtMaxModelos");
            ComboBox<String> cmbTipoEvento = (ComboBox<String>) areaEventos.getScene().getRoot().lookup("#cmbTipoEvento");

            String nombre = txtNombre.getText();
            LocalDate localDate = dpFecha.getValue();
            String lugarNombre = cmbLugar.getValue();
            int maxModelos = Integer.parseInt(txtMaxModelos.getText());
            String tipoEvento = cmbTipoEvento.getValue();

            if (nombre.isEmpty() || localDate == null || lugarNombre == null) {
                mostrarAlerta("Error", "Por favor completa los campos requeridos");
                return;
            }

            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.set(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
            Date fecha = cal.getTime();
            Lugar lugar = agencia.buscarLugarPorNombre(lugarNombre);

            if (lugar == null) {
                mostrarAlerta("Error", "Lugar no encontrado");
                return;
            }

            if (tipoEvento.equals("Público")) {
                TextField txtCapacidad = (TextField) areaEventos.getScene().getRoot().lookup("#txtCapacidad");
                TextField txtPrecioEntrada = (TextField) areaEventos.getScene().getRoot().lookup("#txtPrecioEntrada");

                int capacidad = Integer.parseInt(txtCapacidad.getText());
                float precioEntrada = Float.parseFloat(txtPrecioEntrada.getText());

                EventoPublico evento = new EventoPublico(nombre, fecha, lugar, maxModelos, capacidad, precioEntrada, 20);
                agencia.agregarEventos(evento);
            } else {
                TextField txtCliente = (TextField) areaEventos.getScene().getRoot().lookup("#txtCliente");
                ComboBox<Integer> cmbConfidencialidad = (ComboBox<Integer>) areaEventos.getScene().getRoot().lookup("#cmbConfidencialidad");
                TextField txtPresupuesto = (TextField) areaEventos.getScene().getRoot().lookup("#txtPresupuesto");

                String cliente = txtCliente.getText();
                int confidencialidad = cmbConfidencialidad.getValue();
                float presupuesto = Float.parseFloat(txtPresupuesto.getText());

                EventoPrivado evento = new EventoPrivado(nombre, fecha, lugar, maxModelos, cliente, confidencialidad, presupuesto);
                agencia.agregarEventos(evento);
            }

            agencia.guardar();
            mostrarAlerta("Éxito", "Evento agregado correctamente");
            limpiarFormulario();
            cargarEventos();
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Verifica que los números sean válidos");
        } catch (DatoInvalido | Duplicado | CapacidadMaxima e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    private void eliminarEvento() {
        try {
            TextField txtNombre = (TextField) areaEventos.getScene().getRoot().lookup("#txtNombre");
            String nombre = txtNombre.getText();

            Evento evento = agencia.buscarEventoPorNombre(nombre);
            if (evento == null) {
                mostrarAlerta("Error", "Evento no encontrado");
                return;
            }

            agencia.eliminarEvento(evento);
            agencia.guardar();

            mostrarAlerta("Éxito", "Evento eliminado correctamente");
            limpiarFormulario();
            cargarEventos();
        } catch (ValorInexistente | DatoInvalido e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    private void limpiarFormulario() {
        TextField txtNombre = (TextField) areaEventos.getScene().getRoot().lookup("#txtNombre");
        DatePicker dpFecha = (DatePicker) areaEventos.getScene().getRoot().lookup("#dpFecha");
        ComboBox<String> cmbLugar = (ComboBox<String>) areaEventos.getScene().getRoot().lookup("#cmbLugar");
        TextField txtMaxModelos = (TextField) areaEventos.getScene().getRoot().lookup("#txtMaxModelos");
        TextField txtCapacidad = (TextField) areaEventos.getScene().getRoot().lookup("#txtCapacidad");
        TextField txtPrecioEntrada = (TextField) areaEventos.getScene().getRoot().lookup("#txtPrecioEntrada");
        TextField txtCliente = (TextField) areaEventos.getScene().getRoot().lookup("#txtCliente");
        ComboBox<Integer> cmbConfidencialidad = (ComboBox<Integer>) areaEventos.getScene().getRoot().lookup("#cmbConfidencialidad");
        TextField txtPresupuesto = (TextField) areaEventos.getScene().getRoot().lookup("#txtPresupuesto");

        txtNombre.clear();
        dpFecha.setValue(null);
        cmbLugar.setValue(null);
        txtMaxModelos.clear();
        txtCapacidad.clear();
        txtPrecioEntrada.clear();
        txtCliente.clear();
        cmbConfidencialidad.setValue(null);
        txtPresupuesto.clear();
    }

    private void cargarEventos() {
        areaEventos.clear();
        Evento[] eventos = agencia.getEventos();
        int numEventos = agencia.getNumEventos();

        if (numEventos == 0) {
            areaEventos.setText("No hay eventos registrados.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numEventos; i++) {
            if (eventos[i] != null) {
                sb.append(eventos[i].toString()).append("\n\n");
            }
        }
        areaEventos.setText(sb.toString());
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}