import java.util.*;

public class SistemaTeatro {

    // Estructuras de datos
    private String[][] asientos; // ["libre", "reservado", "vendido"]
    private Venta[] ventas;
    private Cliente[] clientes;
    private int capacidad;
    private double precioBase;
    private String nombreTeatro;
    private int totalVentas = 0;
    private int totalClientes = 0;

    // Manejo de descuentos y reservas
    private List<Descuento> descuentos;
    private List<Reserva> reservas;

    // Constructor
    public SistemaTeatro(String nombre, int filas, int columnas, double precioBase) {
        this.nombreTeatro = nombre;
        this.capacidad = filas * columnas;
        this.precioBase = precioBase;
        this.asientos = new String[filas][columnas];
        this.ventas = new Venta[capacidad];
        this.clientes = new Cliente[capacidad];
        this.descuentos = new ArrayList<>();
        this.reservas = new ArrayList<>();

        for (int i = 0; i < filas; i++) {
            Arrays.fill(asientos[i], "libre");
        }

        // Definir descuentos
        descuentos.add(new Descuento("Normal", 0.0));
        descuentos.add(new Descuento("Estudiante", 0.10));
        descuentos.add(new Descuento("TerceraEdad", 0.15));
    }

    // Mostrar menu
    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\nBienvenido al Teatro " + nombreTeatro);
            System.out.println("1. Reservar asiento");
            System.out.println("2. Comprar entrada");
            System.out.println("3. Ver ventas");
            System.out.println("4. Ver reservas");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> reservarAsiento(scanner);
                case 2 -> comprarEntrada(scanner);
                case 3 -> mostrarVentas();
                case 4 -> mostrarReservas();
                case 0 -> System.out.println("Gracias por usar el sistema.");
                default -> System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    // Compra de entrada
    private void comprarEntrada(Scanner scanner) {
        System.out.println("--- Compra de Entrada ---");
        int fila = pedirFila(scanner);
        int columna = pedirColumna(scanner);

        if (!asientos[fila][columna].equals("libre") && !asientos[fila][columna].equals("reservado")) {
            System.out.println("Asiento no disponible.");
            return;
        }

        scanner.nextLine(); // limpiar buffer
        System.out.print("Ingrese nombre del cliente: ");
        String nombre = scanner.nextLine();
        String tipo = seleccionarTipoCliente(scanner);

        double descuento = obtenerDescuento(tipo);
        double precioFinal = precioBase * (1 - descuento);

        Cliente cliente = new Cliente(totalClientes, nombre, tipo);
        clientes[totalClientes] = cliente;

        Venta venta = new Venta(totalVentas, cliente.id, fila, columna, precioFinal);
        ventas[totalVentas] = venta;

        asientos[fila][columna] = "vendido";

        System.out.println("Venta exitosa. Precio final: $" + precioFinal);

        totalClientes++;
        totalVentas++;
    }

    // Reserva de asiento
    private void reservarAsiento(Scanner scanner) {
        System.out.println("--- Reservar Asiento ---");
        int fila = pedirFila(scanner);
        int columna = pedirColumna(scanner);

        if (!asientos[fila][columna].equals("libre")) {
            System.out.println("Asiento no disponible.");
            return;
        }

        scanner.nextLine(); // limpiar buffer
        System.out.print("Ingrese nombre del cliente: ");
        String nombre = scanner.nextLine();
        String tipo = seleccionarTipoCliente(scanner);

        Cliente cliente = new Cliente(totalClientes, nombre, tipo);
        clientes[totalClientes] = cliente;

        Reserva reserva = new Reserva(reservas.size(), cliente.id, fila, columna);
        reservas.add(reserva);

        asientos[fila][columna] = "reservado";
        System.out.println("Reserva registrada exitosamente.");

        totalClientes++;
    }

    // Pedir tipo de cliente por numero
    private String seleccionarTipoCliente(Scanner scanner) {
        int tipo;
        do {
            System.out.println("Seleccione tipo de cliente:");
            System.out.println("1. Normal");
            System.out.println("2. Estudiante");
            System.out.println("3. Tercera Edad");
            System.out.print("Opcion: ");
            tipo = scanner.nextInt();
        } while (tipo < 1 || tipo > 3);

        return switch (tipo) {
            case 2 -> "Estudiante";
            case 3 -> "TerceraEdad";
            default -> "Normal";
        };
    }

    // Validacion de fila
    private int pedirFila(Scanner scanner) {
        int fila;
        do {
            System.out.print("Ingrese numero de fila: ");
            fila = scanner.nextInt();
        } while (fila < 0 || fila >= asientos.length);
        return fila;
    }

    // Validacion de columna
    private int pedirColumna(Scanner scanner) {
        int columna;
        do {
            System.out.print("Ingrese numero de columna: ");
            columna = scanner.nextInt();
        } while (columna < 0 || columna >= asientos[0].length);
        return columna;
    }

    // Obtener descuento segun tipo
    private double obtenerDescuento(String tipo) {
        for (Descuento d : descuentos) {
            if (d.tipo.equalsIgnoreCase(tipo)) return d.porcentaje;
        }
        return 0.0;
    }

    private void mostrarVentas() {
        System.out.println("--- Ventas ---");
        for (int i = 0; i < totalVentas; i++) {
            Venta v = ventas[i];
            Cliente c = clientes[v.clienteId];
            System.out.println("Venta ID: " + v.id + ", Cliente: " + c.nombre + ", Tipo: " + c.tipo + ", Asiento: [" + v.fila + "][" + v.columna + "], Precio: $" + v.precio);
        }
    }

    private void mostrarReservas() {
        System.out.println("--- Reservas ---");
        for (Reserva r : reservas) {
            Cliente c = clientes[r.clienteId];
            System.out.println("Reserva ID: " + r.id + ", Cliente: " + c.nombre + ", Tipo: " + c.tipo + ", Asiento: [" + r.fila + "][" + r.columna + "]");
        }
    }

    // Clases internas
    private static class Cliente {
        int id;
        String nombre;
        String tipo;

        Cliente(int id, String nombre, String tipo) {
            this.id = id;
            this.nombre = nombre;
            this.tipo = tipo;
        }
    }

    private static class Venta {
        int id;
        int clienteId;
        int fila;
        int columna;
        double precio;

        Venta(int id, int clienteId, int fila, int columna, double precio) {
            this.id = id;
            this.clienteId = clienteId;
            this.fila = fila;
            this.columna = columna;
            this.precio = precio;
        }
    }

    private static class Reserva {
        int id;
        int clienteId;
        int fila;
        int columna;

        Reserva(int id, int clienteId, int fila, int columna) {
            this.id = id;
            this.clienteId = clienteId;
            this.fila = fila;
            this.columna = columna;
        }
    }

    private static class Descuento {
        String tipo;
        double porcentaje;

        Descuento(String tipo, double porcentaje) {
            this.tipo = tipo;
            this.porcentaje = porcentaje;
        }
    }

    // Main
    public static void main(String[] args) {
        SistemaTeatro teatro = new SistemaTeatro("Teatro Moro", 5, 5, 50.0);
        teatro.mostrarMenu();
    }
}