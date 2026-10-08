import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/*
 * Q9 - Basic Sentiment Analysis: Classifying Sentences from File Input
 *
 * Steps:  keyboard -> input.txt -> read back -> classify -> output.txt -> screen
 *
 * Positive keywords: happy, good, excellent, positive
 * Negative keywords: sad, bad, terrible, negative
 * The program counts both kinds of keyword in the sentence:
 *   more positive than negative -> Positive
 *   more negative than positive -> Negative
 *   otherwise (none, or a tie)  -> Neutral
 */
public class SentimentAnalysis {

    static final String[] POSITIVE_WORDS = {"happy", "good", "excellent", "positive"};
    static final String[] NEGATIVE_WORDS = {"sad", "bad", "terrible", "negative"};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a sentence: ");
        String sentence = sc.nextLine();

        try {
            // Step 1: write the sentence to input.txt.
            // try-with-resources closes (and flushes) the writer automatically.
            try (PrintWriter writer = new PrintWriter(new FileWriter("input.txt"))) {
                writer.println(sentence);
            }

            // Step 2: read the sentence back from input.txt.
            String sentenceFromFile;
            try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
                sentenceFromFile = reader.readLine();
            }

            // Step 3: classify it.
            String sentiment = classify(sentenceFromFile);

            // Step 4: write the result to output.txt.
            try (PrintWriter writer = new PrintWriter(new FileWriter("output.txt"))) {
                writer.println(sentiment);
            }

            // Step 5: display it.
            System.out.println("Sentiment: " + sentiment);

        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }

        sc.close();
    }

    static String classify(String sentence) {
        // readLine() returns null if the file is completely empty.
        if (sentence == null) {
            return "Neutral";
        }

        // Lower-case first so "Happy" and "HAPPY" match "happy". Then split on every run
        // of characters that are not English letters a-z, so punctuation ("good!", "bad,")
        // does not stop a match and "unhappy" is not counted as "happy".
        String[] words = sentence.toLowerCase().split("[^a-z]+");

        int positiveCount = 0;
        int negativeCount = 0;
        for (String word : words) {
            if (isInList(word, POSITIVE_WORDS)) {
                positiveCount++;
            } else if (isInList(word, NEGATIVE_WORDS)) {
                negativeCount++;
            }
        }

        if (positiveCount > negativeCount) {
            return "Positive";
        } else if (negativeCount > positiveCount) {
            return "Negative";
        } else {
            return "Neutral";
        }
    }

    static boolean isInList(String word, String[] list) {
        for (String keyword : list) {
            // equals() compares the text. == would compare object references,
            // which is the wrong check for Strings.
            if (keyword.equals(word)) {
                return true;
            }
        }
        return false;
    }
}
