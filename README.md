# BACSE102 – Problem Solving using Java: Experiments 1–5

Solutions to experiments **1 to 5** of the BACSE102 lab syllabus. Each experiment has
sub-parts (a, b, c), and each sub-part is its own self-contained program.

| Q | Part | Problem | File |
|---|------|---------|------|
| 1 | a | Interest for two financing options | [`Q1/InterestCalculator.java`](Q1/InterestCalculator.java) |
| 1 | b | Sum of digits of a 3-digit number + even/odd | [`Q1/DigitSumParity.java`](Q1/DigitSumParity.java) |
| 1 | c | Prime numbers in a range (using `for`) | [`Q1/PrimeInRange.java`](Q1/PrimeInRange.java) |
| 2 | a | Total distance from merged step counts | [`Q2/StepDistance.java`](Q2/StepDistance.java) |
| 2 | b | Locate prime numbers in a 2D grid | [`Q2/PrimeGrid.java`](Q2/PrimeGrid.java) |
| 3 | a | `Rectangle` class: area & perimeter | [`Q3/RectangleDemo.java`](Q3/RectangleDemo.java) |
| 3 | b | `ArrayConcatenator` class | [`Q3/ArrayConcatenatorDemo.java`](Q3/ArrayConcatenatorDemo.java) |
| 4 | a | Employee bonuses (hierarchical inheritance) | [`Q4/EmployeeBonusDemo.java`](Q4/EmployeeBonusDemo.java) |
| 4 | b | Fantasy game characters (abstract class) | [`Q4/FantasyGame.java`](Q4/FantasyGame.java) |
| 5 | a | Washing machine (`Motor` interface) | [`Q5/WashingMachineDemo.java`](Q5/WashingMachineDemo.java) |
| 5 | b | Unique characters with `StringBuilder` | [`Q5/UniqueCharacters.java`](Q5/UniqueCharacters.java) |

## How to run

Requires Java 11 or newer. Either run the source file directly:

```bash
java Q1/InterestCalculator.java
```

or compile first and then run:

```bash
javac Q1/InterestCalculator.java
java -cp Q1 InterestCalculator
```

Every file keeps its helper classes (e.g. `Rectangle`, `Employee`) in the same file, so one
file is one complete program. The class with `main` comes first in each file, which is what
`java File.java` expects.

---

## Q1(a) – Calculating Interest for Financing Options

**What it does:** reads the principal and annual rate for two options and prints the interest
for each, using the formula from the question: `interest = principal × rate / 100`.

**Why it is written this way:**
- **`double`, not `int`.** Money and interest rates can have decimals (e.g. 6.5%). With `int`
  you couldn't type `6.5` at all, and integer division would drop fractions:
  `1050 * 7 / 100` gives `73` instead of `73.5`.
- **A separate `calculateInterest()` method.** The same formula is needed twice. Writing it
  once and calling it twice avoids copy-paste mistakes.
- **`printf("%.2f")`** rounds to 2 decimal places, the normal way to show currency.

```
Enter principal amount for Option 1: 50000
Enter annual interest rate (%) for Option 1: 8
Enter principal amount for Option 2: 75000
Enter annual interest rate (%) for Option 2: 6.5
Interest for Option 1: 4000.00
Interest for Option 2: 4875.00
```

## Q1(b) – Sum of Digits and Parity Check

**What it does:** splits a three-digit number into its digits, adds them, and says whether the
sum is even or odd.

**How the digits are extracted** (for `345`):

| Expression | Meaning | Result |
|---|---|---|
| `n / 100` | integer division drops the last two digits | `3` |
| `(n / 10) % 10` | drop the last digit (34), then keep the last one | `4` |
| `n % 10` | remainder after dividing by 10 = last digit | `5` |

**Why:**
- In Java, `/` between two `int`s throws away the fraction, and `%` gives the remainder.
  Together they can pull out any digit without converting the number to a string.
- **Even/odd:** `sum % 2 == 0` means the sum divides by 2 with nothing left over.
- **Validation:** the question says "three-digit number", so anything outside 100–999 is
  rejected. `Math.abs()` lets `-345` count as a three-digit number too.

```
Enter a three-digit number: 345
Sum of digits: 12
The sum is even.
```

## Q1(c) – Prime Numbers Within a Range

**What it does:** prints every prime between `start` and `end`, inclusive, using `for` loops
as the question requires.

**Why:**
- **Outer `for` loop:** goes through every number from `start` to `end`. `<=` makes `end`
  inclusive.
