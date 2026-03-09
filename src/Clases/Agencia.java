package Clases;

import java.io.Serializable;

public class Agencia implements Serializable {
    private Fotografo[] fotografos;
    private Modelo[] modelos;
    private Evento[] eventos;
    private Lugar[] lugares;
    private int numFotografos;
    private int numEventos;
    private int numModelos;
    private int numLugares;

    private static final float ESTATURA_MINIMA = 1.50f;

    public Agencia(int maxFotografos, int maxModelos, int maxEventos) {
        fotografos = new Fotografo[maxFotografos];
        modelos = new Modelo[maxModelos];
        eventos = new Evento[maxEventos];
        lugares = new Lugar[50];

        numFotografos = 0;
        numModelos = 0;
        numEventos = 0;
        numLugares = 0;
    }

    // ===================== GETTERS =====================

    public Fotografo[] getFotografos() { return fotografos; }
    public Modelo[] getModelos() { return modelos; }
    public Evento[] getEventos() { return eventos; }
    public Lugar[] getLugares() { return lugares; }
    public int getNumFotografos() { return numFotografos; }
    public int getNumModelos() { return numModelos; }
    public int getNumEventos() { return numEventos; }
    public int getNumLugares() { return numLugares; }
    public static float getEstaturaMinima() { return ESTATURA_MINIMA; }

    // ===================== MODELOS =====================

    public void eliminarModelo(Modelo m) throws ValorInexistente, DatoInvalido {
        if (m == null) {
            throw new DatoInvalido("El modelo no puede ser null");
        }
        for (int i = 0; i < numModelos; i++) {
            if (modelos[i].equals(m)) {
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
        if (m.getEstatura() < ESTATURA_MINIMA) {
            throw new DatoInvalido("La estatura mínima permitida es " + ESTATURA_MINIMA + "m. El modelo mide " + m.getEstatura() + "m");
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

    public void listarModelos() {
        for (int i = 0; i < numModelos; i++) {
            System.out.println(modelos[i]);
        }
    }

    public void asignarModeloAEvento(Evento e, Modelo m)
            throws DatoInvalido, ValorInexistente, CapacidadMaxima {
        if (e == null || m == null) {
            throw new DatoInvalido("Evento o modelo inválido");
        }
        if (buscarModeloPorCodigo(m.getCodigoModelo()) == null) {
            throw new ValorInexistente("El modelo no pertenece a la agencia");
        }
        e.agregarModelo(m);
    }

    // ===================== FOTOGRAFOS =====================

    public void agregarFotografo(Fotografo f) throws DatoInvalido, Duplicado, CapacidadMaxima {
        if (f == null) {
            throw new DatoInvalido("El fotografo no puede ser null");
        }
        if (buscarFotografoPorCodigo(f.getCodigoFotografo()) != null) {
            throw new Duplicado("Ya existe un fotografo con ese código");
        }
        if (numFotografos >= fotografos.length) {
            throw new CapacidadMaxima("No hay espacio para más fotografos");
        }
        fotografos[numFotografos++] = f;
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

    public void listarFotografos() {
        for (int i = 0; i < numFotografos; i++) {
            System.out.println(fotografos[i]);
        }
    }

    // ===================== LUGARES =====================

    public void agregarLugar(Lugar l) throws DatoInvalido, Duplicado, CapacidadMaxima {
        if (l == null) {
            throw new DatoInvalido("El lugar no puede ser null");
        }
        if (buscarLugarPorNombre(l.getNombreDelLugar()) != null) {
            throw new Duplicado("Ya existe un lugar con ese nombre");
        }
        if (numLugares >= lugares.length) {
            throw new CapacidadMaxima("No hay espacio para más lugares");
        }
        lugares[numLugares++] = l;
    }

    public void eliminarLugar(Lugar l) throws ValorInexistente, DatoInvalido {
        if (l == null) {
            throw new DatoInvalido("El lugar no puede ser null");
        }
        for (int i = 0; i < numLugares; i++) {
            if (lugares[i].equals(l) || lugares[i].getNombreDelLugar().equalsIgnoreCase(l.getNombreDelLugar())) {
                for (int j = i; j < numLugares - 1; j++) {
                    lugares[j] = lugares[j + 1];
                }
                lugares[numLugares - 1] = null;
                numLugares--;
                return;
            }
        }
        throw new ValorInexistente("Lugar no encontrado");
    }

    public Lugar buscarLugarPorNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            return null;
        }
        for (int i = 0; i < numLugares; i++) {
            if (lugares[i] != null &&
                    lugares[i].getNombreDelLugar().equalsIgnoreCase(nombre)) {
                return lugares[i];
            }
        }
        return null;
    }

    public void listarLugares() {
        for (int i = 0; i < numLugares; i++) {
            if (lugares[i] != null) {
                System.out.println(lugares[i]);
            }
        }
    }

    // ===================== EVENTOS =====================

    public void agregarEventos(Evento e) throws DatoInvalido, Duplicado, CapacidadMaxima {
        if (e == null) {
            throw new DatoInvalido("El evento no puede ser null");
        }
        if (buscarEventoPorNombre(e.getNombreDeEvento()) != null) {
            throw new Duplicado("Ya existe un evento con ese nombre");
        }
        if (numEventos >= eventos.length) {
            throw new CapacidadMaxima("No hay espacio para más eventos");
        }
        eventos[numEventos++] = e;
    }

    public void eliminarEvento(Evento e) throws ValorInexistente, DatoInvalido {
        if (e == null) {
            throw new DatoInvalido("El evento no puede ser null");
        }
        for (int i = 0; i < numEventos; i++) {
            if (eventos[i].equals(e)) {
                for (int j = i; j < numEventos - 1; j++) {
                    eventos[j] = eventos[j + 1];
                }
                eventos[numEventos - 1] = null;
                numEventos--;
                return;
            }
        }
        throw new ValorInexistente("Evento no encontrado");
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

    public void listarEventos() {
        for (int i = 0; i < numEventos; i++) {
            System.out.println(eventos[i]);
        }
    }

    // ===================== ASIGNAR FOTOGRAFO A EVENTO =====================

    public void asignarFotografoAEvento(Evento e, Fotografo f)
            throws DatoInvalido, ValorInexistente, CapacidadMaxima {
        if (e == null || f == null) {
            throw new DatoInvalido("Evento o fotografo inválido");
        }
        if (buscarFotografoPorCodigo(f.getCodigoFotografo()) == null) {
            throw new ValorInexistente("El fotografo no pertenece a la agencia");
        }
        e.agregarFotografo(f);
    }

    // ===================== REPORTE =====================

    public void generarReporte() {
        System.out.println("========== REPORTE AGENCIA ==========");
        System.out.println("Total Modelos: " + numModelos);
        System.out.println("Total Fotografos: " + numFotografos);
        System.out.println("Total Eventos: " + numEventos);
        System.out.println("Total Lugares: " + numLugares);
        System.out.println();

        System.out.println("--- MODELOS ---");
        listarModelos();
        System.out.println();

        System.out.println("--- FOTOGRAFOS ---");
        listarFotografos();
        System.out.println();

        System.out.println("--- EVENTOS ---");
        listarEventos();

        System.out.println("--- LUGARES ---");
        listarLugares();
        System.out.println("=====================================");
    }
}