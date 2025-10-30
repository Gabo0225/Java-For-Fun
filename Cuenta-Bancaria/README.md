## Banco CLI en Java

Aplicación de consola que simula la gestión de cuentas bancarias: creación de cuentas, depósitos, retiros, transferencias, consulta de saldo, historial y aplicación de interés/cargo según el tipo de cuenta.

### Características
- **Tipos de cuenta**: Corriente y Ahorros.
- **Operaciones**:
  - Crear cuenta
  - Consultar saldo
  - Depositar
  - Retirar (con validación de fondos)
  - Transferir entre cuentas
  - Ver historial de transacciones
  - Aplicar interés (Ahorros +1%) o cargo (Corriente -0.5%)
  - Listar todas las cuentas
- **Historial** con fecha/hora y formato legible.
- **IDs autoincrementales** de cuentas.
- **Validaciones** de entradas y excepciones personalizadas (`InsufficientFundsException`).
- **Seguridad en concurrencia**: métodos críticos sincronizados en `CuentaBancaria`.

### Requisitos
- Java 17 o superior (recomendado)
- No requiere dependencias externas

### Estructura del proyecto
Archivo único con clases internas:
- `CuentaBancaria` (y `Transaccion`, `InsufficientFundsException`)
- `Banco`
- `main` con menú interactivo

Guarda todo el código en un archivo llamado `CuentaBancaria.java`.

### Compilación
```bash
javac CuentaBancaria.java
```

### Ejecución
```bash
java CuentaBancaria
```

### Uso (Menú)
Al iniciar, verás el menú:
- 1 - Crear cuenta  
- 2 - Consultar saldo  
- 3 - Retirar  
- 4 - Depositar  
- 5 - Transferir  
- 6 - Ver historial  
- 7 - Aplicar interés/cargo  
- 8 - Listar cuentas  
- 9 - Salir

Se crean dos cuentas de ejemplo al arrancar:
- Tony Stark (Corriente) con 1500.00
- Steve Rogers (Ahorros) con 2000.00

### Ejemplo de sesión
```text
====== MENÚ BANCO ======
1 - Crear cuenta
2 - Consultar saldo
3 - Retirar
4 - Depositar
5 - Transferir
6 - Ver historial
7 - Aplicar interés/cargo
8 - Listar cuentas
9 - Salir
Seleccione opción: 8
ID:1 - Tony Stark (CORRIENTE) - Saldo: 1500.00
ID:2 - Steve Rogers (AHORROS) - Saldo: 2000.00

Seleccione opción: 4
Ingrese ID de cuenta: 1
Cantidad a depositar: 300
Depósito exitoso. Nuevo saldo: 1800.0

Seleccione opción: 7
Ingrese ID de cuenta: 2
Interés aplicado correctamente. Nuevo saldo: 2020.0

Seleccione opción: 6
Ingrese ID de cuenta: 2

Historial de transacciones de Steve Rogers:
[2025-10-30 10:15:20] Apertura de cuenta             2000.00
[2025-10-30 10:16:05] Interés aplicado (+1%)            20.00
```

### Notas de diseño
- `synchronized` en operaciones que modifican saldo para evitar condiciones de carrera.
- El historial mantiene monto con signo (retiro/transferencia como negativo).
- `Banco` administra un mapa de cuentas y expone creación/búsqueda/listado.
- Los mensajes al usuario están en español y el formato de fecha es `yyyy-MM-dd HH:mm:ss`.

### Personalización
- Tasa de interés (`1%`) y cargo (`0.5%`) en `aplicarInteresOCargo()`.
- Mensajes y formato de impresión en `toString()` y `Transaccion.toString()`.

