import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CuentaBancaria {
    private static final AtomicInteger SEQ = new AtomicInteger(1);

    public enum TipoCuenta { CORRIENTE, AHORROS }

    private final int id;
    private final String cliente;
    private final TipoCuenta tipo;
    private double saldo;
    private final List<Transaccion> historial = new ArrayList<>();

    public CuentaBancaria(String cliente, TipoCuenta tipo, double saldoInicial) {
        this.id = SEQ.getAndIncrement();
        this.cliente = Objects.requireNonNull(cliente, "Cliente no puede ser null");
        this.tipo = Objects.requireNonNull(tipo, "Tipo de cuenta no puede ser null");
        this.saldo = Math.max(0.0, saldoInicial);
        registrarTransaccion("Apertura de cuenta", saldoInicial);
    }

    public int getId() { return id; }
    public String getCliente() { return cliente; }
    public TipoCuenta getTipo() { return tipo; }
    public synchronized double getSaldo() { return saldo; }

    public synchronized void depositar(double cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad a depositar debe ser mayor que 0");
        saldo += cantidad;
        registrarTransaccion("Depósito", cantidad);
    }

    public synchronized void retirar(double cantidad) throws InsufficientFundsException {
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad a retirar debe ser mayor que 0");
        if (cantidad > saldo) throw new InsufficientFundsException("Saldo insuficiente");
        saldo -= cantidad;
        registrarTransaccion("Retiro", -cantidad);
    }

    public synchronized void aplicarInteresOCargo() {
        double cambio = 0.0;
        if (tipo == TipoCuenta.AHORROS) {
            cambio = saldo * 0.01; // 1% de interés
            saldo += cambio;
            registrarTransaccion("Interés aplicado (+1%)", cambio);
            System.out.println("Interés aplicado correctamente. Nuevo saldo: " + saldo);
        } else if (tipo == TipoCuenta.CORRIENTE) {
            cambio = saldo * 0.005; // 0.5% de cargo
            saldo -= cambio;
            registrarTransaccion("Cargo por mantenimiento (-0.5%)", -cambio);
            System.out.println("Cargo aplicado correctamente. Nuevo saldo: " + saldo);
        }
    }

    public synchronized void transferirA(CuentaBancaria destino, double monto) throws InsufficientFundsException {
        if (destino == null) throw new IllegalArgumentException("Cuenta destino no puede ser nula");
        if (this.id == destino.id) throw new IllegalArgumentException("No puede transferir a la misma cuenta");
        if (monto <= 0) throw new IllegalArgumentException("Monto inválido");
        if (monto > saldo) throw new InsufficientFundsException("Saldo insuficiente para transferir");

        this.saldo -= monto;
        destino.saldo += monto;
        registrarTransaccion("Transferencia enviada a " + destino.cliente, -monto);
        destino.registrarTransaccion("Transferencia recibida de " + this.cliente, monto);
    }

    private void registrarTransaccion(String tipo, double monto) {
        historial.add(new Transaccion(tipo, monto, LocalDateTime.now()));
    }

    public void mostrarHistorial() {
        if (historial.isEmpty()) {
            System.out.println("No hay transacciones registradas.");
            return;
        }
        System.out.println("\nHistorial de transacciones de " + cliente + ":");
        historial.forEach(System.out::println);
    }

    @Override
    public String toString() {
        return String.format("ID:%d - %s (%s) - Saldo: %.2f", id, cliente, tipo, saldo);
    }

    // ======================== CLASE TRANSACCIÓN ===========================
    static class Transaccion {
        private final String tipo;
        private final double monto;
        private final LocalDateTime fecha;

        public Transaccion(String tipo, double monto, LocalDateTime fecha) {
            this.tipo = tipo;
            this.monto = monto;
            this.fecha = fecha;
        }

        @Override
        public String toString() {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return String.format("[%s] %-30s %10.2f", fecha.format(fmt), tipo, monto);
        }
    }

    // ======================== EXCEPCIÓN PERSONALIZADA ======================
    public static class InsufficientFundsException extends Exception {
        public InsufficientFundsException(String msg) { super(msg); }
    }

    // ======================== CLASE BANCO ===========================
    static class Banco {
        private final Map<Integer, CuentaBancaria> cuentas = new LinkedHashMap<>();

        public CuentaBancaria crearCuenta(String cliente, TipoCuenta tipo, double saldoInicial) {
            CuentaBancaria c = new CuentaBancaria(cliente, tipo, saldoInicial);
            cuentas.put(c.getId(), c);
            return c;
        }

        public Optional<CuentaBancaria> obtenerCuenta(int id) {
            return Optional.ofNullable(cuentas.get(id));
        }

        public Collection<CuentaBancaria> listar() {
            return Collections.unmodifiableCollection(cuentas.values());
        }
    }

    // ======================== MAIN (INTERFAZ CON EL USUARIO) ===========================
    public static void main(String[] args) {
        Banco banco = new Banco();
        banco.crearCuenta("Tony Stark", TipoCuenta.CORRIENTE, 1500.00);
        banco.crearCuenta("Steve Rogers", TipoCuenta.AHORROS, 2000.00);

        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n====== MENÚ BANCO ======");
                System.out.println("1 - Crear cuenta");
                System.out.println("2 - Consultar saldo");
                System.out.println("3 - Retirar");
                System.out.println("4 - Depositar");
                System.out.println("5 - Transferir");
                System.out.println("6 - Ver historial");
                System.out.println("7 - Aplicar interés/cargo");
                System.out.println("8 - Listar cuentas");
                System.out.println("9 - Salir");
                System.out.print("Seleccione opción: ");

                String linea = sc.nextLine().trim();
                int opcion;
                try { opcion = Integer.parseInt(linea); }
                catch (NumberFormatException e) { System.out.println("Opción inválida."); continue; }

                switch (opcion) {
                    case 1 -> crearCuentaFlow(sc, banco);
                    case 2 -> consultarSaldoFlow(sc, banco);
                    case 3 -> retirarFlow(sc, banco);
                    case 4 -> depositarFlow(sc, banco);
                    case 5 -> transferirFlow(sc, banco);
                    case 6 -> historialFlow(sc, banco);
                    case 7 -> interesFlow(sc, banco);
                    case 8 -> listarFlow(banco);
                    case 9 -> { System.out.println("Saliendo..."); return; }
                    default -> System.out.println("Opción no válida.");
                }
            }
        }
    }

    // ======================== FLUJOS ===========================
    private static void crearCuentaFlow(Scanner sc, Banco banco) {
        System.out.print("Nombre del titular: ");
        String nombre = sc.nextLine().trim();
        if (nombre.isEmpty()) { System.out.println("Nombre no puede estar vacío."); return; }

        System.out.print("Tipo (1=Corriente, 2=Ahorros): ");
        String t = sc.nextLine().trim();
        TipoCuenta tipo = "2".equals(t) ? TipoCuenta.AHORROS : TipoCuenta.CORRIENTE;

        System.out.print("Saldo inicial: ");
        try {
            double saldo = Double.parseDouble(sc.nextLine().trim());
            CuentaBancaria c = banco.crearCuenta(nombre, tipo, saldo);
            System.out.println("Cuenta creada: " + c);
        } catch (NumberFormatException e) {
            System.out.println("Saldo inválido.");
        }
    }

    private static void consultarSaldoFlow(Scanner sc, Banco banco) {
        Optional<CuentaBancaria> o = obtenerCuentaPorId(sc, banco);
        o.ifPresentOrElse(c -> System.out.println("Saldo: " + c.getSaldo()),
                () -> System.out.println("Cuenta no encontrada."));
    }

    private static void retirarFlow(Scanner sc, Banco banco) {
        Optional<CuentaBancaria> o = obtenerCuentaPorId(sc, banco);
        if (o.isEmpty()) return;
        CuentaBancaria c = o.get();

        System.out.print("Cantidad a retirar: ");
        try {
            double monto = Double.parseDouble(sc.nextLine().trim());
            c.retirar(monto);
            System.out.println("Retiro exitoso. Nuevo saldo: " + c.getSaldo());
        } catch (NumberFormatException e) {
            System.out.println("Monto inválido.");
        } catch (InsufficientFundsException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void depositarFlow(Scanner sc, Banco banco) {
        Optional<CuentaBancaria> o = obtenerCuentaPorId(sc, banco);
        if (o.isEmpty()) return;
        CuentaBancaria c = o.get();

        System.out.print("Cantidad a depositar: ");
        try {
            double monto = Double.parseDouble(sc.nextLine().trim());
            c.depositar(monto);
            System.out.println("Depósito exitoso. Nuevo saldo: " + c.getSaldo());
        } catch (NumberFormatException e) {
            System.out.println("Monto inválido.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void transferirFlow(Scanner sc, Banco banco) {
        System.out.print("ID de cuenta origen: ");
        Optional<CuentaBancaria> origenOpt = obtenerCuentaPorId(sc, banco);
        if (origenOpt.isEmpty()) return;

        System.out.print("ID de cuenta destino: ");
        Optional<CuentaBancaria> destinoOpt = obtenerCuentaPorId(sc, banco);
        if (destinoOpt.isEmpty()) return;

        System.out.print("Monto a transferir: ");
        try {
            double monto = Double.parseDouble(sc.nextLine().trim());
            origenOpt.get().transferirA(destinoOpt.get(), monto);
            System.out.println("Transferencia exitosa.");
        } catch (NumberFormatException e) {
            System.out.println("Monto inválido.");
        } catch (InsufficientFundsException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void historialFlow(Scanner sc, Banco banco) {
        Optional<CuentaBancaria> o = obtenerCuentaPorId(sc, banco);
        o.ifPresentOrElse(CuentaBancaria::mostrarHistorial,
                () -> System.out.println("Cuenta no encontrada."));
    }

    private static void interesFlow(Scanner sc, Banco banco) {
        Optional<CuentaBancaria> o = obtenerCuentaPorId(sc, banco);
        o.ifPresentOrElse(CuentaBancaria::aplicarInteresOCargo,
                () -> System.out.println("Cuenta no encontrada."));
    }

    private static void listarFlow(Banco banco) {
        Collection<CuentaBancaria> cuentas = banco.listar();
        if (cuentas.isEmpty()) {
            System.out.println("No hay cuentas registradas.");
            return;
        }
        cuentas.forEach(System.out::println);
    }

    private static Optional<CuentaBancaria> obtenerCuentaPorId(Scanner sc, Banco banco) {
        System.out.print("Ingrese ID de cuenta: ");
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            return banco.obtenerCuenta(id);
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return Optional.empty();
        }
    }
}
