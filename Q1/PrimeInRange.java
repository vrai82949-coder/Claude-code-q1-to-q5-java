import java.util.Scanner;

/*
 * Q1(c) - Generating Prime Numbers Within a Specified Range
 *
 * Print every prime between start and end (both inclusive) using 'for' loops.
 * A prime is a number greater than 1 whose only divisors are 1 and itself.
 */
public class PrimeInRange {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter start: ");
        int start = sc.nextInt();
        System.out.print("Enter end: ");
        int end = sc.nextInt();

        // If the range is typed backwards (e.g. 50 then 10), swap so the loop still runs.
        if (start > end) {
            int temp = start;
            start = end;
            end = temp;
        }

        System.out.print("Prime numbers between " + start + " and " + end + ":");
        boolean found = false;

        // Outer for loop: visit every number in the range, inclusive of both ends.
        for (int num = start; num <= end; num++) {
            if (isPrime(num)) {
                System.out.print(" " + num);
                found = true;
            }
        }

        if (!found) {
            System.out.print(" none");
        }
        System.out.println();

        sc.close();
    }

    static boolean isPrime(int n) {
        // 0, 1 and negative numbers are not prime by definition.
        if (n < 2) {
            return false;
        }
        // Inner for loop: try divisors from 2 up to sqrt(n). If n = a * b, one of
        // a or b must be <= sqrt(n), so checking beyond sqrt(n) finds nothing new.
        // "i <= n / i" means the same as "i * i <= n" but cannot overflow an int.
        for (int i = 2; i <= n / i; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
