package algoritmos_paralelos;

import java.util.concurrent.RecursiveAction;

import algortimos_seriais.QuickSort;

import java.util.concurrent.ForkJoinPool;

public class ParallelQuickSort {

    private static final int THRESHOLD = 1000; // Limite mínimo para paralelizar

    public static void quickSort(int[] array, int numThreads) {
        ForkJoinPool pool = new ForkJoinPool(numThreads);
        try {
            pool.invoke(new QuickSortTask(array, 0, array.length - 1));
        } finally {
            pool.shutdown();
        }
    }
    

    static class QuickSortTask extends RecursiveAction {
        private final int[] array;
        private final int low;
        private final int high;

        public QuickSortTask(int[] array, int low, int high) {
            this.array = array;
            this.low = low;
            this.high = high;
        }

        @Override
        protected void compute() {
            if (low < high) {
                if (high - low < THRESHOLD) {
                    // Pequeno o suficiente para usar sort sequencial
                    QuickSort.quickSort(array, low, high);
                } else {
                    int pivotIndex = partition(array, low, high);
                    QuickSortTask leftTask = new QuickSortTask(array, low, pivotIndex - 1);
                    QuickSortTask rightTask = new QuickSortTask(array, pivotIndex + 1, high);
                    invokeAll(leftTask, rightTask);
                }
            }
        }

        private int partition(int[] array, int low, int high) {
            int pivot = array[high];
            int i = (low - 1);
            for (int j = low; j < high; j++) {
                if (array[j] < pivot) {
                    i++;
                    swap(array, i, j);
                }
            }
            swap(array, i + 1, high);
            return i + 1;
        }

        private void swap(int[] array, int i, int j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}
