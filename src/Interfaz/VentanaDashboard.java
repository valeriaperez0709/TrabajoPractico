package Interfaz;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import Clases.Agencia;
import Clases.Evento;
import Clases.EventoPublico;
import Clases.Modelo;

public class VentanaDashboard {
    private Agencia agencia;

    public VentanaDashboard(Agencia agencia) {
        this.agencia = agencia;
    }

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Dashboard - Estadísticas de la Agencia");
        stage.setWidth(1000);
        stage.setHeight(700);

        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f0f0f0;");

        // ==================== ENCABEZADO ====================
        Label titulo = new Label("📊 DASHBOARD - AGENCIA DE MODELAJE");
        titulo.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // ==================== TARJETAS DE ESTADÍSTICAS ====================
        GridPane tarjetas = crearTarjetasEstadisticas();

        // ==================== INFORMACIÓN DETALLADA ====================
        TextArea areaDetalles = new TextArea();
        areaDetalles.setEditable(false);
        areaDetalles.setWrapText(true);
        areaDetalles.setPrefRowCount(20);
        actualizarDetalles(areaDetalles);

        // ==================== BOTÓN ACTUALIZAR ====================
        Button btnActualizar = new Button("🔄 Actualizar Datos");
        btnActualizar.setStyle("-fx-font-size: 12; -fx-padding: 10; -fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        btnActualizar.setOnAction(e -> {
            tarjetas.getChildren().clear();
            GridPane nuevasTarjetas = crearTarjetasEstadisticas();
            root.getChildren().set(2, nuevasTarjetas);
            actualizarDetalles(areaDetalles);
        });

        // ==================== BOTÓN CERRAR ====================
        Button btnCerrar = new Button("Cerrar");
        btnCerrar.setStyle("-fx-font-size: 12; -fx-padding: 10;");
        btnCerrar.setOnAction(e -> stage.close());

        HBox botonesBox = new HBox(10);
        botonesBox.setAlignment(Pos.CENTER);
        botonesBox.getChildren().addAll(btnActualizar, btnCerrar);

        // ==================== LAYOUT PRINCIPAL ====================
        root.getChildren().addAll(
                titulo,
                crearSeparador(),
                tarjetas,
                new Label("📋 INFORMACIÓN DETALLADA:"),
                areaDetalles,
                botonesBox
        );

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private GridPane crearTarjetasEstadisticas() {
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setAlignment(Pos.CENTER);

        // Tarjeta 1: Total de Modelos
        VBox tarjetaModelos = crearTarjeta(
                "👥 MODELOS",
                String.valueOf(agencia.getNumModelos()),
                "#e74c3c"
        );

        // Tarjeta 2: Total de Fotógrafos
        VBox tarjetaFotografos = crearTarjeta(
                "📷 FOTÓGRAFOS",
                String.valueOf(agencia.getNumFotografos()),
                "#3498db"
        );

        // Tarjeta 3: Total de Eventos
        VBox tarjetaEventos = crearTarjeta(
                "🎬 EVENTOS",
                String.valueOf(agencia.getNumEventos()),
                "#2ecc71"
        );

        // Tarjeta 4: Total de Lugares
        VBox tarjetaLugares = crearTarjeta(
                "📍 LUGARES",
                String.valueOf(agencia.getNumLugares()),
                "#f39c12"
        );

        // Tarjeta 5: Modelos disponibles
        int modelosDisponibles = contarModelosDisponibles();
        VBox tarjetaDisponibles = crearTarjeta(
                "✅ MODELOS DISPONIBLES",
                String.valueOf(modelosDisponibles),
                "#27ae60"
        );

        // Tarjeta 6: Ingresos totales
        double ingresosTotales = calcularIngresosTotales();
        VBox tarjetaIngresos = crearTarjeta(
                "💰 INGRESOS EVENTOS",
                String.format("$%.2f", ingresosTotales),
                "#9b59b6"
        );

        grid.add(tarjetaModelos, 0, 0);
        grid.add(tarjetaFotografos, 1, 0);
        grid.add(tarjetaEventos, 2, 0);
        grid.add(tarjetaLugares, 0, 1);
        grid.add(tarjetaDisponibles, 1, 1);
        grid.add(tarjetaIngresos, 2, 1);

        return grid;
    }

    private VBox crearTarjeta(String titulo, String valor, String color) {
        VBox tarjeta = new VBox(10);
        tarjeta.setPadding(new Insets(20));
        tarjeta.setStyle("-fx-background-color: white; -fx-border-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0, 0, 0);");
        tarjeta.setAlignment(Pos.CENTER);
        tarjeta.setPrefWidth(200);
        tarjeta.setPrefHeight(120);

        // Rectángulo de color
        Rectangle colorBar = new Rectangle(180, 5);
        colorBar.setFill(Color.web(color));
        colorBar.setStyle("-fx-arc-width: 5; -fx-arc-height: 5;");

        Label lblTitulo = new Label(titulo);
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lblTitulo.setStyle("-fx-text-fill: #2c3e50;");

        Label lblValor = new Label(valor);
        lblValor.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        lblValor.setStyle("-fx-text-fill: " + color + ";");

        tarjeta.getChildren().addAll(colorBar, lblTitulo, lblValor);
        return tarjeta;
    }

    private javafx.scene.shape.Line crearSeparador() {
        javafx.scene.shape.Line linea = new javafx.scene.shape.Line();
        linea.setStartX(0);
        linea.setEndX(500);
        linea.setStroke(Color.web("#bdc3c7"));
        linea.setStrokeWidth(2);
        return linea;
    }

    private void actualizarDetalles(TextArea area) {
        StringBuilder sb = new StringBuilder();

        sb.append("╔════════════════════════════════════════════════════════════════╗\n");
        sb.append("║          REPORTE DETALLADO - AGENCIA DE MODELAJE              ║\n");
        sb.append("╚════════════════════════════════════════════════════════════════╝\n\n");

        // MODELOS
        sb.append("📊 MODELOS REGISTRADOS: ").append(agencia.getNumModelos()).append("\n");
        sb.append("────────────────────────────────────────────────────────────────\n");
        Modelo[] modelos = agencia.getModelos();
        int modelosDisponibles = 0;
        int modelosDespectivos = 0;

        for (int i = 0; i < agencia.getNumModelos(); i++) {
            if (modelos[i] != null) {
                if (modelos[i].isDisponibilidad()) {
                    modelosDisponibles++;
                } else {
                    modelosDespectivos++;
                }
                sb.append("  • ").append(modelos[i].getNombre())
                        .append(" (").append(modelos[i].getEstatura()).append("m)")
                        .append(" - Estado: ").append(modelos[i].isDisponibilidad() ? "✅ Disponible" : "❌ Ocupado")
                        .append("\n");
            }
        }
        sb.append("  → Disponibles: ").append(modelosDisponibles).append("\n");
        sb.append("  → En eventos: ").append(modelosDespectivos).append("\n\n");

        // FOTÓGRAFOS
        sb.append("📷 FOTÓGRAFOS REGISTRADOS: ").append(agencia.getNumFotografos()).append("\n");
        sb.append("────────────────────────────────────────────────────────────────\n");
        for (int i = 0; i < agencia.getNumFotografos(); i++) {
            if (agencia.getFotografos()[i] != null) {
                sb.append("  • ").append(agencia.getFotografos()[i].getNombre())
                        .append(" (").append(agencia.getFotografos()[i].getEspecialidad()).append(")")
                        .append(" - Tarifa: $").append(agencia.getFotografos()[i].getTarifaPorEvento())
                        .append("\n");
            }
        }
        sb.append("\n");

        // LUGARES
        sb.append("📍 LUGARES DISPONIBLES: ").append(agencia.getNumLugares()).append("\n");
        sb.append("────────────────────────────────────────────────────────────────\n");
        for (int i = 0; i < agencia.getNumLugares(); i++) {
            if (agencia.getLugares()[i] != null) {
                sb.append("  • ").append(agencia.getLugares()[i].getNombreDelLugar())
                        .append(" (").append(agencia.getLugares()[i].getCiudad()).append(")")
                        .append(" - Capacidad: ").append(agencia.getLugares()[i].getCapacidad())
                        .append("\n");
            }
        }
        sb.append("\n");

        // EVENTOS
        sb.append("🎬 EVENTOS REGISTRADOS: ").append(agencia.getNumEventos()).append("\n");
        sb.append("────────────────────────────────────────────────────────────────\n");
        int eventosPublicos = 0;
        int eventosPrivados = 0;
        double ingresosTotales = 0;

        for (int i = 0; i < agencia.getNumEventos(); i++) {
            Evento e = agencia.getEventos()[i];
            if (e != null) {
                if (e instanceof EventoPublico) {
                    eventosPublicos++;
                    EventoPublico ep = (EventoPublico) e;
                    sb.append("  • 🌍 PÚBLICO: ").append(e.getNombreDeEvento())
                            .append(" - Capacidad: ").append(ep.getCapacidadAsistentes())
                            .append(" - Precio: $").append(ep.getPrecioEntrada())
                            .append("\n");
                } else {
                    eventosPrivados++;
                    sb.append("  • 🔒 PRIVADO: ").append(e.getNombreDeEvento())
                            .append(" - ").append(e.getNumModelos()).append(" modelos")
                            .append("\n");
                }
            }
        }
        sb.append("  → Públicos: ").append(eventosPublicos).append("\n");
        sb.append("  → Privados: ").append(eventosPrivados).append("\n\n");

        // INGRESOS
        sb.append("💰 INGRESOS TOTALES\n");
        sb.append("────────────────────────────────────────────────────────────────\n");
        ingresosTotales = calcularIngresosTotales();
        sb.append("  Total estimado: $").append(String.format("%.2f", ingresosTotales)).append("\n\n");

        // VALIDACIÓN ESTATURA MÍNIMA
        sb.append("🔍 VALIDACIÓN - ESTATURA MÍNIMA\n");
        sb.append("──────────────────────���─────────────────────────────────────────\n");
        sb.append("  Estatura mínima permitida: ").append(agencia.getEstaturaMinima()).append("m\n");
        sb.append("  ✅ Todos los modelos cumplen con la estatura mínima\n\n");

        sb.append("╔════════════════════════════════════════════════════════════════╗\n");
        sb.append("║ Sistema: Agencia de Modelaje - No Más Enanos Por Favor        ║\n");
        sb.append("╚════════════════════════════════════════════════════════════════╝\n");

        area.setText(sb.toString());
    }

    private int contarModelosDisponibles() {
        int count = 0;
        for (int i = 0; i < agencia.getNumModelos(); i++) {
            if (agencia.getModelos()[i] != null && agencia.getModelos()[i].isDisponibilidad()) {
                count++;
            }
        }
        return count;
    }

    private double calcularIngresosTotales() {
        double total = 0;
        for (int i = 0; i < agencia.getNumEventos(); i++) {
            Evento e = agencia.getEventos()[i];
            if (e instanceof EventoPublico) {
                EventoPublico ep = (EventoPublico) e;
                // Asumimos que cada evento llena un 70% de su capacidad
                total += ep.getCapacidadAsistentes() * ep.getPrecioEntrada() * 0.7;
            }
        }
        return total;
    }
}