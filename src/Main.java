import Clases.*;




import java.util.Scanner;

    public class Main {

        public static void main(String[] args) {

            Scanner sc = new Scanner(System.in);

            Agencia agencia = new Agencia(100,100,100);

            // cargar datos desde los archivos
            agencia.cargar();

            int opcion = -1;

            while(opcion != 0){

                System.out.println("\n===== MENU AGENCIA =====");
                System.out.println("1. Agregar modelo");
                System.out.println("2. Agregar fotografo");
                System.out.println("3. Agregar lugar");
                System.out.println("4. Crear evento");
                System.out.println(("5. Asisgnar Modelo a Evento"));
                System.out.println("6. Listar modelos");
                System.out.println("7. Listar fotografos");
                System.out.println("8. Listar lugares");
                System.out.println("9. Listar eventos");
                System.out.println("10. Generar reporte");
                System.out.println("11. Guardar datos");
                System.out.println("0. Salir");

                System.out.print("Opcion: ");
                opcion = sc.nextInt();
                sc.nextLine();

                try{

                    switch(opcion){

                        case 1:

                            System.out.print("Nombre: ");
                            String nombre = sc.nextLine();

                            System.out.print("Identificacion: ");
                            int id = sc.nextInt();

                            System.out.print("Numero: ");
                            int numero = sc.nextInt();

                            System.out.print("Codigo modelo: ");
                            int codigo = sc.nextInt();

                            System.out.print("Estatura: ");
                            float estatura = sc.nextFloat();
                            sc.nextLine();

                            System.out.print("Categoria: ");
                            String categoria = sc.nextLine();

                            System.out.print("Disponible (true/false): ");
                            boolean disp = sc.nextBoolean();

                            Modelo m = new Modelo(nombre,id,numero,codigo,estatura,categoria,disp);

                            agencia.agregarModelo(m);

                            System.out.println("Modelo agregado.");
                            break;

                        case 2:

                            System.out.print("Nombre: ");
                            String nom = sc.nextLine();

                            System.out.print("Identificacion: ");
                            int ide = sc.nextInt();

                            System.out.print("Numero: ");
                            int num = sc.nextInt();

                            System.out.print("Codigo fotografo: ");
                            int cod = sc.nextInt();
                            sc.nextLine();

                            System.out.print("Especialidad: ");
                            String esp = sc.nextLine();

                            System.out.print("Años experiencia: ");
                            float anos = sc.nextFloat();

                            System.out.print("Tarifa evento: ");
                            float tarifa = sc.nextFloat();

                            Fotografo f = new Fotografo(nom,ide,num,cod,esp,anos,tarifa);

                            agencia.agregarFotografo(f);

                            System.out.println("Fotografo agregado.");
                            break;

                        case 3:

                            System.out.print("Nombre lugar: ");
                            String nombreLugar = sc.nextLine();

                            System.out.print("Direccion: ");
                            String direccion = sc.nextLine();

                            System.out.print("Ciudad: ");
                            String ciudad = sc.nextLine();

                            System.out.print("Capacidad: ");
                            int capacidad = sc.nextInt();
                            sc.nextLine();

                            System.out.print("Tipo de lugar: ");
                            String tipo = sc.nextLine();

                            Lugar l = new Lugar(nombreLugar,direccion,ciudad,capacidad,tipo);

                            agencia.agregarLugar(l);

                            System.out.println("Lugar agregado.");
                            break;



                        case 4:

                            System.out.println("Tipo de evento:");
                            System.out.println("1. Evento Publico");
                            System.out.println("2. Evento Privado");
                            int tipoEvento = sc.nextInt();
                            sc.nextLine();

                            System.out.print("Nombre del evento: ");
                            String nombreEvento = sc.nextLine();

                            System.out.print("Capacidad maxima de modelos: ");
                            int maxModelos = sc.nextInt();

                            System.out.print("Capacidad del lugar: ");
                            int capacidadLugar = sc.nextInt();
                            sc.nextLine();

                            Lugar lugarEvento = new Lugar("Lugar temporal","Direccion","Ciudad",capacidadLugar,"Salon");

                            if(tipoEvento == 1){

                                System.out.print("Capacidad asistentes: ");
                                int asistentes = sc.nextInt();

                                System.out.print("Precio entrada: ");
                                float precio = sc.nextFloat();

                                EventoPublico ep = new EventoPublico(
                                        nombreEvento,
                                        new java.util.Date(),
                                        lugarEvento,
                                        maxModelos,
                                        asistentes,
                                        precio,
                                        50
                                );

                                agencia.agregarEventos(ep);

                                System.out.println("Evento publico creado.");

                            }else if(tipoEvento == 2){
                                // Ejemplo de cómo debería quedar tu bloque de código:
                                System.out.print("Nombre del cliente: ");
                                String cliente = sc.nextLine();

                                System.out.print("Nivel de confidencialidad (1-5): ");
                                int nivel = sc.nextInt();

                                System.out.print("Presupuesto del evento: ");
                                float presupuesto = sc.nextFloat();
                                sc.nextLine(); // Limpiar el buffer

// Finalmente, creas el objeto con TODOS los datos:
                                EventoPrivado ev = new EventoPrivado(nombreEvento, new java.util.Date(), lugarEvento, maxModelos, cliente, nivel, presupuesto);


                                agencia.agregarEventos(ev);

                                System.out.println("Evento privado creado.");

                            }else{

                                System.out.println("Tipo de evento invalido.");

                            }

                            break;
                        case 5:

                            System.out.print("Nombre del evento: ");
                            String nombreEv = sc.nextLine();

                            Evento evento = agencia.buscarEventoPorNombre(nombreEv);

                            if(evento == null){
                                System.out.println("El evento no existe.");
                                break;
                            }

                            System.out.print("Codigo del modelo: ");
                            int codModelo = sc.nextInt();
                            sc.nextLine();

                            Modelo modelo = agencia.buscarModeloPorCodigo(codModelo);

                            if(modelo == null){
                                System.out.println("El modelo no existe en la agencia.");
                                break;
                            }

                            agencia.asignarModeloAEvento(evento, modelo);

                            System.out.println("Modelo asignado al evento correctamente.");
                            break;

                        case 6:
                            agencia.listarModelos();
                            break;

                        case 7:
                            agencia.listarFotografos();
                            break;

                        case 8:
                            agencia.listarLugares();
                            break;

                        case 9:
                            agencia.listarEventos();
                            break;

                        case 10:
                            agencia.generarReporte();
                            break;

                        case 11:
                            agencia.guardar();
                            break;

                        case 0:

                            agencia.guardar();
                            System.out.println("Datos guardados. Saliendo...");
                            break;

                        default:
                            System.out.println("Opcion invalida");

                    }

                }catch(Exception e){
                    System.out.println("Error: " + e.getMessage());
                }

            }

            sc.close();
        }
    }

