package benchmark;

import algortimos_seriais.BubbleSort;
import algortimos_seriais.InsertionSort;
import algortimos_seriais.MergeSort;
import algortimos_seriais.QuickSort;

import algoritmos_paralelos.ParallelInsertionSort;
import algoritmos_paralelos.ParallelMergeSort;
import algoritmos_paralelos.ParallelQuickSort;
import algoritmos_paralelos.ParallelBubbleSort;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class SortingBenchmark {

    //private static final int[] SIZES = {1000, 10000, 50000, 100000};
    private static final int[] SIZES = {1000, 10000};
    private static final int SAMPLES = 5;

    public static void main(String[] args) {
        for (int size : SIZES) {
            int[] baseArray = generateRandomArray(size);

            benchmark("QuickSort", baseArray, 
            SortingBenchmark::runQuickSortSerial, 
            SortingBenchmark::runQuickSortParallel);
   
            benchmark("InsertionSort", baseArray, 
            SortingBenchmark::runInsertionSortSerial, 
            SortingBenchmark::runInsertionSortParallel);
   
            benchmark("BubbleSort", baseArray, 
            SortingBenchmark::runBubbleSortSerial, 
            SortingBenchmark::runBubbleSortParallel);
   
            benchmark("MergeSort", baseArray, 
            SortingBenchmark::runMergeSortSerial, 
            SortingBenchmark::runMergeSortParallel);
        }

        gerarGraficos();
        System.out.println("Benchmark concluído. Resultados salvos em resultados/");
    }

    private static void gerarGraficos(){
        try {
            ProcessBuilder pb = new ProcessBuilder("python", "./benchmark/graficos.py");
            pb.inheritIO(); // Para mostrar logs no console Java
            Process process = pb.start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Erro ao executar o script Python. Código: " + exitCode);
            } else {
                System.out.println("Script Python executado com sucesso!");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void benchmark(String algorithmName, int[] baseArray,
                                  RunnableSupplier serialSupplier,
                                  ThreadedRunnableSupplier parallelSupplier) {

        String[][] results = new String[SAMPLES + 1][];
        results[0] = new String[]{"Tipo", "Threads", "Tamanho", "Execução(ms)"};

        // Serial
        for (int i = 0; i < SAMPLES; i++) {
            int[] array = Arrays.copyOf(baseArray, baseArray.length);
            long time = BenchmarkUtil.measureExecutionTime(serialSupplier.get(array));
            results[i + 1] = new String[]{"Serial", "1", String.valueOf(array.length), String.valueOf(time)};
        }

        // Paralelo com 2, 4 e 8 threads
        int[] threadCounts = {2, 4, 8};
        for (int threads : threadCounts) {
            for (int i = 0; i < SAMPLES; i++) {
                int[] array = Arrays.copyOf(baseArray, baseArray.length);
                long time = BenchmarkUtil.measureExecutionTime(parallelSupplier.get(array, threads));
                results = appendRow(results, new String[]{"Paralelo", String.valueOf(threads), String.valueOf(array.length), String.valueOf(time)});
            }
        }

        BenchmarkUtil.saveToCSV("resultados/resultados_" + algorithmName + "_" + baseArray.length + ".csv",
                results[0], Arrays.copyOfRange(results, 1, results.length));
    }

    private static int[] generateRandomArray(int size) {
        Random rand = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) array[i] = rand.nextInt(size * 10);
        return array;
    }

    
    
    
    
    
    
    
    
    
    
    // Wrappers para facilitar passagem de funções
    @FunctionalInterface
    interface RunnableSupplier {
        Runnable get(int[] array);
    }

    @FunctionalInterface
    interface ThreadedRunnableSupplier {
        Runnable get(int[] array, int threads);
    }

    private static String[][] appendRow(String[][] oldArray, String[] newRow) {
        String[][] newArray = Arrays.copyOf(oldArray, oldArray.length + 1);
        newArray[oldArray.length] = newRow;
        return newArray;
    }


    // Implementações seriadas
    public static Runnable runQuickSortSerial(int[] array) {
        return () -> QuickSort.quickSort(array, 0, array.length - 1);
    }

    public static Runnable runInsertionSortSerial(int[] array) {
        return () -> InsertionSort.insertionSort(array);
    }

    public static Runnable runBubbleSortSerial(int[] array) {
        return () -> BubbleSort.bubbleSort(array);
    }

    public static Runnable runMergeSortSerial(int[] array) {
        return () -> MergeSort.mergeSort(array, array.length - 1);
    }

    // Implementações paralelas
    public static Runnable runBubbleSortParallel(int[] array, int threads) {
        return () -> ParallelBubbleSort.parallelOddEvenBubbleSort(array, threads);
    }

    public static Runnable runQuickSortParallel(int[] array, int threads) {
        return () -> ParallelQuickSort.quickSort(array, threads);
    }

    public static Runnable runInsertionSortParallel(int[] array, int threads) {
        return () -> ParallelInsertionSort.insertionSort(array, threads);
    }

    public static Runnable runMergeSortParallel(int[] array, int threads) {
        return () -> ParallelMergeSort.mergeSort(array, threads);
    }



}
