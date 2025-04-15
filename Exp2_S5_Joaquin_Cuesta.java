import java.util.*;

class Entrada {
    int numero;
    String ubicacion;
    double precioFinal;
    String tipoCliente; // Estudiante, Tercera Edad, General

    public Entrada(int numero, String ubicacion, double precioFinal, String tipoCliente) {
        this.numero = numero;
        this.ubicacion = ubicacion;
        this.precioFinal = precioFinal;
        this.tipoCliente = tipoCliente;
    }

    public void mostrarInfo() {
        System.out.println("Numero: " + numero + ", Ubicacion: " + ubicacion + ", Precio Final: $" + precioFinal + ", Tipo Cliente: " + tipoCliente);
    }
}

public class TeatroMoro {

    static Scanner scanner = new Scanner(System.in);
    static List<Entrada> entradasVendidas = new ArrayList<>();
    static int contadorEntradas = 1;

    // Variables estaticas
    static double totalIngresos = 0;
    static int totalVendidas = 0;
    static int capacidadSala = 100;

    // Precios base por ubicacion
    static double precioVIP = 10000;
    static double precioPlatea = 7000;
    static double precioGeneral = 5000;

    public static void main(String[] args) {
        int opcion;

        do {
            System.out.println("\n--- TEATRO MORO ---");
            System.out.println("1. Venta de entrada");
            System.out.println("2. Ver promociones");
            System.out.println("3. Buscar entrada");
            System.out.println("4. Eliminar entrada");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    venderEntrada();
                    break;
                case 2:
                    mostrarPromociones();
                    break;
                case 3:
                    buscarEntrada();
                    break;
                case 4:
                    eliminarEntrada();
                    break;
                case 5:
                    System.out.println("Gracias por visitar el Teatro Moro!");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 5);
    }

    public static void venderEntrada() {
        if (totalVendidas >= capacidadSala) {
            System.out.println("Lo sentimos, la sala esta llena.");
            return;
        }

        System.out.print("Ingrese ubicacion (VIP / Platea / General): ");
        String ubicacion = scanner.nextLine().trim();
        double precioBase = switch (ubicacion.toLowerCase()) {
            case "vip" -> precioVIP;
            case "platea" -> precioPlatea;
            case "general" -> precioGeneral;
            default -> {
                System.out.println("Ubicacion invalida.");
                return;
            }
        };

        System.out.print("Es estudiante? (s/n): ");
        boolean esEstudiante = scanner.nextLine().equalsIgnoreCase("s");

        System.out.print("Es de la tercera edad? (s/n): ");
        boolean esTerceraEdad = scanner.nextLine().equalsIgnoreCase("s");

        String tipoCliente = "General";
        double descuento = 0;

        // Estructura condicional para descuento
        if (esEstudiante) {
            descuento = 0.10;
            tipoCliente = "Estudiante";
        } else if (esTerceraEdad) {
            descuento = 0.15;
            tipoCliente = "Tercera Edad";
        }

        double precioFinal = precioBase - (precioBase * descuento);

        // Crear y almacenar entrada
        Entrada entrada = new Entrada(contadorEntradas++, ubicacion, precioFinal, tipoCliente);
        entradasVendidas.add(entrada);

        totalIngresos += precioFinal;
        totalVendidas++;

        System.out.println("Entrada vendida exitosamente. Precio final: $" + precioFinal);
    }

    public static void mostrarPromociones() {
        System.out.println("\n--- PROMOCIONES DISPONIBLES ---");
        System.out.println("10% de descuento para estudiantes.");
        System.out.println("15% de descuento para personas de la tercera edad.");
        System.out.println("Compra 3 entradas y obten la 4ta gratis (solo en boleteria).");
    }

    public static void buscarEntrada() {
        System.out.println("\nBuscar por:");
        System.out.println("1. Numero de entrada");
        System.out.println("2. Ubicacion");
        System.out.println("3. Tipo de cliente");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        boolean encontrado = false;

        // Estructura iterativa para buscar
        for (Entrada entrada : entradasVendidas) {
            switch (opcion) {
                case 1 -> {
                    System.out.print("Ingrese numero de entrada: ");
                    int num = scanner.nextInt();
                    if (entrada.numero == num) {
                        entrada.mostrarInfo();
                        encontrado = true;
                    }
                }
                case 2 -> {
                    System.out.print("Ingrese ubicacion: ");
                    String ubi = scanner.nextLine().trim();
                    if (entrada.ubicacion.equalsIgnoreCase(ubi)) {
                        entrada.mostrarInfo();
                        encontrado = true;
                    }
                }
                case 3 -> {
                    System.out.print("Ingrese tipo de cliente (Estudiante/Tercera Edad/General): ");
                    String tipo = scanner.nextLine().trim();
                    if (entrada.tipoCliente.equalsIgnoreCase(tipo)) {
                        entrada.mostrarInfo();
                        encontrado = true;
                    }
                }
                default -> System.out.println("Opcion invalida.");
            }
        }

        if (!encontrado) {
            System.out.println("No se encontraron entradas con esos datos.");
        }
    }

    public static void eliminarEntrada() {
        System.out.print("Ingrese el numero de entrada a eliminar: ");
        int numEliminar = scanner.nextInt();

        Iterator<Entrada> iterator = entradasVendidas.iterator();
        boolean eliminada = false;

        while (iterator.hasNext()) {
            Entrada entrada = iterator.next();
            if (entrada.numero == numEliminar) {
                iterator.remove();
                totalVendidas--;
                totalIngresos -= entrada.precioFinal;
                eliminada = true;
                System.out.println("Entrada eliminada correctamente.");
                break;
            }
        }

        if (!eliminada) {
            System.out.println("No se encontro la entrada.");
        }
    }
}