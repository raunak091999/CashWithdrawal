import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ATMWithdrawal {
    private double balance;
    private Lock lock;

    Map<Integer, Integer> notes = new ConcurrentHashMap<Integer, Integer>();

    public ATMWithdrawal() {
        lock = new ReentrantLock();
        notes.put(2000,20);
        notes.put(1000,20);
        notes.put(500,20);
        notes.put(200,20);
        notes.put(100,20);
        notes.put(20,20);
        notes.put(10,20);
    }


    public void withdraw(int amount, int balance) {
        lock.lock();
        try {
            if(amount%10!=0)
            {
                System.out.println("Please input amount in multiple of 10");
            }
            else if (balance >= amount) {

                int demand=amount;
                int[] notes = new int[]{ 2000, 500, 200, 100, 50, 20, 10 };
                int[] noteCounter = new int[7];
                Iterator<Map.Entry<Integer, Integer>> iterator = notes.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<Integer, Integer> entry = iterator.next();
                    System.out.println("Note: " + entry.getKey() + ", Quantity: " + entry.getValue());
                }
                // count notes using Greedy approach
                for (int i = 0; i < 7; i++) {
                    if (amount >= notes[i]) {
                        noteCounter[i] = amount / notes[i];
                        amount = amount % notes[i];
                    }
                }

                // Print notes
                System.out.println("Currency Count ->");
                for (int i = 0; i < 7; i++) {
                    if (noteCounter[i] != 0) {
                        System.out.println(notes[i] + " : "
                                + noteCounter[i]);
                    }
                }


                balance -= amount;
                System.out.println("Withdrawal: " + demand+ " successfull");

            } else {
                System.out.println("Try to Withdraw: " + amount);
                System.out.println("Insufficient funds. Withdrawal cancelled.");
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ATMWithdrawal account = new ATMWithdrawal();

        Thread withdrawalThread1 = new Thread(() -> account.withdraw(150,200));
        Thread withdrawalThread2 = new Thread(() -> account.withdraw(1200,5000));

        withdrawalThread1.start();
        withdrawalThread2.start();
    }
}
