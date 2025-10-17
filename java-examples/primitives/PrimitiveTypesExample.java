/*
 * PrimitiveTypesExample.java
 * Ejemplo didáctico en español sobre tipos primitivos en Java.
 * Muestra declaración, valores mínimos/máximos, operaciones y arrays.
 * Referencia: documentación oficial de Oracle Java SE.
 */

public class PrimitiveTypesExample {
    public static void main(String[] args) {
        // Enteros
        int entero = 42; // 32-bit signed
        long largo = 1_000_000_000_000L; // 64-bit signed
        short corto = 32000; // 16-bit signed
        byte b = 127; // 8-bit signed

        // Reales
        float flotante = 3.14f; // 32-bit floating point
        double doble = 2.718281828459045; // 64-bit floating point

        // Caracter y booleano
        char caracter = 'A';
        boolean verdad = true;

        // Imprimir valores y propiedades
        System.out.println("Tipos primitivos en Java:");
        System.out.println("int entero = " + entero);
        System.out.println("long largo = " + largo);
        System.out.println("short corto = " + corto);
        System.out.println("byte b = " + b);

        System.out.println("float flotante = " + flotante);
        System.out.println("double doble = " + doble);

        System.out.println("char caracter = " + caracter);
        System.out.println("boolean verdad = " + verdad);

        // Rango de valores usando wrappers
        System.out.println("\nRangos:");
        System.out.println("Integer.MIN_VALUE = " + Integer.MIN_VALUE + ", Integer.MAX_VALUE = " + Integer.MAX_VALUE);
        System.out.println("Long.MIN_VALUE = " + Long.MIN_VALUE + ", Long.MAX_VALUE = " + Long.MAX_VALUE);
        System.out.println("Float.MIN_VALUE = " + Float.MIN_VALUE + ", Float.MAX_VALUE = " + Float.MAX_VALUE);
        System.out.println("Double.MIN_VALUE = " + Double.MIN_VALUE + ", Double.MAX_VALUE = " + Double.MAX_VALUE);

        // Operaciones y casting
        int suma = entero + 8; // promoción normal
        double mezcla = entero + doble; // entero se promueve a double
        int casted = (int) doble; // cast explícito truncará la parte fraccionaria

        System.out.println("\nOperaciones:");
        System.out.println("suma = " + suma);
        System.out.println("mezcla = " + mezcla);
        System.out.println("casted (int)double = " + casted);

        // Arrays de primitivos
        int[] arr = {1, 2, 3, 4, 5};
        System.out.println("\nArray de int: ");
        for (int i = 0; i < arr.length; i++) {
            System.out.println("arr[" + i + "] = " + arr[i]);
        }

        // Null no aplica a tipos primitivos; solo a referencias
        // Ejemplo: Integer (wrapper) puede ser null, int no.
        Integer wrapperNull = null;
        System.out.println("\nEjemplo wrapper Integer puede ser null: " + wrapperNull);

        // Conversión entre cadenas y primitivos
        String numeroStr = "123";
        int parsed = Integer.parseInt(numeroStr);
        System.out.println("\nParsing String -> int: '" + numeroStr + "' => " + parsed);

        // Manejo de overflow (ejemplo simple)
        int max = Integer.MAX_VALUE;
        System.out.println("\nOverflow demo: MAX = " + max + ", MAX+1 = " + (max + 1));
        // Nota: overflow en enteros es silencioso (wrap-around)
    }
}
