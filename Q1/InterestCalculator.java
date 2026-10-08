import java.util.Scanner;

/*
 * Q1(a) - Calculating Interest for Financing Options
 *
 * Bobby wants to compare the interest paid on two financing options.
 * Formula given in the question:  interest = principal * annualRate / 100
 */
public class InterestCalculator {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // double (not int) because money and rates can have decimals, e.g. 7.5%
        System.out.print("Enter principal amount for Option 1: ");
        double principal1 = sc.nextDouble();
        System.out.print("Enter annual interest rate (%) for Option 1: ");
        double rate1 = sc.nextDouble();

        System.out.print("Enter principal amount for Option 2: ");
        double principal2 = sc.nextDouble();
        System.out.print("Enter annual interest rate (%) for Option 2: ");
        double rate2 = sc.nextDouble();

        double interest1 = calculateInterest(principal1, rate1);
        double interest2 = calculateInterest(principal2, rate2);

        // %.2f prints the value rounded to 2 decimal places, like currency
        System.out.printf("Interest for Option 1: %.2f%n", interest1);
        System.out.printf("Interest for Option 2: %.2f%n", interest2);

        sc.close();
    }

    // The formula is written once and reused for both options instead of being repeated.
    static double calculateInterest(double principal, double ratePercent) {
        return principal * ratePercent / 100;
    }
}
