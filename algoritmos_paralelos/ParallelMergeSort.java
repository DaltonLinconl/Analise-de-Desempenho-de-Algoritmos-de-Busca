package algoritmos_paralelos;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelMergeSort {

    public static void main(String[] args) {
        int n = 1_000_000; // Tamanho do array
        int[] array = generateRandomArray(n);

        long startTime = System.currentTimeMillis();

        mergeSort(array, 5); // Número de threads

        long endTime = System.currentTimeMillis();
        System.out.println("\nTempo de execução paralelo para " + n + " elementos: " + (endTime - startTime) + " ms");
    }

    public static void mergeSort(int[] array, int numThreads) {
        ForkJoinPool pool = new ForkJoinPool(numThreads);
        try {
            pool.invoke(new MergeSortTask(array, 0, array.length));
        } finally {
            pool.shutdown();
        }
    }
    

    // Classe que representa a tarefa de merge sort paralelizado
    static class MergeSortTask extends RecursiveAction {
        private final int[] array;
        private final int start;
        private final int end;
        private static final int THRESHOLD = 10_000;

        public MergeSortTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            int length = end - start;
            if (length < 2) return;

            if (length <= THRESHOLD) {
                // Faz o merge sort sequencialmente se for pequeno
                sequentialMergeSort(array, start, end);
                return;
            }

            int mid = start + (length / 2);

            MergeSortTask leftTask = new MergeSortTask(array, start, mid);
            MergeSortTask rightTask = new MergeSortTask(array, mid, end);

            invokeAll(leftTask, rightTask);

            merge(array, start, mid, end);
        }

        // Merge de dois subarrays [start, mid) e [mid, end)
        private void merge(int[] array, int start, int mid, int end) {
            int[] temp = new int[end - start];
            int i = start, j = mid, k = 0;

            while (i < mid && j < end) {
                if (array[i] <= array[j]) {
                    temp[k++] = array[i++];
                } else {
                    temp[k++] = array[j++];
                }
            }

            while (i < mid) temp[k++] = array[i++];
            while (j < end) temp[k++] = array[j++];

            System.arraycopy(temp, 0, array, start, temp.length);
        }

        // Versão sequencial usada para arrays pequenos
        private void sequentialMergeSort(int[] a, int start, int end) {
            int n = end - start;
            if (n < 2) return;

            int mid = start + n / 2;
            sequentialMergeSort(a, start, mid);
            sequentialMergeSort(a, mid, end);
            merge(a, start, mid, end);
        }
    }

    // Gera array com valores aleatórios
    public static int[] generateRandomArray(int size) {
        Random rand = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(size);
        }
        return array;
    }
}
