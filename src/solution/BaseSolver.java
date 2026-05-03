package solution;

import core.Item;
import core.KnapsackSolver;
import java.util.ArrayList;
import java.util.List;

public class BaseSolver implements KnapsackSolver {

    @Override
    public List<Item> solve(List<Item> items, double capacity) {
        List<Item> selectedItems = new ArrayList<>();
        double currentWeight = 0;

        for (Item item : items) {
            // Si el objeto cabe con lo que ya tenemos, para adentro
            if (currentWeight + item.weight() <= capacity) {
                selectedItems.add(item);
                currentWeight += item.weight();
            }
        }

        return selectedItems;
    }
}