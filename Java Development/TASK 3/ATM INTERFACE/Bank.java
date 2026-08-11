import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String, Account> accounts = new HashMap<>();

    public Bank() {
        // Seed some accounts: userId, pin, initialBalance
        addAccount(new Account("user1", "1234", 1000.00));
        addAccount(new Account("user2", "2222", 500.00));
        addAccount(new Account("alice", "0000", 1200.00));
    }

    public void addAccount(Account acct) {
        accounts.put(acct.getUserId(), acct);
    }

    public Account getAccount(String userId) {
        return accounts.get(userId);
    }

    public boolean accountExists(String userId) {
        return accounts.containsKey(userId);
    }
}
