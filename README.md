# 🎒 Knapsack Problem Challenge: The Leaderboard Edition

Bienvenido al reto del **Problema de la Mochila (0/1 Knapsack Problem)**. Este proyecto está diseñado para poner a prueba tus habilidades implementando heurísticas de optimización y compitiendo en tiempo real contra tus compañeros.

---

## 🧐 El Problema
El objetivo es sencillo de entender pero complejo de resolver: tienes una mochila con una **capacidad de peso máxima** y una lista de **objetos**, cada uno con un **valor** y un **peso**. 

Debes decidir qué objetos incluir en la mochila para **maximizar el valor total** sin superar el peso permitido. 

*   **Regla de oro:** Es un problema 0/1. O incluyes el objeto entero, o lo dejas fuera. No se pueden fraccionar.

Este es un problema clásico de la informática (**NP-Hard**), lo que significa que a medida que aumenta el número de objetos, encontrar la solución perfecta requiere algoritmos muy inteligentes. ¡Aquí es donde entran tus **heurísticas**!

---

## 🏗️ Estructura del Proyecto

El proyecto está dividido en dos paquetes principales para separar la lógica de evaluación de tu implementación:
```text
.
├── src
│   ├── core           # ⛔ NO TOCAR: El "Juez" del problema
│   │   ├── Item.java               # Representación del objeto (id, valor, peso)
│   │   ├── KnapsackSolver.java     # Interfaz que debes seguir
│   │   ├── KnapsackValidator.java  # Comprueba que tu mochila sea legal
│   │   └── Main.java               # Orquestador de la evaluación y tests
│   └── solution       # ✅ TU ZONA DE TRABAJO
│       └── BaseSolver.java         # Aquí debes programar tu estrategia
├── tests/             # Archivos .txt de prueba (públicos)
└── README.md
```

## ⚖️ Evaluación de la Heurística

¿Cómo sabemos si tu solución es buena? No nos basta con que "funcione", queremos medir su nivel de optimización de forma objetiva.

### 1. El Techo Teórico (Upper Bound)
Como calcular la solución perfecta en cada test es muy costoso computacionalmente, el sistema utiliza el **Knapsack Fraccionario** como referencia. 

*   Se ordenan los objetos por densidad de valor (**valor / peso**).
*   Se llena la mochila permitiendo "trocear" el último objeto para aprovechar el 100% de la capacidad.
*   Este valor es el **100% teórico**: es físicamente imposible que una solución 0/1 supere este valor.

### 2. El Score
Tu puntuación en cada archivo de prueba se calcula mediante la siguiente fórmula:

$$Score = \left( \frac{\text{Valor obtenido}}{\text{Valor máximo teórico (Upper Bound)}} \right) \times 100$$

> **Nota Importante:** Si tu solución es **inválida** (superas el peso máximo, repites objetos o incluyes objetos que no existen en la lista original), el validador te asignará un score de **0** para ese test.

---

## 🤖 Evaluación Automática

El repositorio está integrado con **GitHub Actions**. Cada vez que realices un `git push` a tu rama principal:

1.  **Compilación:** El sistema compila tu código automáticamente para asegurar que no hay errores sintácticos.
2.  **Testing:** Se ejecuta tu solución contra una batería de **tests privados** (basados en las instancias de Pisinger) con diferentes niveles de dificultad:
    *   **Uncorrelated:** Objetos con pesos y valores aleatorios.
    *   **Strongly Correlated:** Objetos donde el peso y el valor están muy ligados (donde las heurísticas más simples suelen fallar).
3.  **Cálculo de Nota:** Se obtiene la media aritmética de todos los scores obtenidos en la batería de tests.

---

## 🏆 El Leaderboard

¡Aquí empieza la competición! Tras cada entrega válida, el sistema actualiza el **Ranking en Tiempo Real**.

*   **Puntuación:** La media de calidad de tus soluciones sobre 100.00.
*   **Tiempo:** En caso de empate en score, el alumno cuya solución sea más rápida (medida en milisegundos de ejecución) ocupará la posición superior.
*   **Actualización:** El ranking se refresca automáticamente con cada envío.

---

## 🛠️ Cómo empezar (Local)

Para probar tu solución en tu ordenador antes de subirla al ranking oficial:

1.  **Implementa** tu lógica en el archivo `src/solution/BaseSolver.java`.
2.  **Compila** el proyecto completo desde la raíz:
    ```bash
    javac -d out src/core/*.java src/solution/*.java
    ```
3.  **Ejecuta** los tests incluidos en la carpeta pública:
    ```bash
    java -cp out core.Main tests/
    ```

---

*Este proyecto forma parte del módulo de Programación*  
*¡Que gane la mejor heurística!* 🚀