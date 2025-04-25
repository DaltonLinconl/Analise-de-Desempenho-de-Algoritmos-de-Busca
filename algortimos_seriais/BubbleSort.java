package algortimos_seriais;

import java.util.Random;

public class BubbleSort {

    // Função principal para executar o código
    public static void main(String[] args) {
        int n = 50000; // Número de elementos no array
        int[] arr = generateRandomArray(n);

        long startTime = System.currentTimeMillis();
        bubbleSort(arr);
        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;
        System.out.println("Tempo de execução para " + n + " elementos: " + duration + " ms");
    }

    // Função que executa o algoritmo de Bubble Sort
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    swapped = true;
                }
            }

            // Se nenhuma troca ocorreu, o array está ordenado
            if (!swapped) {
                break;
            }
        }
    }

    // Função para trocar dois elementos no array
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Função para gerar um array com elementos aleatórios
    public static int[] generateRandomArray(int size) {
        Random rand = new Random();
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(size); // Valores aleatórios de 0 a 999_999_999
        }

        return array;
    }
}
