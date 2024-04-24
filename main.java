public class main {
    public static void main(String[] args) {
        ATMWithdrawal account = new ATMWithdrawal();

        Thread withdrawalThread1 = new Thread(() -> account.withdraw(150, 200));
        Thread withdrawalThread2 = new Thread(() -> account.withdraw(1220,5000));

        withdrawalThread1.start();
        withdrawalThread2.start();
    }
}
