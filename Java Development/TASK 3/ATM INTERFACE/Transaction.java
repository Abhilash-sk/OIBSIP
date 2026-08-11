import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    public enum Type { WITHDRAW, DEPOSIT, TRANSFER_OUT, TRANSFER_IN }
    private Type type;
    private double amount;
    private String details;
    private LocalDateTime timestamp;

    public Transaction(Type type, double amount, String details) {
        this.type = type;
        this.amount = amount;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[%s] %s: $%.2f %s", timestamp.format(f), type, amount, details == null ? "" : ("(" + details + ")"));
    }
}
