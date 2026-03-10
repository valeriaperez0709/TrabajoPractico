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
import Interfaz.VentanaDashboard;

public class InterfazPrincipal extends Application {

    private Agencia agencia;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Inicializar agencia con capacidades máximas
        agencia = new Agencia(100, 100, 50);
        agencia.cargar(); // Cargar datos persisitidos

        primaryStage.setTitle("Sistema de Gestión - Agencia de Modelaje");
        primaryStage.setWidth(600);
        primaryStage.setHeight(500);

        // Crear escena principal
        Scene scene = crearEscenaPrincipal();
        primaryStage.setScene(scene);

        // Al cerrar, guardar datos
        primaryStage.setOnCloseRequest(e -> {
            agencia.guardar();
        });

        primaryStage.show();
    }

    private Scene crearEscenaPrincipal() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f0f0f0;");

        // ================== ENCABEZADO ==================
        VBox encabezado = crearEncabezado();
        root.setTop(encabezado);

        // ================== CONTENIDO CENTRAL ==================
        VBox contenido = crearContenidoCentral();
        root.setCenter(contenido);

        // ================== PIE DE PÁGINA ==================
        HBox pie = crearPie();
        root.setBottom(pie);

        return new Scene(root);
    }

    private VBox crearEncabezado() {
        VBox encabezado = new VBox();
        encabezado.setStyle("-fx-background-color: #2c3e50; -fx-padding: 20;");
        encabezado.setAlignment(Pos.CENTER);

        Label titulo = new Label("Agencia de Modelaje");
        titulo.setStyle("-fx-text-fill: white;");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));

        Label subtitulo = new Label("No Más Enanos Por Favor");
        subtitulo.setStyle("-fx-text-fill: #ecf0f1;");
        subtitulo.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));

        encabezado.getChildren().addAll(titulo, subtitulo);
        return encabezado;
    }

    private VBox crearContenidoCentral() {
        VBox contenido = new VBox(20);
        contenido.setPadding(new Insets(30));
        contenido.setAlignment(Pos.CENTER);

        Label bienvenida = new Label("Bienvenido al Sistema de Gestión");
        bienvenida.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Label instruccion = new Label("Selecciona una opción del menú inferior para comenzar");
        instruccion.setStyle("-fx-text-fill: #7f8c8d;");
        instruccion.setFont(Font.font("Arial", 12));

        contenido.getChildren().addAll(bienvenida, instruccion);
        return contenido;
    }

    private HBox crearPie() {
        HBox pie = new HBox(10);
        pie.setPadding(new Insets(15));
        pie.setStyle("-fx-background-color: #34495e;");
        pie.setAlignment(Pos.CENTER);

        Button btnModelos = crearBoton("Gestión de Modelos", "#e74c3c");
        Button btnFotografos = crearBoton("Gestión de Fotógrafos", "#3498db");
        Button btnLugares = crearBoton("Gestión de Lugares", "#2ecc71");
        Button btnEventos = crearBoton("Gestión de Eventos", "#f39c12");
        Button btnDashboard = crearBoton("Dashboard", "#9b59b6");
        Button btnSalir = crearBoton("Salir", "#95a5a6");

        // Acciones
        btnModelos.setOnAction(e -> new VentanaModelos(agencia).mostrar());
        btnFotografos.setOnAction(e -> new VentanaFotografos(agencia).mostrar());
        btnLugares.setOnAction(e -> new VentanaLugares(agencia).mostrar());
        btnEventos.setOnAction(e -> new VentanaEventos(agencia).mostrar());
        btnDashboard.setOnAction(e -> new VentanaDashboard(agencia).mostrar());
        btnSalir.setOnAction(e -> {
            agencia.guardar();
            primaryStage.close();
        });

        pie.getChildren().addAll(btnModelos, btnFotografos, btnLugares, btnEventos, btnDashboard, btnSalir);
        return pie;
    }

    private Button crearBoton(String texto, String color) {
        Button btn = new Button(texto);
        btn.setStyle("-fx-font-size: 12; -fx-padding: 10; -fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-weight: bold;");
        btn.setPrefWidth(130);
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-font-size: 12; -fx-padding: 10; -fx-background-color: " + oscurecerColor(color) + "; -fx-text-fill: white; -fx-font-weight: bold;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-font-size: 12; -fx-padding: 10; -fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-weight: bold;"));
        return btn;
    }

    private String oscurecerColor(String color) {
        // Simplificación: solo algunos colores
        switch (color) {
            case "#e74c3c": return "#c0392b";
            case "#3498db": return "#2980b9";
            case "#2ecc71": return "#27ae60";
            case "#f39c12": return "#d68910";
            case "#95a5a6": return "#7f8c8d";
            default: return color;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}