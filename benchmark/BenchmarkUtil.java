package benchmark;

import java.io.FileWriter;
import java.io.IOException;
//import java.util.concurrent.TimeUnit;

public class BenchmarkUtil {

    public static long measureExecutionTime(Runnable algorithm) {
        long start = System.currentTimeMillis();
        algorithm.run();
        long end = System.currentTimeMillis();
        return end - start; // Retorna tempo em ms
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
