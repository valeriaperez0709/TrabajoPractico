package Clases;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Persistencia {

    private static final String SEPARADOR = ";";
    private static final SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

    // ===================== GUARDAR =====================

    public static void guardarModelos(Modelo[] modelos, int numModelos, String archivo) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
        for (int i = 0; i < numModelos; i++) {
            if (modelos[i] != null) {
                Modelo m = modelos[i];
                bw.write(m.getNombre() + SEPARADOR
                        + m.getIdentificacion() + SEPARADOR
                        + m.getNumero() + SEPARADOR
                        + m.getCodigoModelo() + SEPARADOR
                        + m.getEstatura() + SEPARADOR
                        + m.getCategoria() + SEPARADOR
                        + m.isDisponibilidad());
                bw.newLine();
            }
        }
        bw.close();
    }

    public static void guardarFotografos(Fotografo[] fotografos, int numFotografos, String archivo) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
        for (int i = 0; i < numFotografos; i++) {
            if (fotografos[i] != null) {
                Fotografo f = fotografos[i];
                bw.write(f.getNombre() + SEPARADOR
                        + f.getIdentificacion() + SEPARADOR
                        + f.getNumero() + SEPARADOR
                        + f.getCodigoFotografo() + SEPARADOR
                        + f.getEspecialidad() + SEPARADOR
                        + f.getAnosExperiencia() + SEPARADOR
                        + f.getTarifaPorEvento());
                bw.newLine();
            }
        }
        bw.close();
    }

    public static void guardarLugares(Lugar[] lugares, int numLugares, String archivo) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
        for (int i = 0; i < numLugares; i++) {
            if (lugares[i] != null) {
                Lugar l = lugares[i];
                bw.write(l.getNombreDelLugar() + SEPARADOR
                        + l.getDireccion() + SEPARADOR
                        + l.getCiudad() + SEPARADOR
                        + l.getCapacidad() + SEPARADOR
                        + l.getTipoDeLugar());
                bw.newLine();
            }
        }
        bw.close();
    }

    public static void guardarPatrocinadores(Patrocinador[] patrocinadores, int numPatrocinadores, String archivo) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
        for (int i = 0; i < numPatrocinadores; i++) {
            if (patrocinadores[i] != null) {
                Patrocinador p = patrocinadores[i];
                bw.write(p.getCodigoPatrocinador() + SEPARADOR
                        + p.getNombre() + SEPARADOR
                        + p.getTipoEmpresa() + SEPARADOR
                        + p.getContacto() + SEPARADOR
                        + p.getAporteEconomico());
                bw.newLine();
            }
        }
        bw.close();
    }

    public static void guardarEventos(Evento[] eventos, int numEventos, String archivo) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
        for (int i = 0; i < numEventos; i++) {
            if (eventos[i] != null) {
                Evento e = eventos[i];
                String tipo = e.tipoEvento();
                String linea = tipo + SEPARADOR
                        + e.getNombreDeEvento() + SEPARADOR
                        + formatoFecha.format(e.getFecha()) + SEPARADOR
                        + e.getLugar().getNombreDelLugar() + SEPARADOR
                        + e.getNumModelos();

                if (e instanceof EventoPublico) {
                    EventoPublico ep = (EventoPublico) e;
                    linea += SEPARADOR + ep.tipoEvento();
                } else if (e instanceof EventoPrivado) {
                    EventoPrivado ep = (EventoPrivado) e;
                    linea += SEPARADOR + ep.getCliente() + SEPARADOR
                            + ep.getNivelConfidencialidad() + SEPARADOR
                            + ep.getPresupuesto();
                }

                bw.write(linea);
                bw.newLine();
            }
        }
        bw.close();
    }

    // ===================== CARGAR =====================

    public static int cargarModelos(Modelo[] modelos, String archivo) throws IOException {
        int count = 0;
        File file = new File(archivo);
        if (!file.exists()) return 0;

        BufferedReader br = new BufferedReader(new FileReader(file));
        String linea;
        while ((linea = br.readLine()) != null) {
            if (linea.trim().isEmpty()) continue;
            String[] datos = linea.split(SEPARADOR);
            // nombre;identificacion;numero;codigoModelo;estatura;categoria;disponibilidad
            if (datos.length >= 7 && count < modelos.length) {
                String nombre = datos[0];
                int identificacion = Integer.parseInt(datos[1]);
                int numero = Integer.parseInt(datos[2]);
                int codigoModelo = Integer.parseInt(datos[3]);
                float estatura = Float.parseFloat(datos[4]);
                String categoria = datos[5];
                boolean disponibilidad = Boolean.parseBoolean(datos[6]);

                modelos[count] = new Modelo(nombre, identificacion, numero,
                        codigoModelo, estatura, categoria, disponibilidad);
                count++;
            }
        }
        br.close();
        return count;
    }

    public static int cargarFotografos(Fotografo[] fotografos, String archivo) throws IOException {
        int count = 0;
        File file = new File(archivo);
        if (!file.exists()) return 0;

        BufferedReader br = new BufferedReader(new FileReader(file));
        String linea;
        while ((linea = br.readLine()) != null) {
            if (linea.trim().isEmpty()) continue;
            String[] datos = linea.split(SEPARADOR);
            // nombre;identificacion;numero;codigoFotografo;especialidad;anosExperiencia;tarifaPorEvento
            if (datos.length >= 7 && count < fotografos.length) {
                String nombre = datos[0];
                int identificacion = Integer.parseInt(datos[1]);
                int numero = Integer.parseInt(datos[2]);
                int codigoFotografo = Integer.parseInt(datos[3]);
                String especialidad = datos[4];
                float anosExperiencia = Float.parseFloat(datos[5]);
                float tarifaPorEvento = Float.parseFloat(datos[6]);

                fotografos[count] = new Fotografo(nombre, identificacion, numero,
                        codigoFotografo, especialidad, anosExperiencia, tarifaPorEvento);
                count++;
            }
        }
        br.close();
        return count;
    }

    public static int cargarLugares(Lugar[] lugares, String archivo) throws IOException {
        int count = 0;
        File file = new File(archivo);
        if (!file.exists()) return 0;

        BufferedReader br = new BufferedReader(new FileReader(file));
        String linea;
        while ((linea = br.readLine()) != null) {
            if (linea.trim().isEmpty()) continue;
            String[] datos = linea.split(SEPARADOR);
            // nombreDelLugar;direccion;ciudad;capacidad;tipoDeLugar
            if (datos.length >= 5 && count < lugares.length) {
                String nombreDelLugar = datos[0];
                String direccion = datos[1];
                String ciudad = datos[2];
                int capacidad = Integer.parseInt(datos[3]);
                String tipoDeLugar = datos[4];

                lugares[count] = new Lugar(nombreDelLugar, direccion, ciudad, capacidad, tipoDeLugar);
                count++;
            }
        }
        br.close();
        return count;
    }

    public static int cargarPatrocinadores(Patrocinador[] patrocinadores, String archivo) throws IOException {
        int count = 0;
        File file = new File(archivo);
        if (!file.exists()) return 0;

        BufferedReader br = new BufferedReader(new FileReader(file));
        String linea;
        while ((linea = br.readLine()) != null) {
            if (linea.trim().isEmpty()) continue;
            String[] datos = linea.split(SEPARADOR);
            // codigoPatrocinador;nombre;tipoEmpresa;contacto;aporteEconomico
            if (datos.length >= 5 && count < patrocinadores.length) {
                int codigo = Integer.parseInt(datos[0]);
                String nombre = datos[1];
                String tipoEmpresa = datos[2];
                String contacto = datos[3];
                double aporteEconomico = Double.parseDouble(datos[4]);

                patrocinadores[count] = new Patrocinador(codigo, nombre, tipoEmpresa, contacto, aporteEconomico);
                count++;
            }
        }
        br.close();
        return count;
    }
}
