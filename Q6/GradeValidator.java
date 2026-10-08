import java.util.Scanner;

/*
 * Q6(a) - Validating Student Grade Input with Multi-Catch Exception Handling
 *
 * Read a student's name and grade, check the grade, and display it.
 *   - grade is not an integer      -> NumberFormatException   (thrown by Integer.parseInt)
 *   - grade is < 0 or > 100        -> IllegalArgumentException (thrown by validateGrade)
 *
 * Why not "catch (NumberFormatException | IllegalArgumentException e)"?
 * NumberFormatException is a SUBCLASS of IllegalArgumentException, and Java does not
 * allow a multi-catch whose alternatives are related by inheritance. That line is a
 * compile error: "Alternatives in a multi-catch statement cannot be related by
 * subclassing". So the try has one catch block per exception instead, with the
 * subclass first. If IllegalArgumentException came first it would also catch every
 * NumberFormatException, the second block could never run, and that too is a
 * compile error.
 */
public class GradeValidator {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter student name: ");
        String name = sc.nextLine();
        System.out.print("Enter grade: ");
        // Read the grade as text so that Integer.parseInt can throw NumberFormatException.
        // sc.nextInt() would throw a different exception (InputMismatchException) instead.
        String gradeInput = sc.nextLine().trim();

        try {
            int grade = Integer.parseInt(gradeInput);   // may throw NumberFormatException
            validateGrade(grade);                       // may throw IllegalArgumentException
            System.out.println("Grade for " + name + ": " + grade);
        } catch (NumberFormatException e) {
            // getMessage() is the built-in message Java created, e.g.  For input string: "abc"
            System.out.println("NumberFormatException caught: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException caught: " + e.getMessage());
        }

        sc.close();
    }

    static void validateGrade(int grade) {
        if (grade < 0 || grade > 100) {
            // "throw" stops this method immediately and jumps to the matching catch block.
            throw new IllegalArgumentException("Grade must be between 0 and 100, but was " + grade);
        }
    }
}
