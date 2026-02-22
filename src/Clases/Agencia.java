package Clases;

import java.io.Serializable;

public class Agencia implements Serializable {
    private Fotografo[] fotografos;
    private Modelo[] modelos;
    private Evento[] eventos;
    private int numFotografos;
    private int numEventos;
    private int numModelos;

    public Agencia(int maxFotografos, int maxModelos, int maxEventos) {
        fotografos = new Fotografo[maxFotografos];
        modelos = new Modelo[maxModelos];
        eventos = new Evento[maxEventos];

        numFotografos = 0;
        numModelos = 0;
        numEventos = 0;
    }

    public void eliminarModelo(Modelo m) throws ValorInexistente, DatoInvalido {
        if (m == null) {
            throw new DatoInvalido("El modelo no puede ser null");
        }
        for (int i = 0; i < numModelos; i++) {
            if ( modelos[i].equals(m)) {
                for (int j = i; j < numModelos - 1; j++) {
                    modelos[j] = modelos[j + 1];
                }
                modelos[numModelos - 1] = null;
                numModelos--;
                return;
            }
        }
        throw new ValorInexistente("Modelo no encontrado");
    }

    public void agregarModelo(Modelo m) throws DatoInvalido, Duplicado, CapacidadMaxima {

        if (m == null) {
            throw new DatoInvalido("El modelo no puede ser null");
        }

        if (buscarModeloPorCodigo(m.getCodigoModelo()) != null) {
            throw new Duplicado("Ya existe un modelo con ese código");
        }

        if (numModelos >= modelos.length) {
            throw new CapacidadMaxima("No hay espacio para más modelos");
        }
        modelos[numModelos++] = m;

    }

    public Modelo buscarModeloPorCodigo(int codigo) {
        for (int i = 0; i < numModelos; i++) {
            if (modelos[i] != null &&
                    modelos[i].getCodigoModelo() == codigo) {
                return modelos[i];
            }
        }
        return null;
    }

    public void asignarModeloAEvento(Evento e, Modelo m)
            throws DatoInvalido, ValorInexistente {

        if (e == null || m == null) {
            throw new DatoInvalido("Evento o modelo inválido");
        }

        if (buscarModeloPorCodigo(m.getCodigoModelo()) == null) {
            throw new ValorInexistente("El modelo no pertenece a la agencia");
        }

        e.agregarModelo(m);
    }

    public void agregarFotografo(Fotografo f) throws DatoInvalido, Duplicado, CapacidadMaxima {
        if (f == null) {
            throw new DatoInvalido("El fotografo no puede ser null");
        }

        if (buscarModeloPorCodigo(f.getCodigoFotografo()) != null) {
            throw new Duplicado("Ya existe un fotografo con ese código");
        }

        if (numModelos >= modelos.length) {
            throw new CapacidadMaxima("No hay espacio para más fotografos");
        }
            numFotografos++;

    }

    public void eliminarFotografo(Fotografo f) throws ValorInexistente, DatoInvalido {
        if (f == null) {
            throw new DatoInvalido("El fotografo no puede ser null");
        }
        for (int i = 0; i < numFotografos; i++) {
            if (fotografos[i].equals(f)) {
                for (int j = i; j < numFotografos - 1; j++) {
                    fotografos[j] = fotografos[j + 1];
                }
                fotografos[numFotografos - 1] = null;
                numFotografos--;
                return;
            }
        }
        throw new ValorInexistente("Fotografo no encontrado");

    }

    public Fotografo buscarFotografoPorCodigo(int codigo) {
        for (int i = 0; i < numFotografos; i++) {
            if (fotografos[i] != null &&
                    fotografos[i].getCodigoFotografo() == codigo) {
                return fotografos[i];
            }
        }
        return null;
    }

    public void agregarEventos(Evento e) {
        if (numEventos < eventos.length) {
            eventos[numEventos] = e;
            numEventos++;
        }
    }

    public void eliminarEvento(Evento e) throws ValorInexistente, DatoInvalido{
        if (e == null) {
            throw new DatoInvalido("El evento no puede ser null");
        }
        for (int i = 0; i < numEventos; i++) {
            if ( eventos[i].equals(e)) {
                for (int j = i; j < numEventos - 1; j++) {
                    eventos[j] = eventos[j + 1];
                }
                eventos[numEventos - 1] = null;
                numEventos--;
                return;
            }
        }
        throw new ValorInexistente("Fotografo no encontrado");
    }

    public Evento buscarEventoPorNombre(String nombre) {
        for (int i = 0; i < numEventos; i++) {
            if (eventos[i] != null &&
                    eventos[i].getNombreDeEvento().equalsIgnoreCase(nombre)) {
                return eventos[i];
            }
        }
        return null;
    }

    public void asignarEvento() {
        // Falta
    }

    public void cargar() {
        // Falta
    }

    public void guardar() {
        // Falta
    }

    public void generarReporte() {
        System.out.println("REPORTE AGENCIA");
        System.out.println("Modelos: " + numModelos);
        System.out.println("Fotografos: " + numFotografos);
        System.out.println("Eventos: " + numEventos);
    }
}