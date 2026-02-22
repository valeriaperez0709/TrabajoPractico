package Clases;

import java.io.Serializable;

public abstract class Persona implements Serializable {

    protected String nombre;
    protected int identificacion;
    protected int numero;

    public Persona(String nombre, int identificacion, int numero) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdentificacion() {
        return identificacion;
    }

    public int getNumero() {
        return numero;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setIdentificacion(int identificacion) {
        this.identificacion = identificacion;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public abstract void mostrarInformacion();
}