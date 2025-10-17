/*
 * CollectionsExample.java
 * Ejemplo en español de uso de ArrayList y HashMap.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionsExample {
    public static void main(String[] args) {
        // ArrayList: lista dinámica
        List<String> lista = new ArrayList<>();
        lista.add("uno");
        lista.add("dos");
        lista.add("tres");

        System.out.println("ArrayList contents:");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(i + ": " + lista.get(i));
        }

        // HashMap: mapa clave -> valor
        Map<String, Integer> mapa = new HashMap<>();
        mapa.put("manzana", 3);
        mapa.put("pera", 5);

        System.out.println("\nHashMap contents:");
        for (Map.Entry<String, Integer> e : mapa.entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue());
        }

        // Ejemplo de uso combinado: contar ocurrencias
        String[] palabras = {"hola", "mundo", "hola", "java"};
        Map<String, Integer> contador = new HashMap<>();
        for (String p : palabras) {
            contador.put(p, contador.getOrDefault(p, 0) + 1);
        }

        System.out.println("\nConteo de palabras:");
        System.out.println(contador);
    }
}