- **Inner `for` loop (in `isPrime`)** tries divisors from 2 upwards. If any divides evenly,
  the number is not prime.
- **Stopping at √n.** If `n = a × b`, one of the two factors is at most √n. If no divisor up
  to √n exists, none exists at all. This makes the check much faster: about 31,000 tries
  instead of 1,000,000,000 for a number near a billion.
- **`i <= n / i`** means the same as `i * i <= n`, but `i * i` can overflow an `int` for
  large `n`, and division cannot.
- **Edge cases:** numbers below 2 are not prime. If the user enters the range backwards
  (`50 10`), the values are swapped. If no primes are found, it prints `none`.

```
Enter start: 10
Enter end: 50
Prime numbers between 10 and 50: 11 13 17 19 23 29 31 37 41 43 47
```

## Q2(a) – Total Distance from Merged Daily Step Counts

**What it does:** combines (merges) the steps of two days into one total and multiplies by the
distance per step (1 unit).

**Why:**
- **`long` instead of `int`.** Adding two large step counts could go past `int`'s limit of
  about 2.1 billion. `long` makes that practically impossible.
- **`DISTANCE_PER_STEP` constant.** Writing `mergedSteps * DISTANCE_PER_STEP` instead of
  `* 1` explains what the number means. If the step length changes, only one line needs
  editing.
- Negative step counts make no sense, so they are rejected.

```
Enter steps taken on Day 1: 8500
Enter steps taken on Day 2: 9200
Merged step count: 17700
Total distance covered: 17700 units
```

## Q2(b) – Locating Prime Numbers in a 2D Grid

**What it does:** reads the grid size and its elements into a 2D array, then prints each prime
together with its `(row, column)` position.

**Why:**
- **`int[][] grid`** is a 2D array: `grid[i][j]` is row `i`, column `j`.
- **Nested loops:** the outer loop picks a row and the inner loop walks across that row's
  columns, so every cell is visited once.
- The same `isPrime()` logic as Q1(c) is reused.
- **0-based coordinates** match how Java numbers array positions, so `(0, 1)` means first
  row, second column.
- Rows or columns ≤ 0 are rejected, because `new int[-1][3]` would crash the program.

```
Enter number of rows: 3
Enter number of columns: 3
Enter the grid elements row by row:
4 7 10
11 15 17
20 23 1
Prime number 7 found at (0, 1)
Prime number 11 found at (1, 0)
Prime number 17 found at (1, 2)
Prime number 23 found at (2, 1)
```

## Q3(a) – Rectangle Class (Area and Perimeter)

**What it does:** defines a `Rectangle` class with `length` and `breadth`, then calculates
and prints the area (`l × b`) and perimeter (`2 × (l + b)`).

**Key OOP ideas:**
- **Class vs object.** `Rectangle` is the blueprint. `new Rectangle(10, 5)` creates one
  actual rectangle (an object) from it.
- **Encapsulation.** `length` and `breadth` are `private`, so no code outside the class can
  change them directly. The data and the methods that use it (`calculateArea`,
  `calculatePerimeter`, `display`) live together in one class.
- **Constructor.** It runs automatically when the object is created and stores the
  dimensions. `this.length = length` copies the parameter into the field. `this.` tells the
  two apart because they have the same name.

```
Enter length: 10
Enter breadth: 5
Area of the rectangle: 50
Perimeter of the rectangle: 30
```

## Q3(b) – ArrayConcatenator Class

**What it does:** reads two arrays in the exact input format from the question and prints them
joined into one array.

**Why:**
- **The concatenation happens in the constructor**, as the question asks. As soon as
  `new ArrayConcatenator(first, second)` runs, the object already holds the merged array.
- **Java arrays have a fixed size.** You cannot grow `first`, so a new array of size `N + M`
  is created. The first array is copied into positions `0 … N-1` and the second into
  positions `N … N+M-1` (that is why `result[first.length + j]` is used).
