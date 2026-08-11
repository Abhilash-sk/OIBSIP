import java.util.List;
import java.util.Scanner;

public class ATM {
    private Bank bank;
    private Scanner sc = new Scanner(System.in);
    private Account currentAccount;

    public ATM(Bank bank) {
        this.bank = bank;
    }

    public void start() {
        if (!authenticate()) {
            System.out.println("Too many failed attempts. Access denied.");
            return;
        }
        boolean running = true;
        while (running) {
            showMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": showTransactionHistory(); break;
                case "2": withdraw(); break;
                case "3": deposit(); break;
                case "4": transfer(); break;
                case "5": running = false; System.out.println("Goodbye!"); break;
                default: System.out.println("Invalid option. Try again.");
            }
        }
    }

    private boolean authenticate() {
        System.out.println("Welcome to Console ATM");
        int attempts = 0;
        while (attempts < 3) {
            System.out.print("Enter User ID: ");
            String userId = sc.nextLine().trim();
            System.out.print("Enter PIN: ");
            String pin = sc.nextLine().trim();
            Account acct = bank.getAccount(userId);
            if (acct != null && acct.checkPin(pin)) {
                currentAccount = acct;
                System.out.println("Login successful.");
                return true;
            } else {
                attempts++;
                System.out.println("Invalid credentials. Attempts left: " + (3 - attempts));
            }
        }
        return false;
    }

    private void showMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Transaction History");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer");
        System.out.println("5. Quit");
        System.out.print("Choose an option: ");
    }

    private void showTransactionHistory() {
        List<Transaction> txs = currentAccount.getTransactions();
        if (txs.isEmpty()) {
            System.out.println("No transactions this session.");
        } else {
            System.out.println("Transaction History:");
            txs.forEach(t -> System.out.println(t.toString()));
        }
        System.out.printf("Current Balance: $%.2f%n", currentAccount.getBalance());
    }

    private void withdraw() {
        System.out.printf("Current Balance: $%.2f%n", currentAccount.getBalance());
        System.out.print("Enter amount to withdraw: ");
        double amt = readPositiveDouble();
        if (amt <= 0) { System.out.println("Invalid amount."); return; }
        if (!currentAccount.withdraw(amt)) {
            System.out.println("Insufficient Funds");
        } else {
            System.out.printf("Withdrawn $%.2f. New balance: $%.2f%n", amt, currentAccount.getBalance());
        }
    }

    private void deposit() {
        System.out.print("Enter amount to deposit: ");
        double amt = readPositiveDouble();
        if (amt <= 0) { System.out.println("Invalid amount."); return; }
        currentAccount.deposit(amt);
        System.out.printf("Deposited $%.2f. New balance: $%.2f%n", amt, currentAccount.getBalance());
    }

    private void transfer() {
        System.out.printf("Current Balance: $%.2f%n", currentAccount.getBalance());
        System.out.print("Enter recipient account ID: ");
        String toId = sc.nextLine().trim();
        if (!bank.accountExists(toId)) {
            System.out.println("Recipient account not found.");
            return;
        }
        if (toId.equals(currentAccount.getUserId())) {
            System.out.println("Cannot transfer to the same account.");
            return;
        }
        System.out.print("Enter amount to transfer: ");
        double amt = readPositiveDouble();
        if (amt <= 0) { System.out.println("Invalid amount."); return; }
        if (!currentAccount.transferOut(amt, toId)) {
            System.out.println("Insufficient Funds");
            return;
        }
        Account recipient = bank.getAccount(toId);
        recipient.transferIn(amt, currentAccount.getUserId());
        System.out.printf("Transferred $%.2f to %s. New balance: $%.2f%n", amt, toId, currentAccount.getBalance());
    }

    private double readPositiveDouble() {
        try {
            String line = sc.nextLine().trim();
            return Double.parseDouble(line);
        } catch (Exception e) {
            return -1;
        }
    }
}
