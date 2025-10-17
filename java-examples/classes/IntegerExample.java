/*
 * IntegerExample.java
 * Ejemplo en español sobre la clase wrapper Integer, autoboxing y nullabilidad.
 */

import java.util.Optional;

public class IntegerExample {
    public static void main(String[] args) {
        // Integer es una clase (wrapper) que envuelve el tipo primitivo int.
        Integer wrapped = Integer.valueOf(100); // creación explícita
        int unwrapped = wrapped.intValue(); // obtención del primitivo

        // Autoboxing y unboxing (conversión automática)
        Integer autoWrapped = 200; // autoboxing
        int autoUnwrapped = autoWrapped; // unboxing

        System.out.println("Integer wrapped = " + wrapped);
        System.out.println("int unwrapped = " + unwrapped);
        System.out.println("Autoboxing: Integer autoWrapped = " + autoWrapped);
        System.out.println("Unboxing: int autoUnwrapped = " + autoUnwrapped);

        // Nullabilidad: Integer puede ser null, int no.
        Integer puedeSerNull = null;
        System.out.println("\nInteger puede ser null: " + puedeSerNull);

        // Evitar NullPointerException al unboxear
        try {
            int peligro = puedeSerNull; // esto lanzará NullPointerException
            System.out.println(peligro);
        } catch (NullPointerException npe) {
            System.out.println("Evitar NPE: no se puede unboxear una referencia null");
        }

        // Uso de Optional para envolver valores que pueden ser null
        Optional<Integer> opt = Optional.ofNullable(puedeSerNull);
        System.out.println("\nOptional presente? " + opt.isPresent());
        System.out.println("Optional con valor por defecto (orElse): " + opt.orElse(0));

        // Métodos útiles
        System.out.println("Integer.parseInt(\"123\") = " + Integer.parseInt("123"));
        System.out.println("Integer.toString(456) = " + Integer.toString(456));

        // Comparaciones
        Integer a = 127; // en cache de wrappers suele estar -128..127
        Integer b = 127;
        System.out.println("a == b => " + (a == b) + "  (== compara referencias)");
        System.out.println("a.equals(b) => " + a.equals(b) + "  (equals compara valor)");

        Integer c = 128;
        Integer d = 128;
        System.out.println("c == d => " + (c == d) + "  (puede ser false fuera del cache)");
        System.out.println("c.equals(d) => " + c.equals(d));
    }
}
