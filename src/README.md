# 🐭 Juego del Ratón y el Queso 🧀

¡Bienvenido al **Juego del Ratón y el Queso**! Este es un juego de estrategia en el que guías a un ratón a través de un tablero para encontrar un queso, evitando al gato y resolviendo preguntas en celdas especiales. ¡Suma puntos y demuestra tus habilidades!

---

## 🎮 **Descripción del Juego**

El objetivo del juego es mover al ratón (`MM`) por un tablero de 4x4 celdas para alcanzar el queso (`CH`), evitando al gato (`CC`) y descubriendo celdas especiales (`++` y `--`) que contienen preguntas. Cada movimiento suma puntos, pero ¡cuidado con las trampas!

### Características:
- **Tablero dinámico**: Generado aleatoriamente en cada partida.
- **Celdas especiales**:
    - `++`: Responde preguntas para ganar puntos.
    - `--`: Acierta el número o pierde puntos.
- **Sistema de puntuación**: Acumula puntos moviéndote y acertando preguntas.
- **Modo test**: Visualiza el tablero completo para depuración. (Desde la terminal del ordenador)

---

## 🧠 **Proceso de Creación**

### **Diseño de Clases**
- **Clase abstracta `GameCell`**:
    - Centraliza atributos comunes como `content` (contenido visual) e `isDiscovered` (si la celda ha sido descubierta).
    - Proporciona métodos base como `setDiscovered()`.
- **Interfaz `Questionable`**:
    - Define métodos esenciales para celdas con preguntas: `getQuestion()` y `submitAnswer()`.
- **Jerarquía de celdas**:
    - **`RegularCell`**: Celda normal con puntuación aleatoria (10, 20, 30 puntos).
    - **`EndGameCell`**: Celda de fin de juego (queso `CH` o gato `CC`).
    - **`PlusCell`**: Celda con preguntas que otorgan 50 puntos si se aciertan.
    - **`MinusCell`**: Celda con preguntas que restan 50 puntos si se fallan.

### **Lógica del Tablero**
- **Generación aleatoria**:
    - El tablero se crea con celdas especiales (`++`, `--`, `CC`, `CH`) en posiciones aleatorias.
    - Se evita que las celdas especiales se solapen usando el método `getValidPosition()`.
- **Validación de movimientos**:
    - Se comprueban límites del tablero y celdas ya descubiertas.
- **Detección de fin de juego**:
    - El juego termina si el ratón encuentra el queso, es atrapado por el gato, o no tiene movimientos válidos.

### **Interacción con el Usuario**
- **Clase `Main`**:
    - Maneja la entrada/salida: lectura de movimientos (`W/A/S/D`) y respuestas a preguntas.
    - Separa claramente la lógica del juego (clases `model`) de la interacción con el usuario.
- **Mensajes intuitivos**:
    - Se muestra la puntuación acumulada tras cada movimiento.
    - Mensajes de fin de juego detallados (victoria/derrota).



