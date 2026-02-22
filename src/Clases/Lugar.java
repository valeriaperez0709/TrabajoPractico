package Clases;

import java.io.Serializable;
import java.time.LocalDate;

public class Lugar implements Serializable {

    private String nombreDelLugar;
    private String direccion;
    private String ciudad;
    private int capacidad;
    private String tipoDeLugar;

    public Lugar(String nombreDelLugar, String direccion, String ciudad, int capacidad, String tipoDeLugar) {
        this.nombreDelLugar = nombreDelLugar;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.capacidad = capacidad;
        this.tipoDeLugar = tipoDeLugar;
    }

    public String getNombreDelLugar() { return nombreDelLugar; }
    public String getDireccion() { return direccion; }
    public String getCiudad() { return ciudad; }
    public int getCapacidad() { return capacidad; }
    public String getTipoDeLugar() { return tipoDeLugar; }

    public void setNombreDelLugar(String nombreDelLugar) { this.nombreDelLugar = nombreDelLugar; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }
    public void setTipoDeLugar(String tipoDeLugar) { this.tipoDeLugar = tipoDeLugar; }

    public boolean estaDisponible(LocalDate fecha) {
        return true;
    }

    @Override
    public String toString() {
        return "Lugar{" +
                "nombreDelLugar='" + nombreDelLugar + '\'' +
                ", direccion='" + direccion + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", capacidad=" + capacidad +
                ", tipoDeLugar='" + tipoDeLugar + '\'' +
                '}';
    }
}
