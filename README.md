# Algoritmos de ordenamiento y búsqueda

Práctica de SOFT-10 Estructuras de Datos. El programa llena un arreglo `String[]` de longitud fija a partir del tamaño indicado por el usuario. Luego lo ordena con el método elegido y realiza una búsqueda binaria sobre ese arreglo.

## Compilar y ejecutar

Se requiere un JDK 8 o posterior. Abra una terminal en la carpeta que contiene el archivo Java. En Windows, use una terminal con entrada UTF-8 para escribir palabras con tildes (`chcp 65001` si usa Símbolo del sistema).

```text
javac -encoding UTF-8 OrdenamientoYBusqueda.java
java -Dfile.encoding=UTF-8 OrdenamientoYBusqueda
```

Escriba la cantidad de palabras y luego una por línea. Después elija un algoritmo del 1 al 5 e ingrese la palabra que desea buscar. Los cinco métodos y la búsqueda usan el orden lexicográfico de `String.compareTo`, que distingue mayúsculas y tildes. La posición mostrada empieza en 1 y el índice empieza en 0. Si hay duplicados, la búsqueda devuelve una de las coincidencias.

## Complejidad temporal

Se usa `n` para la cantidad de palabras. La tabla supone que comparar dos palabras tiene costo constante. Si se toma en cuenta su longitud máxima `L`, cada comparación puede costar hasta `O(L)`.

| Algoritmo | Mejor caso | Promedio o esperado | Peor caso |
|---|---|---|---|
| Selección | O(n²) | O(n²) | O(n²) |
| Inserción | O(n) | O(n²) | O(n²) |
| Burbuja con salida anticipada | O(n) | O(n²) | O(n²) |
| Mezcla | O(n log n) | O(n log n) | O(n log n) |
| Rápido con pivote aleatorio y partición en tres | O(n) con claves iguales | O(n log n) esperado | O(n²) |
| Búsqueda binaria | O(1) | O(log n) | O(log n) |

Selección explora el tramo pendiente en cada pasada. Inserción desplaza los elementos mayores de un prefijo ordenado. Burbuja intercambia vecinos invertidos y se detiene si completa una pasada sin cambios. Mezcla divide el arreglo en mitades y las reúne en orden, con trabajo lineal por nivel. Rápido separa las palabras menores, iguales y mayores al pivote y procesa los dos tramos exteriores. Si las particiones quedan muy desbalanceadas repetidamente, su costo puede ser cuadrático. La búsqueda binaria reduce el tramo pendiente aproximadamente a la mitad en cada paso.

## Ejemplo

Ingrese cinco palabras en este orden: `pera`, `uva`, `mango`, `pera`, `cereza`. Al elegir cualquier método, el programa muestra `[cereza, mango, pera, pera, uva]`. Buscar `mango` devuelve la posición 2 (índice 1). En una nueva ejecución con los mismos datos, buscar `papaya` indica que la palabra no se encontró.

## Referencias

- Salas Cerdas, R. *Análisis y diseño de algoritmos*. Material 9.1 de SOFT-10.
- Salas Cerdas, R. *Algoritmos de ordenamiento y búsqueda*. Material 9.2 de SOFT-10.
- Sedgewick, R. y Wayne, K. *Algorithms*, 4.ª ed. [§ 2.2 Mergesort](https://algs4.cs.princeton.edu/22mergesort/) y [§ 2.3 Quicksort](https://algs4.cs.princeton.edu/23quicksort/).
