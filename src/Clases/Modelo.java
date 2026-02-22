package Clases;

public class Modelo extends Persona {

    private int codigoModelo;
    private float estatura;
    private String categoria;
    private boolean disponibilidad;

    public Modelo(String nombre, int identificacion, int numero,
                  int codigoModelo, float estatura, String categoria, boolean disponibilidad) {
        super(nombre, identificacion, numero);
        this.codigoModelo = codigoModelo;
        this.estatura = estatura;
        this.categoria = categoria;
        this.disponibilidad = disponibilidad;
    }

    public int getCodigoModelo() { return codigoModelo; }
    public float getEstatura() { return estatura; }
    public String getCategoria() { return categoria; }
    public boolean isDisponibilidad() { return disponibilidad; }

    public void setCodigoModelo(int codigoModelo) { this.codigoModelo = codigoModelo; }
    public void setEstatura(float estatura) { this.estatura = estatura; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public void cambiarDisponibilidad(boolean estado) {
        this.disponibilidad = estado;
    }

    public void aceptarEvento(Evento e) {
        this.disponibilidad = false;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "Modelo{" +
                "nombre='" + nombre + '\'' +
                ", identificacion=" + identificacion +
                ", numero=" + numero +
                ", codigoModelo=" + codigoModelo +
                ", estatura=" + estatura +
                ", categoria='" + categoria + '\'' +
                ", disponibilidad=" + disponibilidad +
                '}';
    }
}