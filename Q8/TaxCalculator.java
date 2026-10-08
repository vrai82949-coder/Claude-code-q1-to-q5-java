import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/*
 * Q8 - Calculating Final Prices with Tax: File Input and Output
 *
 * Input format:
 *   line 1: N               (number of items, 1 <= N <= 10)
 *   line 2: N prices        (space-separated decimal numbers)
 * Output: each price with 10% tax added, 2 decimal places, separated by a space.
 *
 * Steps:  keyboard -> prices.txt -> read back -> add 10% tax -> tax.txt -> screen
 *
 * This question defines an exact input/output format, so no "Enter ..." prompts
 * are printed; anything extra would not match the expected output.
 */
public class TaxCalculator {

    static final double TAX_RATE = 0.10;   // 10%

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        // File operations can fail (no permission, disk full, ...). Java forces us to
        // handle IOException because it is a checked exception.
        try {
            // Step 1: write the entered prices to prices.txt, one per line.
            // try-with-resources: the writer is closed automatically at the end of the
            // block. Closing also flushes, so the data really reaches the file.
            try (PrintWriter pricesWriter = new PrintWriter(new FileWriter("prices.txt"))) {
                for (int i = 0; i < n; i++) {
                    pricesWriter.println(sc.nextDouble());
                }
            }

            // Steps 2-4: read prices.txt, add the tax, write tax.txt, and build the output line.
            StringBuilder output = new StringBuilder();
            try (Scanner fileReader = new Scanner(new File("prices.txt"));
                 PrintWriter taxWriter = new PrintWriter(new FileWriter("tax.txt"))) {

                while (fileReader.hasNextDouble()) {
                    double price = fileReader.nextDouble();
                    double taxedPrice = price * (1 + TAX_RATE);   // price + 10% of price
                    String formatted = String.format("%.2f", taxedPrice);

                    taxWriter.println(formatted);

                    // Space before every value except the first: no trailing space.
                    if (output.length() > 0) {
                        output.append(' ');
                    }
                    output.append(formatted);
                }
            }

            // Step 5: display the taxed amounts.
            System.out.println(output);

        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }

        sc.close();
    }
}
