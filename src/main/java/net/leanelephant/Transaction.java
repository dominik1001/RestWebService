package net.leanelephant;

public class Transaction {

    private long id;

    private double amount;

    private String type;

    private Transaction parent;

    public Transaction(long id, double amount, String type, Transaction parent) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.parent = parent;
    }

    public Transaction(long id, double amount, String type) {
        this(id, amount, type, null);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Transaction getParent() {
        return parent;
    }

    public void setParent(Transaction parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                ", parent=" + parent +
                '}';
    }
}
