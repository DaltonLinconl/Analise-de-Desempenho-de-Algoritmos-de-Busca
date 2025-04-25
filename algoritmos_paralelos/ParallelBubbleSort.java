package algoritmos_paralelos;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelBubbleSort {

    public static void main(String[] args) {
        int n = 50000;
        int[] arr = generateRandomArray(n);

        long startTime = System.currentTimeMillis();
        parallelOddEvenBubbleSort(arr, 5);
        long endTime = System.currentTimeMillis();

        System.out.println("\nTempo de execução com ForkJoinPool para " + n + " elementos: " + (endTime - startTime) + " ms");
    }

    public static void parallelOddEvenBubbleSort(int[] arr, int numThreads) {
        int n = arr.length;
        ForkJoinPool pool = new ForkJoinPool(numThreads);
    
        for (int phase = 0; phase < n; phase++) {
            int start = (phase % 2 == 0) ? 0 : 1;
    
            // Submete a tarefa à pool personalizada
            pool.invoke(new BubbleSortTask(arr, start, n - 1));
        }
    
        pool.shutdown(); // Boa prática: liberar recursos
    }

    // Tarefa paralela que realiza comparações e trocas
    static class BubbleSortTask extends RecursiveAction {
        private final int[] arr;
        private final int start;
        private final int end;
        private static final int THRESHOLD = 1000;

        public BubbleSortTask(int[] arr, int start, int end) {
            this.arr = arr;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if ((end - start) <= THRESHOLD) {
                for (int i = start; i < end; i += 2) {
                    if (arr[i] > arr[i + 1]) {
                        swap(arr, i, i + 1);
                    }
                }
            } else {
                int mid = (start + end) / 2;
                BubbleSortTask left = new BubbleSortTask(arr, start, mid);
                BubbleSortTask right = new BubbleSortTask(arr, mid + 1, end);
                invokeAll(left, right);
            }
        }

        private void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    // Gera um array com valores aleatórios
    public static int[] generateRandomArray(int size) {
        Random rand = new Random();
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(size);
        }

        return array;
    }
}
