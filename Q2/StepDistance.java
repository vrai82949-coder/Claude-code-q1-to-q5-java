import java.util.Scanner;

/*
 * Q2(a) - Calculating Total Distance from Merged Daily Step Counts
 *
 * Merge (combine) the step counts of two consecutive days and work out the
 * total distance, given that one step covers a fixed distance of 1 unit.
 */
public class StepDistance {

    // A named constant instead of a bare "1" makes the formula self-explanatory
    // and easy to change if the step length is ever different.
    static final int DISTANCE_PER_STEP = 1;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // long instead of int so that very large step counts cannot overflow when added.
        System.out.print("Enter steps taken on Day 1: ");
        long day1 = sc.nextLong();
        System.out.print("Enter steps taken on Day 2: ");
        long day2 = sc.nextLong();

        if (day1 < 0 || day2 < 0) {
            System.out.println("Invalid input: step counts cannot be negative.");
        } else {
            long mergedSteps = day1 + day2;
            long totalDistance = mergedSteps * DISTANCE_PER_STEP;

            System.out.println("Merged step count: " + mergedSteps);
            System.out.println("Total distance covered: " + totalDistance + " units");
        }

        sc.close();
    }
}
