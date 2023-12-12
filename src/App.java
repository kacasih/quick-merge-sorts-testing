import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        List<Double> numbers = readNumbersFromCSV("AMD.csv");
        if (numbers != null) {
            System.out.println("Before sorting: " + numbers);
            bubbleSort(numbers);
            System.out.println("After sorting: " + numbers);
        }
    }

    public static void bubbleSort(List<Double> numbers) {
        boolean swapped = true;
        while (swapped) {
            swapped = false;
            for (int i = 0; i < numbers.size() - 1; i++) {
                if (numbers.get(i) > numbers.get(i + 1)) {
                    double temp = numbers.get(i);
                    numbers.set(i, numbers.get(i + 1));
                    numbers.set(i + 1, temp);
                    swapped = true;
                }
            }
        }
    }

    public static List<Double> readNumbersFromCSV(String filename) {
        List<Double> numbers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // Skip the header line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                double number = Double.parseDouble(values[1]); // Assuming the number is in the second column
                numbers.add(number);
            }
        } catch (IOException e) {
            System.err.println("Error reading numbers from CSV: " + e.getMessage());
            return null;
        }

        return numbers;
    }
}
