package core;
import java.util.*;

public class KnapsackValidator {
    public record ValidationResult(double totalValue, boolean isValid, String message) {}

    public ValidationResult validate(List<Item> selected, List<Item> originalItems, double capacity) {
        double totalWeight = 0;
        double totalValue = 0;
        Set<Integer> seenIds = new HashSet<>();

        for (Item item : selected) {
            // Regla 1: No meter objetos duplicados (el ID debe ser único)
            if (seenIds.contains(item.id())) {
                return new ValidationResult(0, false, "Objeto duplicado: " + item.id());
            }
            
            // Regla 2: El objeto debe existir en la lista original
            boolean exists = originalItems.stream().anyMatch(i -> i.id() == item.id());
            if (!exists) {
                return new ValidationResult(0, false, "Objeto inexistente: " + item.id());
            }

            totalWeight += item.weight();
            totalValue += item.value();
            seenIds.add(item.id());
        }

        // Regla 3: No superar la capacidad
        if (totalWeight > capacity) {
            return new ValidationResult(0, false, "Capacidad excedida: " + totalWeight + "/" + capacity);
        }

        return new ValidationResult(totalValue, true, "OK");
    }
}