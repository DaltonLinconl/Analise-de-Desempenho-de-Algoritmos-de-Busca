package algortimos_seriais;

import java.util.Random;

public class MergeSort {

    public static void main(String[] args) {
        int n = 1000000; // Tamanho do array
        int[] array = generateRandomArray(n);

        long startTime = System.currentTimeMillis();
        mergeSort(array, array.length);
        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;
        System.out.println("Tempo de execução para " + n + " elementos: " + duration + " ms");
    }

    // Função que executa o Merge Sort
    public static void mergeSort(int[] array, int n) {
        if (n < 2) {
            return; // Base case: array com tamanho 1 ou vazio
        }

        int mid = n / 2;
        int[] left = new int[mid];
        int[] right = new int[n - mid];

        System.arraycopy(array, 0, left, 0, mid);
        System.arraycopy(array, mid, right, 0, n - mid);

        mergeSort(left, mid);
        mergeSort(right, n - mid);

        merge(array, left, right, mid, n - mid);
    }

    // Combina dois arrays ordenados em um único array
    public static void merge(int[] a, int[] left, int[] right, int leftSize, int rightSize) {
        int i = 0, j = 0, k = 0;

        while (i < leftSize && j < rightSize) {
            if (left[i] <= right[j]) {
                a[k++] = left[i++];
            } else {
                a[k++] = right[j++];
            }
        }

        while (i < leftSize) {
            a[k++] = left[i++];
        }

        while (j < rightSize) {
            a[k++] = right[j++];
        }
    }

    // Gera um array de tamanho `size` com números aleatórios
    public static int[] generateRandomArray(int size) {
        Random rand = new Random();
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(size);
        }

        return array;
    }
}
