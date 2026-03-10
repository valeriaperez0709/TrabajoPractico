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

    // CONTROLES
    private TextArea areaEventos;

    private ComboBox<String> cmbTipoEvento;
    private ComboBox<String> cmbLugar;

    private ComboBox<String> cmbEventoAsignar;
    private ComboBox<String> cmbModelo;
    private ComboBox<String> cmbFotografo;

    private TextField txtNombre;
    private DatePicker dpFecha;
    private TextField txtNumModelos;

    private TextField txtCapacidad;
    private TextField txtPrecio;
    private TextField txtPatrocinadores;

    private TextField txtCliente;
    private Spinner<Integer> spnConfidencialidad;
    private TextField txtPresupuesto;

    private VBox eventoPublicoBox;
    private VBox eventoPrivadoBox;

    public VentanaEventos(Agencia agencia) {
        this.agencia = agencia;
    }

    private void asignarModelo(){

        try{

            String nombreEvento = cmbEventoAsignar.getValue();
            String codigoModelo = cmbModelo.getValue();

            if(nombreEvento == null || codigoModelo == null){
                mostrarAlerta("Error","Selecciona un evento y un modelo");
                return;
            }

            // buscar evento
            Evento evento = agencia.buscarEventoPorNombre(nombreEvento);

            // buscar modelo
            int codigo = Integer.parseInt(codigoModelo);
            Modelo modelo = agencia.buscarModeloPorCodigo(codigo);

            // asignar
            agencia.asignarModeloAEvento(evento, modelo);

            Persistencia.guardar(agencia);

            mostrarAlerta("Éxito","Modelo asignado al evento correctamente");

            cargarEventos();

        }catch(Exception ex){
            mostrarAlerta("Error",ex.getMessage());
        }
    }

    private void asignarFotografo(){

        try{

            String nombreEvento = cmbEventoAsignar.getValue();
            String codigoFotografo = cmbFotografo.getValue();

            if(nombreEvento == null || codigoFotografo == null){
                mostrarAlerta("Error","Selecciona evento y fotógrafo");
                return;
            }

            Evento evento = agencia.buscarEventoPorNombre(nombreEvento);

            int codigo = Integer.parseInt(codigoFotografo);
            Fotografo fotografo = agencia.buscarFotografoPorCodigo(codigo);

            agencia.asignarFotografoAEvento(evento,fotografo);

            Persistencia.guardar(agencia);

            mostrarAlerta("Éxito","Fotógrafo asignado correctamente");

            cargarEventos();

        }catch(Exception ex){
            mostrarAlerta("Error",ex.getMessage());
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

        // ================= TIPO EVENTO =================
        HBox tipoEventoBox = new HBox(10);

        Label lblTipoEvento = new Label("Tipo de Evento:");

        cmbTipoEvento = new ComboBox<>();
        cmbTipoEvento.getItems().addAll("Público", "Privado");
        cmbTipoEvento.setValue("Público");

        tipoEventoBox.getChildren().addAll(lblTipoEvento, cmbTipoEvento);

        VBox formulario = crearFormulario();

        // ================= BOTONES =================
        HBox botonesAccion = new HBox(10);
        botonesAccion.setAlignment(Pos.CENTER);

        Button btnAgregar = new Button("✅ Agregar Evento");
        Button btnEliminar = new Button("❌ Eliminar Evento");
        Button btnLimpiar = new Button("🗑️ Limpiar");

        botonesAccion.getChildren().addAll(btnAgregar, btnEliminar, btnLimpiar);

        // ================= AREA EVENTOS =================
        areaEventos = new TextArea();
        areaEventos.setEditable(false);
        areaEventos.setPrefRowCount(10);

        Button btnCerrar = new Button("Cerrar Ventana");
        btnCerrar.setOnAction(e -> stage.close());

        Label lblAsignar = new Label("🎯 Asignar personal al evento");
        HBox asignacionBox = new HBox(10);


        root.getChildren().addAll(
                titulo,
                new Separator(),
                tipoEventoBox,
                formulario,
                botonesAccion,
                new Separator(),
                lblAsignar,
                asignacionBox,
                new Label("📋 Eventos registrados"),
                areaEventos,
                btnCerrar
        );

        cmbEventoAsignar = new ComboBox<>();
        cmbModelo = new ComboBox<>();
        cmbFotografo = new ComboBox<>();

        Button btnAsignarModelo = new Button("Asignar Modelo");
        Button btnAsignarFotografo = new Button("Asignar Fotógrafo");

        asignacionBox.getChildren().addAll(
                new Label("Evento:"), cmbEventoAsignar,
                new Label("Modelo:"), cmbModelo,
                btnAsignarModelo,
                new Label("Fotógrafo:"), cmbFotografo,
                btnAsignarFotografo
        );

        btnAgregar.setOnAction(e -> agregarEvento());
        btnEliminar.setOnAction(e -> eliminarEvento());
        btnLimpiar.setOnAction(e -> limpiarFormulario());
        btnAsignarModelo.setOnAction(e -> asignarModelo());
        btnAsignarFotografo.setOnAction(e -> asignarFotografo());

        ScrollPane scroll = new ScrollPane();
        scroll.setContent(root);
        scroll.setFitToWidth(true);
        scroll.setPannable(true);

        Scene scene = new Scene(scroll, 900, 700);
        stage.setScene(scene);
        stage.show();

        cargarEventos();
    }



    private VBox crearFormulario() {

        VBox form = new VBox(10);
        form.setPadding(new Insets(10));

        // ===== Nombre =====
        HBox hNombre = new HBox(10);

        Label lblNombre = new Label("Nombre:");
        lblNombre.setPrefWidth(120);

        txtNombre = new TextField();

        hNombre.getChildren().addAll(lblNombre, txtNombre);

        // ===== Fecha =====
        HBox hFecha = new HBox(10);

        Label lblFecha = new Label("Fecha:");
        lblFecha.setPrefWidth(120);

        dpFecha = new DatePicker();

        hFecha.getChildren().addAll(lblFecha, dpFecha);

        // ===== Lugar =====
        HBox hLugar = new HBox(10);

        Label lblLugar = new Label("Lugar:");
        lblLugar.setPrefWidth(120);

        cmbLugar = new ComboBox<>();

        hLugar.getChildren().addAll(lblLugar, cmbLugar);

        // ===== Modelos =====
        HBox hModelos = new HBox(10);

        Label lblModelos = new Label("Máx Modelos:");
        lblModelos.setPrefWidth(120);

        txtNumModelos = new TextField("10");

        hModelos.getChildren().addAll(lblModelos, txtNumModelos);

        // ===== PUBLICO =====
        eventoPublicoBox = new VBox(10);

        Label lblPublico = new Label("⭐ EVENTO PUBLICO");

        txtCapacidad = new TextField();
        txtPrecio = new TextField();
        txtPatrocinadores = new TextField("5");

        eventoPublicoBox.getChildren().addAll(
                lblPublico,
                new Label("Capacidad"), txtCapacidad,
                new Label("Precio Entrada"), txtPrecio,
                new Label("Máx Patrocinadores"), txtPatrocinadores
        );

        // ===== PRIVADO =====
        eventoPrivadoBox = new VBox(10);

        Label lblPrivado = new Label("🔒 EVENTO PRIVADO");

        txtCliente = new TextField();
        spnConfidencialidad = new Spinner<>(1,5,3);
        txtPresupuesto = new TextField();

        eventoPrivadoBox.getChildren().addAll(
                lblPrivado,
                new Label("Cliente"), txtCliente,
                new Label("Confidencialidad"), spnConfidencialidad,
                new Label("Presupuesto"), txtPresupuesto
        );

        eventoPrivadoBox.setVisible(false);

        cmbTipoEvento.setOnAction(e -> {

            boolean esPublico = cmbTipoEvento.getValue().equals("Público");

            eventoPublicoBox.setVisible(esPublico);
            eventoPublicoBox.setManaged(esPublico);

            eventoPrivadoBox.setVisible(!esPublico);
            eventoPrivadoBox.setManaged(!esPublico);

        });

        form.getChildren().addAll(
                hNombre,
                hFecha,
                hLugar,
                hModelos,
                new Separator(),
                eventoPublicoBox,
                eventoPrivadoBox
        );

        return form;
    }

    private void agregarEvento() {

        try {

            String nombre = txtNombre.getText();
            LocalDate fechaLocal = dpFecha.getValue();
            String nombreLugar = cmbLugar.getValue();
            int numModelos = Integer.parseInt(txtNumModelos.getText());

            if(nombre.isEmpty() || fechaLocal == null || nombreLugar == null){
                mostrarAlerta("Error","Completa todos los campos");
                return;
            }

            Lugar lugar = agencia.buscarLugarPorNombre(nombreLugar);

            Date fecha = java.sql.Date.valueOf(fechaLocal);

            Evento evento;

            if(cmbTipoEvento.getValue().equals("Público")){

                int capacidad = Integer.parseInt(txtCapacidad.getText());
                float precio = Float.parseFloat(txtPrecio.getText());
                int maxPat = Integer.parseInt(txtPatrocinadores.getText());

                evento = new EventoPublico(nombre,fecha,lugar,numModelos,capacidad,precio,maxPat);

            } else {

                String cliente = txtCliente.getText();
                int nivel = spnConfidencialidad.getValue();
                float presupuesto = Float.parseFloat(txtPresupuesto.getText());

                evento = new EventoPrivado(nombre,fecha,lugar,numModelos,cliente,nivel,presupuesto);

            }

            agencia.agregarEventos(evento);
            Persistencia.guardar(agencia);

            mostrarAlerta("Éxito","Evento agregado");
            limpiarFormulario();
            cargarEventos();

        } catch(Exception e){
            mostrarAlerta("Error",e.getMessage());
        }

    }

    private void eliminarEvento(){

        String nombre = txtNombre.getText();

        Evento evento = agencia.buscarEventoPorNombre(nombre);

        if(evento == null){
            mostrarAlerta("Error","Evento no encontrado");
            return;
        }

        try {

            agencia.eliminarEvento(evento);
            Persistencia.guardar(agencia);

            mostrarAlerta("Éxito","Evento eliminado");
            cargarEventos();

        } catch(Exception e){
            mostrarAlerta("Error",e.getMessage());
        }
    }

    private void limpiarFormulario(){

        txtNombre.clear();
        dpFecha.setValue(null);
        cmbLugar.setValue(null);
        txtNumModelos.setText("10");

        txtCapacidad.clear();
        txtPrecio.clear();
        txtPatrocinadores.setText("5");

        txtCliente.clear();
        txtPresupuesto.clear();

        spnConfidencialidad.getValueFactory().setValue(3);
    }

    private void cargarEventos(){

        areaEventos.clear();

        cmbLugar.getItems().clear();
        cmbEventoAsignar.getItems().clear();
        cmbModelo.getItems().clear();
        cmbFotografo.getItems().clear();

        // ===== LUGARES =====
        Lugar[] lugares = agencia.getLugares();

        for(int i=0;i<agencia.getNumLugares();i++){
            if(lugares[i] != null){
                cmbLugar.getItems().add(lugares[i].getNombreDelLugar());
            }
        }

        // ===== EVENTOS =====
        Evento[] eventos = agencia.getEventos();

        StringBuilder sb = new StringBuilder();

        for(int i=0;i<agencia.getNumEventos();i++){
            if(eventos[i] != null){

                cmbEventoAsignar.getItems().add(eventos[i].getNombreDeEvento());

                sb.append(eventos[i]).append("\n\n");
            }
        }

        // ===== MODELOS =====
        Modelo[] modelos = agencia.getModelos();

        for(int i=0;i<agencia.getNumModelos();i++){
            if(modelos[i] != null){
                cmbModelo.getItems().add(String.valueOf(modelos[i].getCodigoModelo()));
            }
        }

        // ===== FOTOGRAFOS =====
        Fotografo[] fotografos = agencia.getFotografos();

        for(int i=0;i<agencia.getNumFotografos();i++){
            if(fotografos[i] != null){
                cmbFotografo.getItems().add(String.valueOf(fotografos[i].getCodigoFotografo()));
            }
        }

        areaEventos.setText(sb.toString());
    }

    private void mostrarAlerta(String titulo,String mensaje){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();

    }

}