package Clases;

import java.io.Serializable;
import java.util.Date;

public abstract class Evento implements Serializable {

    protected String nombreDeEvento;
    protected Date fecha;
    protected Lugar lugar;
    protected Modelo[] modelos;
    protected Fotografo fotografo;
    protected int numModelos;

    public Evento(String nombreDeEvento, Date fecha, Lugar lugar, int maxModelos) {
        this.nombreDeEvento = nombreDeEvento;
        this.fecha = fecha;
        this.lugar = lugar;
        this.modelos = new Modelo[maxModelos];
        this.numModelos = 0;
    }

    public String getNombreDeEvento() { return nombreDeEvento; }
    public Date getFecha() { return fecha; }
    public Lugar getLugar() { return lugar; }
    public Modelo[] getModelos() { return modelos; }
    public int getNumModelos() { return numModelos; }
    public Fotografo getFotografo() { return fotografo; }

    public void setFotografo(Fotografo fotografo) { this.fotografo = fotografo; }

    public boolean agregarModelo(Modelo m) {
        if (numModelos >= modelos.length) return false;
        modelos[numModelos] = m;
        numModelos++;
        return true;
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
                ", fotografo=" + (fotografo != null ? fotografo.getCodigoFotografo() : "null") +
                ", tipo='" + tipoEvento() + '\'' +
                '}';
    }
}
