import java.util.HashMap;
import java.util.Scanner;

// Interfaz
interface MostrarDatos {
    void mostrarDatosCliente();
}

// Clase abstracta base
abstract class CuentaBancaria {
    protected int numeroCuenta;
    protected int saldo;

    public CuentaBancaria(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = 0;
    }

    public CuentaBancaria(int numeroCuenta, int saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldoInicial;
    }

    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public int getSaldo() {
        return saldo;
    }

    public void depositar(int monto) {
        if (monto > 0) {
            saldo += monto;
            System.out.println("Depósito exitoso. Saldo actual: " + saldo);
        } else {
            System.out.println("Monto inválido para depósito.");
        }
    }

    public abstract void girar(int monto);
}

// Subclases
class CuentaCorriente extends CuentaBancaria {
    public CuentaCorriente(int numeroCuenta) {
        super(numeroCuenta);
    }

    @Override
    public void girar(int monto) {
        if (monto <= 0) {
            System.out.println("Monto inválido.");
        } else if (monto > saldo) {
            System.out.println("Saldo insuficiente.");
        } else {
            saldo -= monto;
            System.out.println("Giro exitoso. Saldo actual: " + saldo);
        }
    }
}

class CuentaAhorro extends CuentaBancaria {
    private double interes = 0.02;

    public CuentaAhorro(int numeroCuenta) {
        super(numeroCuenta);
    }

    @Override
    public void girar(int monto) {
        if (monto > saldo) {
            System.out.println("No se puede girar más del saldo disponible.");
        } else {
            saldo -= monto;
            System.out.println("Giro exitoso en cuenta de ahorro. Saldo: " + saldo);
        }
    }

    public void aplicarInteres() {
        int ganancia = (int)(saldo * interes);
        saldo += ganancia;
        System.out.println("Interés aplicado: " + ganancia + ". Nuevo saldo: " + saldo);
    }
}

class CuentaCredito extends CuentaBancaria {
    private int limiteCredito = 500000;

    public CuentaCredito(int numeroCuenta) {
        super(numeroCuenta);
    }

    @Override
    public void girar(int monto) {
        if (monto <= 0) {
            System.out.println("Monto inválido.");
        } else if (saldo - monto < -limiteCredito) {
            System.out.println("Límite de crédito excedido.");
        } else {
            saldo -= monto;
            System.out.println("Giro exitoso con crédito. Saldo actual: " + saldo);
        }
    }
}

// Clase Cliente
class Cliente implements MostrarDatos {
    private String rut, nombre, apellido, comuna, telefono;
    private CuentaBancaria cuenta;

    public Cliente(String rut, String nombre, String apellido, String comuna, String telefono, CuentaBancaria cuenta) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.comuna = comuna;
        this.telefono = telefono;
        this.cuenta = cuenta;
    }

    public CuentaBancaria getCuenta() {
        return cuenta;
    }

    @Override
    public void mostrarDatosCliente() {
        System.out.println("Rut: " + rut);
        System.out.println("Nombre: " + nombre + " " + apellido);
        System.out.println("Comuna: " + comuna);
        System.out.println("Teléfono: " + telefono);
        System.out.println("N° Cuenta: " + cuenta.getNumeroCuenta());
        System.out.println("Saldo: " + cuenta.getSaldo());
        System.out.println("Tipo Cuenta: " + cuenta.getClass().getSimpleName());
    }
}

// Clase principal con main
public class BankBostonApp {
    static HashMap<String, Cliente> clientes = new HashMap<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Menú Bank Boston ---");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Ver datos");
            System.out.println("3. Depositar");
            System.out.println("4. Girar");
            System.out.println("5. Salir");
            System.out.print("Opción: ");
            int op = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (op) {
                case 1 -> registrar();
                case 2 -> verDatos();
                case 3 -> operar("depositar");
                case 4 -> operar("girar");
                case 5 -> {
                    System.out.println("Gracias por usar Bank Boston.");
                    return;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    static void registrar() {
        System.out.print("Rut: ");
        String rut = sc.nextLine();
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Apellido: ");
        String apellido = sc.nextLine();
        System.out.print("Comuna: ");
        String comuna = sc.nextLine();
        System.out.print("Teléfono: ");
        String tel = sc.nextLine();
        System.out.print("Número de cuenta: ");
        int numCuenta = sc.nextInt();
        sc.nextLine();

        System.out.println("Tipo de cuenta: 1) Corriente 2) Ahorro 3) Crédito");
        int tipo = sc.nextInt();
        sc.nextLine();

        CuentaBancaria cuenta;
        switch (tipo) {
            case 1 -> cuenta = new CuentaCorriente(numCuenta);
            case 2 -> cuenta = new CuentaAhorro(numCuenta);
            case 3 -> cuenta = new CuentaCredito(numCuenta);
            default -> {
                System.out.println("Tipo inválido.");
                return;
            }
        }

        Cliente c = new Cliente(rut, nombre, apellido, comuna, tel, cuenta);
        clientes.put(rut, c);
        System.out.println("¡Cliente registrado!");
    }

    static void verDatos() {
        System.out.print("Rut: ");
        String rut = sc.nextLine();
        Cliente c = clientes.get(rut);
        if (c != null) {
            c.mostrarDatosCliente();
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    static void operar(String tipo) {
        System.out.print("Rut: ");
        String rut = sc.nextLine();
        Cliente c = clientes.get(rut);
        if (c == null) {
            System.out.println("Cliente no existe.");
            return;
        }

        System.out.print("Monto: ");
        int monto = sc.nextInt();
        sc.nextLine();

        if (tipo.equals("depositar")) {
            c.getCuenta().depositar(monto);
        } else {
            c.getCuenta().girar(monto);
        }
    }
}
