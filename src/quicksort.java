import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class quicksort {
    private static Random random = new Random();

    public static void quickSort(double[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }
    
    public static int partition(double[] arr, int low, int high) {
        int randomIndex = random.nextInt(high - low + 1) + low;
        swap(arr, randomIndex, high);

        double pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }
    

    public static void swap(double[] arr, int i, int j) {
        double temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static double[] readNumbersFromCSV(String filename) {
        List<Double> numbersList = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6) {
                    double number = Double.parseDouble(values[5]);
                    numbersList.add(number);
                } else {
                    System.err.println("Invalid line in CSV: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading numbers from CSV: " + e.getMessage());
            return null;
        }
    
        double[] numbers = new double[numbersList.size()];
        for (int i = 0; i < numbersList.size(); i++) {
            numbers[i] = numbersList.get(i);
        }
    
        return numbers;
    }
    

    public static void printArr(double[] arr, int limit) {
        int n = Math.min(arr.length, limit);
        System.out.print("[");
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i]);
            if (i != n - 1)
                System.out.print(", ");
        }
        if (n < arr.length) {
            System.out.print(", ..."); // Print ellipsis if not all elements are shown
        }
        System.out.println("] (Total: " + arr.length + " elements)");
    }

    public static void main(String[] args) {
        double[] numbers = readNumbersFromCSV("AMD (1000).csv");
        if (numbers != null) {
            int replicationFactor = 2000; // Adjust the replication factor as needed
            double[] largeDataset = new double[numbers.length * replicationFactor];

            for (int i = 0; i < replicationFactor; i++) {
                System.arraycopy(numbers, 0, largeDataset, i * numbers.length, numbers.length);
            }
            System.out.println("Before sorting (quicksort): ");
            // Print only a subset of the larger dataset for visualization
            printArr(largeDataset, 50); // Print the first 50 elements

            int numRuns = 5;
            double totalElapsedTime = 0;

            for (int run = 1; run <= numRuns; run++) {
                double[] numbersCopy = largeDataset.clone();

                long startTime = System.nanoTime();
                quickSort(numbersCopy, 0, numbersCopy.length - 1);
                long endTime = System.nanoTime();

                long elapsedTime = endTime - startTime;
                double elapsedTimeMillis = elapsedTime / 1000000.0;

                if (run > 1) { // Skip the first run
                    totalElapsedTime += elapsedTimeMillis;
                }

                System.out.println("\nAfter sorting (Run " + run + "):");
                // Print only a subset of the sorted dataset for visualization
                printArr(numbersCopy, 50);

                System.out.println("Elapsed Time (Run " + run + "): " + elapsedTimeMillis + " milliseconds");
            }

            double averageElapsedTime = totalElapsedTime / (numRuns - 1);
            System.out.println("\nAverage Elapsed Time: " + averageElapsedTime + " milliseconds");
        }
    }
}

