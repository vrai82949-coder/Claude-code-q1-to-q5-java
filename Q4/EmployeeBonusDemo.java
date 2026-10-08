import java.util.Scanner;

/*
 * Q4(a) - Calculating Employee Bonuses Using Hierarchical Inheritance
 *
 * Hierarchical inheritance: one parent class (Employee) with two children
 * (Developer and Designer) that both inherit baseSalary from it.
 *
 *              Employee
 *             /        \
 *       Developer    Designer
 *
 * Bonus percentage: Developer = 10, Designer = 5
 * Final income = baseSalary + (baseSalary * bonusPercentage * workHours / 100)
 */
public class EmployeeBonusDemo {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter base salary of the developer: ");
        double devSalary = sc.nextDouble();
        System.out.print("Enter work hours of the developer: ");
        double devHours = sc.nextDouble();

        System.out.print("Enter base salary of the designer: ");
        double desSalary = sc.nextDouble();
        System.out.print("Enter work hours of the designer: ");
        double desHours = sc.nextDouble();

        Developer developer = new Developer(devSalary, devHours);
        Designer designer = new Designer(desSalary, desHours);

        developer.displayFinalIncome();
        designer.displayFinalIncome();

        sc.close();
    }
}

class Employee {
    // protected: visible to subclasses (Developer, Designer) but not to unrelated classes.
    protected double baseSalary;

    Employee(double baseSalary) {
        this.baseSalary = baseSalary;
    }
}

class Developer extends Employee {
    // final: the bonus percentage is fixed by the company and must not change.
    private final double bonusPercentage = 10;
    private double workHours;

    Developer(double baseSalary, double workHours) {
        // super(...) runs the Employee constructor, which stores baseSalary.
        super(baseSalary);
        this.workHours = workHours;
    }

    double calculateFinalIncome() {
        // baseSalary is not declared here: it is inherited from Employee.
        return baseSalary + (baseSalary * bonusPercentage * workHours / 100);
    }

    void displayFinalIncome() {
        System.out.printf("Developer's final income: %.2f%n", calculateFinalIncome());
    }
}

class Designer extends Employee {
    private final double bonusPercentage = 5;
    private double workHours;

    Designer(double baseSalary, double workHours) {
        super(baseSalary);
        this.workHours = workHours;
    }

    double calculateFinalIncome() {
        return baseSalary + (baseSalary * bonusPercentage * workHours / 100);
    }

    void displayFinalIncome() {
        System.out.printf("Designer's final income: %.2f%n", calculateFinalIncome());
    }
}
