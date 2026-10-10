import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

/*
 * Q11 - Implementing Persistent Bank Account Transactions using Serialization
 *
 * Serialization   = turning an object into bytes so it can be saved in a file.
 * Deserialization = rebuilding the same object from those bytes.
 *
 * Flow:
 *   start -> does bankAccount.ser exist?
 *              yes -> deserialize it and continue with the saved balance
 *              no  -> create a new account
 *         -> deposit / withdraw using a menu
 *         -> serialize the account to bankAccount.ser and print the final balance
 * Run the program twice: the second run picks up where the first one stopped.
 */
public class BankAccountApp {

    static final String FILE_NAME = "bankAccount.ser";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        BankAccount account = loadAccount();
        if (account == null) {
            System.out.println("Creating a new account.");
            System.out.print("Enter account holder name: ");
            String name = sc.nextLine();
            account = new BankAccount(name);
        } else {
            System.out.println("Saved account loaded. Welcome back, " + account.getHolderName() + "!");
        }
        System.out.printf("Current balance: %.2f%n", account.getBalance());

        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check balance");
            System.out.println("4. Save and exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter amount to deposit: ");
                    double deposit = sc.nextDouble();
                    if (account.deposit(deposit)) {
                        System.out.printf("Deposited %.2f. New balance: %.2f%n", deposit, account.getBalance());
                    } else {
                        System.out.println("Deposit amount must be greater than 0.");
                    }
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawal = sc.nextDouble();
                    if (account.withdraw(withdrawal)) {
                        System.out.printf("Withdrew %.2f. New balance: %.2f%n", withdrawal, account.getBalance());
                    } else {
                        System.out.println("Withdrawal failed: the amount must be greater than 0 "
                                + "and not more than the balance.");
                    }
                    break;
                case 3:
                    System.out.printf("Current balance: %.2f%n", account.getBalance());
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, 3 or 4.");
            }
        }

        saveAccount(account);
        System.out.printf("Final balance: %.2f%n", account.getBalance());

        sc.close();
    }

    // Returns the saved account, or null if there is no saved file (or it cannot be read).
    static BankAccount loadAccount() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No saved account found (" + FILE_NAME + " does not exist yet).");
            return null;
        }
        // ObjectInputStream reads objects; FileInputStream supplies the raw bytes from the file.
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            // readObject() returns a plain Object. Check its type with instanceof before
            // casting, so a file holding some other kind of object cannot cause a
            // ClassCastException.
            Object saved = in.readObject();
            if (saved instanceof BankAccount) {
                return (BankAccount) saved;
            }
            System.out.println(FILE_NAME + " does not contain a bank account. Starting fresh.");
            return null;
        } catch (IOException | ClassNotFoundException | RuntimeException e) {
            // IOException and ClassNotFoundException are the checked exceptions readObject()
            // declares. A badly damaged file can also make it throw unchecked (Runtime)
            // exceptions, so those are caught too: any unreadable file means "start fresh".
            System.out.println("Could not read " + FILE_NAME + " (" + e + "). Starting fresh.");
            return null;
        }
    }

    static void saveAccount(BankAccount account) {
        // ObjectOutputStream turns the object into bytes; FileOutputStream writes them to the file.
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(account);
            System.out.println("Account saved to " + FILE_NAME + ".");
        } catch (IOException e) {
            System.out.println("Could not save the account: " + e.getMessage());
        }
    }
}

// "implements Serializable" is what allows objects of this class to be written with
// ObjectOutputStream. Serializable has no methods; it only marks the class as safe to save.
class BankAccount implements Serializable {
    // A version number for the saved format. If the class changes in an incompatible way,
    // changing this number makes Java refuse old files instead of loading them wrongly.
    private static final long serialVersionUID = 1L;

    private String holderName;
    private double balance;

    BankAccount(String holderName) {
        this.holderName = holderName;
        this.balance = 0;
    }

    // Returns true if the deposit was accepted, so the caller can print the right message.
    boolean deposit(double amount) {
        // Double.isFinite rejects the special values NaN and Infinity, which Scanner accepts
        // as input. NaN is false in every comparison, so "amount <= 0" alone would let it in.
        if (!Double.isFinite(amount) || amount <= 0) {
            return false;
        }
        balance += amount;
        return true;
    }

    boolean withdraw(double amount) {
        if (!Double.isFinite(amount) || amount <= 0 || amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }

    String getHolderName() {
        return holderName;
    }

    double getBalance() {
        return balance;
    }
}
