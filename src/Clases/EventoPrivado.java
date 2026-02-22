package Clases;

import java.util.Date;

public class EventoPrivado extends Evento {

    private String cliente;
    private int nivelConfidencialidad;
    private float presupuesto;

    public EventoPrivado(String nombreDeEvento, Date fecha, Lugar lugar, int maxModelos,
                         String cliente, int nivelConfidencialidad, float presupuesto) {
        super(nombreDeEvento, fecha, lugar, maxModelos);
        this.cliente = cliente;
        this.nivelConfidencialidad = nivelConfidencialidad;
        this.presupuesto = presupuesto;
    }

    public String getCliente() { return cliente; }
    public int getNivelConfidencialidad() { return nivelConfidencialidad; }
    public float getPresupuesto() { return presupuesto; }

    public void setCliente(String cliente) { this.cliente = cliente; }
    public void setNivelConfidencialidad(int nivelConfidencialidad) { this.nivelConfidencialidad = nivelConfidencialidad; }
    public void setPresupuesto(float presupuesto) { this.presupuesto = presupuesto; }

    public boolean verificarAcceso() {
        // UML no define regla. Ejemplo simple:
        return nivelConfidencialidad <= 3;
    }

    @Override
    public String tipoEvento() {
        return "PRIVADO";
    }
}