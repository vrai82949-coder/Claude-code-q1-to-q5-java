# BACSE102 – Problem Solving using Java: Experiments 1–10

Solutions to experiments **1 to 10** of the BACSE102 lab syllabus. Some experiments have
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
| 6 | a | Grade validation with exception handling | [`Q6/GradeValidator.java`](Q6/GradeValidator.java) |
| 6 | b | Time validation with custom exceptions | [`Q6/TimeValidator.java`](Q6/TimeValidator.java) |
| 7 | – | Producer–consumer threads (`wait`/`notify`) | [`Q7/Main.java`](Q7/Main.java) |
| 8 | – | 10% tax on prices via `prices.txt` → `tax.txt` | [`Q8/TaxCalculator.java`](Q8/TaxCalculator.java) |
| 9 | – | Sentiment analysis via `input.txt` → `output.txt` | [`Q9/SentimentAnalysis.java`](Q9/SentimentAnalysis.java) |
| 10 | – | km/h → m/s via `data.txt` → `converted.txt` | [`Q10/SpeedConverter.java`](Q10/SpeedConverter.java) |

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

Q8–Q10 create their `.txt` files in the folder you run the command from.

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

## Q6(a) – Validating Student Grade Input (Exception Handling)

**What it does:** reads a student's name and grade. If the grade is a whole number from 0 to
100, it prints it. Otherwise it catches the exception and prints Java's message for it.

| Input | What goes wrong | Exception | Thrown by |
|---|---|---|---|
| `abc`, `85.5` | not an integer | `NumberFormatException` | `Integer.parseInt()` |
| `120`, `-5` | outside 0–100 | `IllegalArgumentException` | `validateGrade()` (our `throw`) |

**Why it is written this way:**
- **The grade is read with `nextLine()` and converted with `Integer.parseInt()`.** The
  question names `NumberFormatException`, and that is what `parseInt` throws for bad text.
  `sc.nextInt()` would throw a different exception (`InputMismatchException`).
- **`validateGrade()` uses `throw`.** `throw new IllegalArgumentException("...")` stops the
  method at once and jumps straight to the matching `catch`. The line that prints the grade
  is skipped.
- **Why not simply `catch (NumberFormatException | IllegalArgumentException e)`?**
  That exact multi-catch **does not compile**. `NumberFormatException` is a *subclass* of
  `IllegalArgumentException`, and Java does not allow a multi-catch whose types are parent
  and child:

  ```
  error: Alternatives in a multi-catch statement cannot be related by subclassing
  ```

- **The multi-catch this program uses instead:**
  `catch (NumberFormatException | NoSuchElementException e)`.
  - `NoSuchElementException` is what `nextLine()` throws when the input ends before a line
    is typed (Ctrl+D, or an empty input file).
  - It is unrelated to `NumberFormatException`, so Java allows the two together. Both mean
    "there is no usable grade", so one block handles both.
  - `IllegalArgumentException` gets its own `catch` **after** that block. A parent class must
    be caught after its subclass. If it came first, it would also catch every
    `NumberFormatException`, the later block could never run, and the compiler would reject
    that too (`exception NumberFormatException has already been caught`).
- **`e.getMessage()`** prints the message stored in the exception. For `parseInt` that is
  Java's own text (`For input string: "abc"`). For our `throw` it is the text we passed in.
  `e.getClass().getSimpleName()` prints the name of the exception that was actually thrown.

```
Enter student name: Ravi
Enter grade: abc
NumberFormatException caught: For input string: "abc"
```
```
Enter student name: Ravi
Enter grade: 120
IllegalArgumentException caught: Grade must be between 0 and 100, but was 120
```

## Q6(b) – Custom Exceptions for Time Validation

**What it does:** reads hours, minutes and seconds (24-hour clock) and throws
`InvalidHourException`, `InvalidMinuteException` or `InvalidSecondException` for the first
part that is out of range. If all three are valid, it prints `Correct Time - h:m:s`.

| Part | Valid range | Exception if invalid |
|---|---|---|
| hours | 0–23 | `InvalidHourException` |
| minutes | 0–59 | `InvalidMinuteException` |
| seconds | 0–59 | `InvalidSecondException` |

