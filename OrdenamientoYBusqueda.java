import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class OrdenamientoYBusqueda {
    public static void main(String[] args) {
        try (Scanner entrada = new Scanner(System.in, StandardCharsets.UTF_8.name())) {
            ejecutar(entrada, System.out);
        }
    }

    static void ejecutar(Scanner entrada, PrintStream salida) {
        salida.println("Ordenamiento y búsqueda de palabras");
        int cantidad = leerEntero(entrada, salida, "Cantidad de palabras: ", 1, Integer.MAX_VALUE);
        if (cantidad == -1) {
            return;
        }

        String[] palabras = new String[cantidad];
        for (int i = 0; i < palabras.length; i++) {
            palabras[i] = leerPalabra(entrada, salida, "Palabra " + (i + 1) + ": ");
            if (palabras[i] == null) {
                return;
            }
        }

        salida.println("\nElija un algoritmo de ordenamiento:");
        salida.println("1. Selección");
        salida.println("2. Inserción");
        salida.println("3. Burbuja");
        salida.println("4. Mezcla (merge sort)");
        salida.println("5. Rápido (quicksort)");
        int opcion = leerEntero(entrada, salida, "Opción (1-5): ", 1, 5);
        switch (opcion) {
            case 1:
                ordenarSeleccion(palabras);
                break;
            case 2:
                ordenarInsercion(palabras);
                break;
            case 3:
                ordenarBurbuja(palabras);
                break;
            case 4:
                ordenarMezcla(palabras);
                break;
            case 5:
                ordenarRapido(palabras);
                break;
            default:
                return;
        }

        salida.println("\nArreglo ordenado: " + Arrays.toString(palabras));
        String objetivo = leerPalabra(entrada, salida, "Palabra que desea buscar: ");
        if (objetivo == null) {
            return;
        }
        int indice = buscarBinaria(palabras, objetivo);
        if (indice >= 0) {
            salida.println("Palabra encontrada en la posición " + (indice + 1)
                    + " (índice " + indice + ").");
        } else {
            salida.println("La palabra no se encontró en el arreglo.");
        }
    }

    private static int leerEntero(Scanner entrada, PrintStream salida, String mensaje,
                                  int minimo, int maximo) {
        while (true) {
            salida.print(mensaje);
            if (!entrada.hasNextLine()) {
                return -1;
            }
            try {
                int valor = Integer.parseInt(entrada.nextLine().trim());
                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }
            } catch (NumberFormatException ex) {
                // Se vuelve a pedir el número.
            }
            if (maximo == Integer.MAX_VALUE) {
                salida.println("Ingrese un entero positivo válido.");
            } else {
                salida.println("Ingrese un entero entre " + minimo + " y " + maximo + ".");
            }
        }
    }

    private static String leerPalabra(Scanner entrada, PrintStream salida, String mensaje) {
        while (true) {
            salida.print(mensaje);
            if (!entrada.hasNextLine()) {
                return null;
            }
            String palabra = entrada.nextLine().trim();
            if (!palabra.isEmpty()) {
                return palabra;
            }
            salida.println("Ingrese una palabra no vacía.");
        }
    }

    // Tiempo O(n²) en todos los casos: cada pasada recorre el tramo pendiente.
    static void ordenarSeleccion(String[] palabras) {
        for (int i = 0; i < palabras.length - 1; i++) {
            // Busca la menor palabra a partir de i.
            int menor = i;
            for (int j = i + 1; j < palabras.length; j++) {
                if (palabras[j].compareTo(palabras[menor]) < 0) {
                    menor = j;
                }
            }
            // Deja esa palabra en la posición i.
            if (menor != i) {
                intercambiar(palabras, i, menor);
            }
        }
    }

    // Tiempo O(n) en el mejor caso y O(n²) en promedio y en el peor caso.
    static void ordenarInsercion(String[] palabras) {
        for (int i = 1; i < palabras.length; i++) {
            String actual = palabras[i];
            int hueco = i;
            // Mueve a la derecha las palabras mayores que la actual.
            while (hueco > 0 && palabras[hueco - 1].compareTo(actual) > 0) {
                palabras[hueco] = palabras[hueco - 1];
                hueco--;
            }
            // Coloca la palabra en el hueco.
            palabras[hueco] = actual;
        }
    }

    // Tiempo O(n) en el mejor caso y O(n²) en promedio y en el peor caso.
    static void ordenarBurbuja(String[] palabras) {
        for (int fin = palabras.length - 1; fin > 0; fin--) {
            boolean huboIntercambio = false;
            // Intercambia vecinos fuera de orden. El mayor queda al final del tramo.
            for (int i = 0; i < fin; i++) {
                if (palabras[i].compareTo(palabras[i + 1]) > 0) {
                    intercambiar(palabras, i, i + 1);
                    huboIntercambio = true;
                }
            }
            // Si nada se movió, el arreglo ya está ordenado.
            if (!huboIntercambio) {
                return;
            }
        }
    }

    // Tiempo O(n log n) en todos los casos: cada nivel mezcla O(n) elementos.
    static void ordenarMezcla(String[] palabras) {
        String[] auxiliar = new String[palabras.length];
        dividirYMezclar(palabras, auxiliar, 0, palabras.length - 1);
    }

    private static void dividirYMezclar(String[] palabras, String[] auxiliar, int inicio, int fin) {
        // Con cero o una palabra, termina la división.
        if (inicio >= fin) {
            return;
        }
        int medio = inicio + (fin - inicio) / 2;
        // Ordena las dos mitades y luego las junta.
        dividirYMezclar(palabras, auxiliar, inicio, medio);
        dividirYMezclar(palabras, auxiliar, medio + 1, fin);
        mezclar(palabras, auxiliar, inicio, medio, fin);
    }

    private static void mezclar(String[] palabras, String[] auxiliar, int inicio, int medio, int fin) {
        int izquierda = inicio;
        int derecha = medio + 1;
        // Toma la menor palabra de las dos mitades. Si una se acaba, usa la otra.
        for (int destino = inicio; destino <= fin; destino++) {
            if (derecha > fin || (izquierda <= medio
                    && palabras[izquierda].compareTo(palabras[derecha]) <= 0)) {
                auxiliar[destino] = palabras[izquierda++];
            } else {
                auxiliar[destino] = palabras[derecha++];
            }
        }
        System.arraycopy(auxiliar, inicio, palabras, inicio, fin - inicio + 1);
    }

    // Tiempo O(n log n) esperado con pivote aleatorio y O(n²) en el peor caso.
    // Si todas las palabras son iguales, una partición resuelve el tramo en O(n).
    static void ordenarRapido(String[] palabras) {
        ordenarRapido(palabras, 0, palabras.length - 1);
    }

    private static void ordenarRapido(String[] palabras, int inicio, int fin) {
        while (inicio < fin) {
            String pivote = palabras[ThreadLocalRandom.current().nextInt(inicio, fin + 1)];
            int menores = inicio;
            int actual = inicio;
            int mayores = fin;
            // Deja las menores a la izquierda, las iguales al centro y las mayores a la derecha.
            while (actual <= mayores) {
                int comparacion = palabras[actual].compareTo(pivote);
                if (comparacion < 0) {
                    intercambiar(palabras, menores, actual);
                    menores++;
                    actual++;
                } else if (comparacion > 0) {
                    intercambiar(palabras, actual, mayores);
                    mayores--;
                    // Revisa la palabra que acaba de llegar a esta posición.
                } else {
                    actual++;
                }
            }
            // Los iguales ya quedaron en su sitio. Ordena primero el lado más corto
            // con recursión y sigue con el otro en el ciclo para ahorrar pila.
            if (menores - inicio < fin - mayores) {
                ordenarRapido(palabras, inicio, menores - 1);
                inicio = mayores + 1;
            } else {
                ordenarRapido(palabras, mayores + 1, fin);
                fin = menores - 1;
            }
        }
    }

    // Tiempo O(log n) en el peor caso, porque cada paso descarta casi la mitad.
    // Si la palabra está justo en el primer punto medio, tarda O(1).
    static int buscarBinaria(String[] palabras, String objetivo) {
        int inicio = 0;
        int fin = palabras.length - 1;
        // Empieza con todo el arreglo ya ordenado.
        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            int comparacion = objetivo.compareTo(palabras[medio]);
            if (comparacion == 0) {
                return medio;
            }
            // Sigue por la izquierda si la palabra es menor, o por la derecha si es mayor.
            if (comparacion < 0) {
                fin = medio - 1;
            } else {
                inicio = medio + 1;
            }
        }
        return -1;
    }

    private static void intercambiar(String[] palabras, int primera, int segunda) {
        String temporal = palabras[primera];
        palabras[primera] = palabras[segunda];
        palabras[segunda] = temporal;
    }
}
