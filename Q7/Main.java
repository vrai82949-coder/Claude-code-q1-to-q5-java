/*
 * Q7 - Synchronizing Producer and Consumer Threads with a Shared Message Buffer
 *
 * MessageBuffer holds ONE message. Two threads share it:
 *   Producer: puts "Message 1", "Message 2", ... and WAITS while the buffer is full.
 *   Consumer: takes each message out and WAITS while the buffer is empty.
 * wait() and notify() let each thread sleep until the other one has done its part,
 * so a message is never read twice, lost, or overwritten.
 */
public class Main {

    // How many messages the producer generates (and the consumer reads).
    static final int MESSAGE_COUNT = 5;

    public static void main(String[] args) throws InterruptedException {
        MessageBuffer buffer = new MessageBuffer();

        // Both threads get the SAME buffer object; that is what makes it shared.
        Thread producer = new Thread(new Producer(buffer, MESSAGE_COUNT), "Producer");
        Thread consumer = new Thread(new Consumer(buffer, MESSAGE_COUNT), "Consumer");

        // start() runs each run() method on its own new thread, at the same time.
        producer.start();
        consumer.start();

        // join() makes main wait until both threads have finished.
        producer.join();
        consumer.join();
        System.out.println("All messages have been produced and consumed.");
    }
}

class MessageBuffer {
    private String message;
    private boolean empty = true;

    public void put(String newMessage) throws InterruptedException {
        // Only one thread at a time can be inside a block synchronized on the same object.
        synchronized (this) {
            // "while", not "if": after waking up, check the condition again. Java allows
            // a thread to wake up without being notified (a "spurious wakeup").
            while (!empty) {
                wait();     // releases the lock and sleeps until notify() is called
            }
            message = newMessage;
            empty = false;
            notify();       // wake the consumer: a new message is available
        }
    }

    public String take() throws InterruptedException {
        synchronized (this) {
            while (empty) {
                wait();     // nothing to read yet, so sleep until the producer notifies
            }
            String taken = message;
            message = null;
            empty = true;
            notify();       // wake the producer: the buffer is empty again
            return taken;
        }
    }
}

// Implementing Runnable (instead of extending Thread) keeps "the job to do" separate
// from "the thread that runs it". The class can still extend something else if needed.
class Producer implements Runnable {
    private final MessageBuffer buffer;
    private final int count;

    Producer(MessageBuffer buffer, int count) {
        this.buffer = buffer;
        this.count = count;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= count; i++) {
                String message = "Message " + i;
                // Holding the buffer's lock while printing keeps the printed lines in the
                // real order. Without it, "Produced: Message 2" could appear on screen
                // before "Consumed: Message 1", even though it happened after.
                synchronized (buffer) {
                    buffer.put(message);
                    System.out.println("Produced: " + message);
                }
            }
        } catch (InterruptedException e) {
            // Someone asked this thread to stop: restore the interrupt flag and exit.
            Thread.currentThread().interrupt();
        }
    }
}

class Consumer implements Runnable {
    private final MessageBuffer buffer;
    private final int count;

    Consumer(MessageBuffer buffer, int count) {
        this.buffer = buffer;
        this.count = count;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= count; i++) {
                // Same reason as in Producer: print while still holding the lock.
                synchronized (buffer) {
                    String message = buffer.take();
                    System.out.println("Consumed: " + message);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
