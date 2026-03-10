package Interfaz;

import Clases.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Date;

public class VentanaEventos {
    private Agencia agencia;
    private TextArea areaEventos;
    private ComboBox<String> cmbTipoEvento;
    private VBox eventoPublicoBox;
    private VBox eventoPrivadoBox;

    public VentanaEventos(Agencia agencia) {
        this.agencia = agencia;
    }



    private void verificarAccesoEventoPrivado() {
        try {
            TextField txtNombre = (TextField) areaEventos.getScene().getRoot().lookup("#txtNombre");
            String nombreEvento = txtNombre.getText();

            if (nombreEvento.isEmpty()) {
                mostrarAlerta("Error", "Ingresa el nombre del evento");
                return;
            }

            Evento evento = agencia.buscarEventoPorNombre(nombreEvento);
            if (evento == null) {
                mostrarAlerta("Error", "Evento no encontrado");
                return;
            }

            if (!(evento instanceof EventoPrivado)) {
                mostrarAlerta("Error", "Este evento no es privado");
                return;
            }

            EventoPrivado eventoPrivado = (EventoPrivado) evento;

            boolean tieneAcceso = eventoPrivado.verificarAcceso();

            if (tieneAcceso) {
                mostrarAlerta("✅ Acceso Permitido",
                        "Evento: " + eventoPrivado.getNombreDeEvento() + "\n" +
                                "Nivel de Confidencialidad: " + eventoPrivado.getNivelConfidencialidad() + "\n" +
                                "Cliente: " + eventoPrivado.getCliente() + "\n\n" +
                                "✅ ACCESO PERMITIDO (Nivel ≤ 3)");
            } else {
                mostrarAlerta("❌ Acceso Denegado",
                        "Evento: " + eventoPrivado.getNombreDeEvento() + "\n" +
                                "Nivel de Confidencialidad: " + eventoPrivado.getNivelConfidencialidad() + "\n" +
                                "Cliente: " + eventoPrivado.getCliente() + "\n\n" +
                                "❌ ACCESO DENEGADO (Nivel > 3)");
            }
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Gestión de Eventos");
        stage.setWidth(900);
        stage.setHeight(700);

        VBox root = new VBox(15);
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: #f0f0f0;");

        Label titulo = new Label("🎬 Gestión de Eventos");
        titulo.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        // ==================== TIPO DE EVENTO ====================
        HBox tipoEventoBox = new HBox(10);
        Label lblTipoEvento = new Label("Tipo de Evento:");
        cmbTipoEvento = new ComboBox<>();
        cmbTipoEvento.getItems().addAll("Público", "Privado");
        cmbTipoEvento.setValue("Público");
        cmbTipoEvento.setId("cmbTipoEvento");
        tipoEventoBox.getChildren().addAll(lblTipoEvento, cmbTipoEvento);

        // ==================== FORMULARIO ====================
        VBox formulario = crearFormulario();

        // Listener para cambiar entre tipos de evento
        cmbTipoEvento.setOnAction(e -> {
            if (cmbTipoEvento.getValue().equals("Público")) {
                eventoPublicoBox.setVisible(true);
                eventoPrivadoBox.setVisible(false);
            } else {
                eventoPublicoBox.setVisible(false);
                eventoPrivadoBox.setVisible(true);
            }
        });

        // ==================== BOTONES DE ACCIÓN ====================
        HBox botonesAccion = new HBox(10);
        botonesAccion.setAlignment(Pos.CENTER);
        Button btnAgregar = new Button("✅ Agregar Evento");
        Button btnEliminar = new Button("❌ Eliminar Evento");
        Button btnAsignarModelo = new Button("👥 Asignar Modelo");
        Button btnAsignarFotografo = new Button("📷 Asignar Fotógrafo");
        Button btnPatrocinadores = new Button("💼 Patrocinadores");
        Button btnVerFotografos = new Button("👁️ Ver Fotógrafos");
        Button btnVerificarAcceso = new Button("🔐 Verificar Acceso");
        Button btnLimpiar = new Button("🗑️ Limpiar");

        btnAgregar.setStyle("-fx-font-size: 10; -fx-padding: 6;");
        btnEliminar.setStyle("-fx-font-size: 10; -fx-padding: 6;");
        btnAsignarModelo.setStyle("-fx-font-size: 10; -fx-padding: 6;");
        btnAsignarFotografo.setStyle("-fx-font-size: 10; -fx-padding: 6;");
        btnPatrocinadores.setStyle("-fx-font-size: 10; -fx-padding: 6;");
        btnVerFotografos.setStyle("-fx-font-size: 10; -fx-padding: 6;");
        btnVerificarAcceso.setStyle("-fx-font-size: 10; -fx-padding: 6;");
        btnLimpiar.setStyle("-fx-font-size: 10; -fx-padding: 6;");

        botonesAccion.getChildren().addAll(btnAgregar, btnEliminar, btnAsignarModelo, btnAsignarFotografo,
                btnPatrocinadores, btnVerFotografos, btnVerificarAcceso, btnLimpiar);

        // ==================== ÁREA DE VISUALIZACIÓN ====================
        areaEventos = new TextArea();
        areaEventos.setEditable(false);
        areaEventos.setWrapText(true);
        areaEventos.setPrefRowCount(10);

        // ==================== BOTÓN CERRAR ====================
        Button btnCerrar = new Button("Cerrar Ventana");
        btnCerrar.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        btnCerrar.setOnAction(e -> stage.close());

        //=============== VERIFICAR ACCESO ============
        botonesAccion.getChildren().addAll(btnAgregar, btnEliminar, btnAsignarModelo, btnAsignarFotografo,
                btnPatrocinadores, btnVerificarAcceso, btnLimpiar);

        btnVerificarAcceso.setOnAction(e -> verificarAccesoEventoPrivado());

        // ==================== LAYOUT PRINCIPAL ====================
        root.getChildren().addAll(
                titulo,
                new Separator(),
                tipoEventoBox,
                formulario,
                botonesAccion,
                new Label("📋 Eventos registrados:"),
                areaEventos,
                btnCerrar
        );

        // ==================== ACCIONES DE BOTONES ====================
        btnAgregar.setOnAction(e -> agregarEvento());
        btnEliminar.setOnAction(e -> eliminarEvento());
        btnAsignarModelo.setOnAction(e -> asignarModeloAEvento());
        btnAsignarFotografo.setOnAction(e -> asignarFotografoAEvento());
        btnPatrocinadores.setOnAction(e -> abrirPatrocinadores());
        btnVerFotografos.setOnAction(e -> verFotografosEvento());
        btnVerificarAcceso.setOnAction(e -> verificarAccesoEventoPrivado());
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

        // ==================== CAMPOS COMUNES ====================
        HBox hboxNombre = new HBox(10);
        Label lblNombre = new Label("Nombre:");
        lblNombre.setPrefWidth(100);
        TextField txtNombre = new TextField();
        txtNombre.setId("txtNombre");
        hboxNombre.getChildren().addAll(lblNombre, txtNombre);

        HBox hboxFecha = new HBox(10);
        Label lblFecha = new Label("Fecha:");
        lblFecha.setPrefWidth(100);
        DatePicker dpFecha = new DatePicker();
        dpFecha.setId("dpFecha");
        hboxFecha.getChildren().addAll(lblFecha, dpFecha);

        HBox hboxLugar = new HBox(10);
        Label lblLugar = new Label("Lugar:");
        lblLugar.setPrefWidth(100);
        ComboBox<String> cmbLugar = new ComboBox<>();
        cmbLugar.setId("cmbLugar");
        hboxLugar.getChildren().addAll(lblLugar, cmbLugar);

        HBox hboxNumModelos = new HBox(10);
        Label lblNumModelos = new Label("Máx. Modelos:");
        lblNumModelos.setPrefWidth(100);
        TextField txtNumModelos = new TextField();
        txtNumModelos.setId("txtNumModelos");
        txtNumModelos.setText("10");
        hboxNumModelos.getChildren().addAll(lblNumModelos, txtNumModelos);

        // ==================== CAMPOS PARA EVENTO PÚBLICO ====================
        eventoPublicoBox = new VBox(10);
        eventoPublicoBox.setId("eventoPublicoBox");
        eventoPublicoBox.setStyle("-fx-border-color: #2ecc71; -fx-padding: 10; -fx-border-radius: 5;");

        Label lblPublico = new Label("⭐ EVENTO PÚBLICO");
        lblPublico.setStyle("-fx-font-weight: bold; -fx-text-fill: #2ecc71; -fx-font-size: 12;");

        HBox hboxCapacidad = new HBox(10);
        Label lblCapacidad = new Label("Capacidad:");
        lblCapacidad.setPrefWidth(100);
        TextField txtCapacidad = new TextField();
        txtCapacidad.setId("txtCapacidad");
        hboxCapacidad.getChildren().addAll(lblCapacidad, txtCapacidad);

        HBox hboxPrecio = new HBox(10);
        Label lblPrecio = new Label("Precio Entrada:");
        lblPrecio.setPrefWidth(100);
        TextField txtPrecio = new TextField();
        txtPrecio.setId("txtPrecio");
        hboxPrecio.getChildren().addAll(lblPrecio, txtPrecio);

        HBox hboxPatrocinadores = new HBox(10);
        Label lblPatrocinadores = new Label("Máx. Patrocinadores:");
        lblPatrocinadores.setPrefWidth(100);
        TextField txtPatrocinadores = new TextField();
        txtPatrocinadores.setId("txtPatrocinadores");
        txtPatrocinadores.setText("5");
        hboxPatrocinadores.getChildren().addAll(lblPatrocinadores, txtPatrocinadores);

        eventoPublicoBox.getChildren().addAll(lblPublico, hboxCapacidad, hboxPrecio, hboxPatrocinadores);

        // ==================== CAMPOS PARA EVENTO PRIVADO ====================
        eventoPrivadoBox = new VBox(10);
        eventoPrivadoBox.setId("eventoPrivadoBox");
        eventoPrivadoBox.setStyle("-fx-border-color: #9b59b6; -fx-padding: 10; -fx-border-radius: 5;");
        eventoPrivadoBox.setVisible(false);

        Label lblPrivado = new Label("🔒 EVENTO PRIVADO");
        lblPrivado.setStyle("-fx-font-weight: bold; -fx-text-fill: #9b59b6; -fx-font-size: 12;");

        HBox hboxCliente = new HBox(10);
        Label lblCliente = new Label("Cliente:");
        lblCliente.setPrefWidth(100);
        TextField txtCliente = new TextField();
        txtCliente.setId("txtCliente");
        hboxCliente.getChildren().addAll(lblCliente, txtCliente);

        HBox hboxConfidencialidad = new HBox(10);
        Label lblConfidencialidad = new Label("Nivel Confidencialidad:");
        lblConfidencialidad.setPrefWidth(100);
        Spinner<Integer> spnConfidencialidad = new Spinner<>(1, 5, 3);
        spnConfidencialidad.setId("spnConfidencialidad");
        hboxConfidencialidad.getChildren().addAll(lblConfidencialidad, spnConfidencialidad);

        HBox hboxPresupuesto = new HBox(10);
        Label lblPresupuesto = new Label("Presupuesto:");
        lblPresupuesto.setPrefWidth(100);
        TextField txtPresupuesto = new TextField();
        txtPresupuesto.setId("txtPresupuesto");
        hboxPresupuesto.getChildren().addAll(lblPresupuesto, txtPresupuesto);

        eventoPrivadoBox.getChildren().addAll(lblPrivado, hboxCliente, hboxConfidencialidad, hboxPresupuesto);

        // ==================== AGREGAR CAMPOS AL FORMULARIO ====================
        form.getChildren().addAll(
                hboxNombre,
                hboxFecha,
                hboxLugar,
                hboxNumModelos,
                new Separator(),
                eventoPublicoBox,
                eventoPrivadoBox
        );

        return form;
    }

    private void agregarEvento() {
        try {
            TextField txtNombre = (TextField) areaEventos.getScene().getRoot().lookup("#txtNombre");
            DatePicker dpFecha = (DatePicker) areaEventos.getScene().getRoot().lookup("#dpFecha");
            ComboBox<String> cmbLugar = (ComboBox<String>) areaEventos.getScene().getRoot().lookup("#cmbLugar");
            TextField txtNumModelos = (TextField) areaEventos.getScene().getRoot().lookup("#txtNumModelos");

            String nombre = txtNombre.getText();
            LocalDate localDate = dpFecha.getValue();
            String nombreLugar = cmbLugar.getValue();
            int numModelos = Integer.parseInt(txtNumModelos.getText());
            String tipo = cmbTipoEvento.getValue();

            if (nombre.isEmpty() || localDate == null || nombreLugar == null) {
                mostrarAlerta("Error", "Por favor completa todos los campos");
                return;
            }

            Lugar lugar = agencia.buscarLugarPorNombre(nombreLugar);
            if (lugar == null) {
                mostrarAlerta("Error", "Lugar no encontrado");
                return;
            }

            if (!lugar.estaDisponible(localDate.atStartOfDay(java.time.ZoneId.systemDefault()).toLocalDate())) {
                mostrarAlerta("Error", "El lugar no está disponible en esa fecha");
                return;
            }

            Date fecha = java.sql.Date.valueOf(localDate);

            Evento evento = null;

            if (tipo.equals("Público")) {
                TextField txtCapacidad = (TextField) areaEventos.getScene().getRoot().lookup("#txtCapacidad");
                TextField txtPrecio = (TextField) areaEventos.getScene().getRoot().lookup("#txtPrecio");
                TextField txtPatrocinadores = (TextField) areaEventos.getScene().getRoot().lookup("#txtPatrocinadores");

                if (txtCapacidad.getText().isEmpty() || txtPrecio.getText().isEmpty()) {
                    mostrarAlerta("Error", "Completa todos los campos del evento público");
                    return;
                }

                int capacidad = Integer.parseInt(txtCapacidad.getText());
                String precioStr = txtPrecio.getText().replace(",", ".");
                float precio = Float.parseFloat(precioStr);
                int maxPatrocinadores = Integer.parseInt(txtPatrocinadores.getText());

                evento = new EventoPublico(nombre, fecha, lugar, numModelos, capacidad, precio, maxPatrocinadores);
            } else if (tipo.equals("Privado")) {
                TextField txtCliente = (TextField) areaEventos.getScene().getRoot().lookup("#txtCliente");
                Spinner<Integer> spnConfidencialidad = (Spinner<Integer>) areaEventos.getScene().getRoot().lookup("#spnConfidencialidad");
                TextField txtPresupuesto = (TextField) areaEventos.getScene().getRoot().lookup("#txtPresupuesto");

                String cliente = txtCliente.getText();
                int confidencialidad = spnConfidencialidad.getValue();
                String presupuestoStr = txtPresupuesto.getText().replace(",", ".");
                float presupuesto = Float.parseFloat(presupuestoStr);

                if (cliente.isEmpty() || txtPresupuesto.getText().isEmpty()) {
                    mostrarAlerta("Error", "Completa todos los campos del evento privado");
                    return;
                }

                evento = new EventoPrivado(nombre, fecha, lugar, numModelos, cliente, confidencialidad, presupuesto);
            }

            if (evento != null) {
                agencia.agregarEventos(evento);
                Persistencia.guardar(agencia);

                mostrarAlerta("✅ Éxito", "Evento agregado correctamente");
                limpiarFormulario();
                cargarEventos();
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Verifica que los números sean válidos\nPara decimales usa formato: 100.50 o 100,50");
        } catch (DatoInvalido | Duplicado | CapacidadMaxima e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    private void eliminarEvento() {
        try {
            TextField txtNombre = (TextField) areaEventos.getScene().getRoot().lookup("#txtNombre");
            String nombre = txtNombre.getText();

            if (nombre.isEmpty()) {
                mostrarAlerta("Error", "Ingresa el nombre del evento a eliminar");
                return;
            }

            Evento evento = agencia.buscarEventoPorNombre(nombre);
            if (evento == null) {
                mostrarAlerta("Error", "Evento no encontrado");
                return;
            }

            agencia.eliminarEvento(evento);
            Persistencia.guardar(agencia);

            mostrarAlerta("✅ Éxito", "Evento eliminado correctamente");
            limpiarFormulario();
            cargarEventos();
        } catch (ValorInexistente | DatoInvalido e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    private void asignarModeloAEvento() {
        try {
            TextField txtNombre = (TextField) areaEventos.getScene().getRoot().lookup("#txtNombre");
            String nombreEvento = txtNombre.getText();

            if (nombreEvento.isEmpty()) {
                mostrarAlerta("Error", "Ingresa el nombre del evento");
                return;
            }

            Evento evento = agencia.buscarEventoPorNombre(nombreEvento);
            if (evento == null) {
                mostrarAlerta("Error", "Evento no encontrado");
                return;
            }

            // Crear ventana de selección de modelos
            Stage stageSeleccion = new Stage();
            stageSeleccion.setTitle("Seleccionar Modelo");
            stageSeleccion.setWidth(400);
            stageSeleccion.setHeight(300);

            VBox vbox = new VBox(15);
            vbox.setPadding(new Insets(15));

            Label lbl = new Label("Selecciona un modelo disponible:");
            ComboBox<String> cmbModelos = new ComboBox<>();

            Modelo[] modelos = agencia.getModelos();
            for (int i = 0; i < agencia.getNumModelos(); i++) {
                if (modelos[i] != null && modelos[i].isDisponibilidad()) {
                    cmbModelos.getItems().add(modelos[i].getNombre() + " (Código: " + modelos[i].getCodigoModelo() + ")");
                }
            }

            if (cmbModelos.getItems().isEmpty()) {
                mostrarAlerta("Error", "No hay modelos disponibles");
                stageSeleccion.close();
                return;
            }



            Button btnSeleccionar = new Button("Asignar Modelo");
            btnSeleccionar.setStyle("-fx-font-size: 12; -fx-padding: 8;");
            btnSeleccionar.setOnAction(e -> {
                String modeloSeleccionado = cmbModelos.getValue();
                if (modeloSeleccionado == null) {
                    mostrarAlerta("Error", "Selecciona un modelo");
                    return;
                }

                try {
                    int codigo = Integer.parseInt(modeloSeleccionado.split("Código: ")[1].replace(")", ""));
                    Modelo modelo = agencia.buscarModeloPorCodigo(codigo);

                    if (modelo != null) {
                        modelo.aceptarEvento(evento);
                        modelo.cambiarDisponibilidad(false);
                        agencia.asignarModeloAEvento(evento, modelo);
                        Persistencia.guardar(agencia);
                        mostrarAlerta("✅ Éxito", "Modelo asignado correctamente al evento");
                        cargarEventos();
                        stageSeleccion.close();
                    }
                } catch (DatoInvalido | ValorInexistente | CapacidadMaxima ex) {
                    mostrarAlerta("Error", ex.getMessage());
                }
            });

            vbox.getChildren().addAll(lbl, cmbModelos, btnSeleccionar);
            Scene scene = new Scene(vbox);
            stageSeleccion.setScene(scene);
            stageSeleccion.show();

        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error: " + e.getMessage());
        }
    }

    private void asignarFotografoAEvento() {
        try {
            TextField txtNombre = (TextField) areaEventos.getScene().getRoot().lookup("#txtNombre");
            String nombreEvento = txtNombre.getText();

            if (nombreEvento.isEmpty()) {
                mostrarAlerta("Error", "Ingresa el nombre del evento");
                return;
            }

            Evento evento = agencia.buscarEventoPorNombre(nombreEvento);
            if (evento == null) {
                mostrarAlerta("Error", "Evento no encontrado");
                return;
            }

            // Crear ventana de selección de fotógrafos
            Stage stageSeleccion = new Stage();
            stageSeleccion.setTitle("Seleccionar Fotógrafo");
            stageSeleccion.setWidth(400);
            stageSeleccion.setHeight(300);

            VBox vbox = new VBox(15);
            vbox.setPadding(new Insets(15));

            Label lbl = new Label("Selecciona un fotógrafo:");
            ComboBox<String> cmbFotografos = new ComboBox<>();

            Fotografo[] fotografos = agencia.getFotografos();
            for (int i = 0; i < agencia.getNumFotografos(); i++) {
                if (fotografos[i] != null) {
                    cmbFotografos.getItems().add(fotografos[i].getNombre() + " (Código: " + fotografos[i].getCodigoFotografo() + ")");
                }
            }

            if (cmbFotografos.getItems().isEmpty()) {
                mostrarAlerta("Error", "No hay fotógrafos registrados");
                stageSeleccion.close();
                return;
            }

            Button btnSeleccionar = new Button("Asignar Fotógrafo");
            btnSeleccionar.setStyle("-fx-font-size: 12; -fx-padding: 8;");
            btnSeleccionar.setOnAction(e -> {
                String fotografoSeleccionado = cmbFotografos.getValue();
                if (fotografoSeleccionado == null) {
                    mostrarAlerta("Error", "Selecciona un fotógrafo");
                    return;
                }

                try {
                    int codigo = Integer.parseInt(fotografoSeleccionado.split("Código: ")[1].replace(")", ""));
                    Fotografo fotografo = agencia.buscarFotografoPorCodigo(codigo);

                    if (fotografo != null) {
                        agencia.asignarFotografoAEvento(evento, fotografo);
                        Persistencia.guardar(agencia);
                        mostrarAlerta("✅ Éxito", "Fotógrafo asignado correctamente al evento");
                        cargarEventos();
                        stageSeleccion.close();
                    }
                } catch (DatoInvalido | ValorInexistente | CapacidadMaxima ex) {
                    mostrarAlerta("Error", ex.getMessage());
                }
            });

            vbox.getChildren().addAll(lbl, cmbFotografos, btnSeleccionar);
            Scene scene = new Scene(vbox);
            stageSeleccion.setScene(scene);
            stageSeleccion.show();

        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error: " + e.getMessage());
        }
    }

    private void limpiarFormulario() {
        TextField txtNombre = (TextField) areaEventos.getScene().getRoot().lookup("#txtNombre");
        DatePicker dpFecha = (DatePicker) areaEventos.getScene().getRoot().lookup("#dpFecha");
        ComboBox<String> cmbLugar = (ComboBox<String>) areaEventos.getScene().getRoot().lookup("#cmbLugar");
        TextField txtNumModelos = (TextField) areaEventos.getScene().getRoot().lookup("#txtNumModelos");
        TextField txtCapacidad = (TextField) areaEventos.getScene().getRoot().lookup("#txtCapacidad");
        TextField txtPrecio = (TextField) areaEventos.getScene().getRoot().lookup("#txtPrecio");
        TextField txtPatrocinadores = (TextField) areaEventos.getScene().getRoot().lookup("#txtPatrocinadores");
        TextField txtCliente = (TextField) areaEventos.getScene().getRoot().lookup("#txtCliente");
        Spinner<Integer> spnConfidencialidad = (Spinner<Integer>) areaEventos.getScene().getRoot().lookup("#spnConfidencialidad");
        TextField txtPresupuesto = (TextField) areaEventos.getScene().getRoot().lookup("#txtPresupuesto");

        if (txtNombre != null) txtNombre.clear();
        if (dpFecha != null) dpFecha.setValue(null);
        if (cmbLugar != null) cmbLugar.setValue(null);
        if (txtNumModelos != null) txtNumModelos.setText("10");
        if (txtCapacidad != null) txtCapacidad.clear();
        if (txtPrecio != null) txtPrecio.clear();
        if (txtPatrocinadores != null) txtPatrocinadores.setText("5");
        if (txtCliente != null) txtCliente.clear();
        if (spnConfidencialidad != null) spnConfidencialidad.getValueFactory().setValue(3);
        if (txtPresupuesto != null) txtPresupuesto.clear();
    }

    private void cargarEventos() {
        areaEventos.clear();

        // Cargar lugares en ComboBox
        ComboBox<String> cmbLugar = (ComboBox<String>) areaEventos.getScene().getRoot().lookup("#cmbLugar");
        if (cmbLugar != null) {
            cmbLugar.getItems().clear();
            Lugar[] lugares = agencia.getLugares();
            for (int i = 0; i < agencia.getNumLugares(); i++) {
                if (lugares[i] != null) {
                    cmbLugar.getItems().add(lugares[i].getNombreDelLugar());
                }
            }
        }

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
    private void abrirPatrocinadores() {
        try {
            TextField txtNombre = (TextField) areaEventos.getScene().getRoot().lookup("#txtNombre");
            String nombreEvento = txtNombre.getText();

            if (nombreEvento.isEmpty()) {
                mostrarAlerta("Error", "Selecciona un evento primero");
                return;
            }

            Evento evento = agencia.buscarEventoPorNombre(nombreEvento);
            if (evento == null) {
                mostrarAlerta("Error", "Evento no encontrado");
                return;
            }

            if (!(evento instanceof EventoPublico)) {
                mostrarAlerta("Error", "Solo los eventos públicos pueden tener patrocinadores");
                return;
            }

            new VentanaPatrocinadores(agencia, evento).mostrar();
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    private void verFotografosEvento() {
        try {
            TextField txtNombre = (TextField) areaEventos.getScene().getRoot().lookup("#txtNombre");
            String nombreEvento = txtNombre.getText();

            if (nombreEvento.isEmpty()) {
                mostrarAlerta("Error", "Ingresa el nombre del evento");
                return;
            }

            Evento evento = agencia.buscarEventoPorNombre(nombreEvento);
            if (evento == null) {
                mostrarAlerta("Error", "Evento no encontrado");
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("📷 FOTÓGRAFOS ASIGNADOS AL EVENTO\n");
            sb.append("════════════════════════════════════\n\n");
            sb.append("Evento: ").append(evento.getNombreDeEvento()).append("\n");
            sb.append("Total fotógrafos: ").append(evento.getNumFotografos()).append("\n\n");

            if (evento.getNumFotografos() == 0) {
                sb.append("No hay fotógrafos asignados.\n");
            } else {
                for (int i = 0; i < evento.getNumFotografos(); i++) {
                    if (evento.getFotografos()[i] != null) {
                        Fotografo f = evento.getFotografos()[i];
                        sb.append((i + 1)).append(". ").append(f.getNombre())
                                .append(" (").append(f.getEspecialidad()).append(")")
                                .append("\n   Tarifa: $").append(f.getTarifaPorEvento())
                                .append("\n\n");
                    }
                }
            }

            mostrarAlerta("📷 Fotógrafos del Evento", sb.toString());
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}