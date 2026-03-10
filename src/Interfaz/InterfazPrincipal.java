package Interfaz;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import Clases.Agencia;
import Clases.Persistencia;

public class InterfazPrincipal extends Application {
    private Agencia agencia;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // ==================== CARGAR DATOS ====================
        agencia = Persistencia.cargar();
        if (agencia == null) {
            agencia = new Agencia(50, 100, 50);
        }

        // ==================== CONFIGURACIÓN DE VENTANA ====================
        primaryStage.setTitle("🎬 Agencia de Modelaje - No Más Enanos Por Favor");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ecf0f1;");

        // ==================== ENCABEZADO ====================
        root.setTop(crearEncabezado());

        // ==================== CONTENIDO CENTRAL ====================
        root.setCenter(crearContenidoCentral());

        // ==================== PIE ====================
        root.setBottom(crearPie());

        // ==================== ESCENA ====================
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        // ==================== CERRAR ====================
        primaryStage.setOnCloseRequest(e -> {
            Persistencia.guardar(agencia);
            System.exit(0);
        });

        primaryStage.show();
    }

    private VBox crearEncabezado() {
        VBox encabezado = new VBox(10);
        encabezado.setPadding(new Insets(20));
        encabezado.setStyle("-fx-background-color: #2c3e50;");
        encabezado.setAlignment(Pos.CENTER);

        Label titulo = new Label("🎬 SISTEMA DE GESTIÓN - AGENCIA DE MODELAJE");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titulo.setStyle("-fx-text-fill: white;");

        Label subtitulo = new Label("Política: No Más Enanos Por Favor (Estatura Mínima: 1.50m)");
        subtitulo.setFont(Font.font("Arial", 12));
        subtitulo.setStyle("-fx-text-fill: #ecf0f1;");

        encabezado.getChildren().addAll(titulo, subtitulo);
        return encabezado;
    }

    private VBox crearContenidoCentral() {
        VBox contenido = new VBox(20);
        contenido.setPadding(new Insets(30));
        contenido.setAlignment(Pos.TOP_CENTER);

        Label bienvenida = new Label("Bienvenido al Sistema de Gestión");
        bienvenida.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Label instruccion = new Label("Selecciona una opción del menú inferior para comenzar");
        instruccion.setStyle("-fx-text-fill: #7f8c8d;");
        instruccion.setFont(Font.font("Arial", 12));

        // ========== INFORMACIÓN IMPORTANTE ==========
        VBox infoBox = new VBox(10);
        infoBox.setStyle("-fx-border-color: #e74c3c; -fx-border-radius: 5; -fx-padding: 15; -fx-background-color: #fadbd8;");

        Label lblTitulo = new Label("⚠️ POLÍTICA DE LA AGENCIA");
        lblTitulo.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: #c0392b;");

        Label lblPolitica = new Label("Estatura Mínima Permitida: 1.50 metros\n\n\"No Más Enanos Por Favor\"");
        lblPolitica.setStyle("-fx-font-size: 12; -fx-text-fill: #922b21; -fx-wrap-text: true;");
        lblPolitica.setWrapText(true);

        infoBox.getChildren().addAll(lblTitulo, lblPolitica);

        contenido.getChildren().addAll(bienvenida, instruccion, infoBox);
        return contenido;
    }

    private HBox crearPie() {
        HBox pie = new HBox(10);
        pie.setPadding(new Insets(15));
        pie.setStyle("-fx-background-color: #34495e;");
        pie.setAlignment(Pos.CENTER);

        Button btnModelos = crearBoton("👥 Gestión de Modelos", "#e74c3c");
        Button btnFotografos = crearBoton("📷 Gestión de Fotógrafos", "#3498db");
        Button btnLugares = crearBoton("📍 Gestión de Lugares", "#2ecc71");
        Button btnEventos = crearBoton("🎬 Gestión de Eventos", "#f39c12");
        Button btnDashboard = crearBoton("📊 Dashboard", "#9b59b6");
        Button btnSalir = crearBoton("🚪 Salir", "#95a5a6");

        // Acciones
        btnModelos.setOnAction(e -> new VentanaModelos(agencia).mostrar());
        btnFotografos.setOnAction(e -> new VentanaFotografos(agencia).mostrar());
        btnLugares.setOnAction(e -> new VentanaLugares(agencia).mostrar());
        btnEventos.setOnAction(e -> new VentanaEventos(agencia).mostrar());
        btnDashboard.setOnAction(e -> new VentanaDashboard(agencia).mostrar());
        btnSalir.setOnAction(e -> {
            Persistencia.guardar(agencia);
            primaryStage.close();
        });

        pie.getChildren().addAll(btnModelos, btnFotografos, btnLugares, btnEventos, btnDashboard, btnSalir);
        return pie;
    }

    private Button crearBoton(String texto, String color) {
        Button btn = new Button(texto);
        btn.setStyle("-fx-background-color: " + color + "; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 12; " +
                "-fx-padding: 10; " +
                "-fx-font-weight: bold; " +
                "-fx-cursor: hand;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: " + oscurecerColor(color) + "; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 12; " +
                "-fx-padding: 10; " +
                "-fx-font-weight: bold; " +
                "-fx-cursor: hand;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: " + color + "; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 12; " +
                "-fx-padding: 10; " +
                "-fx-font-weight: bold; " +
                "-fx-cursor: hand;"));
        return btn;
    }

    private String oscurecerColor(String color) {
        // Simplificar versión oscura
        return color;
    }

    public static void main(String[] args) {
        launch(args);
    }
}