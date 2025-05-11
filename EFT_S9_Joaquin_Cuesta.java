import java.util.*;

public class TeatroMoro {

    // Clases internas
    static class Persona {
        String nombre;
        int edad;
        String genero;
        boolean esEstudiante;
        boolean terceraEdad;

        public Persona(String nombre, int edad, String genero, boolean esEstudiante) {
            this.nombre = nombre;
            this.edad = edad;
            this.genero = genero;
            this.esEstudiante = esEstudiante;
            this.terceraEdad = edad >= 60;
        }

        public double obtenerDescuento() {
            if (terceraEdad) return 0.25;
            if (esEstudiante) return 0.15;
            if (genero.equalsIgnoreCase("F")) return 0.20;
            if (edad < 12) return 0.10;
            return 0.0;
        }
    }

    static class Asiento {
        String tipo;
        boolean disponible;

        public Asiento(String tipo) {
            this.tipo = tipo;
            this.disponible = true;
        }
    }

    static class Entrada {
        Persona comprador;
        Asiento asiento;
        double precioBase;
        double precioFinal;

        public Entrada(Persona comprador, Asiento asiento, double precioBase) {
            this.comprador = comprador;
            this.asiento = asiento;
            this.precioBase = precioBase;
            this.precioFinal = precioBase - (precioBase * comprador.obtenerDescuento());
            asiento.disponible = false;
        }

        public void imprimirBoleta() {
            System.out.println("\n======= BOLETA =======");
            System.out.println("Nombre: " + comprador.nombre);
            System.out.println("Edad: " + comprador.edad);
            System.out.println("Tipo de asiento: " + asiento.tipo);
            System.out.println("Precio base: $" + precioBase);
            System.out.println("Descuento aplicado: " + (comprador.obtenerDescuento() * 100) + "%");
            System.out.println("Total a pagar: $" + precioFinal);
            System.out.println("======================\n");
        }
    }

    // Variables del sistema
    static List<Asiento> asientos = new ArrayList<>();
    static List<Entrada> ventas = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        inicializarAsientos();

        while (true) {
            System.out.println("\n--- Nueva venta de entrada ---");
            Persona persona = crearPersona();
            mostrarAsientosDisponibles();
            Asiento asiento = seleccionarAsiento();
            double precioBase = obtenerPrecioPorTipo(asiento.tipo);

            Entrada entrada = new Entrada(persona, asiento, precioBase);
            ventas.add(entrada);
            entrada.imprimirBoleta();

            System.out.print("Desea realizar otra venta? (s/n): ");
            String respuesta = scanner.nextLine();
            if (!respuesta.equalsIgnoreCase("s")) break;
        }

        System.out.println("Gracias por usar el sistema del Teatro Moro.");
    }

    static void inicializarAsientos() {
        for (int i = 0; i < 5; i++) asientos.add(new Asiento("VIP"));
        for (int i = 0; i < 10; i++) asientos.add(new Asiento("Palco"));
        for (int i = 0; i < 15; i++) asientos.add(new Asiento("Platea Baja"));
        for (int i = 0; i < 15; i++) asientos.add(new Asiento("Platea Alta"));
        for (int i = 0; i < 20; i++) asientos.add(new Asiento("Galeria"));
    }

    static Persona crearPersona() {
        System.out.print("Ingrese nombre: ");
        String nombre = scanner.nextLine();

        int edad;
        while (true) {
            System.out.print("Ingrese edad (mayor a 0): ");
            try {
                edad = Integer.parseInt(scanner.nextLine());
                if (edad <= 0) {
                    System.out.println("Edad invalida. Debe ser mayor a 0.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada no valida. Ingrese un numero entero.");
            }
        }

        System.out.print("Ingrese genero (M/F): ");
        String genero = scanner.nextLine();

        System.out.print("Es estudiante? (s/n): ");
        boolean esEstudiante = scanner.nextLine().equalsIgnoreCase("s");

        return new Persona(nombre, edad, genero, esEstudiante);
    }

    static void mostrarAsientosDisponibles() {
        System.out.println("Asientos disponibles:");
        Set<String> tiposMostrados = new HashSet<>();
        for (Asiento a : asientos) {
            if (a.disponible && !tiposMostrados.contains(a.tipo)) {
                System.out.println("- " + a.tipo);
                tiposMostrados.add(a.tipo);
            }
        }
    }

    static Asiento seleccionarAsiento() {
        System.out.print("Seleccione tipo de asiento: ");
        String tipo = scanner.nextLine();

        for (Asiento a : asientos) {
            if (a.tipo.equalsIgnoreCase(tipo) && a.disponible) {
                return a;
            }
        }

        System.out.println("Asiento no disponible o invalido. Intente de nuevo.");
        return seleccionarAsiento();
    }

    static double obtenerPrecioPorTipo(String tipo) {
        switch (tipo.toLowerCase()) {
            case "vip": return 10000;
            case "palco": return 8000;
            case "platea baja": return 6000;
            case "platea alta": return 5000;
            case "galeria": return 3000;
            default: return 0;
        }
    }
}
