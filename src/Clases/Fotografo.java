package Clases;

public class Fotografo extends Persona {

    private String especialidad;
    private float anosExperiencia;
    private int codigoFotografo;
    private float tarifaPorEvento;

    public Fotografo(String nombre, int identificacion, int numero, int codigoFotografo,
                     String especialidad, float aniosExperiencia, float tarifaPorEvento) {
        super(nombre, identificacion, numero);
        this.codigoFotografo = codigoFotografo;
        this.especialidad = especialidad;
        this.anosExperiencia = aniosExperiencia;
        this.tarifaPorEvento = tarifaPorEvento;
    }

    public String getEspecialidad() { return especialidad; }
    public float getAnosExperiencia() { return anosExperiencia; }
    public int getCodigoFotografo() { return codigoFotografo; }
    public float getTarifaPorEvento() { return tarifaPorEvento; }

    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public void setAnosExperiencia(float anosExperiencia) { this.anosExperiencia = anosExperiencia; }
    public void setCodigoFotografo(int codigoFotografo) { this.codigoFotografo = codigoFotografo; }
    public void setTarifaPorEvento(float tarifaPorEvento) { this.tarifaPorEvento = tarifaPorEvento; }

    // UML: +calcularTarifa(): double
    public double calcularTarifa() {
        return tarifaPorEvento;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "Fotografo{" +
                "nombre='" + nombre + '\'' +
                ", identificacion=" + identificacion +
                ", numero=" + numero +
                ", codigoFotografo=" + codigoFotografo +
                ", especialidad='" + especialidad + '\'' +
                ", anosExperiencia=" + anosExperiencia +
                ", tarifaPorEvento=" + tarifaPorEvento +
                '}';
    }
}