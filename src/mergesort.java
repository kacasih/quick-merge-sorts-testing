import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class MergeSort {
    void merge(double arr[], int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        double L[] = new double[n1];
        double R[] = new double[n2];

        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];

        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    void sort(double arr[], int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;
            sort(arr, l, m);
            sort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    static void printArray(double arr[], int limit) {
        int n = Math.min(arr.length, limit);
        System.out.print("[");
        for (int i = 0; i < n; ++i) {
            System.out.print(arr[i]);
            if (i != n - 1)
                System.out.print(", ");
        }
        if (n < arr.length) {
            System.out.print(", ..."); // Print ellipsis if not all elements are shown
        }
        System.out.println("] (Total: " + arr.length + " elements)");
    }

    public static double[] readNumbersFromCSV(String filename) {
        List<Double> numbersList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // Skip the header line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                double number = Double.parseDouble(values[4]);
                numbersList.add(number);
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

    public static void main(String args[]) {
        double[] arr = readNumbersFromCSV("AMD (1000).csv");

        if (arr != null) {
            int replicationFactor = 5;
            double[] largeDataset = new double[arr.length * replicationFactor];

            for (int i = 0; i < replicationFactor; i++) {
                System.arraycopy(arr, 0, largeDataset, i * arr.length, arr.length);
            }

            System.out.println("Before sorting (mergesort):");
            // Print only a subset of the larger dataset for visualization
            printArray(largeDataset, 50); // Print the first 100 elements

            int numRuns = 5;
            double totalElapsedTime = 0;

            boolean skipFirstRun = true; // Flag to skip the first run

            for (int run = 1; run <= numRuns; run++) {
                double[] arrCopy = largeDataset.clone();

                long startTime = System.nanoTime();

                MergeSort ob = new MergeSort();
                ob.sort(arrCopy, 0, arrCopy.length - 1);

                long endTime = System.nanoTime();

                long elapsedTime = endTime - startTime;
                double elapsedTimeMillis = elapsedTime / 1000000.0;

                if (!skipFirstRun || run > 1) { // Check the flag and run number to skip the first run
                    totalElapsedTime += elapsedTimeMillis;
                }

                System.out.println("\nAfter sorting (Run " + run + "):");
                // Print only a subset of the sorted dataset for visualization
                printArray(arrCopy, 50); // Print the first 100 elements

                System.out.println("Elapsed Time (Run " + run + "): " + elapsedTimeMillis + " milliseconds");
            }

            double averageElapsedTime = totalElapsedTime / (numRuns - (skipFirstRun ? 1 : 0));
            System.out.println("\nAverage Elapsed Time: " + averageElapsedTime + " milliseconds");
        }
    }
}

