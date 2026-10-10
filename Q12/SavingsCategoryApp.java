import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

/*
 * Q12 - Serializing and Deserializing Savings Data to Determine Savings Category
 *
 * Steps: read salary and savings -> create a SavingsData object -> serialize it to
 *        savings.ser -> deserialize it into a NEW object -> print that object's category.
 *
 * savings percentage = savings * 100 / salary
 *   1% (inclusive) to below 10%  -> "Poor savings"
 *   10% (inclusive) to below 20% -> "Good savings"
 *   20% or more                  -> "High savings"
 *   anything else                -> "Invalid input"
 */
public class SavingsCategoryApp {

    static final String FILE_NAME = "savings.ser";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter salary: ");
        double salary = sc.nextDouble();
        System.out.print("Enter savings: ");
        double savings = sc.nextDouble();

        SavingsData original = new SavingsData(salary, savings);

        try {
            // Serialize: write the object to savings.ser.
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
                out.writeObject(original);
            }
            System.out.println("SavingsData serialized to " + FILE_NAME);

            // Deserialize: read it back. This creates a brand-new object with the same data.
            SavingsData restored;
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                restored = (SavingsData) in.readObject();
            }
            System.out.println("SavingsData deserialized from " + FILE_NAME);

            // The category is worked out from the deserialized object, as the question asks.
            if (restored.getSalary() > 0) {
                System.out.printf("Savings percentage: %.2f%%%n", restored.getSavingsPercentage());
            }
            System.out.println("Category: " + restored.getCategory());

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Serialization error: " + e.getMessage());
        }

        sc.close();
    }
}

class SavingsData implements Serializable {
    private static final long serialVersionUID = 1L;

    private double salary;
    private double savings;

    SavingsData(double salary, double savings) {
        this.salary = salary;
        this.savings = savings;
    }

    double getSalary() {
        return salary;
    }

    double getSavingsPercentage() {
        // Multiply before dividing: with whole-number inputs this gives exactly 10.0 for
        // 100 out of 1000. (savings / salary * 100 can land a tiny bit off, e.g. 0.07 * 100
        // is 7.000000000000001 in a double, which matters right at a category boundary.)
        return savings * 100 / salary;
    }

    String getCategory() {
        // A salary of 0 or less would mean dividing by zero, so it cannot be categorised.
        if (salary <= 0) {
            return "Invalid input";
        }
        double percentage = getSavingsPercentage();
        if (percentage >= 1 && percentage < 10) {
            return "Poor savings";
        } else if (percentage >= 10 && percentage < 20) {
            return "Good savings";
        } else if (percentage >= 20) {
            return "High savings";
        } else {
            return "Invalid input";   // below 1% (including 0 or negative savings)
        }
    }
}
