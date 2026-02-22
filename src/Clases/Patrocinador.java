package Clases;

import java.io.Serializable;

public class Patrocinador implements Serializable {

    private String nombre;
    private String tipoEmpresa;
    private String contacto;
    private double aporteEconomico;

    public Patrocinador(String nombre, String tipoEmpresa, String contacto, double aporteEconomico) {
        this.nombre = nombre;
        this.tipoEmpresa = tipoEmpresa;
        this.contacto = contacto;
        this.aporteEconomico = aporteEconomico;
    }

    public String getNombre() { return nombre; }
    public String getTipoEmpresa() { return tipoEmpresa; }
    public String getContacto() { return contacto; }
    public double getAporteEconomico() { return aporteEconomico; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTipoEmpresa(String tipoEmpresa) { this.tipoEmpresa = tipoEmpresa; }
    public void setContacto(String contacto) { this.contacto = contacto; }
    public void setAporteEconomico(double aporteEconomico) { this.aporteEconomico = aporteEconomico; }

    public void mostrarInformacion() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "Patrocinador{" +
                "nombre='" + nombre + '\'' +
                ", tipoEmpresa='" + tipoEmpresa + '\'' +
                ", contacto='" + contacto + '\'' +
                ", aporteEconomico=" + aporteEconomico +
                '}';
    }
}