import java.util.logging.Logger;

public class main {
    private static final Logger logger = Logger.getLogger(main.class.getName());
    private static int success;
    public static void main(String[] args) {


        ATMWithdrawal account = new ATMWithdrawal();

        Thread withdrawalThread1 = new Thread(() ->
        {
            success= (account.withdraw(150, 200));
            if(success==0)
                logger.severe("There is some issue. Try again");
            else
                logger.fine("Amount withdrwan successfully");
        });
        Thread withdrawalThread2 = new Thread(() -> {
             success=account.withdraw(1220,5000);
            if(success==0)
                logger.severe("There is some issue. Try again");
            else
                logger.fine("Amount withdrwan successfully");
        });

        withdrawalThread1.start();
        withdrawalThread2.start();
    }
}
