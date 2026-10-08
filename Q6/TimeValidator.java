import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * Q6(b) - Implementing Custom Exceptions for Time Input Validation
 *
 * Read hours, minutes and seconds (24-hour clock) and validate each one:
 *   hours   0-23  else InvalidHourException
 *   minutes 0-59  else InvalidMinuteException
 *   seconds 0-59  else InvalidSecondException
 * If all are valid, print "Correct Time - hours:minutes:seconds".
 */
public class TimeValidator {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter hours: ");
            int hours = sc.nextInt();
            System.out.print("Enter minutes: ");
            int minutes = sc.nextInt();
            System.out.print("Enter seconds: ");
            int seconds = sc.nextInt();

            validateTime(hours, minutes, seconds);
            // Only reached when validateTime did not throw anything.
            System.out.println("Correct Time - " + hours + ":" + minutes + ":" + seconds);

        } catch (InvalidHourException | InvalidMinuteException | InvalidSecondException e) {
            // A real multi-catch: these three classes are siblings (none extends another),
            // so Java allows them in one catch. They are all handled the same way.
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            // nextInt() throws this for text like "ab", and also for a number too big for an int.
            System.out.println("Invalid input: enter a whole number (like 12) for hours, minutes and seconds.");
        }

        sc.close();
    }

    // "throws" lists the checked exceptions this method can throw, so every caller
    // is forced by the compiler to handle them.
    static void validateTime(int hours, int minutes, int seconds)
            throws InvalidHourException, InvalidMinuteException, InvalidSecondException {
        if (hours < 0 || hours > 23) {
            throw new InvalidHourException("Invalid hours: " + hours + " (must be between 0 and 23)");
        }
        if (minutes < 0 || minutes > 59) {
            throw new InvalidMinuteException("Invalid minutes: " + minutes + " (must be between 0 and 59)");
        }
        if (seconds < 0 || seconds > 59) {
            throw new InvalidSecondException("Invalid seconds: " + seconds + " (must be between 0 and 59)");
        }
    }
}

// A custom exception is just a class that extends Exception. Extending Exception
// (not RuntimeException) makes it a CHECKED exception: the compiler insists that it
// is either caught or declared with "throws".
class InvalidHourException extends Exception {
    // Exceptions are Serializable; this version number stops the compiler warning about it.
    private static final long serialVersionUID = 1L;

    InvalidHourException(String message) {
        // Pass the message up to Exception, which stores it for getMessage().
        super(message);
    }
}

class InvalidMinuteException extends Exception {
    private static final long serialVersionUID = 1L;

    InvalidMinuteException(String message) {
        super(message);
    }
}

class InvalidSecondException extends Exception {
    private static final long serialVersionUID = 1L;

    InvalidSecondException(String message) {
        super(message);
    }
}
