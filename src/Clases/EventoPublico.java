package Clases;

import java.util.Date;

public class EventoPublico extends Evento {

    private int capacidadAsistentes;
    private Patrocinador[] patrocinadores;
    private float precioEntrada;
    private int numPatrocinadores;

    public EventoPublico(String nombreDeEvento,
                         Date fecha,
                         Lugar lugar,
                         int maxModelos,
                         int capacidadAsistentes,
                         float precioEntrada,
                         int maxPatrocinadores) {

        super(nombreDeEvento, fecha, lugar, maxModelos);

        this.capacidadAsistentes = capacidadAsistentes;
        this.precioEntrada = precioEntrada;
        this.patrocinadores = new Patrocinador[maxPatrocinadores];
        this.numPatrocinadores = 0;
    }

    public float calcularIngresos(int boletosVendidos) throws DatoInvalido {
        if(boletosVendidos<0){
            throw new DatoInvalido("Los boletos vendidos no pueden ser negativos");
        }
        return boletosVendidos * precioEntrada;
    }

    @Override
    public String tipoEvento() {
        return "PUBLICO";
    }

    public void agregarPatrocinador(Patrocinador p) {
        if (numPatrocinadores < patrocinadores.length) {
            patrocinadores[numPatrocinadores] = p;
            numPatrocinadores++;
        }
    }

    public void eliminarPatrocinador(Patrocinador p)  throws ValorInexistente, DatoInvalido {
        if (p == null) {
            throw new DatoInvalido("El patrocinador no puede ser null");
        }
        for (int i = 0; i < numPatrocinadores; i++) {
            if (patrocinadores[i].equals(p)) {

                for (int j = i; j < numPatrocinadores - 1; j++) {
                    patrocinadores[j] = patrocinadores[j + 1];
                }

                patrocinadores[numPatrocinadores - 1] = null;
                numPatrocinadores--;
                return;
            }
        }
        throw new ValorInexistente("Patrocinador no encontrado");
    }

    public Patrocinador buscarPatrocinador() {
        if (numPatrocinadores > 0) {
            return patrocinadores[0];
        }
        return null;
    }

    public void listaPatrocinadores() {
        for (int i = 0; i < numPatrocinadores; i++) {
            if (patrocinadores[i] != null) {
                patrocinadores[i].mostrarInformacion();
            }
        }
    }

    public void calcularTotalPatrocinios() {
        double total = 0;

        for (int i = 0; i < numPatrocinadores; i++) {
            if (patrocinadores[i] != null) {
                total += patrocinadores[i].getAporteEconomico();
            }
        }

        System.out.println("Total de patrocinios: " + total);
    }
}