- **No input prompts here.** This question gives a strict input/output format ("the output
  prints the concatenated array"). Extra text such as "Enter N:" would make the output fail
  to match.
- **No trailing space:** a space is printed *before* every element except the first.

```
Input:            Output:
3                 1 2 3 4 5 6 7
1 2 3
4
4 5 6 7
```

## Q4(a) – Employee Bonuses (Hierarchical Inheritance)

**What it does:** calculates the final income of a Developer (10% bonus) and a Designer
(5% bonus) with the formula from the question:
`final income = baseSalary + (baseSalary × bonusPercentage × workHours / 100)`.

```
        Employee          ← has baseSalary
        /       \
  Developer   Designer    ← each adds bonusPercentage + workHours
```

**Key OOP ideas:**
- **Hierarchical inheritance** means one parent with several children. `baseSalary` is
  written once in `Employee`, and both subclasses inherit it with `extends Employee`.
- **`super(baseSalary)`** calls the parent's constructor so `Employee` can set up its own
  field. A child constructor must do this first.
- **`protected`** lets the child classes read `baseSalary` while still hiding it from
  unrelated classes.
- **`final`** on `bonusPercentage` makes the rate fixed. It cannot be changed by accident.

Worked example: Developer, salary 50000, 8 hours → `50000 + 50000×10×8/100 = 90000`.

```
Enter base salary of the developer: 50000
Enter work hours of the developer: 8
Enter base salary of the designer: 40000
Enter work hours of the designer: 6
Developer's final income: 90000.00
Designer's final income: 52000.00
```

## Q4(b) – Fantasy Game Character System (Abstract Class)

**What it does:** the player picks Warrior or Wizard and enters strength or magic power. The
program then shows that character's attack (Warrior: strength × 3, Wizard: power × 2) and
defence.

**Key OOP ideas:**
- **Abstract class.** `GameCharacter` declares `attack()` and `defend()` with no body. You
  cannot create a plain `GameCharacter`, because it is only an idea of "a character". Every
  subclass is *forced* to write its own version, or the code will not compile.
- **`@Override`** asks the compiler to check that a method really replaces a parent method.
  A typo such as `atack()` would then be caught as an error.
- **Runtime polymorphism.** The variable is declared as `GameCharacter character`, but it
  holds a `Warrior` or a `Wizard`. `character.attack()` runs the right version, chosen when
  the program runs. `main` never needs an `if` to decide which attack to call.
- The question gives no formula for `defend()`, so each class prints its own defence
  message.

```
Choose your character class:
1. Warrior
2. Wizard
Enter your choice: 1
Enter the Warrior's strength: 10
Warrior swings a mighty sword! Attack power: 30
Warrior raises a shield to block the attack!
```

## Q5(a) – Washing Machine Control System (Interface)

**What it does:** the `Motor` interface declares `run()` and `consume(double capacity)`.
`WashingMachine` implements them and also has a no-argument `consume()` that prints a message.
Consumption in kWh = `capacity × 0.05`.

**Key OOP ideas:**
- **Interface = contract.** `Motor` lists *what* a motor must do, not *how*. By writing
  `implements Motor`, `WashingMachine` promises to provide both methods. The compiler
  rejects the class if it doesn't.
- **Why `public` on the methods?** Interface methods are automatically `public`. An
  implementation may not reduce that visibility, so leaving out `public` is a compile error.
- **Overloading vs overriding.** The question asks for both `consume()` and
  `consume(double capacity)`:
  - `consume(double)` **overrides** (implements) the interface method, so it has
    `@Override`.
  - `consume()` **overloads** it: same name, different parameters. It is not in `Motor`, so
    it has no `@Override`.
- Because `consume()` exists only in `WashingMachine`, the variable is declared as
  `WashingMachine`, not `Motor`. A `Motor` variable could not call it.

```
Enter the capacity of the washing machine: 7
Washing machine is running.
Washing machine is consuming electricity.
Electricity consumption: 0.35 kWh
```

## Q5(b) – Unique Character Extraction (StringBuilder)

**What it does:** builds a new string that keeps only the first occurrence of each character,
e.g. `programming → progamin`.

**Why:**
- **Why `StringBuilder`?** A Java `String` is *immutable* (it never changes), so
  `result = result + ch` inside a loop creates a brand-new `String` every time. A
  `StringBuilder` is changeable: `append()` adds to the same object, which is much cheaper.
  This is the "efficiently use StringBuilder" part of the question. `StringBuilder` is in
  `java.lang`, so it needs no import.
- **How duplicates are skipped:** for each character, `unique.indexOf(...)` checks whether
  it is already in the result. `-1` means "not found", so it is appended. Anything else
  means it is a duplicate and is skipped.
- **`nextLine()` instead of `next()`** so that input containing spaces, like
  `hello world`, is read completely.
- The check is case-sensitive (`A` and `a` are different), and spaces count as characters.

```
Enter a string: programming
String with unique characters: progamin
```
