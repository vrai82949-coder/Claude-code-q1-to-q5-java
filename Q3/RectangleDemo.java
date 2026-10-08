import java.util.Scanner;

/*
 * Q3(a) - Designing a Rectangle Class to Calculate Area and Perimeter
 *
 * The Rectangle class encapsulates the data (length, breadth) together with the
 * behaviour that uses that data (area, perimeter, display).
 */
public class RectangleDemo {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter length: ");
        int length = sc.nextInt();
        System.out.print("Enter breadth: ");
        int breadth = sc.nextInt();

        // Create an object: the constructor stores the dimensions inside it.
        Rectangle rect = new Rectangle(length, breadth);
        rect.display();

        sc.close();
    }
}

class Rectangle {
    // private = encapsulation: code outside this class cannot change the
    // dimensions directly; it has to go through the constructor.
    private int length;
    private int breadth;

    Rectangle(int length, int breadth) {
        // "this.length" is the field; plain "length" is the constructor parameter.
        this.length = length;
        this.breadth = breadth;
    }

    int calculateArea() {
        return length * breadth;
    }

    int calculatePerimeter() {
        return 2 * (length + breadth);
    }

    void display() {
        System.out.println("Area of the rectangle: " + calculateArea());
        System.out.println("Perimeter of the rectangle: " + calculatePerimeter());
    }
}
