import java.util.Scanner;

/*
 * Q13 - Implementing a Generic Record Holder for Academic Data
 *
 * RecordHolder<T> is a GENERIC class: T is a placeholder for a type that is filled in
 * when the holder is created, e.g. RecordHolder<GradeRecord>. One class can then hold
 * any kind of academic record, and the compiler still knows the exact type inside.
 *
 * "T extends AcademicRecord" limits T to classes that implement AcademicRecord. That is
 * what lets RecordHolder call record.displayRecordInfo(): every allowed T has that method.
 */
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // nextLine() everywhere, so names with spaces ("Java Programming") work.
        System.out.print("Enter student name: ");
        String studentName = sc.nextLine();
        System.out.print("Enter grade: ");
        double grade = Double.parseDouble(sc.nextLine().trim());
        System.out.print("Enter course name: ");
        String courseName = sc.nextLine();
        System.out.print("Enter student ID: ");
        int studentId = Integer.parseInt(sc.nextLine().trim());

        // The type in <> decides what each holder may contain. "new RecordHolder<>(...)"
        // lets Java fill in the type from the left-hand side (the "diamond" operator).
        RecordHolder<GradeRecord> gradeHolder = new RecordHolder<>(new GradeRecord(studentName, grade));
        RecordHolder<EnrollmentRecord> enrollmentHolder =
                new RecordHolder<>(new EnrollmentRecord(courseName, studentId));

        System.out.println();

        // getRecord() returns a GradeRecord directly - no cast needed, because the compiler
        // knows that T is GradeRecord for this holder.
        GradeRecord gradeRecord = gradeHolder.getRecord();
        System.out.println("getRecord() returned the grade record of " + gradeRecord.getStudentName());
        gradeHolder.displayRecordInfo();

        EnrollmentRecord enrollmentRecord = enrollmentHolder.getRecord();
        System.out.println("getRecord() returned the enrollment record for " + enrollmentRecord.getCourseName());
        enrollmentHolder.displayRecordInfo();

        sc.close();
    }
}

// Every academic record must be able to describe itself.
interface AcademicRecord {
    void displayRecordInfo();
}

class RecordHolder<T extends AcademicRecord> {
    private T record;

    RecordHolder(T record) {
        this.record = record;
    }

    T getRecord() {
        return record;
    }

    // What gets printed depends on the actual record type: GradeRecord and
    // EnrollmentRecord each provide their own displayRecordInfo().
    void displayRecordInfo() {
        record.displayRecordInfo();
    }
}

class GradeRecord implements AcademicRecord {
    // Double and Integer (wrapper classes) as the question specifies. Java converts
    // between double and Double automatically ("autoboxing").
    private String studentName;
    private Double grade;

    GradeRecord(String studentName, Double grade) {
        this.studentName = studentName;
        this.grade = grade;
    }

    String getStudentName() {
        return studentName;
    }

    @Override
    public void displayRecordInfo() {
        System.out.println("Grade for " + studentName + ": " + grade);
    }
}

class EnrollmentRecord implements AcademicRecord {
    private String courseName;
    private Integer studentId;

    EnrollmentRecord(String courseName, Integer studentId) {
        this.courseName = courseName;
        this.studentId = studentId;
    }

    String getCourseName() {
        return courseName;
    }

    @Override
    public void displayRecordInfo() {
        System.out.println("Student " + studentId + " is enrolled in " + courseName);
    }
}
