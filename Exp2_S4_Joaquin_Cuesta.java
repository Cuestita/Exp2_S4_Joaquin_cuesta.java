import java.util.Scanner;

public class VentaEntradasTeatro {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        boolean continuar = true;

        // Menu principal
        while (continuar) {
            System.out.println("Menu Principal:");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    comprarEntrada(scanner);  // Llamamos a la funcion para comprar entrada
                    break;
                case 2:
                    System.out.println("Gracias por su visita. ¡Hasta luego!");
                    continuar = false;  // Salimos del ciclo y terminamos el programa
                    break;
                default:
                    System.out.println("Opcion invalida, por favor intente nuevamente.");
            }
        }
        scanner.close();
    }

    public static void comprarEntrada(Scanner scanner) {
        scanner.nextLine();  // Limpiar el buffer de entrada

        // Paso 1: Solicitar ubicacion del asiento
        System.out.println("\nSeleccione una zona para su asiento (1, 2, 3):");
        System.out.print("Zona 1: $10,000 CLP, Zona 2: $5,000 CLP, Zona 3: $3,500 CLP\n");
        int zona = scanner.nextInt();

        while (zona < 1 || zona > 3) {
            System.out.print("Opcion invalida. Seleccione una zona valida (1, 2, 3): ");
            zona = scanner.nextInt();
        }

        // Paso 2: Solicitar edad y aplicar descuento
        System.out.print("Ingrese su edad: ");
        int edad = scanner.nextInt();

        while (edad <= 0) {
            System.out.print("Edad no valida. Ingrese una edad mayor a 0: ");
            edad = scanner.nextInt();
        }

        // Precios en pesos chilenos
        double precioBase = 0;

        switch (zona) {
            case 1:
                precioBase = 10000;  // Zona 1: 10,000 CLP
                break;
            case 2:
                precioBase = 5000;   // Zona 2: 5,000 CLP
                break;
            case 3:
                precioBase = 3500;   // Zona 3: 3,500 CLP
                break;
        }

        // Paso 3: Calcular el descuento
        double descuento = 0;

        if (edad <= 25) {  // Descuento para estudiantes (hasta 25 años)
            descuento = 0.10;  // 10% de descuento
        } else if (edad >= 65) {  // Descuento para personas de la tercera edad (a partir de 65 años)
            descuento = 0.15;  // 15% de descuento
        } else {
            // No hay descuento para el rango de 26 a 64 años
            descuento = 0.0;
        }

        // Calcular precio con descuento
        double precioConDescuento = precioBase - (precioBase * descuento);

        // Paso 4: Resumen de la compra
        System.out.println("\nResumen de la compra:");
        System.out.println("Ubicacion del asiento: Zona " + zona);
        System.out.println("Precio base: $" + precioBase + " CLP");
        System.out.println("Descuento aplicado: " + (descuento * 100) + "%");
        System.out.println("Precio final a pagar: $" + precioConDescuento + " CLP");

        // Paso 5: Preguntar si desea hacer otra compra o salir
        scanner.nextLine();  // Limpiar el buffer de entrada
        System.out.print("\n Desea realizar otra compra? (Si/No): ");
        String respuesta = scanner.nextLine().toLowerCase();

        if (respuesta.equals("no")) {
            System.out.println("Gracias por su compra. ¡Hasta luego!");
            return;  // Salimos de la funcion, lo que lleva al fin del programa
        }
    }
}