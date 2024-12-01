import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class Warehouse {
    public static void main(String[] args) throws Exception {
        List<Integer> warehouse = new ArrayList<>(List.of(10, 20, 30, 40, 50, 15, 25, 35, 45, 5, 10, 20, 30, 40, 50, 15, 25, 35, 45, 5, 10, 20, 30, 40, 50, 15, 25, 35, 45, 5));
        AtomicInteger totalWeight = new AtomicInteger(0);

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Loader loader = new LoaderRealization();
        Future future1 = executorService.submit(() ->
                loader.loadItems(warehouse, totalWeight)
        );
        Future future2 = executorService.submit(() ->
                loader.loadItems(warehouse, totalWeight)
        );
        Future future3 = executorService.submit(() ->
                loader.loadItems(warehouse, totalWeight)
        );

        future1.get();
        future2.get();
        future3.get();
        executorService.shutdown();

        System.out.println("Общий перенесенный вес: " + totalWeight.get() + " кг");
    }
}