**Key ideas:**
- **A custom exception is just a class that `extends Exception`.** Its constructor passes
  the message to the parent with `super(message)`, so `getMessage()` returns it later.
- **Checked exceptions.** These extend `Exception`, not `RuntimeException`, so they are
  *checked*. The compiler applies the *catch-or-declare* rule. `validateTime()` throws them,
  so it must declare them with `throws`. `main` calls it, so `main` must either catch them or
  declare them too. Here `main` catches them, so an invalid time prints a message instead of
  crashing with a stack trace.
- **This multi-catch *is* legal:**
  `catch (InvalidHourException | InvalidMinuteException | InvalidSecondException e)`. All
  three are siblings (each extends `Exception` directly, none extends another), so Java
  allows them in one block. Compare this with Q6(a), where the parent/child pair
  `NumberFormatException | IllegalArgumentException` is rejected.
- **Typing letters instead of numbers** makes `nextInt()` throw `InputMismatchException`. A
  number too big for an `int` does the same. This exception gets its own `catch`, so the
  program prints a message instead of crashing.
- `serialVersionUID` is there only because every `Exception` is `Serializable`. Without it,
  `javac -Xlint` prints a warning. It has nothing to do with the time logic.

```
Enter hours: 12
Enter minutes: 30
Enter seconds: 45
Correct Time - 12:30:45
```
```
Enter hours: 25
Enter minutes: 30
Enter seconds: 45
Invalid hours: 25 (must be between 0 and 23)
```

## Q7 – Producer and Consumer Threads with a Shared Buffer

**What it does:** a `Producer` thread puts `Message 1` … `Message 5` into a one-slot
`MessageBuffer`, and a `Consumer` thread takes each one out and prints it. `wait()` and
`notify()` make the two threads take turns.

```
 Producer ──put()──►  [ MessageBuffer: 1 slot ]  ──take()──► Consumer
   waits while FULL                                  waits while EMPTY
```

**How the synchronization works:**
- **`synchronized (this)`.** Only one thread at a time can *hold* an object's lock, so only
  one thread at a time runs code in blocks synchronized on that object. The producer and
  consumer can never change `message` and `empty` at the same moment.
- **`wait()`.** If the producer finds the buffer full, it calls `wait()`. That *releases the
  lock* and puts the thread to sleep. Releasing the lock matters: otherwise the consumer could
  never get in to empty the buffer. The consumer does the same when the buffer is empty.
