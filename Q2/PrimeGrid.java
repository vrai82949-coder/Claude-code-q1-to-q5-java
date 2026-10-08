import java.util.Scanner;

/*
 * Q2(b) - Identifying and Locating Prime Numbers in a 2D Grid
 *
 * Read the grid's size and elements into a 2D array, then scan every cell and
 * print the value and (row, column) coordinates of each prime. Coordinates are
 * 0-based, matching Java array indexes.
 */
public class PrimeGrid {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of rows: ");
        int rows = sc.nextInt();
        System.out.print("Enter number of columns: ");
        int cols = sc.nextInt();

        // A negative size would crash "new int[rows][cols]", so check it first.
        if (rows <= 0 || cols <= 0) {
            System.out.println("Invalid input: rows and columns must be positive.");
            sc.close();
            return;
        }

        int[][] grid = new int[rows][cols];
        System.out.println("Enter the grid elements row by row:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = sc.nextInt();
            }
        }

        boolean found = false;
        // Nested loops visit every cell: i is the row, j is the column.
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (isPrime(grid[i][j])) {
                    System.out.println("Prime number " + grid[i][j] + " found at (" + i + ", " + j + ")");
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No prime numbers found in the grid.");
        }

        sc.close();
    }

    static boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        // Only divisors up to sqrt(n) need checking ("i <= n / i" avoids int overflow).
        for (int i = 2; i <= n / i; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
