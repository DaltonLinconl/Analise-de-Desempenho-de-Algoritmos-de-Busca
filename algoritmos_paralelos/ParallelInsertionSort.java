package algoritmos_paralelos;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class ParallelInsertionSort {

    private static final int BLOCK_SIZE = 1000;

    public static void insertionSort(int[] array, int numThreads) {
        ForkJoinPool pool = new ForkJoinPool(numThreads);
        try {
            pool.invoke(new SortTask(array, 0, array.length));
        } finally {
            pool.shutdown(); // Garante que o pool será encerrado, mesmo se ocorrer uma exceção
        }
    }
    

    static class SortTask extends RecursiveAction {
        private final int[] array;
        private final int start;
        private final int end;

        public SortTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            int length = end - start;
            if (length <= BLOCK_SIZE) {
                insertionSort(array, start, end);
            } else {
                int mid = start + length / 2;
                SortTask left = new SortTask(array, start, mid);
                SortTask right = new SortTask(array, mid, end);
                invokeAll(left, right);
                merge(array, start, mid, end);
            }
        }

        private void insertionSort(int[] array, int start, int end) {
            for (int i = start + 1; i < end; i++) {
                int key = array[i];
                int j = i - 1;
                while (j >= start && array[j] > key) {
                    array[j + 1] = array[j];
                    j--;
                }
                array[j + 1] = key;
            }
        }

        private void merge(int[] array, int start, int mid, int end) {
            int[] merged = new int[end - start];
            int i = start, j = mid, k = 0;

            while (i < mid && j < end) {
                if (array[i] <= array[j]) {
                    merged[k++] = array[i++];
                } else {
                    merged[k++] = array[j++];
                }
            }

            while (i < mid) {
                merged[k++] = array[i++];
            }

            while (j < end) {
                merged[k++] = array[j++];
            }

            System.arraycopy(merged, 0, array, start, merged.length);
        }
    }
}
