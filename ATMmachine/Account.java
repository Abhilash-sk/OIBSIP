import java.util.ArrayList;
import java.util.List;

public class Account {
    private String userId;
    private String pin;
    private double balance;
    private List<Transaction> transactions = new ArrayList<>();

    public Account(String userId, String pin, double initialBalance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = initialBalance;
    }

    public String getUserId() { return userId; }
    public boolean checkPin(String pin) { return this.pin.equals(pin); }
    public double getBalance() { return balance; }

    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction(Transaction.Type.DEPOSIT, amount, null));
    }

    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) return false;
        balance -= amount;
        transactions.add(new Transaction(Transaction.Type.WITHDRAW, amount, null));
        return true;
    }

    public boolean transferOut(double amount, String toAccountId) {
        if (amount <= 0 || amount > balance) return false;
        balance -= amount;
        transactions.add(new Transaction(Transaction.Type.TRANSFER_OUT, amount, "to " + toAccountId));
        return true;
    }

    public void transferIn(double amount, String fromAccountId) {
        balance += amount;
        transactions.add(new Transaction(Transaction.Type.TRANSFER_IN, amount, "from " + fromAccountId));
    }

    public List<Transaction> getTransactions() { return transactions; }
}
