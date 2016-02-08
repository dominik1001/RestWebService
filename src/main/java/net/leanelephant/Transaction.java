package net.leanelephant;

public class Transaction {

    private Long id;

    private double amount;

    private String type;

    private Transaction parent;

    private Long parentId;

    public Transaction() {

    }

    public Transaction(Long id, double amount, String type, Transaction parent) {
        this();

        this.id = id;
        this.amount = amount;
        this.type = type;
        this.parent = parent;
    }

    public Transaction(double amount, String type) {
        this(null, amount, type, null);
    }

    public Transaction(long id, double amount, String type) {
        this(id, amount, type, null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
