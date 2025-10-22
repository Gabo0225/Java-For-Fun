# Ejercicio: Extender la Cuenta Bancaria

Objetivo  
Agregar 3 funcionalidades nuevas al proyecto Cuenta-Bancaria para practicar principios OOP (encapsulación, SRP, OCP, DIP) y manejo de errores.

Requisitos mínimos (implementar 3 funcionalidades distintas)
1. Transferencia entre cuentas
   - Método: Banco.transferir(int fromId, int toId, double monto)
   - Comportamiento: valida existencia de cuentas, monto positivo y fondos suficientes; realiza débito y crédito de forma atómica (si falla, no cambia nada).
   - Mensajes de error claros (cuenta no encontrada, monto inválido, fondos insuficientes).

2. Historial de transacciones
   - Registrar cada operación (depósito, retiro, transferencia, interés aplicado) con: tipo, monto, fecha/hora y saldo resultante.
   - Proveer método para listar historial por cuenta: Banco.obtenerHistorial(int id) o CuentaBancaria.getHistorial().
   - El historial debe ser inmutable desde fuera (devuelve copia o vista no modificable).

3. Aplicar intereses / cargos periódicos
   - Para cuentas de tipo AHORROS: método aplicarInteres(double tasa) que agregue el interés y registre la transacción.
   - Para cuentas CORRIENTE: permitir un cargo mensual fijo (p. ej. aplicarCargoMensual(double monto)).
   - Añadir comando en CLI para ejecutar la aplicación de todos los intereses/cargos en todas las cuentas.

Criterios de aceptación
- Código compila con Java 11+ (evitar sintaxis de versiones más nuevas si el contenedor no las soporta).
- Validaciones y manejo de excepciones claros.
- Mantener separación de responsabilidades: lógica de negocio en Banco/Cuenta, I/O sólo en main.
- Tests básicos o programa de demostración que muestre las nuevas funcionalidades funcionando.


Pruebas manuales rápidas (CLI)
- Compilar:
  - javac -d out $(find . -name "*.java")
- Ejecutar:
  - java -cp out CuentaBancaria
- Flujo a probar:
  1. Crear cuenta A (corriente) y B (ahorros).
  2. Depositar en A.
  3. Transferir X de A a B.
  4. Intentar transferir monto mayor que saldo -> ver error y sin cambios.
  5. Aplicar intereses globales -> comprobar historial y saldos.
  6. Listar historial de A y B.

Entrega
- Crear un nuevo proyecto en su repositorio con el ejercicio y las nuevas tres funcionalidades
- Añadir al menos un archivo de ejemplo o test que demuestre las nuevas funcionalidades (puede ser un `Demo.java` o tests simples).