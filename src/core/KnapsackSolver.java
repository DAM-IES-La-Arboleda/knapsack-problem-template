package core;
import java.util.List;

public interface KnapsackSolver {
    /**
     * @param items Lista de objetos disponibles
     * @param capacity Capacidad máxima de peso de la mochila
     * @return Lista de objetos seleccionados
     */
    List<Item> solve(List<Item> items, double capacity);
}