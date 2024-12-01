import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

class LoaderRealization implements Loader {
    int max_load = 150;

    @Override
    public void loadItems(List<Integer> warehouse, AtomicInteger totalWeight) {
        List<Integer> carriedItems = new ArrayList<>();
        int localWeight = 0;

        while (true) {
            synchronized (warehouse) {
                if (warehouse.isEmpty()) {
                    break;
                }
                int itemWeight = warehouse.get(0);
                if (localWeight + itemWeight <= max_load) {
                    carriedItems.add(itemWeight);
                    localWeight += itemWeight;
                    warehouse.remove(0);
                } else {
                    break;
                }
            }
        }

        if (!carriedItems.isEmpty()) {
            System.out.println(Thread.currentThread().getName() + " несет предметы: " + carriedItems + ", общий вес: " + localWeight + " кг");
            totalWeight.addAndGet(localWeight);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println(Thread.currentThread().getName() + " донес грузы");
        }
    }
}


