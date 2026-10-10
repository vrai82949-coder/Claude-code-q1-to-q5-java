import java.util.ArrayList;
import java.util.Scanner;

/*
 * Q14 - Student Registration System using ArrayList
 *
 * Register student names in an ArrayList, then look one up by its index.
 * Indexes start at 0, as they do for every Java list and array: the first student
 * registered is at index 0, the second at index 1, and so on.
 */
public class StudentRegistration {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // An ArrayList grows automatically as names are added, unlike an array whose
        // size is fixed when it is created.
        ArrayList<String> students = new ArrayList<>();

        System.out.print("Enter number of students: ");
        int count = sc.nextInt();
        // nextInt() reads the number but leaves the Enter key ("\n") behind. Without this
        // extra nextLine(), the first name would be read as an empty line.
        sc.nextLine();

        for (int i = 0; i < count; i++) {
            System.out.print("Enter name of student [" + i + "]: ");
            students.add(sc.nextLine());    // add() appends to the end of the list
        }

        if (students.isEmpty()) {
            System.out.println("No students registered.");
            sc.close();
            return;
        }

        // An ArrayList prints its contents neatly, e.g. [Asha, Ravi, Meena]
        System.out.println("Registered students: " + students);

        int lastIndex = students.size() - 1;
        System.out.print("Enter index to retrieve (0 to " + lastIndex + "): ");
        int index = sc.nextInt();

        // Check the index first: get() with an index outside 0..size()-1 would throw
        // an IndexOutOfBoundsException and stop the program.
        if (index >= 0 && index < students.size()) {
            System.out.println("Student at index " + index + ": " + students.get(index));
        } else {
            System.out.println("Invalid index. Please enter a number from 0 to " + lastIndex + ".");
        }

        sc.close();
    }
}