- **`notify()`** wakes the thread that is waiting on the same object. After `put()` it wakes
  the consumer ("a message is ready"). After `take()` it wakes the producer ("the slot is
  free"). With exactly two threads, `notify()` is enough. With several producers or
  consumers you would use `notifyAll()`.
- **Why `while (...) wait();` and not `if`.** Java allows a thread to wake up without being
  notified (a *spurious wakeup*). The `while` re-checks the condition after every wake-up.
  If the buffer still isn't ready, the thread simply waits again.
- **Why the Producer and Consumer also print inside `synchronized (buffer)`.** Holding the
  lock while printing keeps the printed lines in the real order. Without it,
  `Produced: Message 2` could appear before `Consumed: Message 1`. This works because Java
  locks are *re-entrant*: a thread that holds a lock can enter another block on the same
  object. `wait()` releases the lock completely, so the other thread can still get in.
- **`implements Runnable`** keeps the job (`run()`) separate from the thread that runs it.
  `new Thread(...).start()` starts the job on a new thread.
- **`join()`** makes `main` wait for both threads before printing the final line.
- **`InterruptedException`.** `wait()` can be interrupted. The threads catch it, restore the
  interrupt flag with `Thread.currentThread().interrupt()`, and stop cleanly.

```
Produced: Message 1
Consumed: Message 1
Produced: Message 2
Consumed: Message 2
Produced: Message 3
Consumed: Message 3
Produced: Message 4
Consumed: Message 4
Produced: Message 5
Consumed: Message 5
All messages have been produced and consumed.
```

## Q8 – Final Prices with 10% Tax (File Input and Output)

**What it does:** reads N prices, writes them to `prices.txt`, reads them back from that
file, adds 10% tax, writes the results to `tax.txt` and prints them with 2 decimals.

```
keyboard ──► prices.txt ──► read back ──► price × 1.10 ──► tax.txt ──► screen
```

**Why it is written this way:**
- **`PrintWriter(new FileWriter("prices.txt"))`** creates (or overwrites) the file and lets
  you use the familiar `println`.
- **`Scanner(new File("prices.txt"))`** reads the numbers back. The loop uses
  `hasNextDouble()`, so it processes whatever is in the file without depending on N again.
- **try-with-resources** (`try (PrintWriter w = ...) { }`) closes the file automatically,
  even if an error happens. Closing also *flushes* the writer. Without it, the data can sit
  in memory and `prices.txt` would be empty when read back.
- **`IOException` is a checked exception.** Opening a file can fail (no permission, a missing
  folder, a folder with the same name). So `new FileWriter(...)` and
  `new Scanner(new File(...))` can throw it, and Java forces the program to handle it. The
  `catch` prints a clear message.
- **`PrintWriter` never throws `IOException`, even while writing.** If a write fails (for
  example, the disk is full), it only remembers the error. You can check for that with
  `writer.checkError()`. This program does not call it, so a failed write would go
  unnoticed. This is fine for a lab exercise, but worth knowing.
- **Final price = `price × (1 + 0.10)`**, i.e. the price plus 10% of the price. It is
  formatted once with `String.format("%.2f")`, and the same text goes to `tax.txt` and the
  screen.
- **No prompts:** the question gives an exact output format.

```
Input:                 Output:
3                      110.00 220.00 330.00
100.0 200.0 300.0
```

## Q9 – Basic Sentiment Analysis (File Input and Output)

**What it does:** writes the entered sentence to `input.txt`, reads it back, classifies it
as Positive, Negative or Neutral, writes the result to `output.txt` and displays it.

**How it classifies:**
1. Convert to lower case, so `Happy`, `HAPPY` and `happy` all match.
2. Split into words on anything that is not an English letter a–z (`split("[^a-z]+")`).
   This removes punctuation, so `good!` still matches `good`, and `unhappy` stays one word
   that does *not* match `happy`.
3. Count the positive keywords (happy, good, excellent, positive) and the negative keywords
   (sad, bad, terrible, negative).
4. More positive → **Positive**. More negative → **Negative**. No keywords, or a tie →
   **Neutral**.

**Why:**
- **Counting instead of "first keyword found."** The question doesn't say what to do when a
  sentence has both kinds. Counting treats both fairly: `Good food but bad service` is a
  tie, so it is Neutral.
- **`equals()`, not `==`, to compare Strings.** `==` checks whether two variables point to
  the *same object*. `equals()` checks whether the *text* is the same.
- **`BufferedReader.readLine()`** reads one whole line from the file, spaces included.
- Same file-handling pattern as Q8: try-with-resources plus a `catch (IOException e)`.

```
Enter a sentence: I am so happy today, this is a good day!
Sentiment: Positive
```

## Q10 – Converting km/h to m/s (File Input and Output)

**What it does:** writes the entered speed to `data.txt`, reads it back, converts it to m/s,
writes the result to `converted.txt` and displays it.

**The formula:** 1 km = 1000 m and 1 hour = 3600 s, so

```
m/s = km/h × 1000 / 3600     (the same as km/h × 5/18)
72 km/h  →  72 × 1000 / 3600  =  20.00 m/s
```

**Why:**
- **`double`**, because speeds such as 27.78 m/s are not whole numbers.
- **The speed is read back from `data.txt`** before converting, as the question requires,
  rather than reusing the variable from the keyboard.
- **The conversion lives in its own method, `kmphToMps()`**, so the formula sits in one
  clearly named place.
- `data.txt` stores the number as Java writes it (`72` becomes `72.0`).

```
Enter speed in km/h: 72
Speed read from data.txt: 72.0 km/h
Converted speed: 20.00 m/s
```
