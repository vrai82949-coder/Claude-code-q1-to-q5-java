import java.util.Scanner;

/*
 * Q1(b) - Three-Digit Number Analysis: Sum of Digits and Parity Check
 *
 * Read a three-digit number, add its digits, and say whether that sum is even or odd.
 * Digits are pulled out with integer division (/) and remainder (%).
 */
public class DigitSumParity {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a three-digit number: ");
        int number = sc.nextInt();

        // Use the absolute value so that -345 is treated the same as 345.
        int n = Math.abs(number);

        if (n < 100 || n > 999) {
            System.out.println("Invalid input: please enter a three-digit number.");
        } else {
            int hundreds = n / 100;        // 345 / 100 = 3
            int tens = (n / 10) % 10;      // 345 / 10 = 34, then 34 % 10 = 4
            int units = n % 10;            // 345 % 10 = 5

            int sum = hundreds + tens + units;
            System.out.println("Sum of digits: " + sum);

            // A number is even when dividing it by 2 leaves no remainder.
            if (sum % 2 == 0) {
                System.out.println("The sum is even.");
            } else {
                System.out.println("The sum is odd.");
            }
        }

        sc.close();
    }
}
