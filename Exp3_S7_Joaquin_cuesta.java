import java.util.ArrayList;
import java.util.Scanner;

public class TeatroMoro {

    // Variables estaticas (estadisticas globales)
    static double ingresosTotales = 0;
    static int totalEntradasVendidas = 0;
    static int totalVentas = 0;

    // Variables de instancia (ventas individuales)
    static ArrayList<String> ubicaciones = new ArrayList<>();
    static ArrayList<Double> costosFinales = new ArrayList<>();
    static ArrayList<String> descuentosAplicados = new ArrayList<>();

    // Constantes de precios base por ubicacion
    static final double PRECIO_VIP = 15000;
    static final double PRECIO_PLATEA = 10000;
    static final double PRECIO_BALCON = 7000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        String nombreTeatro = "Teatro Moro";
        int capacidadSala = 100;
        int entradasDisponibles = capacidadSala;

        while (continuar) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Venta de entradas");
            System.out.println("2. Visualizar resumen de ventas");
            System.out.println("3. Generar boleta");
            System.out.println("4. Calcular ingresos totales");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    if (entradasDisponibles > 0) {
                        ventaEntrada(scanner);
                        entradasDisponibles--;
                    } else {
                        System.out.println("¡No hay mas entradas disponibles!");
                    }
                    break;
                case 2:
                    resumenVentas();
                    break;
                case 3:
                    generarBoletas();
                    break;
                case 4:
                    System.out.println("Ingresos totales: $" + ingresosTotales);
                    break;
                case 5:
                    System.out.println("Gracias por su compra. ¡Vuelva pronto!");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opcion invalida. Intente nuevamente.");
            }
        }

        scanner.close();
    }

    public static void ventaEntrada(Scanner scanner) {
        System.out.println("\nSeleccione ubicacion:");
        System.out.println("1. VIP ($15000)");
        System.out.println("2. Platea ($10000)");
        System.out.println("3. Balcon ($7000)");
        System.out.print("Ingrese el numero de la ubicacion: ");
        int opcionUbicacion = scanner.nextInt();
        scanner.nextLine();  // limpiar buffer

        String ubicacion = "";
        double precioBase = 0;

        switch (opcionUbicacion) {
            case 1:
                ubicacion = "VIP";
                precioBase = PRECIO_VIP;
                break;
            case 2:
                ubicacion = "Platea";
                precioBase = PRECIO_PLATEA;
                break;
            case 3:
                ubicacion = "Balcon";
                precioBase = PRECIO_BALCON;
                break;
            default:
                System.out.println("Opcion de ubicacion no valida. Venta cancelada.");
                return;
        }

        System.out.println("¿Es estudiante?");
        System.out.println("1. Si");
        System.out.println("2. No");
        System.out.print("Seleccione una opcion: ");
        int estudianteOpcion = scanner.nextInt();
        scanner.nextLine();
        boolean esEstudiante = (estudianteOpcion == 1);

        System.out.println("¿Es persona de la tercera edad?");
        System.out.println("1. Si");
        System.out.println("2. No");
        System.out.print("Seleccione una opcion: ");
        int terceraEdadOpcion = scanner.nextInt();
        scanner.nextLine();
        boolean esTerceraEdad = (terceraEdadOpcion == 1);

        double descuento = 0;
        String tipoDescuento = "Ninguno";

        if (esTerceraEdad) {
            descuento = 0.15;
            tipoDescuento = "15% (Tercera Edad)";
        } else if (esEstudiante) {
            descuento = 0.10;
            tipoDescuento = "10% (Estudiante)";
        }

        double precioFinal = precioBase * (1 - descuento);

        ubicaciones.add(ubicacion);
        descuentosAplicados.add(tipoDescuento);
        costosFinales.add(precioFinal);

        ingresosTotales += precioFinal;
        totalEntradasVendidas++;
        totalVentas++;

        System.out.println("¡Venta registrada con exito!");
    }

    public static void resumenVentas() {
        System.out.println("\n--- RESUMEN DE VENTAS ---");
        if (ubicaciones.isEmpty()) {
            System.out.println("No se han realizado ventas aun.");
            return;
        }

        for (int i = 0; i < ubicaciones.size(); i++) {
            System.out.printf("Venta %d: Ubicacion: %s | Descuento: %s | Precio final: $%.0f\n",
                    i + 1, ubicaciones.get(i), descuentosAplicados.get(i), costosFinales.get(i));
        }
    }

    public static void generarBoletas() {
        System.out.println("\n--- BOLETAS DETALLADAS ---");
        if (ubicaciones.isEmpty()) {
            System.out.println("No hay boletas para mostrar.");
            return;
        }

        for (int i = 0; i < ubicaciones.size(); i++) {
            double base = 0;
            switch (ubicaciones.get(i).toLowerCase()) {
                case "vip": base = PRECIO_VIP; break;
                case "platea": base = PRECIO_PLATEA; break;
                case "balcon": base = PRECIO_BALCON; break;
            }

            double finalPrecio = costosFinales.get(i);
            double descuentoValor = base - finalPrecio;

            System.out.println("\n--------------------------");
            System.out.println("           BOLETA         ");
            System.out.println("--------------------------");
            System.out.println("Ubicacion: " + ubicaciones.get(i));
            System.out.println("Precio base: $" + base);
            System.out.println("Descuento aplicado: " + descuentosAplicados.get(i));
            System.out.printf("Descuento en $: $%.0f\n", descuentoValor);
            System.out.printf("Precio final: $%.0f\n", finalPrecio);
            System.out.println("Gracias por su compra en el Teatro Moro!");
            System.out.println("--------------------------");
        }
    }
}