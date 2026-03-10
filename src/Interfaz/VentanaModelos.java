package Interfaz;

import Clases.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VentanaModelos {
    private Agencia agencia;
    private TextArea areaModelos;

    public VentanaModelos(Agencia agencia) {
        this.agencia = agencia;
    }

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Gestión de Modelos");
        stage.setWidth(800);
        stage.setHeight(650);

        VBox root = new VBox(15);
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: #f0f0f0;");

        Label titulo = new Label("👥 Gestión de Modelos");
        titulo.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        VBox formulario = crearFormulario();

        HBox botonesAccion = new HBox(10);
        botonesAccion.setAlignment(Pos.CENTER);
        Button btnAgregar = new Button("✅ Agregar Modelo");
        Button btnEliminar = new Button("❌ Eliminar Modelo");
        Button btnLimpiar = new Button("🗑️ Limpiar");

        btnAgregar.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        btnEliminar.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        btnLimpiar.setStyle("-fx-font-size: 12; -fx-padding: 8;");

        botonesAccion.getChildren().addAll(btnAgregar, btnEliminar, btnLimpiar);

        areaModelos = new TextArea();
        areaModelos.setEditable(false);
        areaModelos.setWrapText(true);
        areaModelos.setPrefRowCount(12);

        Button btnCerrar = new Button("Cerrar Ventana");
        btnCerrar.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        btnCerrar.setOnAction(e -> stage.close());

        root.getChildren().addAll(
                titulo,
                new Separator(),
                formulario,
                botonesAccion,
                new Label("📋 Modelos registrados:"),
                areaModelos,
                btnCerrar
        );

        btnAgregar.setOnAction(e -> agregarModelo());
        btnEliminar.setOnAction(e -> eliminarModelo());
        btnLimpiar.setOnAction(e -> limpiarFormulario());

        cargarModelos();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private VBox crearFormulario() {
        VBox form = new VBox(10);
        form.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-padding: 15; -fx-background-color: white;");

        // Banner de estatura mínima
        VBox bannerEstatura = new VBox(5);
        bannerEstatura.setStyle("-fx-background-color: #e74c3c; -fx-padding: 10; -fx-border-radius: 5;");
        bannerEstatura.setAlignment(Pos.CENTER);

        Label lblBannerTitulo = new Label("⚠️ REQUISITO IMPORTANTE");
        lblBannerTitulo.setStyle("-fx-text-fill: white; -fx-font-size: 12; -fx-font-weight: bold;");

        Label lblBannerTexto = new Label("Estatura Mínima Requerida: 1.50m");
        lblBannerTexto.setStyle("-fx-text-fill: white; -fx-font-size: 14; -fx-font-weight: bold;");

        Label lblBannerSubtexto = new Label("No Más Enanos Por Favor - Política de la Agencia");
        lblBannerSubtexto.setStyle("-fx-text-fill: #fff8dc; -fx-font-size: 10;");

        bannerEstatura.getChildren().addAll(lblBannerTitulo, lblBannerTexto, lblBannerSubtexto);

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

        // Número
        HBox hboxNumero = new HBox(10);
        Label lblNumero = new Label("Contacto:");
        lblNumero.setPrefWidth(100);
        TextField txtNumero = new TextField();
        txtNumero.setId("txtNumero");
        hboxNumero.getChildren().addAll(lblNumero, txtNumero);

        // Código Modelo
        HBox hboxCodigoModelo = new HBox(10);
        Label lblCodigoModelo = new Label("Código Modelo:");
        lblCodigoModelo.setPrefWidth(100);
        TextField txtCodigoModelo = new TextField();
        txtCodigoModelo.setId("txtCodigoModelo");
        hboxCodigoModelo.getChildren().addAll(lblCodigoModelo, txtCodigoModelo);

        // Estatura
        HBox hboxEstatura = new HBox(10);
        Label lblEstatura = new Label("Estatura (m):");
        lblEstatura.setPrefWidth(100);
        lblEstatura.setStyle("-fx-font-weight: bold; -fx-text-fill: #e74c3c;");
        TextField txtEstatura = new TextField();
        txtEstatura.setId("txtEstatura");
        txtEstatura.setStyle("-fx-border-color: #e74c3c; -fx-border-width: 2;");
        Label lblNotaEstatura = new Label("✓ Mínimo: 1.50m");
        lblNotaEstatura.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold; -fx-font-size: 11;");
        hboxEstatura.getChildren().addAll(lblEstatura, txtEstatura, lblNotaEstatura);

        // Categoría
        HBox hboxCategoria = new HBox(10);
        Label lblCategoria = new Label("Categoría:");
        lblCategoria.setPrefWidth(100);
        ComboBox<String> cmbCategoria = new ComboBox<>();
        cmbCategoria.getItems().addAll("Pasarela", "Comercial", "Fitness", "Plus Size", "Infantil");
        cmbCategoria.setId("cmbCategoria");
        hboxCategoria.getChildren().addAll(lblCategoria, cmbCategoria);

        // Disponibilidad
        HBox hboxDisponibilidad = new HBox(10);
        Label lblDisponibilidad = new Label("Disponibilidad:");
        lblDisponibilidad.setPrefWidth(100);
        CheckBox chkDisponibilidad = new CheckBox("Disponible");
        chkDisponibilidad.setId("chkDisponibilidad");
        chkDisponibilidad.setSelected(true);
        hboxDisponibilidad.getChildren().addAll(lblDisponibilidad, chkDisponibilidad);

        form.getChildren().addAll(
                bannerEstatura,
                new Separator(),
                hboxNombre,
                hboxIdentificacion,
                hboxNumero,
                hboxCodigoModelo,
                hboxEstatura,
                hboxCategoria,
                hboxDisponibilidad
        );

        return form;
    }

    private void agregarModelo() {
        try {
            TextField txtNombre = (TextField) areaModelos.getScene().getRoot().lookup("#txtNombre");
            TextField txtIdentificacion = (TextField) areaModelos.getScene().getRoot().lookup("#txtIdentificacion");
            TextField txtNumero = (TextField) areaModelos.getScene().getRoot().lookup("#txtNumero");
            TextField txtCodigoModelo = (TextField) areaModelos.getScene().getRoot().lookup("#txtCodigoModelo");
            TextField txtEstatura = (TextField) areaModelos.getScene().getRoot().lookup("#txtEstatura");
            ComboBox<String> cmbCategoria = (ComboBox<String>) areaModelos.getScene().getRoot().lookup("#cmbCategoria");
            CheckBox chkDisponibilidad = (CheckBox) areaModelos.getScene().getRoot().lookup("#chkDisponibilidad");

            String nombre = txtNombre.getText();
            int identificacion = Integer.parseInt(txtIdentificacion.getText());
            int numero = Integer.parseInt(txtNumero.getText());
            int codigoModelo = Integer.parseInt(txtCodigoModelo.getText());

            String estaturaStr = txtEstatura.getText().replace(",", ".");
            float estatura = Float.parseFloat(estaturaStr);

            String categoria = cmbCategoria.getValue();
            boolean disponibilidad = chkDisponibilidad.isSelected();

            if (nombre.isEmpty() || categoria == null) {
                mostrarAlerta("Error", "Por favor completa todos los campos");
                return;
            }

            if (estatura < agencia.getEstaturaMinima()) {
                mostrarAlerta("❌ NO MÁS ENANOS POR FAVOR ❌",
                        "La estatura mínima permitida es " + agencia.getEstaturaMinima() + "m\n" +
                                "El modelo mide " + estatura + "m\n\n" +
                                "Lo sentimos, este modelo no puede ser registrado en la agencia.");
                return;
            }

            Modelo modelo = new Modelo(nombre, identificacion, numero, codigoModelo, estatura, categoria, disponibilidad);
            agencia.agregarModelo(modelo);
            Persistencia.guardar(agencia);

            mostrarAlerta("✅ Éxito", "Modelo agregado correctamente");
            limpiarFormulario();
            cargarModelos();
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Verifica que los números sean válidos\nPara estatura usa formato: 1.50 o 1,50");
        } catch (DatoInvalido | Duplicado | CapacidadMaxima e) {
            mostrarAlerta("❌ Error", e.getMessage());
        }
    }

    private void eliminarModelo() {
        try {
            TextField txtCodigoModelo = (TextField) areaModelos.getScene().getRoot().lookup("#txtCodigoModelo");
            int codigo = Integer.parseInt(txtCodigoModelo.getText());

            Modelo modelo = agencia.buscarModeloPorCodigo(codigo);
            if (modelo == null) {
                mostrarAlerta("Error", "Modelo no encontrado");
                return;
            }

            agencia.eliminarModelo(modelo);
            Persistencia.guardar(agencia);

            mostrarAlerta("✅ Éxito", "Modelo eliminado correctamente");
            limpiarFormulario();
            cargarModelos();
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Ingresa un código válido");
        } catch (ValorInexistente | DatoInvalido e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    private void limpiarFormulario() {
        TextField txtNombre = (TextField) areaModelos.getScene().getRoot().lookup("#txtNombre");
        TextField txtIdentificacion = (TextField) areaModelos.getScene().getRoot().lookup("#txtIdentificacion");
        TextField txtNumero = (TextField) areaModelos.getScene().getRoot().lookup("#txtNumero");
        TextField txtCodigoModelo = (TextField) areaModelos.getScene().getRoot().lookup("#txtCodigoModelo");
        TextField txtEstatura = (TextField) areaModelos.getScene().getRoot().lookup("#txtEstatura");
        ComboBox<String> cmbCategoria = (ComboBox<String>) areaModelos.getScene().getRoot().lookup("#cmbCategoria");
        CheckBox chkDisponibilidad = (CheckBox) areaModelos.getScene().getRoot().lookup("#chkDisponibilidad");

        txtNombre.clear();
        txtIdentificacion.clear();
        txtNumero.clear();
        txtCodigoModelo.clear();
        txtEstatura.clear();
        cmbCategoria.setValue(null);
        chkDisponibilidad.setSelected(true);
    }

    private void cargarModelos() {
        areaModelos.clear();
        Modelo[] modelos = agencia.getModelos();
        int numModelos = agencia.getNumModelos();

        if (numModelos == 0) {
            areaModelos.setText("No hay modelos registrados.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numModelos; i++) {
            if (modelos[i] != null) {
                sb.append(modelos[i].toString()).append("\n\n");
            }
        }
        areaModelos.setText(sb.toString());
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}