package benchmark;

import java.io.FileWriter;
import java.io.IOException;

public class BenchmarkUtil {

    public static long measureExecutionTime(Runnable algorithm) {
        long start = System.nanoTime();       // alta precisão
        algorithm.run();
        long end = System.nanoTime();
        return (end - start) / 1_000_000;     // converte para milissegundos
    }

    public static void saveToCSV(String filename, String[] header, String[][] data) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.append(String.join(",", header)).append("\n");
            for (String[] row : data) {
                writer.append(String.join(",", row)).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
