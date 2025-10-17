/*
 * StringExample.java
 * Ejemplo en español de uso de String y StringBuilder.
 */

public class StringExample {
    public static void main(String[] args) {
        // String es inmutable: cada operación produce una nueva instancia
        String s = "Hola";
        String t = s + ", mundo"; // concatenación crea nuevo String

        System.out.println("s = " + s);
        System.out.println("t = " + t);

        // Métodos comunes
        System.out.println("Longitud de t = " + t.length());
        System.out.println("t contiene 'mundo'? " + t.contains("mundo"));
        System.out.println("t en mayúsculas = " + t.toUpperCase());

        // Comparación: equals vs ==
        String a = new String("abc");
        String b = new String("abc");
        System.out.println("a == b => " + (a == b) + "  (false, diferentes referencias)");
        System.out.println("a.equals(b) => " + a.equals(b) + "  (true, mismo contenido)");

        // StringBuilder para concatenaciones eficientes
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append(i).append("-");
        }
        System.out.println("StringBuilder result: " + sb.toString());

        // Inmutabilidad y seguridad: por qué preferir String para claves
        java.util.HashMap<String, Integer> map = new java.util.HashMap<>();
        map.put("clave", 1);
        System.out.println("HashMap con String como clave: " + map.get("clave"));
    }
}
