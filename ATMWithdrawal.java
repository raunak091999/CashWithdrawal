import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class ATMWithdrawal {
    private final Logger logger = Logger.getLogger(ATMWithdrawal.class.getName());
    private double balance;
    private Lock lock;

    Map<Integer, Integer> notes = new ConcurrentHashMap<Integer, Integer>();
    int[] denominations = new int[]{2000, 500, 200, 100, 50, 20, 10};

    public ATMWithdrawal() {
        lock = new ReentrantLock();

        notes.put(2000, 20);
        notes.put(1000, 20);
        notes.put(500, 20);
        notes.put(200, 20);
        notes.put(100, 20);
        notes.put(50, 20);
        notes.put(20, 20);
        notes.put(10, 20);
    }

    public int validationBalance(int amount, int balance) {
        return amount % 10 != 0 ? 0 : 1;

    }

    /**
     * To withdraw minimum number of notes
     * @param amount - entered by user
     * @param balance- avialable balance
     */
    public int withdraw(int amount, int balance) {
        lock.lock();
        try {
            if (validationBalance(amount, balance) == 0) {
                logger.severe("Please input amount in multiple of 10");
            } else if (balance >= amount) {
                for (int denomination : denominations) {
                    if (amount >= denomination) {
                        int leftNotes = notes.get(denomination);
                        int numberOfNotes = amount / notes.get(denomination);
                        notes.replace(denomination, leftNotes - numberOfNotes);
                        amount = amount % denomination;
                    }
                }
            }

        } finally {
            lock.unlock();
        }
        if(amount==0)
            return 1;
        else
            return 0;
    }

    public static void main(String[] args) {
        ATMWithdrawal account = new ATMWithdrawal();

        Thread withdrawalThread1 = new Thread(() -> account.withdraw(150, 200));
        Thread withdrawalThread2 = new Thread(() -> account.withdraw(1220,5000));

        withdrawalThread1.start();
        withdrawalThread2.start();
    }
}
