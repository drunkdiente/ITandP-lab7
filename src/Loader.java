import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

interface Loader {
    void loadItems(List<Integer> warehouse, AtomicInteger totalWeight);
}