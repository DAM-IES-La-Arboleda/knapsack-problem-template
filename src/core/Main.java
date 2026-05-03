package core;

import solution.BaseSolver; 
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Uso: java core.Main <directorio_de_pruebas>");
            return;
        }

        File folder = new File(args[0]);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));
        
        if (files == null || files.length == 0) {
            System.out.println("No se encontraron archivos .txt");
            return;
        }

        Arrays.sort(files);
        KnapsackSolver solver = new BaseSolver(); 
        
        double totalScore = 0;
        int count = 0;

        System.out.println("\n=== EVALUACIÓN DE MOCHILA (KNAPSACK) ===");
        System.out.printf("%-15s | %-8s | %-8s | %-8s | %-6s%n", "ARCHIVO", "VALOR", "UPPER B.", "TIEMPO", "SCORE");
        System.out.println("------------------------------------------------------------------");

        for (File f : files) {
            double score = evaluateFile(f, solver);
            if (score >= 0) {
                totalScore += score;
                count++;
            }
        }

        if (count > 0) {
            System.out.println("------------------------------------------------------------------");
            System.out.printf("PUNTUACIÓN MEDIA FINAL: %.2f / 100.00%n", (totalScore / count));
            System.out.println("==================================================================\n");
            System.out.println("FINAL_SCORE=" + (totalScore / count));
        }
    }

    private static double evaluateFile(File f, KnapsackSolver solver) {
        try (Scanner sc = new Scanner(f).useLocale(Locale.US)) {
            double capacity = sc.nextDouble();
            List<Item> allItems = new ArrayList<>();
            
            while (sc.hasNextInt()) {
                int id = sc.nextInt();
                double val = sc.nextDouble();
                double weight = sc.nextDouble();
                allItems.add(new Item(id, val, weight));
            }

            double upperBound = calculateUpperBound(capacity, allItems);

            long start = System.nanoTime();
            List<Item> selected = solver.solve(allItems, capacity);
            long end = System.nanoTime();

            // --- LÓGICA DE VALIDACIÓN CORREGIDA ---
            KnapsackValidator validator = new KnapsackValidator();
            KnapsackValidator.ValidationResult result = validator.validate(selected, allItems, capacity);

            if (result.isValid()) {
                double valueObtained = result.totalValue();
                // Si la mochila está vacía pero es válida, el score es 0 (a menos que el ideal sea 0)
                double score = (upperBound == 0) ? 100 : (valueObtained / upperBound) * 100;
                double ms = (end - start) / 1_000_000.0;

                System.out.printf("%-15s | %-8.2f | %-8.2f | %-6.2fms | %-6.2f%n", 
                                  f.getName(), valueObtained, upperBound, ms, score);
                return score;
            } else {
                // Si no es válida, imprimimos el mensaje del record
                System.out.printf("%-15s | INVALID: %s%n", f.getName(), result.message());
                return 0;
            }
            // --------------------------------------

        } catch (Exception e) {
            System.err.println("Error al procesar " + f.getName() + ": " + e.getMessage());
            return -1;
        }
    }

    private static double calculateUpperBound(double capacity, List<Item> items) {
        List<Item> sorted = new ArrayList<>(items);
        sorted.sort((a, b) -> Double.compare(b.value()/b.weight(), a.value()/a.weight()));

        double currentWeight = 0;
        double totalValue = 0;

        for (Item item : sorted) {
            if (currentWeight + item.weight() <= capacity) {
                currentWeight += item.weight();
                totalValue += item.value();
            } else {
                double remaining = capacity - currentWeight;
                totalValue += item.value() * (remaining / item.weight());
                break;
            }
        }
        return totalValue;
    }
}