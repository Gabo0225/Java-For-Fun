# Java-For-Fun — Ejemplos en Java

Esta carpeta contiene ejemplos educativos en Java sobre tipos primitivos y tipos basados en clases (Wrapper y String), pensados para aprender y experimentar.

Estructura:

- `java-examples/`
  - `primitives/` — ejemplos y README sobre tipos primitivos.
  - `classes/` — ejemplos y README sobre tipos basados en clases (Integer, String, colecciones).

Requisitos:
- JDK 11+ (recomendado JDK 17). Comprueba tu versión con `java -version`.

Cómo compilar y ejecutar todos los ejemplos desde la raíz del repositorio:

```bash
# Compilar todos los .java recursivamente
find java-examples -name "*.java" -print | xargs javac

# Ejecutar ejemplos (desde la raíz)
java -cp java-examples/primitives PrimitiveTypesExample
java -cp java-examples/classes IntegerExample
java -cp java-examples/classes StringExample
java -cp java-examples/classes CollectionsExample
```

Notas:
- Los archivos `.java` incluyen comentarios en español y referencias bibliográficas.
- Cada subcarpeta incluye su propio `README.md` con explicación teórica y comandos de compilación/ejecución locales.
