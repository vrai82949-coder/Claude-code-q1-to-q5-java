import java.util.Scanner;

/*
 * Q3(b) - Developing an ArrayConcatenator Class for Merging Arrays
 *
 * Input format:
 *   line 1: N             (size of the first array)
 *   line 2: N integers    (first array)
 *   line 3: M             (size of the second array)
 *   line 4: M integers    (second array)
 * Output: the concatenated array, elements separated by a space.
 *
 * This question defines an exact input/output format, so no "Enter ..." prompts
 * are printed; anything extra would not match the expected output.
 */
public class ArrayConcatenatorDemo {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] first = new int[n];
        for (int i = 0; i < n; i++) {
            first[i] = sc.nextInt();
        }

        int m = sc.nextInt();
        int[] second = new int[m];
        for (int i = 0; i < m; i++) {
            second[i] = sc.nextInt();
        }

        ArrayConcatenator concatenator = new ArrayConcatenator(first, second);
        concatenator.printArray();

        sc.close();
    }
}

class ArrayConcatenator {
    private int[] result;

    // The question asks for the concatenation to happen in the constructor, so the
    // object holds the merged array as soon as it is created.
    ArrayConcatenator(int[] first, int[] second) {
        // Java arrays have a fixed size, so allocate room for both arrays up front.
        result = new int[first.length + second.length];

        // Copy the first array into positions 0 .. N-1.
        for (int i = 0; i < first.length; i++) {
            result[i] = first[i];
        }
        // Copy the second array right after it, into positions N .. N+M-1.
        for (int j = 0; j < second.length; j++) {
            result[first.length + j] = second[j];
        }
    }

    void printArray() {
        for (int i = 0; i < result.length; i++) {
            // Print a space before every element except the first: no trailing space.
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(result[i]);
        }
        System.out.println();
    }
}
