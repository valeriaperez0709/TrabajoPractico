package Clases;

import java.io.Serializable;
import java.util.Date;

public abstract class Evento implements Serializable {

    protected String nombreDeEvento;
    protected Date fecha;
    protected Lugar lugar;
    protected Modelo[] modelos;
    protected Fotografo[] fotografos;
    protected int numModelos;
    protected int numFotografos;

    public Evento(String nombreDeEvento, Date fecha, Lugar lugar, int maxModelos, int maxFotografos) {
        this.nombreDeEvento = nombreDeEvento;
        this.fecha = fecha;
        this.lugar = lugar;
        this.modelos = new Modelo[maxModelos];
        this.numModelos = 0;
        this.fotografos= new Fotografo[maxFotografos];
        this.numFotografos= 0;
    }

    public String getNombreDeEvento() { return nombreDeEvento; }
    public Date getFecha() { return fecha; }
    public Lugar getLugar() { return lugar; }
    public Modelo[] getModelos() { return modelos; }
    public int getNumModelos() { return numModelos; }
    public Fotografo[] getFotografos() { return fotografos; }

    public void setNombreDeEvento(String nombreDeEvento) {
        this.nombreDeEvento = nombreDeEvento;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setLugar(Lugar lugar) {
        this.lugar = lugar;
    }

    public void setModelos(Modelo[] modelos) {

        this.modelos = modelos;
    }

    public void setFotografos(Fotografo[] fotografos) {
        this.fotografos = fotografos;
    }

    public void setNumModelos(int numModelos) {
        this.numModelos = numModelos;
    }

    public void setNumFotografos(int numFotografos) {
        this.numFotografos = numFotografos;
    }


    public void agregarModelo(Modelo m) throws CapacidadMaxima {
        if (numModelos >= modelos.length) {
            throw new CapacidadMaxima("El evento ya tiene el máximo de modelos");
        }

        modelos[numModelos++] = m;
    }
    public void agregarFotografo(Fotografo f) throws CapacidadMaxima {
        if (numFotografos >= fotografos.length) {
            throw new CapacidadMaxima("El evento ya tiene el máximo de fotografos");
        }

        fotografos[numFotografos++] = f;
    }

    public void mostrarDetalles() {
        System.out.println(toString());
    }

    public abstract String tipoEvento();

    @Override
    public String toString() {
        return "Evento{" +
                "nombreDeEvento='" + nombreDeEvento + '\'' +
                ", fecha=" + fecha +
                ", lugar=" + (lugar != null ? lugar.getNombreDelLugar() : "null") +
                ", numModelos=" + numModelos +
                ", numFotografos=" + numFotografos +
                ", tipo='" + tipoEvento() + '\'' +
                '}';
    }
}
