import java.util.Scanner;

/*
 * Q5(a) - Designing a Washing Machine Control System
 *
 * Motor is an interface: a contract listing what every motor must be able to do.
 * WashingMachine "implements" Motor, so it must provide a body for every method in it.
 * Electricity consumption (kWh) = capacity * 0.05
 */
public class WashingMachineDemo {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the capacity of the washing machine: ");
        double capacity = sc.nextDouble();

        // WashingMachine is used as the variable type (not Motor) because the no-argument
        // consume() exists only in WashingMachine, not in the Motor interface.
        WashingMachine machine = new WashingMachine();

        machine.run();
        machine.consume();
        double consumption = machine.consume(capacity);
        System.out.printf("Electricity consumption: %.2f kWh%n", consumption);

        sc.close();
    }
}

interface Motor {
    // Interface methods are automatically public and abstract.
    void run();

    double consume(double capacity);
}

class WashingMachine implements Motor {

    // "public" is required: an implementation cannot be less visible than the
    // interface method it implements, and interface methods are public.
    @Override
    public void run() {
        System.out.println("Washing machine is running.");
    }

    // Method OVERLOADING: same name as consume(double), different parameter list.
    // Not part of Motor, so there is no @Override here.
    public void consume() {
        System.out.println("Washing machine is consuming electricity.");
    }

    // Implements (overrides) the method declared in Motor.
    @Override
    public double consume(double capacity) {
        return capacity * 0.05;
    }
}
