import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/*
 * Q10 - Converting Speed Units (km/h to m/s) with File Input and Output
 *
 * Steps:  keyboard -> data.txt -> read back -> convert -> converted.txt -> screen
 *
 * Formula: 1 km = 1000 m and 1 h = 3600 s, so
 *          m/s = km/h * 1000 / 3600   (the same as km/h * 5 / 18)
 */
public class SpeedConverter {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter speed in km/h: ");
        double speedInput = sc.nextDouble();

        try {
            // Step 1: write the entered speed to data.txt.
            // try-with-resources closes (and flushes) the writer automatically.
            try (PrintWriter writer = new PrintWriter(new FileWriter("data.txt"))) {
                writer.println(speedInput);
            }

            // Step 2: read the speed back from data.txt.
            double kmph;
            try (Scanner fileReader = new Scanner(new File("data.txt"))) {
                kmph = fileReader.nextDouble();
            }

            // Step 3: convert km/h to m/s.
            double mps = kmphToMps(kmph);
            String result = String.format("%.2f", mps);

            // Step 4: write the converted speed to converted.txt.
            try (PrintWriter writer = new PrintWriter(new FileWriter("converted.txt"))) {
                writer.println(result + " m/s");
            }

            // Step 5: display it.
            System.out.println("Speed read from data.txt: " + kmph + " km/h");
            System.out.println("Converted speed: " + result + " m/s");

        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }

        sc.close();
    }

    static double kmphToMps(double kmph) {
        return kmph * 1000 / 3600;
    }
}
