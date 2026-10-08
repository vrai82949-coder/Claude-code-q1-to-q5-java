import java.util.Scanner;

/*
 * Q5(b) - Unique Character Extraction
 *
 * Build a new string that keeps only the first occurrence of each character
 * of the input, e.g. "programming" -> "progamin".
 *
 * StringBuilder (java.lang) is used instead of String concatenation because a
 * String can never change: every "s = s + ch" creates a brand-new String object.
 * A StringBuilder appends into the same object, which is far cheaper.
 */
public class UniqueCharacters {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a string: ");
        // nextLine() (not next()) so the whole line is read, including spaces.
        String input = sc.nextLine();

        String result = extractUnique(input);
        System.out.println("String with unique characters: " + result);

        sc.close();
    }

    static String extractUnique(String input) {
        StringBuilder unique = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            // indexOf returns -1 when ch has not been added yet, so add it now.
            // If it is already present, skip it: that is a duplicate.
            if (unique.indexOf(String.valueOf(ch)) == -1) {
                unique.append(ch);
            }
        }

        return unique.toString();
    }
}
