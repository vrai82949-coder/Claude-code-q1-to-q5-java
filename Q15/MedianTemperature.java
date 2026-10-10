import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/*
 * Q15 - Calculating Median Temperature from City Data using HashMap
 *
 * The HashMap stores   city name (key)  ->  list of temperature readings (value).
 * The program prints the median for each city and the median of all readings together.
 *
 * Median: sort the numbers in ascending order.
 *   odd count  -> the middle number           e.g. 30 31 [32] 33 34    -> 32
 *   even count -> average of the two middles  e.g. 25 [27 28] 30       -> 27.5
 */
public class MedianTemperature {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        HashMap<String, List<Double>> cityTemperatures = new HashMap<>();

        System.out.print("Enter number of cities: ");
        int cityCount = Integer.parseInt(sc.nextLine().trim());

        for (int i = 1; i <= cityCount; i++) {
            System.out.print("Enter name of city " + i + ": ");
            String city = sc.nextLine().trim();
            System.out.print("Enter temperature readings for " + city + " (separated by spaces): ");
            String line = sc.nextLine().trim();

            // If this city is not in the map yet, give it an empty list first.
            // (If the same city is entered twice, its readings are added together.)
            if (!cityTemperatures.containsKey(city)) {
                cityTemperatures.put(city, new ArrayList<>());
            }
            List<Double> readings = cityTemperatures.get(city);

            if (!line.isEmpty()) {
                // "\\s+" splits on one or more spaces, so extra spaces do not matter.
                for (String value : line.split("\\s+")) {
                    readings.add(Double.parseDouble(value));
                }
            }
        }

        // A HashMap does not keep its keys in any particular order, so the city names
        // are copied into a list and sorted to print them alphabetically.
        List<String> cities = new ArrayList<>(cityTemperatures.keySet());
        Collections.sort(cities);

        List<Double> allReadings = new ArrayList<>();
        System.out.println();
        for (String city : cities) {
            List<Double> readings = cityTemperatures.get(city);
            if (readings.isEmpty()) {
                System.out.println(city + ": no readings entered");
                continue;
            }
            System.out.printf("Median temperature of %s: %.2f%n", city, median(readings));
            allReadings.addAll(readings);
        }

        if (!allReadings.isEmpty()) {
            System.out.printf("Overall median temperature (all cities): %.2f%n", median(allReadings));
        }

        sc.close();
    }

    static double median(List<Double> values) {
        // Sort a COPY so the original readings keep the order they were entered in.
        List<Double> sorted = new ArrayList<>(values);
        Collections.sort(sorted);

        int n = sorted.size();
        int middle = n / 2;   // integer division: for n = 5 this is 2, for n = 4 it is 2
        if (n % 2 == 1) {
            return sorted.get(middle);
        } else {
            return (sorted.get(middle - 1) + sorted.get(middle)) / 2;
        }
    }
}
