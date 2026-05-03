package core;

public record Item(int id, double value, double weight) {
    @Override
    public String toString() {
        return String.format("Item %d [v=%.2f, w=%.2f]", id, value, weight);
    }
}