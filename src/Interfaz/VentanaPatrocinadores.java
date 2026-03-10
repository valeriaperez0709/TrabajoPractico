package Interfaz;

import Clases.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VentanaPatrocinadores {
    private Agencia agencia;
    private EventoPublico evento;
    private TextArea areaPatrocinadores;

    public VentanaPatrocinadores(Agencia agencia, Evento evento) {
        this.agencia = agencia;
        this.evento = (EventoPublico) evento;
    }

    private void mostrarTotalPatrocinios() {
        StringBuilder sb = new StringBuilder();
        sb.append("📋 LISTA DE PATROCINADORES\n");
        sb.append("════════════════════════════════════════\n\n");

        Patrocinador[] patrocinadores = evento.getPatrocinadores();
        int numPatrocinadores = evento.getNumPatrocinadores();

        if (numPatrocinadores == 0) {
            sb.append("No hay patrocinadores registrados.\n");
        } else {
            double totalAportes = 0;
            for (int i = 0; i < numPatrocinadores; i++) {
                if (patrocinadores[i] != null) {
                    Patrocinador p = patrocinadores[i];
                    sb.append((i + 1)).append(". ").append(p.getNombre())
                            .append(" (").append(p.getTipoEmpresa()).append(")")
                            .append("\n   Aporte: $").append(String.format("%.2f", p.getAporteEconomico()))
                            .append("\n   Contacto: ").append(p.getContacto())
                            .append("\n\n");
                    totalAportes += p.getAporteEconomico();
                }
            }
            sb.append("════════════════════════════════════════\n");
            sb.append("💰 TOTAL APORTES: $").append(String.format("%.2f", totalAportes)).append("\n");
            sb.append("════════════════════════════════════════\n");
        }

        // ✅ USAR listaPatrocinadores() (imprime en consola)
        System.out.println("\n" + sb.toString());
        evento.listaPatrocinadores();  // Método de EventoPublico que lista en consola

        mostrarAlerta("📋 Reporte de Patrocinadores", "Ver consola para detalles completos.\n\n" + sb.toString());
    }

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Gestión de Patrocinadores - " + evento.getNombreDeEvento());
        stage.setWidth(800);
        stage.setHeight(600);

        VBox root = new VBox(15);
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: #f0f0f0;");

        Label titulo = new Label("💼 Gestión de Patrocinadores - " + evento.getNombreDeEvento());
        titulo.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        VBox formulario = crearFormulario();

        HBox botonesAccion = new HBox(10);
        botonesAccion.setAlignment(Pos.CENTER);
        Button btnAgregar = new Button("✅ Agregar Patrocinador");
        Button btnEliminar = new Button("❌ Eliminar Patrocinador");
        Button btnLimpiar = new Button("🗑️ Limpiar");
        Button btnCalcularTotal = new Button("💰 Calcular Total");

        btnAgregar.setStyle("-fx-font-size: 11; -fx-padding: 8;");
        btnEliminar.setStyle("-fx-font-size: 11; -fx-padding: 8;");
        btnLimpiar.setStyle("-fx-font-size: 11; -fx-padding: 8;");
        btnCalcularTotal.setStyle("-fx-font-size: 11; -fx-padding: 8;");

        botonesAccion.getChildren().addAll(btnAgregar, btnEliminar, btnCalcularTotal, btnLimpiar);

        areaPatrocinadores = new TextArea();
        areaPatrocinadores.setEditable(false);
        areaPatrocinadores.setWrapText(true);
        areaPatrocinadores.setPrefRowCount(12);

        Button btnCerrar = new Button("Cerrar Ventana");
        btnCerrar.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        btnCerrar.setOnAction(e -> stage.close());

        root.getChildren().addAll(
                titulo,
                new Separator(),
                formulario,
                botonesAccion,
                new Label("📋 Patrocinadores registrados:"),
                areaPatrocinadores,
                btnCerrar
        );



        btnAgregar.setOnAction(e -> agregarPatrocinador());
        btnEliminar.setOnAction(e -> eliminarPatrocinador());
        btnCalcularTotal.setOnAction(e -> mostrarTotalPatrocinios());
        btnLimpiar.setOnAction(e -> limpiarFormulario());

        cargarPatrocinadores();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private VBox crearFormulario() {
        VBox form = new VBox(10);
        form.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-padding: 15; -fx-background-color: white;");

        HBox hboxCodigo = new HBox(10);
        Label lblCodigo = new Label("Código:");
        lblCodigo.setPrefWidth(100);
        TextField txtCodigo = new TextField();
        txtCodigo.setId("txtCodigo");
        hboxCodigo.getChildren().addAll(lblCodigo, txtCodigo);

        HBox hboxNombre = new HBox(10);
        Label lblNombre = new Label("Nombre:");
        lblNombre.setPrefWidth(100);
        TextField txtNombre = new TextField();
        txtNombre.setId("txtNombre");
        hboxNombre.getChildren().addAll(lblNombre, txtNombre);

        HBox hboxTipoEmpresa = new HBox(10);
        Label lblTipoEmpresa = new Label("Tipo Empresa:");
        lblTipoEmpresa.setPrefWidth(100);
        ComboBox<String> cmbTipoEmpresa = new ComboBox<>();
        cmbTipoEmpresa.getItems().addAll("Bebidas", "Ropa", "Electrónica", "Tecnología", "Belleza", "Otros");
        cmbTipoEmpresa.setId("cmbTipoEmpresa");
        hboxTipoEmpresa.getChildren().addAll(lblTipoEmpresa, cmbTipoEmpresa);

        HBox hboxContacto = new HBox(10);
        Label lblContacto = new Label("Contacto:");
        lblContacto.setPrefWidth(100);
        TextField txtContacto = new TextField();
        txtContacto.setId("txtContacto");
        hboxContacto.getChildren().addAll(lblContacto, txtContacto);

        HBox hboxAporte = new HBox(10);
        Label lblAporte = new Label("Aporte Económico:");
        lblAporte.setPrefWidth(100);
        TextField txtAporte = new TextField();
        txtAporte.setId("txtAporte");
        hboxAporte.getChildren().addAll(lblAporte, txtAporte);

        form.getChildren().addAll(
                hboxCodigo,
                hboxNombre,
                hboxTipoEmpresa,
                hboxContacto,
                hboxAporte
        );

        return form;
    }

    private void agregarPatrocinador() {
        try {
            TextField txtCodigo = (TextField) areaPatrocinadores.getScene().getRoot().lookup("#txtCodigo");
            TextField txtNombre = (TextField) areaPatrocinadores.getScene().getRoot().lookup("#txtNombre");
            ComboBox<String> cmbTipoEmpresa = (ComboBox<String>) areaPatrocinadores.getScene().getRoot().lookup("#cmbTipoEmpresa");
            TextField txtContacto = (TextField) areaPatrocinadores.getScene().getRoot().lookup("#txtContacto");
            TextField txtAporte = (TextField) areaPatrocinadores.getScene().getRoot().lookup("#txtAporte");

            int codigo = Integer.parseInt(txtCodigo.getText());
            String nombre = txtNombre.getText();
            String tipoEmpresa = cmbTipoEmpresa.getValue();
            String contacto = txtContacto.getText();
            String aporteStr = txtAporte.getText().replace(",", ".");
            double aporte = Double.parseDouble(aporteStr);

            if (nombre.isEmpty() || tipoEmpresa == null || contacto.isEmpty()) {
                mostrarAlerta("Error", "Por favor completa todos los campos");
                return;
            }

            Patrocinador patrocinador = new Patrocinador(codigo, nombre, tipoEmpresa, contacto, aporte);
            evento.agregarPatrocinador(patrocinador);
            Persistencia.guardar(agencia);

            mostrarAlerta("✅ Éxito", "Patrocinador agregado correctamente");
            limpiarFormulario();
            cargarPatrocinadores();
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Verifica que los números sean válidos");
        } catch (DatoInvalido | Duplicado | CapacidadMaxima e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    private void eliminarPatrocinador() {
        try {
            TextField txtCodigo = (TextField) areaPatrocinadores.getScene().getRoot().lookup("#txtCodigo");
            int codigo = Integer.parseInt(txtCodigo.getText());

            Patrocinador patrocinador = evento.buscarPatrocinadorPorCodigo(codigo);
            if (patrocinador == null) {
                mostrarAlerta("Error", "Patrocinador no encontrado");
                return;
            }

            evento.eliminarPatrocinador(patrocinador);
            Persistencia.guardar(agencia);

            mostrarAlerta("✅ Éxito", "Patrocinador eliminado correctamente");
            limpiarFormulario();
            cargarPatrocinadores();
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Ingresa un código válido");
        } catch (ValorInexistente | DatoInvalido e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    private void limpiarFormulario() {
        TextField txtCodigo = (TextField) areaPatrocinadores.getScene().getRoot().lookup("#txtCodigo");
        TextField txtNombre = (TextField) areaPatrocinadores.getScene().getRoot().lookup("#txtNombre");
        ComboBox<String> cmbTipoEmpresa = (ComboBox<String>) areaPatrocinadores.getScene().getRoot().lookup("#cmbTipoEmpresa");
        TextField txtContacto = (TextField) areaPatrocinadores.getScene().getRoot().lookup("#txtContacto");
        TextField txtAporte = (TextField) areaPatrocinadores.getScene().getRoot().lookup("#txtAporte");

        txtCodigo.clear();
        txtNombre.clear();
        cmbTipoEmpresa.setValue(null);
        txtContacto.clear();
        txtAporte.clear();
    }

    private void cargarPatrocinadores() {
        areaPatrocinadores.clear();
        Patrocinador[] patrocinadores = evento.getPatrocinadores();
        int numPatrocinadores = evento.getNumPatrocinadores();

        if (numPatrocinadores == 0) {
            areaPatrocinadores.setText("No hay patrocinadores registrados.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        double totalAportes = 0;
        for (int i = 0; i < numPatrocinadores; i++) {
            if (patrocinadores[i] != null) {
                sb.append(patrocinadores[i].toString()).append("\n\n");
                totalAportes += patrocinadores[i].getAporteEconomico();
            }
        }
        sb.append("\n═════════════════════════════════════════\n");
        sb.append("💰 TOTAL APORTES: $").append(String.format("%.2f", totalAportes)).append("\n");
        sb.append("═════════════════════════════════════════\n");

        areaPatrocinadores.setText(sb.toString());
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}