import java.util.*;

public class SistemaTeatro {

    // Variables estaticas (estadisticas globales)
    static int totalEntradasVendidas = 0;
    static double totalIngresos = 0.0;
    static int totalReservas = 0;

    // Variables de instancia (datos persistentes)
    private String[][] asientos;
    private List<Entrada> ventas;
    private int capacidad;
    private double precioEntrada;
    private String nombreTeatro;

    // Constructor
    public SistemaTeatro(String nombre, int filas, int columnas, double precio) {
        this.nombreTeatro = nombre;
        this.capacidad = filas * columnas;
        this.asientos = new String[filas][columnas];
        this.ventas = new ArrayList<>();
        this.precioEntrada = precio;

        // Inicializar asientos como "libre"
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                asientos[i][j] = "libre";
            }
        }
    }

    // Menu interactivo
    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\nBienvenido al Teatro " + nombreTeatro);
            System.out.println("1. Reservar entradas");
            System.out.println("2. Comprar entradas");
            System.out.println("3. Modificar una venta existente");
            System.out.println("4. Imprimir boleta");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    reservarEntradas(scanner);
                    break;
                case 2:
                    comprarEntradas(scanner);
                    break;
                case 3:
                    modificarVenta(scanner);
                    break;
                case 4:
                    imprimirBoleta(scanner);
                    break;
                case 0:
                    System.out.println("Gracias por usar el sistema.");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);

        scanner.close();
    }

    // Reserva de entradas
    private void reservarEntradas(Scanner scanner) {
        System.out.println("--- Reserva de Entradas ---");
        int fila = pedirFila(scanner);
        int columna = pedirColumna(scanner);

        if (asientos[fila][columna].equals("libre")) {
            asientos[fila][columna] = "reservado";
            totalReservas++;

            // Variables locales
            String tipoEntrada = "General";
            boolean descuentoTemporal = false;
            String estadoReserva = "activa";
            long tiempoReserva = System.currentTimeMillis();

            System.out.println("Entrada reservada con exito en [" + fila + "][" + columna + "]");
            // Punto de depuracion
            System.out.println("[DEBUG] Reserva en fila " + fila + ", columna " + columna);
        } else {
            System.out.println("Asiento no disponible.");
        }
    }

    // Compra de entradas
    private void comprarEntradas(Scanner scanner) {
        System.out.println("--- Compra de Entradas ---");
        int fila = pedirFila(scanner);
        int columna = pedirColumna(scanner);

        if (asientos[fila][columna].equals("libre") || asientos[fila][columna].equals("reservado")) {
            asientos[fila][columna] = "vendido";
            Entrada entrada = new Entrada(fila, columna, precioEntrada);
            ventas.add(entrada);
            totalEntradasVendidas++;
            totalIngresos += precioEntrada;

            System.out.println("Compra realizada exitosamente.");
            // Punto de depuracion
            System.out.println("[DEBUG] Entrada vendida en [" + fila + "][" + columna + "]");
        } else {
            System.out.println("Asiento no disponible para la compra.");
        }
    }

    // Modificar venta
    private void modificarVenta(Scanner scanner) {
        System.out.println("--- Modificacion de Venta ---");
        System.out.print("Ingrese fila del asiento: ");
        int fila = scanner.nextInt();
        System.out.print("Ingrese columna del asiento: ");
        int columna = scanner.nextInt();

        for (Entrada entrada : ventas) {
            if (entrada.getFila() == fila && entrada.getColumna() == columna) {
                System.out.print("Ingrese nuevo precio: ");
                double nuevoPrecio = scanner.nextDouble();
                entrada.setPrecio(nuevoPrecio);
                System.out.println("Venta modificada.");
                // Punto de depuracion
                System.out.println("[DEBUG] Entrada modificada: fila " + fila + ", columna " + columna);
                return;
            }
        }

        System.out.println("No se encontro ninguna venta para esos datos.");
    }

    // Imprimir boleta
    private void imprimirBoleta(Scanner scanner) {
        System.out.println("--- Imprimir Boleta ---");
        for (Entrada entrada : ventas) {
            System.out.println("Boleta: Asiento [" + entrada.getFila() + "][" + entrada.getColumna() + "] - Precio: $" + entrada.getPrecio());
            // Punto de depuracion
            System.out.println("[DEBUG] Boleta generada para asiento " + entrada.getFila() + ", " + entrada.getColumna());
        }
    }

    // Pedir fila
    private int pedirFila(Scanner scanner) {
        System.out.print("Ingrese numero de fila: ");
        return scanner.nextInt();
    }

    // Pedir columna
    private int pedirColumna(Scanner scanner) {
        System.out.print("Ingrese numero de columna: ");
        return scanner.nextInt();
    }

    // Clase interna para entradas
    private class Entrada {
        private int fila;
        private int columna;
        private double precio;

        public Entrada(int fila, int columna, double precio) {
            this.fila = fila;
            this.columna = columna;
            this.precio = precio;
        }

        public int getFila() {
            return fila;
        }

        public int getColumna() {
            return columna;
        }

        public double getPrecio() {
            return precio;
        }

        public void setPrecio(double precio) {
            this.precio = precio;
        }
    }

    // Main
    public static void main(String[] args) {
        SistemaTeatro teatro = new SistemaTeatro("Gran Teatro", 5, 5, 50.0);
        teatro.mostrarMenu();
    }
}