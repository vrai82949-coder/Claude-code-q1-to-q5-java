import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/*
 * Q16 - Employee Management System using Java and JDBC (MySQL)
 *
 * A menu-driven program that stores employees in a MySQL table:
 *   1. Add Employee   2. View Employees   3. Update Employee Salary
 *   4. Delete Employee   5. Exit
 *
 * Needs (see README): a running MySQL server, and the MySQL Connector/J jar on the
 * classpath, e.g.   java -cp .:mysql-connector-j-9.4.0.jar EmployeeApp
 *
 * JDBC in four steps:
 *   1. DriverManager.getConnection(...)  opens a Connection to the database
 *   2. conn.prepareStatement(sql)         prepares an SQL command with ? placeholders
 *   3. setString / setDouble / setInt     fills in the ? values
 *   4. executeUpdate() (INSERT/UPDATE/DELETE) or executeQuery() (SELECT) runs it
 */
public class EmployeeApp {

    // Change these three values to match your own MySQL installation.
    // "createDatabaseIfNotExist=true" makes the driver create employee_db on first use.
    static final String DB_URL = "jdbc:mysql://localhost:3306/employee_db?createDatabaseIfNotExist=true";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "your_password";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // try-with-resources closes the connection automatically when the program leaves the block.
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            System.out.println("Connected to the database.");
            createTableIfMissing(conn);

            boolean running = true;
            while (running) {
                System.out.println();
                System.out.println("===== Employee Management =====");
                System.out.println("1. Add Employee");
                System.out.println("2. View Employees");
                System.out.println("3. Update Employee Salary");
                System.out.println("4. Delete Employee");
                System.out.println("5. Exit");

                // Each menu action has its own try/catch, so one failed action (a bad number,
                // a database error) prints a message and the menu simply shows again.
                try {
                    int choice = readInt(sc, "Enter your choice: ");
                    switch (choice) {
                        case 1:
                            addEmployee(conn, sc);
                            break;
                        case 2:
                            viewEmployees(conn);
                            break;
                        case 3:
                            updateSalary(conn, sc);
                            break;
                        case 4:
                            deleteEmployee(conn, sc);
                            break;
                        case 5:
                            running = false;
                            System.out.println("Goodbye!");
                            break;
                        default:
                            System.out.println("Invalid choice. Please enter a number from 1 to 5.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                } catch (SQLException e) {
                    System.out.println("Database error: " + e.getMessage());
                }
            }

        } catch (SQLException e) {
            // Reached if the connection itself fails: MySQL not running, wrong password,
            // or the Connector/J jar missing from the classpath ("No suitable driver").
            System.out.println("Could not connect to the database: " + e.getMessage());
        }

        sc.close();
    }

    static void createTableIfMissing(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS employees ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(100) NOT NULL, "
                + "department VARCHAR(50) NOT NULL, "
                + "salary DECIMAL(10, 2) NOT NULL)";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    static void addEmployee(Connection conn, Scanner sc) throws SQLException {
        String name = readText(sc, "Enter name: ");
        String department = readText(sc, "Enter department: ");
        double salary = readDouble(sc, "Enter salary: ");
        if (salary < 0) {
            System.out.println("Salary cannot be negative.");
            return;
        }

        // The ? placeholders are filled in with setString/setDouble. The driver sends the
        // values separately from the SQL, so input such as  O'Brien  cannot break the
        // command (this is what protects against "SQL injection").
        String sql = "INSERT INTO employees (name, department, salary) VALUES (?, ?, ?)";
        // RETURN_GENERATED_KEYS asks MySQL to hand back the id it generated (AUTO_INCREMENT).
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, department);
            ps.setDouble(3, salary);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    System.out.println("Employee added with ID " + keys.getInt(1) + ".");
                } else {
                    System.out.println("Employee added.");
                }
            }
        }
    }

    static void viewEmployees(Connection conn) throws SQLException {
        String sql = "SELECT id, name, department, salary FROM employees ORDER BY id";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean any = false;
            // A ResultSet is like a cursor over the rows: next() moves to the next row and
            // returns false when there are no more.
            while (rs.next()) {
                if (!any) {
                    System.out.printf("%-5s %-20s %-15s %12s%n", "ID", "Name", "Department", "Salary");
                    any = true;
                }
                System.out.printf("%-5d %-20s %-15s %12.2f%n",
                        rs.getInt("id"), rs.getString("name"),
                        rs.getString("department"), rs.getDouble("salary"));
            }
            if (!any) {
                System.out.println("No employees found.");
            }
        }
    }

    static void updateSalary(Connection conn, Scanner sc) throws SQLException {
        int id = readInt(sc, "Enter employee ID: ");
        double newSalary = readDouble(sc, "Enter new salary: ");
        if (newSalary < 0) {
            System.out.println("Salary cannot be negative.");
            return;
        }

        String sql = "UPDATE employees SET salary = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, newSalary);
            ps.setInt(2, id);
            // executeUpdate() returns how many rows were changed: 0 means no such ID.
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Salary updated for employee ID " + id + ".");
            } else {
                System.out.println("No employee found with ID " + id + ".");
            }
        }
    }

    static void deleteEmployee(Connection conn, Scanner sc) throws SQLException {
        int id = readInt(sc, "Enter employee ID to delete: ");

        String sql = "DELETE FROM employees WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Employee ID " + id + " deleted.");
            } else {
                System.out.println("No employee found with ID " + id + ".");
            }
        }
    }

    // Input helpers: every value is read as a whole line and then converted. This avoids
    // the classic nextInt()/nextLine() problem where a leftover Enter key is read as an
    // empty name. A bad number throws NumberFormatException, which the menu loop catches.
    static String readText(Scanner sc, String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    static int readInt(Scanner sc, String prompt) {
        return Integer.parseInt(readText(sc, prompt));
    }

    static double readDouble(Scanner sc, String prompt) {
        return Double.parseDouble(readText(sc, prompt));
    }
}
