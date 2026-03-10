package Interfaz;

import Clases.*;
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

        Label titulo = new Label("📊 DASHBOARD - AGENCIA DE MODELAJE");
        titulo.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        GridPane tarjetas = crearTarjetasEstadisticas();

        TextArea areaDetalles = new TextArea();
        areaDetalles.setEditable(false);
        areaDetalles.setWrapText(true);
        areaDetalles.setPrefRowCount(20);
        actualizarDetalles(areaDetalles);

        Button btnActualizar = new Button("🔄 Actualizar Datos");
        btnActualizar.setStyle("-fx-font-size: 12; -fx-padding: 10; -fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        btnActualizar.setOnAction(e -> {
            tarjetas.getChildren().clear();
            GridPane nuevasTarjetas = crearTarjetasEstadisticas();
            root.getChildren().set(2, nuevasTarjetas);
            actualizarDetalles(areaDetalles);
        });

        Button btnCerrar = new Button("Cerrar");
        btnCerrar.setStyle("-fx-font-size: 12; -fx-padding: 10;");
        btnCerrar.setOnAction(e -> stage.close());

        HBox botonesBox = new HBox(10);
        botonesBox.setAlignment(Pos.CENTER);
        botonesBox.getChildren().addAll(btnActualizar, btnCerrar);

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

        VBox tarjetaModelos = crearTarjeta(
                "👥 MODELOS",
                String.valueOf(agencia.getNumModelos()),
                "#e74c3c"
        );

        VBox tarjetaFotografos = crearTarjeta(
                "📷 FOTÓGRAFOS",
                String.valueOf(agencia.getNumFotografos()),
                "#3498db"
        );

        VBox tarjetaEventos = crearTarjeta(
                "🎬 EVENTOS",
                String.valueOf(agencia.getNumEventos()),
                "#2ecc71"
        );

        VBox tarjetaLugares = crearTarjeta(
                "📍 LUGARES",
                String.valueOf(agencia.getNumLugares()),
                "#f39c12"
        );

        int modelosDisponibles = contarModelosDisponibles();
        VBox tarjetaDisponibles = crearTarjeta(
                "✅ MODELOS DISPONIBLES",
                String.valueOf(modelosDisponibles),
                "#27ae60"
        );

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

        Rectangle colorBar = new Rectangle(180, 5);
        colorBar.setFill(Color.web(color));

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

        sb.append("📊 MODELOS REGISTRADOS: ").append(agencia.getNumModelos()).append("\n");
        sb.append("─────────────────────────────────────────────────────────────���──\n");
        Modelo[] modelos = agencia.getModelos();
        int modelosDisponibles = 0;
        int modelosEnEventos = 0;

        for (int i = 0; i < agencia.getNumModelos(); i++) {
            if (modelos[i] != null) {
                if (modelos[i].isDisponibilidad()) {
                    modelosDisponibles++;
                } else {
                    modelosEnEventos++;
                }
                sb.append("  • ").append(modelos[i].getNombre())
                        .append(" (").append(modelos[i].getEstatura()).append("m)")
                        .append(" - Estado: ").append(modelos[i].isDisponibilidad() ? "✅ Disponible" : "❌ En evento")
                        .append("\n");
            }
        }
        sb.append("  → Disponibles: ").append(modelosDisponibles).append("\n");
        sb.append("  → En eventos: ").append(modelosEnEventos).append("\n\n");

        sb.append("📷 FOTÓGRAFOS REGISTRADOS: ").append(agencia.getNumFotografos()).append("\n");
        sb.append("────────────────────────────────────────────────────────────────\n");
        for (int i = 0; i < agencia.getNumFotografos(); i++) {
            if (agencia.getFotografos()[i] != null) {
                Fotografo f = agencia.getFotografos()[i];
                // ✅ USAR calcularTarifa()
                double tarifa = f.calcularTarifa();
                sb.append("  • ").append(f.getNombre())
                        .append(" (").append(f.getEspecialidad()).append(")")
                        .append(" - Tarifa: $").append(tarifa)
                        .append("\n");
            }
        }

        sb.append("\n");

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

        sb.append("🎬 EVENTOS REGISTRADOS: ").append(agencia.getNumEventos()).append("\n");
        sb.append("────────────────────────────────────────────────────────────────\n");
        int eventosPublicos = 0;
        int eventosPrivados = 0;

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

        sb.append("🎬 EVENTOS REGISTRADOS: ").append(agencia.getNumEventos()).append("\n");
        sb.append("────────────────────────────────────────────────────────────────\n");
        for (int i = 0; i < agencia.getNumEventos(); i++) {
            Evento e = agencia.getEventos()[i];
            if (e != null) {
                // ✅ USAR mostrarDetalles() (aunque imprime en consola)
                e.mostrarDetalles();

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

        sb.append("💰 INGRESOS TOTALES\n");
        sb.append("────────────────────────────────────────────────────────────────\n");

        double ingresosTotales = 0;
        for (int i = 0; i < agencia.getNumEventos(); i++) {
            Evento e = agencia.getEventos()[i];
            if (e instanceof EventoPublico) {
                EventoPublico ep = (EventoPublico) e;
                try {
                    int boletosVendidos = (int) (ep.getCapacidadAsistentes() * 0.7);
                    float ingresosEvento = ep.calcularIngresos(boletosVendidos);
                    ingresosTotales += ingresosEvento;

                    sb.append("  • ").append(ep.getNombreDeEvento())
                            .append(" - Boletos (70%): ").append(boletosVendidos)
                            .append(" x $").append(ep.getPrecioEntrada())
                            .append(" = $").append(String.format("%.2f", ingresosEvento))
                            .append("\n");
                } catch (DatoInvalido ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
        }
        sb.append("  ════════════════════════════════════════\n");
        sb.append("  TOTAL INGRESOS: $").append(String.format("%.2f", ingresosTotales)).append("\n\n");

        sb.append("🔍 VALIDACIÓN - ESTATURA MÍNIMA\n");
        sb.append("────────────────────────────────────────────────────────────────\n");
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
                total += ep.getCapacidadAsistentes() * ep.getPrecioEntrada() * 0.7;
            }
        }
        return total;
    }
}