import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        System.out.println(sumOfArray(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21}));
        System.out.println(maxFromArray(new int[][]{
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21},
                {5, 6, 7, 8, 9, 10, 11, 151251242, 13, 14, 15, 512515, 17, 18, 19, 20, 21, 22, 23, 24},
                {124, 234, 543, 2, 9, 10, 5342123, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 924324234, 23, 24},
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 15345, 13, 14, 23523, 16, 17, 18, 19, 42343240, 21}
        }));

    }

    public static int maxFromArray(int[][] array) throws ExecutionException, InterruptedException {
        AtomicInteger max1 = new AtomicInteger(array[0][0]);
        AtomicInteger max2 = new AtomicInteger(array[0][0]);

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Future future = executorService.submit(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < array.length; i++) {
                    for (int j = 0; j < array[i].length - i; j++) {
                        if (array[i][j] > max1.get()) {
                            max1.set(array[i][j]);
                        }
                    }
                }
            }
        });

        Future future2 = executorService.submit(new Runnable() {
            @Override
            public void run() {
                for (int i = array.length-1; i > 0; i--) {
                    for (int j = array[i].length - 1; j > i; j--) {
                        if (array[i][j] > max2.get()) {
                            max2.set(array[i][j]);
                        }
                    }
                }
            }
        });

        future.get();
        future2.get();
        executorService.shutdown();

        return Math.max(max1.get(), max2.get());
    }

    public static int sumOfArray(int[] array) throws ExecutionException, InterruptedException {
        int[] a1 = new int[array.length / 2];
        int[] a2 = new int[array.length - a1.length];
        for (int i = 0; i < array.length; i++) {
            if (i < array.length / 2) {
                a1[i] = array[i];
            } else {
                a2[i - array.length / 2] = array[i];
            }
        }

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        AtomicInteger sum1 = new AtomicInteger(0);
        AtomicInteger sum2 = new AtomicInteger(0);
        Future future = executorService.submit(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < a1.length; i++) {
                    sum1.addAndGet(a1[i]);
                }
            }
        });
        Future future1 = executorService.submit(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < a2.length; i++) {
                    sum2.addAndGet(a2[i]);
                }
            }
        });

        future.get();
        future1.get();
        executorService.shutdown();

        return sum1.get() + sum2.get();

    }

}