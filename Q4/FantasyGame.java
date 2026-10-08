import java.util.Scanner;

/*
 * Q4(b) - Designing a Fantasy Game Character System
 *
 * GameCharacter is abstract: it says every character CAN attack() and defend(),
 * but leaves HOW to each subclass. Warrior and Wizard fill in the details:
 *   Warrior attack = strength * 3
 *   Wizard  attack = magic power * 2
 */
public class FantasyGame {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Choose your character class:");
        System.out.println("1. Warrior");
        System.out.println("2. Wizard");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();

        // The variable has the parent type, so it can hold either a Warrior or a Wizard.
        GameCharacter character;

        if (choice == 1) {
            System.out.print("Enter the Warrior's strength: ");
            int strength = sc.nextInt();
            character = new Warrior(strength);
        } else if (choice == 2) {
            System.out.print("Enter the Wizard's magic power: ");
            int magicPower = sc.nextInt();
            character = new Wizard(magicPower);
        } else {
            System.out.println("Invalid choice. Please select 1 or 2.");
            sc.close();
            return;
        }

        // Runtime polymorphism: the same two calls run Warrior's or Wizard's version,
        // depending on which object was actually created above.
        character.attack();
        character.defend();

        sc.close();
    }
}

abstract class GameCharacter {
    // No body: each subclass MUST provide its own implementation, otherwise it won't compile.
    abstract void attack();

    abstract void defend();
}

class Warrior extends GameCharacter {
    private int strength;

    Warrior(int strength) {
        this.strength = strength;
    }

    @Override
    void attack() {
        int attackPower = strength * 3;
        System.out.println("Warrior swings a mighty sword! Attack power: " + attackPower);
    }

    @Override
    void defend() {
        System.out.println("Warrior raises a shield to block the attack!");
    }
}

class Wizard extends GameCharacter {
    private int magicPower;

    Wizard(int magicPower) {
        this.magicPower = magicPower;
    }

    @Override
    void attack() {
        int attackPower = magicPower * 2;
        System.out.println("Wizard casts a fireball! Attack power: " + attackPower);
    }

    @Override
    void defend() {
        System.out.println("Wizard conjures a magical barrier to defend!");
    }
}
