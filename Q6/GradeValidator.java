import java.util.NoSuchElementException;
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
 * subclassing".
 *
 * So the multi-catch pairs NumberFormatException with NoSuchElementException (thrown
 * by nextLine() when the input ends before a line is typed). The two are unrelated,
 * so Java allows them together. IllegalArgumentException gets its own catch block
 * AFTER it, because a parent class must be caught after its subclass.
 */
public class GradeValidator {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter student name: ");
            String name = sc.nextLine();
            System.out.print("Enter grade: ");
            // Read the grade as text so that Integer.parseInt can throw NumberFormatException.
            // sc.nextInt() would throw a different exception (InputMismatchException) instead.
            String gradeInput = sc.nextLine().trim();

            int grade = Integer.parseInt(gradeInput);   // may throw NumberFormatException
            validateGrade(grade);                       // may throw IllegalArgumentException
            System.out.println("Grade for " + name + ": " + grade);

        } catch (NumberFormatException | NoSuchElementException e) {
            // Multi-catch: one block for two unrelated exception types.
            // getClass().getSimpleName() gives the name of whichever one was thrown, and
            // getMessage() is Java's built-in message, e.g.  For input string: "abc"
            System.out.println(e.getClass().getSimpleName() + " caught: " + e.getMessage());
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
