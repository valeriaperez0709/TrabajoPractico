package Clases;

import java.io.*;

public class Persistencia {

    private static final String ARCHIVO = "agencia.dat";

    // ================= GUARDAR =================
    public static void guardar(Agencia agencia) {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {

            oos.writeObject(agencia);
            System.out.println("Datos guardados correctamente en " + ARCHIVO);

        } catch (IOException e) {
            System.out.println("Error al guardar los datos: " + e.getMessage());
        }
    }

    // ================= CARGAR =================
    public static Agencia cargar() {

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(ARCHIVO))) {

            System.out.println("Archivo encontrado. Cargando datos...");
            return (Agencia) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No existe archivo previo. Iniciando sistema vacío.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar archivo: " + e.getMessage());
        }

        return null;
    }
}